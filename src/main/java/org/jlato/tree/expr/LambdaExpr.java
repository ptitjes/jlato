package org.jlato.tree.expr;

import org.jlato.tree.NodeEither;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.util.Mutation;

public interface LambdaExpr extends Expr, TreeCombinators<LambdaExpr> {

	NodeList<FormalParameter> params();

	LambdaExpr withParams(NodeList<FormalParameter> params);

	LambdaExpr withParams(Mutation<NodeList<FormalParameter>> mutation);

	boolean hasParens();

	LambdaExpr setParens(boolean hasParens);

	LambdaExpr setParens(Mutation<Boolean> mutation);

	NodeEither<Expr, BlockStmt> body();

	LambdaExpr withBody(NodeEither<Expr, BlockStmt> body);

	LambdaExpr withBody(Mutation<NodeEither<Expr, BlockStmt>> mutation);
}
