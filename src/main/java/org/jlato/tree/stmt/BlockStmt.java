package org.jlato.tree.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.newLine;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;

public class BlockStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public BlockStmt instantiate(SLocation location) {
			return new BlockStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private BlockStmt(SLocation location) {
		super(location);
	}

	public BlockStmt(NodeList<Stmt> stmts) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(stmts)))));
	}

	public NodeList<Stmt> stmts() {
		return location.nodeChild(STMTS);
	}

	public BlockStmt withStmts(NodeList<Stmt> stmts) {
		return location.nodeWithChild(STMTS, stmts);
	}

	private static final int STMTS = 0;

	public final static LexicalShape shape = composite(
			nonEmptyChildren(STMTS,
					composite(
							token(LToken.BraceLeft).withSpacingAfter(newLine()), indent(BLOCK),
							children(STMTS, none().withSpacing(newLine())),
							unIndent(BLOCK), token(LToken.BraceRight).withSpacingBefore(newLine())
					),
					composite(token(LToken.BraceLeft).withSpacingAfter(newLine()), token(LToken.BraceRight))
			)
	);
}
