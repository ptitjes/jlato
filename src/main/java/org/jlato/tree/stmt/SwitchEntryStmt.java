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
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.*;

import static org.jlato.internal.shapes.IndentationConstraint.Factory.indent;
import static org.jlato.internal.shapes.IndentationConstraint.Factory.unIndent;
import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.newLine;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;

public class SwitchEntryStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public SwitchEntryStmt instantiate(SLocation location) {
			return new SwitchEntryStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private SwitchEntryStmt(SLocation location) {
		super(location);
	}

	public SwitchEntryStmt(Expr label, NodeList<Stmt> stmts) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(label, stmts)))));
	}

	public Expr label() {
		return location.nodeChild(LABEL);
	}

	public SwitchEntryStmt withLabel(Expr label) {
		return location.nodeWithChild(LABEL, label);
	}

	public NodeList<Stmt> stmts() {
		return location.nodeChild(STMTS);
	}

	public SwitchEntryStmt withStmts(NodeList<Stmt> stmts) {
		return location.nodeWithChild(STMTS, stmts);
	}

	private static final int LABEL = 0;
	private static final int STMTS = 1;

	public final static LexicalShape shape = composite(
			nonNullChild(LABEL,
					composite(token(LToken.Case), child(LABEL)),
					token(LToken.Default)
			),
			token(LToken.Colon).withSpacingAfter(newLine()),
			nonNullChild(STMTS,
					composite(
							none().withIndentation(indent(BLOCK)),
							child(STMTS, Stmt.listShape),
							none().withIndentation(unIndent(BLOCK))
					)
			)
	);

	public static final LexicalShape listShape = list(none().withSpacing(newLine()));
}
