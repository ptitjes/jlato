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
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.*;

import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class SingleMemberAnnotationExpr extends TreeBase<SingleMemberAnnotationExpr.State, AnnotationExpr, SingleMemberAnnotationExpr> implements AnnotationExpr {

	public Kind kind() {
		return Kind.SingleMemberAnnotationExpr;
	}

	private SingleMemberAnnotationExpr(SLocation<SingleMemberAnnotationExpr.State> location) {
		super(location);
	}

	public static STree<SingleMemberAnnotationExpr.State> make(STree<QualifiedName.State> name, STree<? extends Expr.State> memberValue) {
		return new STree<SingleMemberAnnotationExpr.State>(new SingleMemberAnnotationExpr.State(name, memberValue));
	}

	public SingleMemberAnnotationExpr(QualifiedName name, Expr memberValue) {
		super(new SLocation<SingleMemberAnnotationExpr.State>(make(TreeBase.<QualifiedName.State>nodeOf(name), TreeBase.<Expr.State>nodeOf(memberValue))));
	}

	public QualifiedName name() {
		return location.safeTraversal(NAME);
	}

	public SingleMemberAnnotationExpr withName(QualifiedName name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public SingleMemberAnnotationExpr withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public Expr memberValue() {
		return location.safeTraversal(MEMBER_VALUE);
	}

	public SingleMemberAnnotationExpr withMemberValue(Expr memberValue) {
		return location.safeTraversalReplace(MEMBER_VALUE, memberValue);
	}

	public SingleMemberAnnotationExpr withMemberValue(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(MEMBER_VALUE, mutation);
	}

	public static class State extends SNodeState<State>implements AnnotationExpr.State {

		public final STree<QualifiedName.State> name;

		public final STree<? extends Expr.State> memberValue;

		State(STree<QualifiedName.State> name, STree<? extends Expr.State> memberValue) {
			this.name = name;
			this.memberValue = memberValue;
		}

		public SingleMemberAnnotationExpr.State withName(STree<QualifiedName.State> name) {
			return new SingleMemberAnnotationExpr.State(name, memberValue);
		}

		public SingleMemberAnnotationExpr.State withMemberValue(STree<? extends Expr.State> memberValue) {
			return new SingleMemberAnnotationExpr.State(name, memberValue);
		}

		@Override
		public Kind kind() {
			return Kind.SingleMemberAnnotationExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<SingleMemberAnnotationExpr.State> location) {
			return new SingleMemberAnnotationExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return NAME;
		}

		@Override
		public STraversal lastChild() {
			return MEMBER_VALUE;
		}
	}

	private static STypeSafeTraversal<SingleMemberAnnotationExpr.State, QualifiedName.State, QualifiedName> NAME = new STypeSafeTraversal<SingleMemberAnnotationExpr.State, QualifiedName.State, QualifiedName>() {

		@Override
		protected STree<?> doTraverse(SingleMemberAnnotationExpr.State state) {
			return state.name;
		}

		@Override
		protected SingleMemberAnnotationExpr.State doRebuildParentState(SingleMemberAnnotationExpr.State state, STree<QualifiedName.State> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return MEMBER_VALUE;
		}
	};

	private static STypeSafeTraversal<SingleMemberAnnotationExpr.State, Expr.State, Expr> MEMBER_VALUE = new STypeSafeTraversal<SingleMemberAnnotationExpr.State, Expr.State, Expr>() {

		@Override
		protected STree<?> doTraverse(SingleMemberAnnotationExpr.State state) {
			return state.memberValue;
		}

		@Override
		protected SingleMemberAnnotationExpr.State doRebuildParentState(SingleMemberAnnotationExpr.State state, STree<Expr.State> child) {
			return state.withMemberValue(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			token(LToken.At), child(NAME),
			token(LToken.ParenthesisLeft),
			child(MEMBER_VALUE),
			token(LToken.ParenthesisRight)
	);
}
