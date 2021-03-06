/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.integration.utils;

import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.BUProblem;
import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STree;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.*;
import org.jlato.tree.expr.LiteralExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QualifiedName;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A normalized JSON writer. We write commas (',') even for the last name/value pairs and array elements. This is to
 * better support GIT diffs.
 *
 * @author Didier Villevalois
 */
public class NormalizedJsonWriter {

	public static final String INDENT_STRING = "  ";
	public static final Comparator<Method> METHOD_COMPARATOR = new Comparator<Method>() {
		public int compare(Method m1, Method m2) {
			return m1.getName().compareTo(m2.getName());
		}
	};
	private final StringBuilder builder;

	public NormalizedJsonWriter(StringBuilder builder) {
		this.builder = builder;
	}

	public static String write(Tree tree) {
		StringBuilder builder = new StringBuilder();
		NormalizedJsonWriter writer = new NormalizedJsonWriter(builder);
		writer.writeTree(tree);
		return builder.toString();
	}

	public void writeTree(Tree tree) {
		writeTree(tree, 0);
		builder.append("\n");
	}

	private void writeTree(Tree tree, int indent) {
		Class<? extends Tree> treeClass = tree.getClass();

		builder.append("{\n");

		BUTree<STree> buTree = TDTree.treeOf(tree);

		final SNode state = (SNode) buTree.state;
		writeField("kind", String.class, state.kind(), indent + 1);

		Vector<BUProblem> problems = buTree.problems();
		if (!problems.isEmpty()) writeProblems(problems, indent + 1);

		writeProperties(tree, treeClass, indent + 1);

		writeIndent(indent);
		builder.append("}");
	}

	private void writeProblems(Vector<BUProblem> problems, int indent) {
		writeIndent(indent);
		builder.append("problems: ");
		builder.append("[\n");
		for (BUProblem problem : problems) {
			writeField("severity", Problem.Severity.class, problem.severity(), indent + 1);
			writeField("code", String.class, problem.code(), indent + 1);
		}
		writeIndent(indent);
		builder.append("]");
		builder.append(",\n");
	}

	private void writeProperties(Tree tree, Class<? extends Tree> treeClass, int indent) {
		final List<Method> methods = new ArrayList<Method>();
		collectPropertyMethods(tree, treeClass, methods);
		Collections.sort(methods, METHOD_COMPARATOR);

		for (Method method : methods) {
			String name = method.getName();
			Class<?> type = method.getReturnType();

			try {
				method.setAccessible(true);
				writeField(name, type, method.invoke(tree), indent);
			} catch (IllegalAccessException e) {
				e.printStackTrace(); // FIXME
			} catch (InvocationTargetException e) {
				e.printStackTrace(); // FIXME
			}
		}
	}

	private void collectPropertyMethods(Tree tree, Class<? extends Tree> treeClass, List<Method> methods) {
		Class<?> superclass = treeClass.getSuperclass();
		if (Tree.class.isAssignableFrom(superclass) &&
				!TDTree.class.equals(superclass)) {
			collectPropertyMethods(tree, superclass.asSubclass(Tree.class), methods);
		}

		for (Method method : treeClass.getDeclaredMethods()) {
			if (Modifier.isStatic(method.getModifiers())) continue;
			if (method.getParameterTypes().length != 0) continue;

			String name = method.getName();

			if (name.equals("toString") || name.equals("kind") || name.startsWith("with")) continue;
			if (LiteralExpr.class.isAssignableFrom(treeClass) && name.equals("value")) continue;

			methods.add(method);
		}
	}

	private void writeField(String fieldName, Class<?> valueClass, Object value, int indent) {
		writeIndent(indent);
		builder.append(fieldName);
		builder.append(": ");
		writeValue(valueClass, value, indent);
		builder.append(",\n");
	}

	private void writeValue(Class<?> valueClass, Object value, int indent) {
		if (value == null) {
			builder.append("null");
		} else {
			if (valueClass.isPrimitive() ||
					String.class.isAssignableFrom(valueClass) ||
					Enum.class.isAssignableFrom(valueClass) ||
					Name.class.isAssignableFrom(valueClass) ||
					QualifiedName.class.isAssignableFrom(valueClass)) {
				builder.append(value.toString());
			} else if (Class.class.isAssignableFrom(valueClass)) {
				builder.append(((Class<?>) value).getName());
			} else if (NodeList.class.isAssignableFrom(valueClass)) {
				NodeList<?> list = (NodeList<?>) value;
				Class<?> elementClass = list.isEmpty() ? Object.class : list.get(0).getClass();
				writeArray(elementClass, list, indent);
			} else if (NodeOption.class.isAssignableFrom(valueClass)) {
				NodeOption<?> option = (NodeOption<?>) value;
				if (option.isNone()) {
					builder.append("null");
				} else {
					Tree tree = option.get();
					writeValue(tree.getClass(), tree, indent);
				}
			} else if (NodeEither.class.isAssignableFrom(valueClass)) {
				NodeEither<?, ?> option = (NodeEither<?, ?>) value;
				Tree tree = option.isLeft() ? option.left() : option.right();
				writeValue(tree.getClass(), tree, indent);
			} else if (Tree.class.isAssignableFrom(valueClass)) {
				writeTree((Tree) value, indent);
			}
		}
	}

	private void writeArray(Class<?> valueClass, Iterable<? extends Object> values, int indent) {
		builder.append("[\n");

		for (Object value : values) {
			writeIndent(indent + 1);
			writeValue(valueClass, value, indent + 1);
			builder.append(",\n");
		}

		writeIndent(indent);
		builder.append("]");
	}

	private void writeIndent(int indent) {
		for (int i = 0; i < indent; i++) {
			builder.append(INDENT_STRING);
		}
	}
}
