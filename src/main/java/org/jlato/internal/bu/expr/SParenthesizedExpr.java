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

package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDParenthesizedExpr;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;
import static org.jlato.internal.shapes.LexicalShape.token;

/**
 * A state object for a parenthesized expression.
 */
public class SParenthesizedExpr extends SNode<SParenthesizedExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new parenthesized expression.
	 *
	 * @param inner the inner child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a parenthesized expression.
	 */
	public static BUTree<SParenthesizedExpr> make(BUTree<? extends SExpr> inner) {
		return new BUTree<SParenthesizedExpr>(new SParenthesizedExpr(inner));
	}

	/**
	 * The inner of this parenthesized expression state.
	 */
	public final BUTree<? extends SExpr> inner;

	/**
	 * Constructs a parenthesized expression state.
	 *
	 * @param inner the inner child <code>BUTree</code>.
	 */
	public SParenthesizedExpr(BUTree<? extends SExpr> inner) {
		this.inner = inner;
	}

	/**
	 * Returns the kind of this parenthesized expression.
	 *
	 * @return the kind of this parenthesized expression.
	 */
	@Override
	public Kind kind() {
		return Kind.ParenthesizedExpr;
	}

	/**
	 * Replaces the inner of this parenthesized expression state.
	 *
	 * @param inner the replacement for the inner of this parenthesized expression state.
	 * @return the resulting mutated parenthesized expression state.
	 */
	public SParenthesizedExpr withInner(BUTree<? extends SExpr> inner) {
		return new SParenthesizedExpr(inner);
	}

	/**
	 * Builds a parenthesized expression facade for the specified parenthesized expression <code>TDLocation</code>.
	 *
	 * @param location the parenthesized expression <code>TDLocation</code>.
	 * @return a parenthesized expression facade for the specified parenthesized expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SParenthesizedExpr> location) {
		return new TDParenthesizedExpr(location);
	}

	/**
	 * Returns the shape for this parenthesized expression state.
	 *
	 * @return the shape for this parenthesized expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this parenthesized expression state.
	 *
	 * @return the first child traversal for this parenthesized expression state.
	 */
	@Override
	public STraversal firstChild() {
		return INNER;
	}

	/**
	 * Returns the last child traversal for this parenthesized expression state.
	 *
	 * @return the last child traversal for this parenthesized expression state.
	 */
	@Override
	public STraversal lastChild() {
		return INNER;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SParenthesizedExpr state = (SParenthesizedExpr) o;
		if (inner == null ? state.inner != null : !inner.equals(state.inner))
			return false;
		return true;
	}

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		int result = 17;
		if (inner != null) result = 37 * result + inner.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SParenthesizedExpr, SExpr, Expr> INNER = new STypeSafeTraversal<SParenthesizedExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SParenthesizedExpr state) {
			return state.inner;
		}

		@Override
		public SParenthesizedExpr doRebuildParentState(SParenthesizedExpr state, BUTree<SExpr> child) {
			return state.withInner(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.ParenthesisLeft), child(INNER), token(LToken.ParenthesisRight)
	);
}
