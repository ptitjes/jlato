package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.SLocation;
import org.jlato.tree.*;
import org.jlato.tree.decl.Parameter;
import org.jlato.tree.stmt.BlockStmt;

public class LambdaExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public LambdaExpr instantiate(SLocation location) {
			return new LambdaExpr(location);
		}
	};

	private LambdaExpr(SLocation location) {
		super(location);
	}

	public LambdaExpr(NodeList<Parameter> parameters, Expr expr) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(parameters, expr, null)))));
	}

	public LambdaExpr(NodeList<Parameter> parameters, BlockStmt body) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(parameters, null, body)))));
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
}
