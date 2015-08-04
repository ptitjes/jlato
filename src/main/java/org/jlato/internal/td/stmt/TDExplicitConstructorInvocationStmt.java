package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.stmt.SExplicitConstructorInvocationStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.ExplicitConstructorInvocationStmt;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDExplicitConstructorInvocationStmt extends TDTree<SExplicitConstructorInvocationStmt, Stmt, ExplicitConstructorInvocationStmt> implements ExplicitConstructorInvocationStmt {

	public Kind kind() {
		return Kind.ExplicitConstructorInvocationStmt;
	}

	public TDExplicitConstructorInvocationStmt(TDLocation<SExplicitConstructorInvocationStmt> location) {
		super(location);
	}

	public TDExplicitConstructorInvocationStmt(NodeList<Type> typeArgs, boolean isThis, NodeOption<Expr> expr, NodeList<Expr> args) {
		super(new TDLocation<SExplicitConstructorInvocationStmt>(SExplicitConstructorInvocationStmt.make(TDTree.<SNodeList>treeOf(typeArgs), isThis, TDTree.<SNodeOption>treeOf(expr), TDTree.<SNodeList>treeOf(args))));
	}

	public NodeList<Type> typeArgs() {
		return location.safeTraversal(SExplicitConstructorInvocationStmt.TYPE_ARGS);
	}

	public ExplicitConstructorInvocationStmt withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(SExplicitConstructorInvocationStmt.TYPE_ARGS, typeArgs);
	}

	public ExplicitConstructorInvocationStmt withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(SExplicitConstructorInvocationStmt.TYPE_ARGS, mutation);
	}

	public boolean isThis() {
		return location.safeProperty(SExplicitConstructorInvocationStmt.THIS);
	}

	public ExplicitConstructorInvocationStmt setThis(boolean isThis) {
		return location.safePropertyReplace(SExplicitConstructorInvocationStmt.THIS, isThis);
	}

	public ExplicitConstructorInvocationStmt setThis(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SExplicitConstructorInvocationStmt.THIS, mutation);
	}

	public NodeOption<Expr> expr() {
		return location.safeTraversal(SExplicitConstructorInvocationStmt.EXPR);
	}

	public ExplicitConstructorInvocationStmt withExpr(NodeOption<Expr> expr) {
		return location.safeTraversalReplace(SExplicitConstructorInvocationStmt.EXPR, expr);
	}

	public ExplicitConstructorInvocationStmt withExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SExplicitConstructorInvocationStmt.EXPR, mutation);
	}

	public NodeList<Expr> args() {
		return location.safeTraversal(SExplicitConstructorInvocationStmt.ARGS);
	}

	public ExplicitConstructorInvocationStmt withArgs(NodeList<Expr> args) {
		return location.safeTraversalReplace(SExplicitConstructorInvocationStmt.ARGS, args);
	}

	public ExplicitConstructorInvocationStmt withArgs(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(SExplicitConstructorInvocationStmt.ARGS, mutation);
	}
}
