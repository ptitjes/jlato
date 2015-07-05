package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LSOption;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class UnaryExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public UnaryExpr instantiate(SLocation location) {
			return new UnaryExpr(location);
		}

		public LexicalShape shape() {
			return shape;
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

	public static boolean isPrefix(UnaryOp op) {
		return !isPostfix(op);
	}

	public static boolean isPostfix(UnaryOp op) {
		return op == UnaryOp.PostIncrement || op == UnaryOp.PostDecrement;
	}

	private static final int EXPR = 0;

	private static final int OPERATOR = 0;

	private final static LexicalShape opShape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			return ((UnaryOp) tree.state.data(OPERATOR)).token;
		}
	});

	public final static LexicalShape shape = option(new LexicalShape.ShapeProvider() {
		public LexicalShape shapeFor(STree tree) {
			final UnaryOp op = (UnaryOp) tree.state.data(OPERATOR);
			return isPrefix(op) ? composite(opShape, child(EXPR)) : composite(child(EXPR), opShape);
		}
	});

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
