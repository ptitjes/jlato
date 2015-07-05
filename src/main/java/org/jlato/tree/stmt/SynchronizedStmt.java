package org.jlato.tree.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.space;

public class SynchronizedStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public SynchronizedStmt instantiate(SLocation location) {
			return new SynchronizedStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private SynchronizedStmt(SLocation location) {
		super(location);
	}

	public SynchronizedStmt(Expr expr, BlockStmt block) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(expr, block)))));
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public SynchronizedStmt withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	public BlockStmt block() {
		return location.nodeChild(BLOCK);
	}

	public SynchronizedStmt withBlock(BlockStmt block) {
		return location.nodeWithChild(BLOCK, block);
	}

	private static final int EXPR = 0;
	private static final int BLOCK = 1;

	public final static LexicalShape shape = composite(
			token(LToken.Synchronized),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(EXPR),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BLOCK)
	);
}
