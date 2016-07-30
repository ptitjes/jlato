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
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDReturnStmt;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a 'return' statement.
 */
public class SReturnStmt extends SNode<SReturnStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new 'return' statement.
	 *
	 * @param expr the expression child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a 'return' statement.
	 */
	public static BUTree<SReturnStmt> make(BUTree<SNodeOption> expr) {
		return new BUTree<SReturnStmt>(new SReturnStmt(expr));
	}

	/**
	 * The expression of this 'return' statement state.
	 */
	public final BUTree<SNodeOption> expr;

	/**
	 * Constructs a 'return' statement state.
	 *
	 * @param expr the expression child <code>BUTree</code>.
	 */
	public SReturnStmt(BUTree<SNodeOption> expr) {
		this.expr = expr;
	}

	/**
	 * Returns the kind of this 'return' statement.
	 *
	 * @return the kind of this 'return' statement.
	 */
	@Override
	public Kind kind() {
		return Kind.ReturnStmt;
	}

	/**
	 * Replaces the expression of this 'return' statement state.
	 *
	 * @param expr the replacement for the expression of this 'return' statement state.
	 * @return the resulting mutated 'return' statement state.
	 */
	public SReturnStmt withExpr(BUTree<SNodeOption> expr) {
		return new SReturnStmt(expr);
	}

	/**
	 * Builds a 'return' statement facade for the specified 'return' statement <code>TDLocation</code>.
	 *
	 * @param location the 'return' statement <code>TDLocation</code>.
	 * @return a 'return' statement facade for the specified 'return' statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SReturnStmt> location) {
		return new TDReturnStmt(location);
	}

	/**
	 * Returns the shape for this 'return' statement state.
	 *
	 * @return the shape for this 'return' statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'return' statement state.
	 *
	 * @return the first child traversal for this 'return' statement state.
	 */
	@Override
	public STraversal firstChild() {
		return EXPR;
	}

	/**
	 * Returns the last child traversal for this 'return' statement state.
	 *
	 * @return the last child traversal for this 'return' statement state.
	 */
	@Override
	public STraversal lastChild() {
		return EXPR;
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
		SReturnStmt state = (SReturnStmt) o;
		if (!expr.equals(state.expr))
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
		result = 37 * result + expr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SReturnStmt, SNodeOption, NodeOption<Expr>> EXPR = new STypeSafeTraversal<SReturnStmt, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SReturnStmt state) {
			return state.expr;
		}

		@Override
		public SReturnStmt doRebuildParentState(SReturnStmt state, BUTree<SNodeOption> child) {
			return state.withExpr(child);
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
			token(LToken.Return),
			child(EXPR, when(some(), element().withSpacingBefore(space()))),
			token(LToken.SemiColon)
	);
}
