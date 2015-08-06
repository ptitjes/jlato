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

/**
 * An explicit constructor invocation statement.
 */
public class TDExplicitConstructorInvocationStmt extends TDTree<SExplicitConstructorInvocationStmt, Stmt, ExplicitConstructorInvocationStmt> implements ExplicitConstructorInvocationStmt {

	/**
	 * Returns the kind of this explicit constructor invocation statement.
	 *
	 * @return the kind of this explicit constructor invocation statement.
	 */
	public Kind kind() {
		return Kind.ExplicitConstructorInvocationStmt;
	}

	/**
	 * Creates an explicit constructor invocation statement for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDExplicitConstructorInvocationStmt(TDLocation<SExplicitConstructorInvocationStmt> location) {
		super(location);
	}

	/**
	 * Creates an explicit constructor invocation statement with the specified child trees.
	 *
	 * @param typeArgs the type args child tree.
	 * @param isThis   the is 'this' child tree.
	 * @param expr     the expression child tree.
	 * @param args     the args child tree.
	 */
	public TDExplicitConstructorInvocationStmt(NodeList<Type> typeArgs, boolean isThis, NodeOption<Expr> expr, NodeList<Expr> args) {
		super(new TDLocation<SExplicitConstructorInvocationStmt>(SExplicitConstructorInvocationStmt.make(TDTree.<SNodeList>treeOf(typeArgs), isThis, TDTree.<SNodeOption>treeOf(expr), TDTree.<SNodeList>treeOf(args))));
	}

	/**
	 * Returns the type args of this explicit constructor invocation statement.
	 *
	 * @return the type args of this explicit constructor invocation statement.
	 */
	public NodeList<Type> typeArgs() {
		return location.safeTraversal(SExplicitConstructorInvocationStmt.TYPE_ARGS);
	}

	/**
	 * Replaces the type args of this explicit constructor invocation statement.
	 *
	 * @param typeArgs the replacement for the type args of this explicit constructor invocation statement.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	public ExplicitConstructorInvocationStmt withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(SExplicitConstructorInvocationStmt.TYPE_ARGS, typeArgs);
	}

	/**
	 * Mutates the type args of this explicit constructor invocation statement.
	 *
	 * @param mutation the mutation to apply to the type args of this explicit constructor invocation statement.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	public ExplicitConstructorInvocationStmt withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(SExplicitConstructorInvocationStmt.TYPE_ARGS, mutation);
	}

	/**
	 * Tests whether this explicit constructor invocation statement is 'this'.
	 *
	 * @return <code>true</code> if this explicit constructor invocation statement is 'this', <code>false</code> otherwise.
	 */
	public boolean isThis() {
		return location.safeProperty(SExplicitConstructorInvocationStmt.THIS);
	}

	/**
	 * Sets whether this explicit constructor invocation statement is 'this'.
	 *
	 * @param isThis <code>true</code> if this explicit constructor invocation statement is 'this', <code>false</code> otherwise.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	public ExplicitConstructorInvocationStmt setThis(boolean isThis) {
		return location.safePropertyReplace(SExplicitConstructorInvocationStmt.THIS, isThis);
	}

	/**
	 * Mutates whether this explicit constructor invocation statement is 'this'.
	 *
	 * @param mutation the mutation to apply to whether this explicit constructor invocation statement is 'this'.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	public ExplicitConstructorInvocationStmt setThis(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SExplicitConstructorInvocationStmt.THIS, mutation);
	}

	/**
	 * Returns the expression of this explicit constructor invocation statement.
	 *
	 * @return the expression of this explicit constructor invocation statement.
	 */
	public NodeOption<Expr> expr() {
		return location.safeTraversal(SExplicitConstructorInvocationStmt.EXPR);
	}

	/**
	 * Replaces the expression of this explicit constructor invocation statement.
	 *
	 * @param expr the replacement for the expression of this explicit constructor invocation statement.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	public ExplicitConstructorInvocationStmt withExpr(NodeOption<Expr> expr) {
		return location.safeTraversalReplace(SExplicitConstructorInvocationStmt.EXPR, expr);
	}

	/**
	 * Mutates the expression of this explicit constructor invocation statement.
	 *
	 * @param mutation the mutation to apply to the expression of this explicit constructor invocation statement.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	public ExplicitConstructorInvocationStmt withExpr(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SExplicitConstructorInvocationStmt.EXPR, mutation);
	}

	/**
	 * Returns the args of this explicit constructor invocation statement.
	 *
	 * @return the args of this explicit constructor invocation statement.
	 */
	public NodeList<Expr> args() {
		return location.safeTraversal(SExplicitConstructorInvocationStmt.ARGS);
	}

	/**
	 * Replaces the args of this explicit constructor invocation statement.
	 *
	 * @param args the replacement for the args of this explicit constructor invocation statement.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	public ExplicitConstructorInvocationStmt withArgs(NodeList<Expr> args) {
		return location.safeTraversalReplace(SExplicitConstructorInvocationStmt.ARGS, args);
	}

	/**
	 * Mutates the args of this explicit constructor invocation statement.
	 *
	 * @param mutation the mutation to apply to the args of this explicit constructor invocation statement.
	 * @return the resulting mutated explicit constructor invocation statement.
	 */
	public ExplicitConstructorInvocationStmt withArgs(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(SExplicitConstructorInvocationStmt.ARGS, mutation);
	}
}
