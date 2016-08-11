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
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDInstanceOfExpr;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;
import static org.jlato.internal.shapes.LexicalShape.keyword;

/**
 * A state object for an 'instanceof' expression.
 */
public class SInstanceOfExpr extends SNode<SInstanceOfExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new 'instanceof' expression.
	 *
	 * @param expr the expression child <code>BUTree</code>.
	 * @param type the type child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an 'instanceof' expression.
	 */
	public static BUTree<SInstanceOfExpr> make(BUTree<? extends SExpr> expr, BUTree<? extends SType> type) {
		return new BUTree<SInstanceOfExpr>(new SInstanceOfExpr(expr, type));
	}

	/**
	 * The expression of this 'instanceof' expression state.
	 */
	public final BUTree<? extends SExpr> expr;

	/**
	 * The type of this 'instanceof' expression state.
	 */
	public final BUTree<? extends SType> type;

	/**
	 * Constructs an 'instanceof' expression state.
	 *
	 * @param expr the expression child <code>BUTree</code>.
	 * @param type the type child <code>BUTree</code>.
	 */
	public SInstanceOfExpr(BUTree<? extends SExpr> expr, BUTree<? extends SType> type) {
		this.expr = expr;
		this.type = type;
	}

	/**
	 * Returns the kind of this 'instanceof' expression.
	 *
	 * @return the kind of this 'instanceof' expression.
	 */
	@Override
	public Kind kind() {
		return Kind.InstanceOfExpr;
	}

	/**
	 * Replaces the expression of this 'instanceof' expression state.
	 *
	 * @param expr the replacement for the expression of this 'instanceof' expression state.
	 * @return the resulting mutated 'instanceof' expression state.
	 */
	public SInstanceOfExpr withExpr(BUTree<? extends SExpr> expr) {
		return new SInstanceOfExpr(expr, type);
	}

	/**
	 * Replaces the type of this 'instanceof' expression state.
	 *
	 * @param type the replacement for the type of this 'instanceof' expression state.
	 * @return the resulting mutated 'instanceof' expression state.
	 */
	public SInstanceOfExpr withType(BUTree<? extends SType> type) {
		return new SInstanceOfExpr(expr, type);
	}

	/**
	 * Builds an 'instanceof' expression facade for the specified 'instanceof' expression <code>TDLocation</code>.
	 *
	 * @param location the 'instanceof' expression <code>TDLocation</code>.
	 * @return an 'instanceof' expression facade for the specified 'instanceof' expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SInstanceOfExpr> location) {
		return new TDInstanceOfExpr(location);
	}

	/**
	 * Returns the shape for this 'instanceof' expression state.
	 *
	 * @return the shape for this 'instanceof' expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'instanceof' expression state.
	 *
	 * @return the first child traversal for this 'instanceof' expression state.
	 */
	@Override
	public STraversal firstChild() {
		return EXPR;
	}

	/**
	 * Returns the last child traversal for this 'instanceof' expression state.
	 *
	 * @return the last child traversal for this 'instanceof' expression state.
	 */
	@Override
	public STraversal lastChild() {
		return TYPE;
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
		SInstanceOfExpr state = (SInstanceOfExpr) o;
		if (expr == null ? state.expr != null : !expr.equals(state.expr))
			return false;
		if (type == null ? state.type != null : !type.equals(state.type))
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
		if (expr != null) result = 37 * result + expr.hashCode();
		if (type != null) result = 37 * result + type.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SInstanceOfExpr, SExpr, Expr> EXPR = new STypeSafeTraversal<SInstanceOfExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SInstanceOfExpr state) {
			return state.expr;
		}

		@Override
		public SInstanceOfExpr doRebuildParentState(SInstanceOfExpr state, BUTree<SExpr> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return TYPE;
		}
	};

	public static STypeSafeTraversal<SInstanceOfExpr, SType, Type> TYPE = new STypeSafeTraversal<SInstanceOfExpr, SType, Type>() {

		@Override
		public BUTree<?> doTraverse(SInstanceOfExpr state) {
			return state.type;
		}

		@Override
		public SInstanceOfExpr doRebuildParentState(SInstanceOfExpr state, BUTree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return EXPR;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(EXPR),
			keyword(LToken.InstanceOf),
			child(TYPE)
	);
}
