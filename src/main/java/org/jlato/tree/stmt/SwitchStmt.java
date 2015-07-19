/*
 * Copyright (C) 2015 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.tree.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.FormattingSettings.SpacingLocation.SwitchStmt_AfterSwitchKeyword;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.*;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class SwitchStmt extends TreeBase<SwitchStmt.State, Stmt, SwitchStmt> implements Stmt {

	public Kind kind() {
		return Kind.SwitchStmt;
	}

	private SwitchStmt(SLocation<SwitchStmt.State> location) {
		super(location);
	}

	public static STree<SwitchStmt.State> make(STree<Expr.State> selector, STree<SNodeListState> cases) {
		return new STree<SwitchStmt.State>(new SwitchStmt.State(selector, cases));
	}

	public SwitchStmt(Expr selector, NodeList<SwitchCase> cases) {
		super(new SLocation<SwitchStmt.State>(make(TreeBase.<Expr.State>nodeOf(selector), TreeBase.<SNodeListState>nodeOf(cases))));
	}

	public Expr selector() {
		return location.safeTraversal(SELECTOR);
	}

	public SwitchStmt withSelector(Expr selector) {
		return location.safeTraversalReplace(SELECTOR, selector);
	}

	public SwitchStmt withSelector(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SELECTOR, mutation);
	}

	public NodeList<SwitchCase> cases() {
		return location.safeTraversal(CASES);
	}

	public SwitchStmt withCases(NodeList<SwitchCase> cases) {
		return location.safeTraversalReplace(CASES, cases);
	}

	public SwitchStmt withCases(Mutation<NodeList<SwitchCase>> mutation) {
		return location.safeTraversalMutate(CASES, mutation);
	}

	public final static LexicalShape shape = composite(
			token(LToken.Switch),
			token(LToken.ParenthesisLeft).withSpacingBefore(spacing(SwitchStmt_AfterSwitchKeyword)),
			child(SELECTOR),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			nonEmptyChildren(CASES,
					composite(
							token(LToken.BraceLeft)
									.withSpacingAfter(newLine())
									.withIndentationAfter(indent(BLOCK)),
							child(CASES, SwitchCase.listShape),
							token(LToken.BraceRight)
									.withIndentationBefore(unIndent(BLOCK))
									.withSpacingBefore(newLine())
					),
					composite(
							token(LToken.BraceLeft)
									.withSpacingAfter(newLine())
									.withIndentationAfter(indent(BLOCK)),
							token(LToken.BraceRight)
									.withIndentationBefore(unIndent(BLOCK))
					)
			)
	);
}
