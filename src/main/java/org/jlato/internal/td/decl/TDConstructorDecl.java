package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.decl.SConstructorDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.ConstructorDecl;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.TypeParameter;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

public class TDConstructorDecl extends TDTree<SConstructorDecl, MemberDecl, ConstructorDecl> implements ConstructorDecl {

	public Kind kind() {
		return Kind.ConstructorDecl;
	}

	public TDConstructorDecl(TDLocation<SConstructorDecl> location) {
		super(location);
	}

	public TDConstructorDecl(NodeList<ExtendedModifier> modifiers, NodeList<TypeParameter> typeParams, Name name, NodeList<FormalParameter> params, NodeList<QualifiedType> throwsClause, BlockStmt body) {
		super(new TDLocation<SConstructorDecl>(SConstructorDecl.make(TDTree.<SNodeListState>treeOf(modifiers), TDTree.<SNodeListState>treeOf(typeParams), TDTree.<SName>treeOf(name), TDTree.<SNodeListState>treeOf(params), TDTree.<SNodeListState>treeOf(throwsClause), TDTree.<SBlockStmt>treeOf(body))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SConstructorDecl.MODIFIERS);
	}

	public ConstructorDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SConstructorDecl.MODIFIERS, modifiers);
	}

	public ConstructorDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SConstructorDecl.MODIFIERS, mutation);
	}

	public NodeList<TypeParameter> typeParams() {
		return location.safeTraversal(SConstructorDecl.TYPE_PARAMS);
	}

	public ConstructorDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(SConstructorDecl.TYPE_PARAMS, typeParams);
	}

	public ConstructorDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(SConstructorDecl.TYPE_PARAMS, mutation);
	}

	public Name name() {
		return location.safeTraversal(SConstructorDecl.NAME);
	}

	public ConstructorDecl withName(Name name) {
		return location.safeTraversalReplace(SConstructorDecl.NAME, name);
	}

	public ConstructorDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SConstructorDecl.NAME, mutation);
	}

	public NodeList<FormalParameter> params() {
		return location.safeTraversal(SConstructorDecl.PARAMS);
	}

	public ConstructorDecl withParams(NodeList<FormalParameter> params) {
		return location.safeTraversalReplace(SConstructorDecl.PARAMS, params);
	}

	public ConstructorDecl withParams(Mutation<NodeList<FormalParameter>> mutation) {
		return location.safeTraversalMutate(SConstructorDecl.PARAMS, mutation);
	}

	public NodeList<QualifiedType> throwsClause() {
		return location.safeTraversal(SConstructorDecl.THROWS_CLAUSE);
	}

	public ConstructorDecl withThrowsClause(NodeList<QualifiedType> throwsClause) {
		return location.safeTraversalReplace(SConstructorDecl.THROWS_CLAUSE, throwsClause);
	}

	public ConstructorDecl withThrowsClause(Mutation<NodeList<QualifiedType>> mutation) {
		return location.safeTraversalMutate(SConstructorDecl.THROWS_CLAUSE, mutation);
	}

	public BlockStmt body() {
		return location.safeTraversal(SConstructorDecl.BODY);
	}

	public ConstructorDecl withBody(BlockStmt body) {
		return location.safeTraversalReplace(SConstructorDecl.BODY, body);
	}

	public ConstructorDecl withBody(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(SConstructorDecl.BODY, mutation);
	}
}
