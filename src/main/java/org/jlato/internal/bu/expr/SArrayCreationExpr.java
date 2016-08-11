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
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDArrayCreationExpr;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.expr.ArrayDimExpr;
import org.jlato.tree.expr.ArrayInitializerExpr;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

/**
 * A state object for an array creation expression.
 */
public class SArrayCreationExpr extends SNode<SArrayCreationExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new array creation expression.
	 *
	 * @param type     the type child <code>BUTree</code>.
	 * @param dimExprs the dimension expressions child <code>BUTree</code>.
	 * @param dims     the dimensions child <code>BUTree</code>.
	 * @param init     the init child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an array creation expression.
	 */
	public static BUTree<SArrayCreationExpr> make(BUTree<? extends SType> type, BUTree<SNodeList> dimExprs, BUTree<SNodeList> dims, BUTree<SNodeOption> init) {
		return new BUTree<SArrayCreationExpr>(new SArrayCreationExpr(type, dimExprs, dims, init));
	}

	/**
	 * The type of this array creation expression state.
	 */
	public final BUTree<? extends SType> type;

	/**
	 * The dimension expressions of this array creation expression state.
	 */
	public final BUTree<SNodeList> dimExprs;

	/**
	 * The dimensions of this array creation expression state.
	 */
	public final BUTree<SNodeList> dims;

	/**
	 * The init of this array creation expression state.
	 */
	public final BUTree<SNodeOption> init;

	/**
	 * Constructs an array creation expression state.
	 *
	 * @param type     the type child <code>BUTree</code>.
	 * @param dimExprs the dimension expressions child <code>BUTree</code>.
	 * @param dims     the dimensions child <code>BUTree</code>.
	 * @param init     the init child <code>BUTree</code>.
	 */
	public SArrayCreationExpr(BUTree<? extends SType> type, BUTree<SNodeList> dimExprs, BUTree<SNodeList> dims, BUTree<SNodeOption> init) {
		this.type = type;
		this.dimExprs = dimExprs;
		this.dims = dims;
		this.init = init;
	}

	/**
	 * Returns the kind of this array creation expression.
	 *
	 * @return the kind of this array creation expression.
	 */
	@Override
	public Kind kind() {
		return Kind.ArrayCreationExpr;
	}

	/**
	 * Replaces the type of this array creation expression state.
	 *
	 * @param type the replacement for the type of this array creation expression state.
	 * @return the resulting mutated array creation expression state.
	 */
	public SArrayCreationExpr withType(BUTree<? extends SType> type) {
		return new SArrayCreationExpr(type, dimExprs, dims, init);
	}

	/**
	 * Replaces the dimension expressions of this array creation expression state.
	 *
	 * @param dimExprs the replacement for the dimension expressions of this array creation expression state.
	 * @return the resulting mutated array creation expression state.
	 */
	public SArrayCreationExpr withDimExprs(BUTree<SNodeList> dimExprs) {
		return new SArrayCreationExpr(type, dimExprs, dims, init);
	}

	/**
	 * Replaces the dimensions of this array creation expression state.
	 *
	 * @param dims the replacement for the dimensions of this array creation expression state.
	 * @return the resulting mutated array creation expression state.
	 */
	public SArrayCreationExpr withDims(BUTree<SNodeList> dims) {
		return new SArrayCreationExpr(type, dimExprs, dims, init);
	}

	/**
	 * Replaces the init of this array creation expression state.
	 *
	 * @param init the replacement for the init of this array creation expression state.
	 * @return the resulting mutated array creation expression state.
	 */
	public SArrayCreationExpr withInit(BUTree<SNodeOption> init) {
		return new SArrayCreationExpr(type, dimExprs, dims, init);
	}

	/**
	 * Builds an array creation expression facade for the specified array creation expression <code>TDLocation</code>.
	 *
	 * @param location the array creation expression <code>TDLocation</code>.
	 * @return an array creation expression facade for the specified array creation expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SArrayCreationExpr> location) {
		return new TDArrayCreationExpr(location);
	}

	/**
	 * Returns the shape for this array creation expression state.
	 *
	 * @return the shape for this array creation expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this array creation expression state.
	 *
	 * @return the first child traversal for this array creation expression state.
	 */
	@Override
	public STraversal firstChild() {
		return TYPE;
	}

	/**
	 * Returns the last child traversal for this array creation expression state.
	 *
	 * @return the last child traversal for this array creation expression state.
	 */
	@Override
	public STraversal lastChild() {
		return INIT;
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
		SArrayCreationExpr state = (SArrayCreationExpr) o;
		if (type == null ? state.type != null : !type.equals(state.type))
			return false;
		if (!dimExprs.equals(state.dimExprs))
			return false;
		if (!dims.equals(state.dims))
			return false;
		if (!init.equals(state.init))
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
		if (type != null) result = 37 * result + type.hashCode();
		result = 37 * result + dimExprs.hashCode();
		result = 37 * result + dims.hashCode();
		result = 37 * result + init.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SArrayCreationExpr, SType, Type> TYPE = new STypeSafeTraversal<SArrayCreationExpr, SType, Type>() {

		@Override
		public BUTree<?> doTraverse(SArrayCreationExpr state) {
			return state.type;
		}

		@Override
		public SArrayCreationExpr doRebuildParentState(SArrayCreationExpr state, BUTree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return DIM_EXPRS;
		}
	};

	public static STypeSafeTraversal<SArrayCreationExpr, SNodeList, NodeList<ArrayDimExpr>> DIM_EXPRS = new STypeSafeTraversal<SArrayCreationExpr, SNodeList, NodeList<ArrayDimExpr>>() {

		@Override
		public BUTree<?> doTraverse(SArrayCreationExpr state) {
			return state.dimExprs;
		}

		@Override
		public SArrayCreationExpr doRebuildParentState(SArrayCreationExpr state, BUTree<SNodeList> child) {
			return state.withDimExprs(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return DIMS;
		}
	};

	public static STypeSafeTraversal<SArrayCreationExpr, SNodeList, NodeList<ArrayDim>> DIMS = new STypeSafeTraversal<SArrayCreationExpr, SNodeList, NodeList<ArrayDim>>() {

		@Override
		public BUTree<?> doTraverse(SArrayCreationExpr state) {
			return state.dims;
		}

		@Override
		public SArrayCreationExpr doRebuildParentState(SArrayCreationExpr state, BUTree<SNodeList> child) {
			return state.withDims(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return DIM_EXPRS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return INIT;
		}
	};

	public static STypeSafeTraversal<SArrayCreationExpr, SNodeOption, NodeOption<ArrayInitializerExpr>> INIT = new STypeSafeTraversal<SArrayCreationExpr, SNodeOption, NodeOption<ArrayInitializerExpr>>() {

		@Override
		public BUTree<?> doTraverse(SArrayCreationExpr state) {
			return state.init;
		}

		@Override
		public SArrayCreationExpr doRebuildParentState(SArrayCreationExpr state, BUTree<SNodeOption> child) {
			return state.withInit(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return DIMS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.New),
			child(TYPE),
			child(DIM_EXPRS, list()),
			child(DIMS, list()),
			child(INIT, when(some(),
					element().withSpacingBefore(space())
			))
	);
}
