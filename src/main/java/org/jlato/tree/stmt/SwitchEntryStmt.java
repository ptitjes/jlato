package org.jlato.tree.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.*;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.newLine;
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
			), token(LToken.Colon).withSpacingAfter(newLine()),
			nonNullChild(STMTS, composite(
					indent(BLOCK),
					children(STMTS, none().withSpacing(newLine())),
					unIndent(BLOCK)
			))
	);
}
