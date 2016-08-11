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
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDBlockStmt;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.stmt.Stmt;

import static org.jlato.internal.shapes.IndentationConstraint.indent;
import static org.jlato.internal.shapes.IndentationConstraint.unIndent;
import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.empty;
import static org.jlato.internal.shapes.LSCondition.not;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.newLine;
import static org.jlato.printer.FormattingSettings.IndentationContext.Block;

/**
 * A state object for a block statement.
 */
public class SBlockStmt extends SNode<SBlockStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new block statement.
	 *
	 * @param stmts the statements child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a block statement.
	 */
	public static BUTree<SBlockStmt> make(BUTree<SNodeList> stmts) {
		return new BUTree<SBlockStmt>(new SBlockStmt(stmts));
	}

	/**
	 * The statements of this block statement state.
	 */
	public final BUTree<SNodeList> stmts;

	/**
	 * Constructs a block statement state.
	 *
	 * @param stmts the statements child <code>BUTree</code>.
	 */
	public SBlockStmt(BUTree<SNodeList> stmts) {
		this.stmts = stmts;
	}

	/**
	 * Returns the kind of this block statement.
	 *
	 * @return the kind of this block statement.
	 */
	@Override
	public Kind kind() {
		return Kind.BlockStmt;
	}

	/**
	 * Replaces the statements of this block statement state.
	 *
	 * @param stmts the replacement for the statements of this block statement state.
	 * @return the resulting mutated block statement state.
	 */
	public SBlockStmt withStmts(BUTree<SNodeList> stmts) {
		return new SBlockStmt(stmts);
	}

	/**
	 * Builds a block statement facade for the specified block statement <code>TDLocation</code>.
	 *
	 * @param location the block statement <code>TDLocation</code>.
	 * @return a block statement facade for the specified block statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SBlockStmt> location) {
		return new TDBlockStmt(location);
	}

	/**
	 * Returns the shape for this block statement state.
	 *
	 * @return the shape for this block statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this block statement state.
	 *
	 * @return the first child traversal for this block statement state.
	 */
	@Override
	public STraversal firstChild() {
		return STMTS;
	}

	/**
	 * Returns the last child traversal for this block statement state.
	 *
	 * @return the last child traversal for this block statement state.
	 */
	@Override
	public STraversal lastChild() {
		return STMTS;
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
		SBlockStmt state = (SBlockStmt) o;
		if (!stmts.equals(state.stmts))
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
		result = 37 * result + stmts.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SBlockStmt, SNodeList, NodeList<Stmt>> STMTS = new STypeSafeTraversal<SBlockStmt, SNodeList, NodeList<Stmt>>() {

		@Override
		public BUTree<?> doTraverse(SBlockStmt state) {
			return state.stmts;
		}

		@Override
		public SBlockStmt doRebuildParentState(SBlockStmt state, BUTree<SNodeList> child) {
			return state.withStmts(child);
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
			token(LToken.BraceLeft)
					.withSpacingAfter(newLine())
					.withIndentationAfter(indent(Block)),
			child(STMTS, listShape),
			alternative(childIs(STMTS, not(empty())),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(Block))
							.withSpacingBefore(newLine()),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(Block))
			)
	);
}
