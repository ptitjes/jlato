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
import org.jlato.internal.patterns.TreePattern;
import org.jlato.internal.td.TreeBase;
import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.printer.FormattingSettings;
import org.jlato.printer.Printer;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.type.Type;

/**
 * @author Didier Villevalois
 */
public abstract class Quotes {

	public static Pattern<PackageDecl> packageDecl(String string) {
		return quote(ParseContext.PackageDecl, PackageDecl.class, string);
	}

	public static Pattern<ImportDecl> importDecl(String string) {
		return quote(ParseContext.ImportDecl, ImportDecl.class, string);
	}

	public static Pattern<TypeDecl> typeDecl(String string) {
		return quote(ParseContext.TypeDecl, TypeDecl.class, string);
	}

	public static Pattern<MemberDecl> memberDecl(String string) {
		return quote(ParseContext.MemberDecl, MemberDecl.class, string);
	}

	public static Pattern<FormalParameter> param(String string) {
		return quote(ParseContext.Parameter, FormalParameter.class, string);
	}

	public static Pattern<Stmt> stmt(String string) {
		return quote(ParseContext.Statement, Stmt.class, string);
	}

	public static Pattern<Expr> expr(String string) {
		return quote(ParseContext.Expression, Expr.class, string);
	}

	public static Pattern<Type> type(String string) {
		return quote(ParseContext.Type, Type.class, string);
	}

	public static <T extends Tree> Pattern<T> quote(ParseContext<T> context, Class<T> treeClass, String string) {
		Parser parser = new Parser();
		T expr = null;
		try {
			expr = parser.parse(context, string);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Can't parse quote: " + string, e);
		}
		return buildPattern(expr);
	}

	private static <T extends Tree> Pattern<T> buildPattern(T tree) {
		return new TreePattern<T>(buildTreePattern(TreeBase.treeOf(tree)));
	}

	private static int lastAnnonVariable = 0;

	private static STree<?> buildTreePattern(STree<?> tree) {
		if (tree == null) {
			return null;
		} else {
			// Temporary code until dedicated parser is operational
			STreeState state = tree.state;
			if (tree.kind == Name.kind) {
				String string = (String) state.data(0);
				if (string.startsWith("$")) {
					if (string.startsWith("$$")) {
						// Make a var that match the name string directly
						return new STree<SLeafState>(Name.kind, new SLeafState(TreeBase.dataOf(
								new STree<SVarState>(null, new SVarState(string.substring(2)))
						)));
					} else {
						// Make a var that match something in place of the name
						return new STree<SVarState>(null, new SVarState(string.substring(1)));
					}
				} else {
					return tree;
				}

			} else if (state instanceof SLeafState) {
				STree<SLeafState> leaf = (STree<SLeafState>) tree;
				return new STree<SLeafState>(leaf.kind, new SLeafState(buildDataPattern(state.data)));

			} else if (state instanceof SNodeState) {
				STree<SNodeState> node = (STree<SNodeState>) tree;
				ArrayList<STree<?>> patterChildren = buildTreePattern(((SNodeState) state).children);
				return new STree<SNodeState>(node.kind, new SNodeState(buildDataPattern(state.data), patterChildren));

			} else if (state instanceof SNodeListState) {
				STree<SNodeListState> nodeList = (STree<SNodeListState>) tree;
				Vector<STree<?>> elements = ((SNodeListState) state).children;

				// Handle a unique variable ending with $ as a variable for the whole list
				if (elements.size() == 1) {
					STree<?> nodeListVar = varWithEndingDollar(elements.get(0));
					if (nodeListVar != null) return nodeListVar;
				}

				Vector<STree<?>> patternElements = buildTreePattern(elements);
				return new STree<SNodeListState>(nodeList.kind, new SNodeListState(patternElements));

			} else if (state instanceof SNodeOptionState) {
				STree<SNodeOptionState> nodeOption = (STree<SNodeOptionState>) tree;
				STree<?> element = ((SNodeOptionState) state).element;

				if (element != null && !(element.state instanceof SNodeListState)) {
					// Handle a variable ending with $ as a variable for the whole option
					STree<?> optionVar = varWithEndingDollar(element);
					if (optionVar != null) return optionVar;
				}

				STree<?> patternElement = buildTreePattern(element);
				return new STree<SNodeOptionState>(nodeOption.kind, new SNodeOptionState(patternElement));
			}
		}
		return null;
	}

	private static STree<?> varWithEndingDollar(STree<?> element) {
		if (element != null) {
			// This is a big hack in waiting for a dedicated quote parser
			String name = Printer.printToString(element.asTree(), false, FormattingSettings.Default);
			if (isAlpha(name) && name.startsWith("$") && name.endsWith("$")) {
				String varName = name.substring(1, name.length() - 1);
				return new STree<SVarState>(null, new SVarState(varName));
			}
		}
		return null;
	}

	private static boolean isAlpha(String name) {
		for (char c : name.toCharArray()) {
			if (!Character.isJavaIdentifierPart(c)) return false;
		}
		return true;
	}

	private static ArrayList<STree<?>> buildTreePattern(Iterable<STree<?>> children) {
		Builder<STree<?>, ArrayList<STree<?>>> builder = ArrayList.<STree<?>>factory().newBuilder();
		for (STree<?> child : children) {
			builder.add(buildTreePattern(child));
		}
		return builder.build();
	}

	private static Vector<STree<?>> buildTreePattern(Vector<STree<?>> children) {
		Builder<STree<?>, Vector<STree<?>>> builder = Vector.<STree<?>>factory().newBuilder();
		for (STree<?> child : children) {
			builder.add(buildTreePattern(child));
		}
		return builder.build();
	}

	private static ArrayList<Object> buildDataPattern(ArrayList<Object> data) {
		Builder<Object, ArrayList<Object>> builder = ArrayList.<Object>factory().newBuilder();
		for (Object object : data) {
			builder.add(object);
		}
		return builder.build();
	}
}
