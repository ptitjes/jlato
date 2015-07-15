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
import org.jlato.internal.bu.STree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.STraversal;

public class CastExpr extends TreeBase<SNodeState> implements Expr {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public CastExpr instantiate(SLocation location) {
			return new CastExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private CastExpr(SLocation<SNodeState> location) {
		super(location);
	}

	public CastExpr(Type type, Expr expr) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(type, expr)))));
	}

	public Type type() {
		return location.safeTraversal(TYPE);
	}

	public CastExpr withType(Type type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public CastExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	public Expr expr() {
		return location.safeTraversal(EXPR);
	}

	public CastExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(EXPR, expr);
	}

	public CastExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(EXPR, mutation);
	}

	private static final STraversal<SNodeState> TYPE = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> EXPR = SNodeState.childTraversal(1);

	public final static LexicalShape shape = composite(
			token(LToken.ParenthesisLeft), child(TYPE), token(LToken.ParenthesisRight).withSpacingAfter(space()), child(EXPR)
	);
}
