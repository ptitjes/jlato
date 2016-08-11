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
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDThisExpr;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

/**
 * A state object for a 'this' expression.
 */
public class SThisExpr extends SNode<SThisExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new 'this' expression.
	 *
	 * @param classExpr the 'class' expression child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a 'this' expression.
	 */
	public static BUTree<SThisExpr> make(BUTree<SNodeOption> classExpr) {
		return new BUTree<SThisExpr>(new SThisExpr(classExpr));
	}

	/**
	 * The 'class' expression of this 'this' expression state.
	 */
	public final BUTree<SNodeOption> classExpr;

	/**
	 * Constructs a 'this' expression state.
	 *
	 * @param classExpr the 'class' expression child <code>BUTree</code>.
	 */
	public SThisExpr(BUTree<SNodeOption> classExpr) {
		this.classExpr = classExpr;
	}

	/**
	 * Returns the kind of this 'this' expression.
	 *
	 * @return the kind of this 'this' expression.
	 */
	@Override
	public Kind kind() {
		return Kind.ThisExpr;
	}

	/**
	 * Replaces the 'class' expression of this 'this' expression state.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'this' expression state.
	 * @return the resulting mutated 'this' expression state.
	 */
	public SThisExpr withClassExpr(BUTree<SNodeOption> classExpr) {
		return new SThisExpr(classExpr);
	}

	/**
	 * Builds a 'this' expression facade for the specified 'this' expression <code>TDLocation</code>.
	 *
	 * @param location the 'this' expression <code>TDLocation</code>.
	 * @return a 'this' expression facade for the specified 'this' expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SThisExpr> location) {
		return new TDThisExpr(location);
	}

	/**
	 * Returns the shape for this 'this' expression state.
	 *
	 * @return the shape for this 'this' expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'this' expression state.
	 *
	 * @return the first child traversal for this 'this' expression state.
	 */
	@Override
	public STraversal firstChild() {
		return CLASS_EXPR;
	}

	/**
	 * Returns the last child traversal for this 'this' expression state.
	 *
	 * @return the last child traversal for this 'this' expression state.
	 */
	@Override
	public STraversal lastChild() {
		return CLASS_EXPR;
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
		SThisExpr state = (SThisExpr) o;
		if (!classExpr.equals(state.classExpr))
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
		result = 37 * result + classExpr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SThisExpr, SNodeOption, NodeOption<Expr>> CLASS_EXPR = new STypeSafeTraversal<SThisExpr, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SThisExpr state) {
			return state.classExpr;
		}

		@Override
		public SThisExpr doRebuildParentState(SThisExpr state, BUTree<SNodeOption> child) {
			return state.withClassExpr(child);
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
			child(CLASS_EXPR, when(some(), composite(element(), token(LToken.Dot)))),
			token(LToken.This)
	);
}
