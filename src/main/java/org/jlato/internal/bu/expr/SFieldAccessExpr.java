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
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDFieldAccessExpr;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

/**
 * A state object for a field access expression.
 */
public class SFieldAccessExpr extends SNode<SFieldAccessExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new field access expression.
	 *
	 * @param scope the scope child <code>BUTree</code>.
	 * @param name  the name child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a field access expression.
	 */
	public static BUTree<SFieldAccessExpr> make(BUTree<SNodeOption> scope, BUTree<SName> name) {
		return new BUTree<SFieldAccessExpr>(new SFieldAccessExpr(scope, name));
	}

	/**
	 * The scope of this field access expression state.
	 */
	public final BUTree<SNodeOption> scope;

	/**
	 * The name of this field access expression state.
	 */
	public final BUTree<SName> name;

	/**
	 * Constructs a field access expression state.
	 *
	 * @param scope the scope child <code>BUTree</code>.
	 * @param name  the name child <code>BUTree</code>.
	 */
	public SFieldAccessExpr(BUTree<SNodeOption> scope, BUTree<SName> name) {
		this.scope = scope;
		this.name = name;
	}

	/**
	 * Returns the kind of this field access expression.
	 *
	 * @return the kind of this field access expression.
	 */
	@Override
	public Kind kind() {
		return Kind.FieldAccessExpr;
	}

	/**
	 * Replaces the scope of this field access expression state.
	 *
	 * @param scope the replacement for the scope of this field access expression state.
	 * @return the resulting mutated field access expression state.
	 */
	public SFieldAccessExpr withScope(BUTree<SNodeOption> scope) {
		return new SFieldAccessExpr(scope, name);
	}

	/**
	 * Replaces the name of this field access expression state.
	 *
	 * @param name the replacement for the name of this field access expression state.
	 * @return the resulting mutated field access expression state.
	 */
	public SFieldAccessExpr withName(BUTree<SName> name) {
		return new SFieldAccessExpr(scope, name);
	}

	/**
	 * Builds a field access expression facade for the specified field access expression <code>TDLocation</code>.
	 *
	 * @param location the field access expression <code>TDLocation</code>.
	 * @return a field access expression facade for the specified field access expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SFieldAccessExpr> location) {
		return new TDFieldAccessExpr(location);
	}

	/**
	 * Returns the shape for this field access expression state.
	 *
	 * @return the shape for this field access expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this field access expression state.
	 *
	 * @return the first child traversal for this field access expression state.
	 */
	@Override
	public STraversal firstChild() {
		return SCOPE;
	}

	/**
	 * Returns the last child traversal for this field access expression state.
	 *
	 * @return the last child traversal for this field access expression state.
	 */
	@Override
	public STraversal lastChild() {
		return NAME;
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
		SFieldAccessExpr state = (SFieldAccessExpr) o;
		if (!scope.equals(state.scope))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
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
		result = 37 * result + scope.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SFieldAccessExpr, SNodeOption, NodeOption<Expr>> SCOPE = new STypeSafeTraversal<SFieldAccessExpr, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SFieldAccessExpr state) {
			return state.scope;
		}

		@Override
		public SFieldAccessExpr doRebuildParentState(SFieldAccessExpr state, BUTree<SNodeOption> child) {
			return state.withScope(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SFieldAccessExpr, SName, Name> NAME = new STypeSafeTraversal<SFieldAccessExpr, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SFieldAccessExpr state) {
			return state.name;
		}

		@Override
		public SFieldAccessExpr doRebuildParentState(SFieldAccessExpr state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return SCOPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(SCOPE, when(some(), composite(element(), token(LToken.Dot)))),
			child(NAME)
	);
}
