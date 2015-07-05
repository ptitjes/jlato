package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

public class UnaryExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public UnaryExpr instantiate(SLocation location) {
			return new UnaryExpr(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private UnaryExpr(SLocation location) {
		super(location);
	}

	public UnaryExpr(UnaryOp operator, Expr expr) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(expr), dataOf(operator)))));
	}

	public UnaryOp op() {
		return location.nodeData(OPERATOR);
	}

	public UnaryExpr withOp(UnaryOp operator) {
		return location.nodeWithData(OPERATOR, operator);
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public UnaryExpr withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	private static final int EXPR = 0;

	private static final int OPERATOR = 0;

	public enum UnaryOp {
		Positive(LToken.Plus),
		Negative(LToken.Minus),
		PreIncrement(LToken.Increment),
		PreDecrement(LToken.Decrement),
		Not(LToken.Not),
		Inverse(LToken.Inverse),
		PostIncrement(LToken.Increment),
		PostDecrement(LToken.Decrement),
		// Keep last comma
		;


		protected final LToken token;

		UnaryOp(LToken token) {
			this.token = token;
		}

		public String toString() {
			return token.toString();
		}
	}
}
