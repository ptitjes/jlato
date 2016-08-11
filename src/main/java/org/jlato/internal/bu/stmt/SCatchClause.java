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

package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.decl.SFormalParameter;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDCatchClause;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

/**
 * A state object for a 'catch' clause.
 */
public class SCatchClause extends SNode<SCatchClause> implements STree {

	/**
	 * Creates a <code>BUTree</code> with a new 'catch' clause.
	 *
	 * @param param      the parameter child <code>BUTree</code>.
	 * @param catchBlock the 'catch' block child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a 'catch' clause.
	 */
	public static BUTree<SCatchClause> make(BUTree<SFormalParameter> param, BUTree<SBlockStmt> catchBlock) {
		return new BUTree<SCatchClause>(new SCatchClause(param, catchBlock));
	}

	/**
	 * The parameter of this 'catch' clause state.
	 */
	public final BUTree<SFormalParameter> param;

	/**
	 * The 'catch' block of this 'catch' clause state.
	 */
	public final BUTree<SBlockStmt> catchBlock;

	/**
	 * Constructs a 'catch' clause state.
	 *
	 * @param param      the parameter child <code>BUTree</code>.
	 * @param catchBlock the 'catch' block child <code>BUTree</code>.
	 */
	public SCatchClause(BUTree<SFormalParameter> param, BUTree<SBlockStmt> catchBlock) {
		this.param = param;
		this.catchBlock = catchBlock;
	}

	/**
	 * Returns the kind of this 'catch' clause.
	 *
	 * @return the kind of this 'catch' clause.
	 */
	@Override
	public Kind kind() {
		return Kind.CatchClause;
	}

	/**
	 * Replaces the parameter of this 'catch' clause state.
	 *
	 * @param param the replacement for the parameter of this 'catch' clause state.
	 * @return the resulting mutated 'catch' clause state.
	 */
	public SCatchClause withParam(BUTree<SFormalParameter> param) {
		return new SCatchClause(param, catchBlock);
	}

	/**
	 * Replaces the 'catch' block of this 'catch' clause state.
	 *
	 * @param catchBlock the replacement for the 'catch' block of this 'catch' clause state.
	 * @return the resulting mutated 'catch' clause state.
	 */
	public SCatchClause withCatchBlock(BUTree<SBlockStmt> catchBlock) {
		return new SCatchClause(param, catchBlock);
	}

	/**
	 * Builds a 'catch' clause facade for the specified 'catch' clause <code>TDLocation</code>.
	 *
	 * @param location the 'catch' clause <code>TDLocation</code>.
	 * @return a 'catch' clause facade for the specified 'catch' clause <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SCatchClause> location) {
		return new TDCatchClause(location);
	}

	/**
	 * Returns the shape for this 'catch' clause state.
	 *
	 * @return the shape for this 'catch' clause state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'catch' clause state.
	 *
	 * @return the first child traversal for this 'catch' clause state.
	 */
	@Override
	public STraversal firstChild() {
		return PARAM;
	}

	/**
	 * Returns the last child traversal for this 'catch' clause state.
	 *
	 * @return the last child traversal for this 'catch' clause state.
	 */
	@Override
	public STraversal lastChild() {
		return CATCH_BLOCK;
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
		SCatchClause state = (SCatchClause) o;
		if (param == null ? state.param != null : !param.equals(state.param))
			return false;
		if (catchBlock == null ? state.catchBlock != null : !catchBlock.equals(state.catchBlock))
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
		if (param != null) result = 37 * result + param.hashCode();
		if (catchBlock != null) result = 37 * result + catchBlock.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SCatchClause, SFormalParameter, FormalParameter> PARAM = new STypeSafeTraversal<SCatchClause, SFormalParameter, FormalParameter>() {

		@Override
		public BUTree<?> doTraverse(SCatchClause state) {
			return state.param;
		}

		@Override
		public SCatchClause doRebuildParentState(SCatchClause state, BUTree<SFormalParameter> child) {
			return state.withParam(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return CATCH_BLOCK;
		}
	};

	public static STypeSafeTraversal<SCatchClause, SBlockStmt, BlockStmt> CATCH_BLOCK = new STypeSafeTraversal<SCatchClause, SBlockStmt, BlockStmt>() {

		@Override
		public BUTree<?> doTraverse(SCatchClause state) {
			return state.catchBlock;
		}

		@Override
		public SCatchClause doRebuildParentState(SCatchClause state, BUTree<SBlockStmt> child) {
			return state.withCatchBlock(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return PARAM;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.Catch),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(PARAM),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(CATCH_BLOCK)
	);

	public static final LexicalShape listShape = list();
}
