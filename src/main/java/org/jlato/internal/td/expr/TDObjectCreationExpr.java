package org.jlato.internal.td.expr;

import org.jlato.internal.bu.SNodeList;
import org.jlato.internal.bu.SNodeOption;
import org.jlato.internal.bu.expr.SObjectCreationExpr;
import org.jlato.internal.bu.type.SQualifiedType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.ObjectCreationExpr;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDObjectCreationExpr extends TDTree<SObjectCreationExpr, Expr, ObjectCreationExpr> implements ObjectCreationExpr {

	public Kind kind() {
		return Kind.ObjectCreationExpr;
	}

	public TDObjectCreationExpr(TDLocation<SObjectCreationExpr> location) {
		super(location);
	}

	public TDObjectCreationExpr(NodeOption<Expr> scope, NodeList<Type> typeArgs, QualifiedType type, NodeList<Expr> args, NodeOption<NodeList<MemberDecl>> body) {
		super(new TDLocation<SObjectCreationExpr>(SObjectCreationExpr.make(TDTree.<SNodeOption>treeOf(scope), TDTree.<SNodeList>treeOf(typeArgs), TDTree.<SQualifiedType>treeOf(type), TDTree.<SNodeList>treeOf(args), TDTree.<SNodeOption>treeOf(body))));
	}

	public NodeOption<Expr> scope() {
		return location.safeTraversal(SObjectCreationExpr.SCOPE);
	}

	public ObjectCreationExpr withScope(NodeOption<Expr> scope) {
		return location.safeTraversalReplace(SObjectCreationExpr.SCOPE, scope);
	}

	public ObjectCreationExpr withScope(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SObjectCreationExpr.SCOPE, mutation);
	}

	public NodeList<Type> typeArgs() {
		return location.safeTraversal(SObjectCreationExpr.TYPE_ARGS);
	}

	public ObjectCreationExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(SObjectCreationExpr.TYPE_ARGS, typeArgs);
	}

	public ObjectCreationExpr withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(SObjectCreationExpr.TYPE_ARGS, mutation);
	}

	public QualifiedType type() {
		return location.safeTraversal(SObjectCreationExpr.TYPE);
	}

	public ObjectCreationExpr withType(QualifiedType type) {
		return location.safeTraversalReplace(SObjectCreationExpr.TYPE, type);
	}

	public ObjectCreationExpr withType(Mutation<QualifiedType> mutation) {
		return location.safeTraversalMutate(SObjectCreationExpr.TYPE, mutation);
	}

	public NodeList<Expr> args() {
		return location.safeTraversal(SObjectCreationExpr.ARGS);
	}

	public ObjectCreationExpr withArgs(NodeList<Expr> args) {
		return location.safeTraversalReplace(SObjectCreationExpr.ARGS, args);
	}

	public ObjectCreationExpr withArgs(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(SObjectCreationExpr.ARGS, mutation);
	}

	public NodeOption<NodeList<MemberDecl>> body() {
		return location.safeTraversal(SObjectCreationExpr.BODY);
	}

	public ObjectCreationExpr withBody(NodeOption<NodeList<MemberDecl>> body) {
		return location.safeTraversalReplace(SObjectCreationExpr.BODY, body);
	}

	public ObjectCreationExpr withBody(Mutation<NodeOption<NodeList<MemberDecl>>> mutation) {
		return location.safeTraversalMutate(SObjectCreationExpr.BODY, mutation);
	}
}
