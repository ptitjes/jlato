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
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LSCondition;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.*;

public class IfStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public IfStmt instantiate(SLocation location) {
			return new IfStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private IfStmt(SLocation location) {
		super(location);
	}

	public IfStmt(Expr condition, Stmt thenStmt, NodeOption<Stmt> elseStmt) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(condition, thenStmt, elseStmt)))));
	}

	public Expr condition() {
		return location.nodeChild(CONDITION);
	}

	public IfStmt withCondition(Expr condition) {
		return location.nodeWithChild(CONDITION, condition);
	}

	public IfStmt withCondition(Mutation<Expr> mutation) {
		return location.nodeMutateChild(CONDITION, mutation);
	}

	public Stmt thenStmt() {
		return location.nodeChild(THEN_STMT);
	}

	public IfStmt withThenStmt(Stmt thenStmt) {
		return location.nodeWithChild(THEN_STMT, thenStmt);
	}

	public IfStmt withThenStmt(Mutation<Stmt> mutation) {
		return location.nodeMutateChild(THEN_STMT, mutation);
	}

	public NodeOption<Stmt> elseStmt() {
		return location.nodeChild(ELSE_STMT);
	}

	public IfStmt withElseStmt(NodeOption<Stmt> elseStmt) {
		return location.nodeWithChild(ELSE_STMT, elseStmt);
	}

	public IfStmt withElseStmt(Mutation<NodeOption<Stmt>> mutation) {
		return location.nodeMutateChild(ELSE_STMT, mutation);
	}

	private static final int CONDITION = 0;
	private static final int THEN_STMT = 1;
	private static final int ELSE_STMT = 2;

	public final static LexicalShape shape = composite(
			token(LToken.If), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(CONDITION),
			token(LToken.ParenthesisRight),
			alternative(childHas(THEN_STMT, kind(BlockStmt.kind)),
					composite(none().withSpacingAfter(space()), child(THEN_STMT)),
					alternative(childHas(THEN_STMT, kind(ExpressionStmt.kind)),
							composite(
									none().withSpacingAfter(spacing(IfStmt_ThenExpressionStmt)).withIndentationAfter(indent(BLOCK)),
									child(THEN_STMT),
									none().withIndentationBefore(unIndent(BLOCK)).withSpacingAfter(newLine())
							),
							composite(
									none().withSpacingAfter(spacing(IfStmt_ThenOtherStmt)).withIndentationAfter(indent(BLOCK)),
									child(THEN_STMT),
									none().withIndentationBefore(unIndent(BLOCK)).withSpacingAfter(newLine())
							)
					)
			),
			when(childIs(ELSE_STMT, some()), composite(
					token(LToken.Else).withSpacingBefore(space()),
					alternative(childHas(ELSE_STMT, elementHas(kind(BlockStmt.kind))),
							composite(none().withSpacingAfter(space()), child(ELSE_STMT, element())),
							alternative(childHas(ELSE_STMT, elementHas(kind(IfStmt.kind))),
									composite(
											none().withSpacingAfter(spacing(IfStmt_ElseIfStmt)),
											child(ELSE_STMT, element())
									),
									alternative(childHas(ELSE_STMT, elementHas(kind(ExpressionStmt.kind))),
											composite(
													none().withSpacingAfter(spacing(IfStmt_ElseExpressionStmt)).withIndentationAfter(indent(BLOCK)),
													child(ELSE_STMT, element()),
													none().withIndentationBefore(unIndent(BLOCK))
											),
											composite(
													none().withSpacingAfter(spacing(IfStmt_ElseOtherStmt)).withIndentationAfter(indent(BLOCK)),
													child(ELSE_STMT, element()),
													none().withIndentationBefore(unIndent(BLOCK)).withSpacingAfter(newLine())
											)
									)
							)
					)
			))
	);
}
