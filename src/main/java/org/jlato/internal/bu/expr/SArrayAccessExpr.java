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

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDArrayAccessExpr;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an array access expression.
 */
public class SArrayAccessExpr extends SNode<SArrayAccessExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new array access expression.
	 *
	 * @param name  the name child <code>BUTree</code>.
	 * @param index the index child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an array access expression.
	 */
	public static BUTree<SArrayAccessExpr> make(BUTree<? extends SExpr> name, BUTree<? extends SExpr> index) {
		return new BUTree<SArrayAccessExpr>(new SArrayAccessExpr(name, index));
	}

	/**
	 * The name of this array access expression state.
	 */
	public final BUTree<? extends SExpr> name;

	/**
	 * The index of this array access expression state.
	 */
	public final BUTree<? extends SExpr> index;

	/**
	 * Constructs an array access expression state.
	 *
	 * @param name  the name child <code>BUTree</code>.
	 * @param index the index child <code>BUTree</code>.
	 */
	public SArrayAccessExpr(BUTree<? extends SExpr> name, BUTree<? extends SExpr> index) {
		this.name = name;
		this.index = index;
	}

	/**
	 * Returns the kind of this array access expression.
	 *
	 * @return the kind of this array access expression.
	 */
	@Override
	public Kind kind() {
		return Kind.ArrayAccessExpr;
	}

	/**
	 * Replaces the name of this array access expression state.
	 *
	 * @param name the replacement for the name of this array access expression state.
	 * @return the resulting mutated array access expression state.
	 */
	public SArrayAccessExpr withName(BUTree<? extends SExpr> name) {
		return new SArrayAccessExpr(name, index);
	}

	/**
	 * Replaces the index of this array access expression state.
	 *
	 * @param index the replacement for the index of this array access expression state.
	 * @return the resulting mutated array access expression state.
	 */
	public SArrayAccessExpr withIndex(BUTree<? extends SExpr> index) {
		return new SArrayAccessExpr(name, index);
	}

	/**
	 * Builds an array access expression facade for the specified array access expression <code>TDLocation</code>.
	 *
	 * @param location the array access expression <code>TDLocation</code>.
	 * @return an array access expression facade for the specified array access expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SArrayAccessExpr> location) {
		return new TDArrayAccessExpr(location);
	}

	/**
	 * Returns the shape for this array access expression state.
	 *
	 * @return the shape for this array access expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this array access expression state.
	 *
	 * @return the first child traversal for this array access expression state.
	 */
	@Override
	public STraversal firstChild() {
		return NAME;
	}

	/**
	 * Returns the last child traversal for this array access expression state.
	 *
	 * @return the last child traversal for this array access expression state.
	 */
	@Override
	public STraversal lastChild() {
		return INDEX;
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
		SArrayAccessExpr state = (SArrayAccessExpr) o;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (index == null ? state.index != null : !index.equals(state.index))
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
		if (name != null) result = 37 * result + name.hashCode();
		if (index != null) result = 37 * result + index.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SArrayAccessExpr, SExpr, Expr> NAME = new STypeSafeTraversal<SArrayAccessExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SArrayAccessExpr state) {
			return state.name;
		}

		@Override
		public SArrayAccessExpr doRebuildParentState(SArrayAccessExpr state, BUTree<SExpr> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return INDEX;
		}
	};

	public static STypeSafeTraversal<SArrayAccessExpr, SExpr, Expr> INDEX = new STypeSafeTraversal<SArrayAccessExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SArrayAccessExpr state) {
			return state.index;
		}

		@Override
		public SArrayAccessExpr doRebuildParentState(SArrayAccessExpr state, BUTree<SExpr> child) {
			return state.withIndex(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(NAME),
			token(LToken.BracketLeft), child(INDEX), token(LToken.BracketRight)
	);
}
