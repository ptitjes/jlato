package org.jlato.internal.td.expr;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SMethodReferenceExpr;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.MethodReferenceExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDMethodReferenceExpr extends TDTree<SMethodReferenceExpr, Expr, MethodReferenceExpr> implements MethodReferenceExpr {

	public Kind kind() {
		return Kind.MethodReferenceExpr;
	}

	public TDMethodReferenceExpr(SLocation<SMethodReferenceExpr> location) {
		super(location);
	}

	public TDMethodReferenceExpr(Expr scope, NodeList<Type> typeArgs, Name name) {
		super(new SLocation<SMethodReferenceExpr>(SMethodReferenceExpr.make(TDTree.<SExpr>treeOf(scope), TDTree.<SNodeListState>treeOf(typeArgs), TDTree.<SName>treeOf(name))));
	}

	public Expr scope() {
		return location.safeTraversal(SMethodReferenceExpr.SCOPE);
	}

	public MethodReferenceExpr withScope(Expr scope) {
		return location.safeTraversalReplace(SMethodReferenceExpr.SCOPE, scope);
	}

	public MethodReferenceExpr withScope(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SMethodReferenceExpr.SCOPE, mutation);
	}

	public NodeList<Type> typeArgs() {
		return location.safeTraversal(SMethodReferenceExpr.TYPE_ARGS);
	}

	public MethodReferenceExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(SMethodReferenceExpr.TYPE_ARGS, typeArgs);
	}

	public MethodReferenceExpr withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(SMethodReferenceExpr.TYPE_ARGS, mutation);
	}

	public Name name() {
		return location.safeTraversal(SMethodReferenceExpr.NAME);
	}

	public MethodReferenceExpr withName(Name name) {
		return location.safeTraversalReplace(SMethodReferenceExpr.NAME, name);
	}

	public MethodReferenceExpr withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SMethodReferenceExpr.NAME, mutation);
	}
}
