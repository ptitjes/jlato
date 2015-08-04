package org.jlato.internal.td.expr;

import org.jlato.internal.bu.SNodeEither;
import org.jlato.internal.bu.SNodeList;
import org.jlato.internal.bu.expr.SLambdaExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeEither;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.LambdaExpr;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.util.Mutation;

public class TDLambdaExpr extends TDTree<SLambdaExpr, Expr, LambdaExpr> implements LambdaExpr {

	public Kind kind() {
		return Kind.LambdaExpr;
	}

	public TDLambdaExpr(TDLocation<SLambdaExpr> location) {
		super(location);
	}

	public TDLambdaExpr(NodeList<FormalParameter> params, boolean hasParens, NodeEither<Expr, BlockStmt> body) {
		super(new TDLocation<SLambdaExpr>(SLambdaExpr.make(TDTree.<SNodeList>treeOf(params), hasParens, TDTree.<SNodeEither>treeOf(body))));
	}

	public NodeList<FormalParameter> params() {
		return location.safeTraversal(SLambdaExpr.PARAMS);
	}

	public LambdaExpr withParams(NodeList<FormalParameter> params) {
		return location.safeTraversalReplace(SLambdaExpr.PARAMS, params);
	}

	public LambdaExpr withParams(Mutation<NodeList<FormalParameter>> mutation) {
		return location.safeTraversalMutate(SLambdaExpr.PARAMS, mutation);
	}

	public boolean hasParens() {
		return location.safeProperty(SLambdaExpr.PARENS);
	}

	public LambdaExpr setParens(boolean hasParens) {
		return location.safePropertyReplace(SLambdaExpr.PARENS, hasParens);
	}

	public LambdaExpr setParens(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SLambdaExpr.PARENS, mutation);
	}

	public NodeEither<Expr, BlockStmt> body() {
		return location.safeTraversal(SLambdaExpr.BODY);
	}

	public LambdaExpr withBody(NodeEither<Expr, BlockStmt> body) {
		return location.safeTraversalReplace(SLambdaExpr.BODY, body);
	}

	public LambdaExpr withBody(Mutation<NodeEither<Expr, BlockStmt>> mutation) {
		return location.safeTraversalMutate(SLambdaExpr.BODY, mutation);
	}
}
