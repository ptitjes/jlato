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
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDForStmt;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

/**
 * A state object for a 'for' statement.
 */
public class SForStmt extends SNode<SForStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new 'for' statement.
	 *
	 * @param init    the init child <code>BUTree</code>.
	 * @param compare the compare child <code>BUTree</code>.
	 * @param update  the update child <code>BUTree</code>.
	 * @param body    the body child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a 'for' statement.
	 */
	public static BUTree<SForStmt> make(BUTree<SNodeList> init, BUTree<? extends SExpr> compare, BUTree<SNodeList> update, BUTree<? extends SStmt> body) {
		return new BUTree<SForStmt>(new SForStmt(init, compare, update, body));
	}

	/**
	 * The init of this 'for' statement state.
	 */
	public final BUTree<SNodeList> init;

	/**
	 * The compare of this 'for' statement state.
	 */
	public final BUTree<? extends SExpr> compare;

	/**
	 * The update of this 'for' statement state.
	 */
	public final BUTree<SNodeList> update;

	/**
	 * The body of this 'for' statement state.
	 */
	public final BUTree<? extends SStmt> body;

	/**
	 * Constructs a 'for' statement state.
	 *
	 * @param init    the init child <code>BUTree</code>.
	 * @param compare the compare child <code>BUTree</code>.
	 * @param update  the update child <code>BUTree</code>.
	 * @param body    the body child <code>BUTree</code>.
	 */
	public SForStmt(BUTree<SNodeList> init, BUTree<? extends SExpr> compare, BUTree<SNodeList> update, BUTree<? extends SStmt> body) {
		this.init = init;
		this.compare = compare;
		this.update = update;
		this.body = body;
	}

	/**
	 * Returns the kind of this 'for' statement.
	 *
	 * @return the kind of this 'for' statement.
	 */
	@Override
	public Kind kind() {
		return Kind.ForStmt;
	}

	/**
	 * Replaces the init of this 'for' statement state.
	 *
	 * @param init the replacement for the init of this 'for' statement state.
	 * @return the resulting mutated 'for' statement state.
	 */
	public SForStmt withInit(BUTree<SNodeList> init) {
		return new SForStmt(init, compare, update, body);
	}

	/**
	 * Replaces the compare of this 'for' statement state.
	 *
	 * @param compare the replacement for the compare of this 'for' statement state.
	 * @return the resulting mutated 'for' statement state.
	 */
	public SForStmt withCompare(BUTree<? extends SExpr> compare) {
		return new SForStmt(init, compare, update, body);
	}

	/**
	 * Replaces the update of this 'for' statement state.
	 *
	 * @param update the replacement for the update of this 'for' statement state.
	 * @return the resulting mutated 'for' statement state.
	 */
	public SForStmt withUpdate(BUTree<SNodeList> update) {
		return new SForStmt(init, compare, update, body);
	}

	/**
	 * Replaces the body of this 'for' statement state.
	 *
	 * @param body the replacement for the body of this 'for' statement state.
	 * @return the resulting mutated 'for' statement state.
	 */
	public SForStmt withBody(BUTree<? extends SStmt> body) {
		return new SForStmt(init, compare, update, body);
	}

	/**
	 * Builds a 'for' statement facade for the specified 'for' statement <code>TDLocation</code>.
	 *
	 * @param location the 'for' statement <code>TDLocation</code>.
	 * @return a 'for' statement facade for the specified 'for' statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SForStmt> location) {
		return new TDForStmt(location);
	}

	/**
	 * Returns the shape for this 'for' statement state.
	 *
	 * @return the shape for this 'for' statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'for' statement state.
	 *
	 * @return the first child traversal for this 'for' statement state.
	 */
	@Override
	public STraversal firstChild() {
		return INIT;
	}

	/**
	 * Returns the last child traversal for this 'for' statement state.
	 *
	 * @return the last child traversal for this 'for' statement state.
	 */
	@Override
	public STraversal lastChild() {
		return BODY;
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
		SForStmt state = (SForStmt) o;
		if (!init.equals(state.init))
			return false;
		if (compare == null ? state.compare != null : !compare.equals(state.compare))
			return false;
		if (!update.equals(state.update))
			return false;
		if (body == null ? state.body != null : !body.equals(state.body))
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
		result = 37 * result + init.hashCode();
		if (compare != null) result = 37 * result + compare.hashCode();
		result = 37 * result + update.hashCode();
		if (body != null) result = 37 * result + body.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SForStmt, SNodeList, NodeList<Expr>> INIT = new STypeSafeTraversal<SForStmt, SNodeList, NodeList<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SForStmt state) {
			return state.init;
		}

		@Override
		public SForStmt doRebuildParentState(SForStmt state, BUTree<SNodeList> child) {
			return state.withInit(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return COMPARE;
		}
	};

	public static STypeSafeTraversal<SForStmt, SExpr, Expr> COMPARE = new STypeSafeTraversal<SForStmt, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SForStmt state) {
			return state.compare;
		}

		@Override
		public SForStmt doRebuildParentState(SForStmt state, BUTree<SExpr> child) {
			return state.withCompare(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return INIT;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return UPDATE;
		}
	};

	public static STypeSafeTraversal<SForStmt, SNodeList, NodeList<Expr>> UPDATE = new STypeSafeTraversal<SForStmt, SNodeList, NodeList<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SForStmt state) {
			return state.update;
		}

		@Override
		public SForStmt doRebuildParentState(SForStmt state, BUTree<SNodeList> child) {
			return state.withUpdate(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return COMPARE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SForStmt, SStmt, Stmt> BODY = new STypeSafeTraversal<SForStmt, SStmt, Stmt>() {

		@Override
		public BUTree<?> doTraverse(SForStmt state) {
			return state.body;
		}

		@Override
		public SForStmt doRebuildParentState(SForStmt state, BUTree<SStmt> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return UPDATE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.For), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(INIT, list(token(LToken.Comma).withSpacingAfter(space()))),
			token(LToken.SemiColon).withSpacingAfter(space()),
			child(COMPARE),
			token(LToken.SemiColon).withSpacingAfter(space()),
			child(UPDATE, list(token(LToken.Comma).withSpacingAfter(space()))),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);
}
