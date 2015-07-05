package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.Parameter;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class LambdaExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public LambdaExpr instantiate(SLocation location) {
			return new LambdaExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private LambdaExpr(SLocation location) {
		super(location);
	}

	public LambdaExpr(NodeList<Parameter> parameters, Expr expr) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(parameters, expr, null)))));
	}

	public LambdaExpr(NodeList<Parameter> parameters, BlockStmt body) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(parameters, null, body)))));
	}

	public NodeList<Parameter> parameters() {
		return location.nodeChild(PARAMETERS);
	}

	public LambdaExpr withParameters(NodeList<Parameter> parameters) {
		return location.nodeWithChild(PARAMETERS, parameters);
	}

	public Expr expr() {
		return location.nodeChild(EXPR);
	}

	public LambdaExpr withExpr(Expr expr) {
		return location.nodeWithChild(EXPR, expr);
	}

	public BlockStmt block() {
		return location.nodeChild(BLOCK);
	}

	public LambdaExpr withBlock(BlockStmt block) {
		return location.nodeWithChild(BLOCK, block);
	}

	private static final int PARAMETERS = 0;
	private static final int EXPR = 1;
	private static final int BLOCK = 2;

	public final static LexicalShape shape = composite(
			token(LToken.ParenthesisLeft),
			nonNullChild(PARAMETERS, composite(children(PARAMETERS, token(LToken.Comma)))),
			token(LToken.ParenthesisRight),
			token(LToken.Arrow),
			nonNullChild(EXPR, child(EXPR), child(BLOCK))
	);
}
