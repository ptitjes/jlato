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
import org.jlato.internal.bu.STree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LSCondition;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.newLine;
import org.jlato.internal.bu.STraversal;

public class SwitchCase extends TreeBase<SNodeState> implements Tree {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public SwitchCase instantiate(SLocation location) {
			return new SwitchCase(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private SwitchCase(SLocation<SNodeState> location) {
		super(location);
	}

	public SwitchCase(NodeOption<Expr> label, NodeList<Stmt> stmts) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(label, stmts)))));
	}

	public NodeOption<Expr> label() {
		return location.safeTraversal(LABEL);
	}

	public SwitchCase withLabel(NodeOption<Expr> label) {
		return location.safeTraversalReplace(LABEL, label);
	}

	public SwitchCase withLabel(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(LABEL, mutation);
	}

	public NodeList<Stmt> stmts() {
		return location.safeTraversal(STMTS);
	}

	public SwitchCase withStmts(NodeList<Stmt> stmts) {
		return location.safeTraversalReplace(STMTS, stmts);
	}

	public SwitchCase withStmts(Mutation<NodeList<Stmt>> mutation) {
		return location.safeTraversalMutate(STMTS, mutation);
	}

	private static final STraversal<SNodeState> LABEL = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> STMTS = SNodeState.childTraversal(1);

	public final static LexicalShape shape = composite(
			alternative(childIs(LABEL, some()),
					composite(token(LToken.Case), child(LABEL, element())),
					token(LToken.Default)
			),
			token(LToken.Colon).withSpacingAfter(newLine()),
			none().withIndentationAfter(indent(BLOCK)),
			child(STMTS, Stmt.listShape),
			none().withIndentationBefore(unIndent(BLOCK))
	);

	public static final LexicalShape listShape = list(none().withSpacingAfter(newLine()));
}
