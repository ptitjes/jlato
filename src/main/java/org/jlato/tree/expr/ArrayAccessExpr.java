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
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;

import static org.jlato.internal.shapes.LexicalShape.*;

public class ArrayAccessExpr extends TreeBase<SNodeState> implements Expr {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public ArrayAccessExpr instantiate(SLocation location) {
			return new ArrayAccessExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ArrayAccessExpr(SLocation<SNodeState> location) {
		super(location);
	}

	public ArrayAccessExpr(Expr name, Expr index) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(name, index)))));
	}

	public Expr name() {
		return location.safeTraversal(NAME);
	}

	public ArrayAccessExpr withName(Expr name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public ArrayAccessExpr withName(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public Expr index() {
		return location.safeTraversal(INDEX);
	}

	public ArrayAccessExpr withIndex(Expr index) {
		return location.safeTraversalReplace(INDEX, index);
	}

	public ArrayAccessExpr withIndex(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(INDEX, mutation);
	}

	private static final STraversal<SNodeState> NAME = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> INDEX = SNodeState.childTraversal(1);

	public final static LexicalShape shape = composite(
			child(NAME),
			token(LToken.BracketLeft), child(INDEX), token(LToken.BracketRight)
	);
}
