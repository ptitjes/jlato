package org.jlato.tree.stmt;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public interface ExplicitConstructorInvocationStmt extends Stmt, TreeCombinators<ExplicitConstructorInvocationStmt> {

	NodeList<Type> typeArgs();

	ExplicitConstructorInvocationStmt withTypeArgs(NodeList<Type> typeArgs);

	ExplicitConstructorInvocationStmt withTypeArgs(Mutation<NodeList<Type>> mutation);

	boolean isThis();

	ExplicitConstructorInvocationStmt setThis(boolean isThis);

	ExplicitConstructorInvocationStmt setThis(Mutation<Boolean> mutation);

	NodeOption<Expr> expr();

	ExplicitConstructorInvocationStmt withExpr(NodeOption<Expr> expr);

	ExplicitConstructorInvocationStmt withExpr(Mutation<NodeOption<Expr>> mutation);

	NodeList<Expr> args();

	ExplicitConstructorInvocationStmt withArgs(NodeList<Expr> args);

	ExplicitConstructorInvocationStmt withArgs(Mutation<NodeList<Expr>> mutation);
}
