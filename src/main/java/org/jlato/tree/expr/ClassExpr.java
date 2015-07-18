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

package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;

import org.jlato.tree.Tree;

public class ClassExpr extends TreeBase<ClassExpr.State, Expr, ClassExpr> implements Expr {

	public Kind kind() {
		return Kind.ClassExpr;
	}

	private ClassExpr(SLocation<ClassExpr.State> location) {
		super(location);
	}

	public static STree<ClassExpr.State> make(Type type) {
		return new STree<ClassExpr.State>(new ClassExpr.State(TreeBase.<Type.State>nodeOf(type)));
	}

	public ClassExpr(Type type) {
		super(new SLocation<ClassExpr.State>(make(type)));
	}

	public Type type() {
		return location.safeTraversal(TYPE);
	}

	public ClassExpr withType(Type type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public ClassExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	private static final STraversal<ClassExpr.State> TYPE = new STraversal<ClassExpr.State>() {

		public STree<?> traverse(ClassExpr.State state) {
			return state.type;
		}

		public ClassExpr.State rebuildParentState(ClassExpr.State state, STree<?> child) {
			return state.withType((STree) child);
		}

		public STraversal<ClassExpr.State> leftSibling(ClassExpr.State state) {
			return null;
		}

		public STraversal<ClassExpr.State> rightSibling(ClassExpr.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(TYPE),
			token(LToken.Dot), token(LToken.Class)
	);

	public static class State extends SNodeState<State> {

		public final STree<Type.State> type;

		State(STree<Type.State> type) {
			this.type = type;
		}

		public ClassExpr.State withType(STree<Type.State> type) {
			return new ClassExpr.State(type);
		}

		public STraversal<ClassExpr.State> firstChild() {
			return null;
		}

		public STraversal<ClassExpr.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<ClassExpr.State> location) {
			return new ClassExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.ClassExpr;
		}
	}
}
