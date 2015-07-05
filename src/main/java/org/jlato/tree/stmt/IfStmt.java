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

	public IfStmt(Expr condition, Stmt thenStmt, Stmt elseStmt) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(condition, thenStmt, elseStmt)))));
	}

	public Expr condition() {
		return location.nodeChild(CONDITION);
	}

	public IfStmt withCondition(Expr condition) {
		return location.nodeWithChild(CONDITION, condition);
	}

	public Stmt thenStmt() {
		return location.nodeChild(THEN_STMT);
	}

	public IfStmt withThenStmt(Stmt thenStmt) {
		return location.nodeWithChild(THEN_STMT, thenStmt);
	}

	public Stmt elseStmt() {
		return location.nodeChild(ELSE_STMT);
	}

	public IfStmt withElseStmt(Stmt elseStmt) {
		return location.nodeWithChild(ELSE_STMT, elseStmt);
	}

	private static final int CONDITION = 0;
	private static final int THEN_STMT = 1;
	private static final int ELSE_STMT = 2;

	public final static LexicalShape shape = composite(
			token(LToken.If), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(CONDITION),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(THEN_STMT),
			nonNullChild(ELSE_STMT, composite(
					token(LToken.Else).withSpacing(space(), space()),
					child(ELSE_STMT)
			))
	);
}
