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

package org.jlato.rewrite;

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.*;
import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;

/**
 * @author Didier Villevalois
 */
public abstract class Quotes {

	public static Pattern<Expr> expr(String string) throws ParseException {
		return quote(ParseContext.Expression, Expr.class, string);
	}

	public static <T extends Tree> Pattern<T> quote(ParseContext<T> context, Class<T> treeClass, String string) throws ParseException {
		Parser parser = new Parser();
		T expr = parser.parse(context, string);
		return buildTreePattern(expr, treeClass);
	}

	private static <T extends Tree> Pattern<T> buildTreePattern(T tree, Class<T> treeClass) {
		return buildTreePattern(Tree.treeOf(tree), treeClass);
	}

	private static int lastAnnonVariable = 0;

	private static <T extends Tree> Pattern<T> buildTreePattern(STree tree, Class<T> treeClass) {
		if (tree == null) {
			return new Variable<T>("___" + (lastAnnonVariable++), treeClass);
		} else {
			STreeState state = tree.state;
			if (tree.kind == Name.kind) {
				String string = (String) state.data(0);
				if (string.startsWith("$$")) {
					return new LeafPattern<T>(Name.kind, Pattern.termsOf(
							new Variable<String>(string.substring(2), String.class)
					));
				} else if (string.startsWith("$")) {
					return new Variable<T>(string.substring(1), treeClass);
				} else {
					return new LeafPattern<T>(Name.kind, Pattern.termsOf(Constant.of(string)));
				}
			} else if (state instanceof SLeafState) {
				return new LeafPattern<T>(tree.kind, buildDataPattern(state.data));
			} else if (state instanceof SNodeState) {
				return new NodePattern<T>(tree.kind, buildDataPattern(state.data), buildTreePattern(((SNodeState) state).children));
			} else if (state instanceof SNodeListState) {
				return new NodeListPattern<T>(tree.kind, buildDataPattern(state.data), buildTreePattern(((SNodeListState) state).children));
			}
		}
		return null;
	}

	private static ArrayList<Pattern<? extends Tree>> buildTreePattern(ArrayList<STree> children) {
		Builder<Pattern<? extends Tree>, ArrayList<Pattern<? extends Tree>>> builder = ArrayList.<Pattern<? extends Tree>>factory().newBuilder();
		for (int i = 0; i < children.size(); i++) {
			STree child = children.get(i);
			builder.add(buildTreePattern(child, Tree.class));
		}
		return builder.build();
	}

	private static ArrayList<Pattern<? extends Tree>> buildTreePattern(Vector<STree> children) {
		Builder<Pattern<? extends Tree>, ArrayList<Pattern<? extends Tree>>> builder = ArrayList.<Pattern<? extends Tree>>factory().newBuilder();
		for (int i = 0; i < children.size(); i++) {
			STree child = children.get(i);
			builder.add(buildTreePattern(child, Tree.class));
		}
		return builder.build();
	}

	private static ArrayList<Pattern<?>> buildDataPattern(ArrayList<Object> data) {
		Builder<Pattern<?>, ArrayList<Pattern<?>>> builder = ArrayList.<Pattern<?>>factory().newBuilder();
		for (Object object : data) {
			builder.add(buildDataPattern(object));
		}
		return builder.build();
	}

	private static Pattern<?> buildDataPattern(Object object) {
		return Constant.of(object);
	}
}
