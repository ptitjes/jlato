/*
 * Copyright (C) 2015 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.integration.utils;

import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QName;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

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
		String className = treeClass.getName();

		builder.append("{\n");

		writeField("class", String.class, className, indent + 1);

		writeProperties(tree, treeClass, indent + 1);

		writeIndent(indent);
		builder.append("}");
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
		if (Tree.class.isAssignableFrom(superclass)) {
			collectPropertyMethods(tree, superclass.asSubclass(Tree.class), methods);
		}

		for (Method method : treeClass.getDeclaredMethods()) {
			if (Modifier.isStatic(method.getModifiers())) continue;
			if (method.getParameterTypes().length != 0) continue;

			String name = method.getName();

			if (name.equals("parent") ||
					name.equals("root") ||
					name.equals("toString")) continue;

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
					QName.class.isAssignableFrom(valueClass)) {
				builder.append(value.toString());
			} else if (NodeList.class.isAssignableFrom(valueClass)) {
				NodeList<?> list = (NodeList<?>) value;
				Class<?> elementClass = list.isEmpty() ? Object.class : list.get(0).getClass();
				writeArray(elementClass, list, indent);
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
