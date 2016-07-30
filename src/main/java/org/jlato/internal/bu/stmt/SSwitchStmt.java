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

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDSwitchStmt;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.SwitchCase;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a 'switch' statement.
 */
public class SSwitchStmt extends SNode<SSwitchStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new 'switch' statement.
	 *
	 * @param selector the selector child <code>BUTree</code>.
	 * @param cases    the cases child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a 'switch' statement.
	 */
	public static BUTree<SSwitchStmt> make(BUTree<? extends SExpr> selector, BUTree<SNodeList> cases) {
		return new BUTree<SSwitchStmt>(new SSwitchStmt(selector, cases));
	}

	/**
	 * The selector of this 'switch' statement state.
	 */
	public final BUTree<? extends SExpr> selector;

	/**
	 * The cases of this 'switch' statement state.
	 */
	public final BUTree<SNodeList> cases;

	/**
	 * Constructs a 'switch' statement state.
	 *
	 * @param selector the selector child <code>BUTree</code>.
	 * @param cases    the cases child <code>BUTree</code>.
	 */
	public SSwitchStmt(BUTree<? extends SExpr> selector, BUTree<SNodeList> cases) {
		this.selector = selector;
		this.cases = cases;
	}

	/**
	 * Returns the kind of this 'switch' statement.
	 *
	 * @return the kind of this 'switch' statement.
	 */
	@Override
	public Kind kind() {
		return Kind.SwitchStmt;
	}

	/**
	 * Replaces the selector of this 'switch' statement state.
	 *
	 * @param selector the replacement for the selector of this 'switch' statement state.
	 * @return the resulting mutated 'switch' statement state.
	 */
	public SSwitchStmt withSelector(BUTree<? extends SExpr> selector) {
		return new SSwitchStmt(selector, cases);
	}

	/**
	 * Replaces the cases of this 'switch' statement state.
	 *
	 * @param cases the replacement for the cases of this 'switch' statement state.
	 * @return the resulting mutated 'switch' statement state.
	 */
	public SSwitchStmt withCases(BUTree<SNodeList> cases) {
		return new SSwitchStmt(selector, cases);
	}

	/**
	 * Builds a 'switch' statement facade for the specified 'switch' statement <code>TDLocation</code>.
	 *
	 * @param location the 'switch' statement <code>TDLocation</code>.
	 * @return a 'switch' statement facade for the specified 'switch' statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SSwitchStmt> location) {
		return new TDSwitchStmt(location);
	}

	/**
	 * Returns the shape for this 'switch' statement state.
	 *
	 * @return the shape for this 'switch' statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'switch' statement state.
	 *
	 * @return the first child traversal for this 'switch' statement state.
	 */
	@Override
	public STraversal firstChild() {
		return SELECTOR;
	}

	/**
	 * Returns the last child traversal for this 'switch' statement state.
	 *
	 * @return the last child traversal for this 'switch' statement state.
	 */
	@Override
	public STraversal lastChild() {
		return CASES;
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
		SSwitchStmt state = (SSwitchStmt) o;
		if (selector == null ? state.selector != null : !selector.equals(state.selector))
			return false;
		if (!cases.equals(state.cases))
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
		if (selector != null) result = 37 * result + selector.hashCode();
		result = 37 * result + cases.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SSwitchStmt, SExpr, Expr> SELECTOR = new STypeSafeTraversal<SSwitchStmt, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SSwitchStmt state) {
			return state.selector;
		}

		@Override
		public SSwitchStmt doRebuildParentState(SSwitchStmt state, BUTree<SExpr> child) {
			return state.withSelector(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return CASES;
		}
	};

	public static STypeSafeTraversal<SSwitchStmt, SNodeList, NodeList<SwitchCase>> CASES = new STypeSafeTraversal<SSwitchStmt, SNodeList, NodeList<SwitchCase>>() {

		@Override
		public BUTree<?> doTraverse(SSwitchStmt state) {
			return state.cases;
		}

		@Override
		public SSwitchStmt doRebuildParentState(SSwitchStmt state, BUTree<SNodeList> child) {
			return state.withCases(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return SELECTOR;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.Switch),
			token(LToken.ParenthesisLeft).withSpacingBefore(spacing(SwitchStmt_AfterSwitchKeyword)),
			child(SELECTOR),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			token(LToken.BraceLeft)
					.withSpacingAfter(newLine())
					.withIndentationAfter(indent(IndentationContext.Switch)),
			child(CASES, SSwitchCase.listShape),
			alternative(childIs(CASES, not(empty())),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(IndentationContext.Switch))
							.withSpacingBefore(newLine()),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(IndentationContext.Switch))
			)
	);
}
