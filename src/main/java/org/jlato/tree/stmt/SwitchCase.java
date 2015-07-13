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
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.newLine;

public class SwitchCase extends Tree {

	public final static Tree.Kind kind = new Tree.Kind() {
		public SwitchCase instantiate(SLocation location) {
			return new SwitchCase(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private SwitchCase(SLocation location) {
		super(location);
	}

	public SwitchCase(Expr label, NodeList<Stmt> stmts) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(label, stmts)))));
	}

	public Expr label() {
		return location.nodeChild(LABEL);
	}

	public SwitchCase withLabel(Expr label) {
		return location.nodeWithChild(LABEL, label);
	}

	public SwitchCase withLabel(Mutation<Expr> label) {
		return location.nodeMutateChild(LABEL, label);
	}

	public NodeList<Stmt> stmts() {
		return location.nodeChild(STMTS);
	}

	public SwitchCase withStmts(NodeList<Stmt> stmts) {
		return location.nodeWithChild(STMTS, stmts);
	}

	public SwitchCase withStmts(Mutation<NodeList<Stmt>> stmts) {
		return location.nodeMutateChild(STMTS, stmts);
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
							none().withIndentationAfter(indent(BLOCK)),
							child(STMTS, Stmt.listShape),
							none().withIndentationBefore(unIndent(BLOCK))
					)
			)
	);

	public static final LexicalShape listShape = list(none().withSpacingAfter(newLine()));
}
