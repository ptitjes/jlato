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
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class ArrayInitializerExpr extends TreeBase<ArrayInitializerExpr.State, Expr, ArrayInitializerExpr> implements Expr {

	public final static SKind<ArrayInitializerExpr.State> kind = new SKind<ArrayInitializerExpr.State>() {
		public ArrayInitializerExpr instantiate(SLocation<ArrayInitializerExpr.State> location) {
			return new ArrayInitializerExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ArrayInitializerExpr(SLocation<ArrayInitializerExpr.State> location) {
		super(location);
	}

	public static STree<ArrayInitializerExpr.State> make(NodeList<Expr> values) {
		return new STree<ArrayInitializerExpr.State>(kind, new ArrayInitializerExpr.State(TreeBase.<SNodeListState>nodeOf(values)));
	}

	public ArrayInitializerExpr(NodeList<Expr> values) {
		super(new SLocation<ArrayInitializerExpr.State>(make(values)));
	}

	public NodeList<Expr> values() {
		return location.safeTraversal(VALUES);
	}

	public ArrayInitializerExpr withValues(NodeList<Expr> values) {
		return location.safeTraversalReplace(VALUES, values);
	}

	public ArrayInitializerExpr withValues(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(VALUES, mutation);
	}

	private static final STraversal<ArrayInitializerExpr.State> VALUES = SNodeState.childTraversal(0);

	public final static LexicalShape shape = composite(
			nonEmptyChildren(VALUES,
					composite(
							token(LToken.BraceLeft).withSpacingAfter(space()),
							child(VALUES, Expr.listShape),
							token(LToken.BraceRight).withSpacingBefore(space())
					),
					composite(token(LToken.BraceLeft), token(LToken.BraceRight))
			)
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> values;

		State(STree<SNodeListState> values) {
			this.values = values;
		}

		public ArrayInitializerExpr.State withValues(STree<SNodeListState> values) {
			return new ArrayInitializerExpr.State(values);
		}

		public STraversal<ArrayInitializerExpr.State> firstChild() {
			return null;
		}

		public STraversal<ArrayInitializerExpr.State> lastChild() {
			return null;
		}
	}
}
