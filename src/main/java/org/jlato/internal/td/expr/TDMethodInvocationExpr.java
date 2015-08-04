package org.jlato.internal.td.expr;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.expr.SMethodInvocationExpr;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.MethodInvocationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDMethodInvocationExpr extends TreeBase<SMethodInvocationExpr, Expr, MethodInvocationExpr> implements MethodInvocationExpr {

	public Kind kind() {
		return Kind.MethodInvocationExpr;
	}

	public TDMethodInvocationExpr(SLocation<SMethodInvocationExpr> location) {
		super(location);
	}

	public TDMethodInvocationExpr(NodeOption<Expr> scope, NodeList<Type> typeArgs, Name name, NodeList<Expr> args) {
		super(new SLocation<SMethodInvocationExpr>(SMethodInvocationExpr.make(TreeBase.<SNodeOptionState>treeOf(scope), TreeBase.<SNodeListState>treeOf(typeArgs), TreeBase.<SName>treeOf(name), TreeBase.<SNodeListState>treeOf(args))));
	}

	public NodeOption<Expr> scope() {
		return location.safeTraversal(SMethodInvocationExpr.SCOPE);
	}

	public MethodInvocationExpr withScope(NodeOption<Expr> scope) {
		return location.safeTraversalReplace(SMethodInvocationExpr.SCOPE, scope);
	}

	public MethodInvocationExpr withScope(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SMethodInvocationExpr.SCOPE, mutation);
	}

	public NodeList<Type> typeArgs() {
		return location.safeTraversal(SMethodInvocationExpr.TYPE_ARGS);
	}

	public MethodInvocationExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(SMethodInvocationExpr.TYPE_ARGS, typeArgs);
	}

	public MethodInvocationExpr withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(SMethodInvocationExpr.TYPE_ARGS, mutation);
	}

	public Name name() {
		return location.safeTraversal(SMethodInvocationExpr.NAME);
	}

	public MethodInvocationExpr withName(Name name) {
		return location.safeTraversalReplace(SMethodInvocationExpr.NAME, name);
	}

	public MethodInvocationExpr withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SMethodInvocationExpr.NAME, mutation);
	}

	public NodeList<Expr> args() {
		return location.safeTraversal(SMethodInvocationExpr.ARGS);
	}

	public MethodInvocationExpr withArgs(NodeList<Expr> args) {
		return location.safeTraversalReplace(SMethodInvocationExpr.ARGS, args);
	}

	public MethodInvocationExpr withArgs(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(SMethodInvocationExpr.ARGS, mutation);
	}
}
