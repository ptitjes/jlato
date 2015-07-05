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

public class WhileStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public WhileStmt instantiate(SLocation location) {
			return new WhileStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private WhileStmt(SLocation location) {
		super(location);
	}

	public WhileStmt(Expr condition, Stmt body) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(condition, body)))));
	}

	public Expr condition() {
		return location.nodeChild(CONDITION);
	}

	public WhileStmt withCondition(Expr condition) {
		return location.nodeWithChild(CONDITION, condition);
	}

	public Stmt body() {
		return location.nodeChild(BODY);
	}

	public WhileStmt withBody(Stmt body) {
		return location.nodeWithChild(BODY, body);
	}

	private static final int CONDITION = 0;
	private static final int BODY = 1;

	public final static LexicalShape shape = composite(
			token(LToken.While), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(CONDITION),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);
}
