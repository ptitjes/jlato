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
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDDoStmt;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

/**
 * A state object for a 'do-while' statement.
 */
public class SDoStmt extends SNode<SDoStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new 'do-while' statement.
	 *
	 * @param body      the body child <code>BUTree</code>.
	 * @param condition the condition child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a 'do-while' statement.
	 */
	public static BUTree<SDoStmt> make(BUTree<? extends SStmt> body, BUTree<? extends SExpr> condition) {
		return new BUTree<SDoStmt>(new SDoStmt(body, condition));
	}

	/**
	 * The body of this 'do-while' statement state.
	 */
	public final BUTree<? extends SStmt> body;

	/**
	 * The condition of this 'do-while' statement state.
	 */
	public final BUTree<? extends SExpr> condition;

	/**
	 * Constructs a 'do-while' statement state.
	 *
	 * @param body      the body child <code>BUTree</code>.
	 * @param condition the condition child <code>BUTree</code>.
	 */
	public SDoStmt(BUTree<? extends SStmt> body, BUTree<? extends SExpr> condition) {
		this.body = body;
		this.condition = condition;
	}

	/**
	 * Returns the kind of this 'do-while' statement.
	 *
	 * @return the kind of this 'do-while' statement.
	 */
	@Override
	public Kind kind() {
		return Kind.DoStmt;
	}

	/**
	 * Replaces the body of this 'do-while' statement state.
	 *
	 * @param body the replacement for the body of this 'do-while' statement state.
	 * @return the resulting mutated 'do-while' statement state.
	 */
	public SDoStmt withBody(BUTree<? extends SStmt> body) {
		return new SDoStmt(body, condition);
	}

	/**
	 * Replaces the condition of this 'do-while' statement state.
	 *
	 * @param condition the replacement for the condition of this 'do-while' statement state.
	 * @return the resulting mutated 'do-while' statement state.
	 */
	public SDoStmt withCondition(BUTree<? extends SExpr> condition) {
		return new SDoStmt(body, condition);
	}

	/**
	 * Builds a 'do-while' statement facade for the specified 'do-while' statement <code>TDLocation</code>.
	 *
	 * @param location the 'do-while' statement <code>TDLocation</code>.
	 * @return a 'do-while' statement facade for the specified 'do-while' statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SDoStmt> location) {
		return new TDDoStmt(location);
	}

	/**
	 * Returns the shape for this 'do-while' statement state.
	 *
	 * @return the shape for this 'do-while' statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'do-while' statement state.
	 *
	 * @return the first child traversal for this 'do-while' statement state.
	 */
	@Override
	public STraversal firstChild() {
		return BODY;
	}

	/**
	 * Returns the last child traversal for this 'do-while' statement state.
	 *
	 * @return the last child traversal for this 'do-while' statement state.
	 */
	@Override
	public STraversal lastChild() {
		return CONDITION;
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
		SDoStmt state = (SDoStmt) o;
		if (body == null ? state.body != null : !body.equals(state.body))
			return false;
		if (condition == null ? state.condition != null : !condition.equals(state.condition))
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
		if (body != null) result = 37 * result + body.hashCode();
		if (condition != null) result = 37 * result + condition.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SDoStmt, SStmt, Stmt> BODY = new STypeSafeTraversal<SDoStmt, SStmt, Stmt>() {

		@Override
		public BUTree<?> doTraverse(SDoStmt state) {
			return state.body;
		}

		@Override
		public SDoStmt doRebuildParentState(SDoStmt state, BUTree<SStmt> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return CONDITION;
		}
	};

	public static STypeSafeTraversal<SDoStmt, SExpr, Expr> CONDITION = new STypeSafeTraversal<SDoStmt, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SDoStmt state) {
			return state.condition;
		}

		@Override
		public SDoStmt doRebuildParentState(SDoStmt state, BUTree<SExpr> child) {
			return state.withCondition(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return BODY;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.Do),
			child(BODY),
			keyword(LToken.While),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(CONDITION),
			token(LToken.ParenthesisRight),
			token(LToken.SemiColon)
	);
}
