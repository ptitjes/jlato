package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.decl.SMethodDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.MethodDecl;
import org.jlato.tree.decl.TypeParameter;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDMethodDecl extends TDTree<SMethodDecl, MemberDecl, MethodDecl> implements MethodDecl {

	public Kind kind() {
		return Kind.MethodDecl;
	}

	public TDMethodDecl(SLocation<SMethodDecl> location) {
		super(location);
	}

	public TDMethodDecl(NodeList<ExtendedModifier> modifiers, NodeList<TypeParameter> typeParams, Type type, Name name, NodeList<FormalParameter> params, NodeList<ArrayDim> dims, NodeList<QualifiedType> throwsClause, NodeOption<BlockStmt> body) {
		super(new SLocation<SMethodDecl>(SMethodDecl.make(TDTree.<SNodeListState>treeOf(modifiers), TDTree.<SNodeListState>treeOf(typeParams), TDTree.<SType>treeOf(type), TDTree.<SName>treeOf(name), TDTree.<SNodeListState>treeOf(params), TDTree.<SNodeListState>treeOf(dims), TDTree.<SNodeListState>treeOf(throwsClause), TDTree.<SNodeOptionState>treeOf(body))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SMethodDecl.MODIFIERS);
	}

	public MethodDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SMethodDecl.MODIFIERS, modifiers);
	}

	public MethodDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SMethodDecl.MODIFIERS, mutation);
	}

	public NodeList<TypeParameter> typeParams() {
		return location.safeTraversal(SMethodDecl.TYPE_PARAMS);
	}

	public MethodDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(SMethodDecl.TYPE_PARAMS, typeParams);
	}

	public MethodDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(SMethodDecl.TYPE_PARAMS, mutation);
	}

	public Type type() {
		return location.safeTraversal(SMethodDecl.TYPE);
	}

	public MethodDecl withType(Type type) {
		return location.safeTraversalReplace(SMethodDecl.TYPE, type);
	}

	public MethodDecl withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SMethodDecl.TYPE, mutation);
	}

	public Name name() {
		return location.safeTraversal(SMethodDecl.NAME);
	}

	public MethodDecl withName(Name name) {
		return location.safeTraversalReplace(SMethodDecl.NAME, name);
	}

	public MethodDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SMethodDecl.NAME, mutation);
	}

	public NodeList<FormalParameter> params() {
		return location.safeTraversal(SMethodDecl.PARAMS);
	}

	public MethodDecl withParams(NodeList<FormalParameter> params) {
		return location.safeTraversalReplace(SMethodDecl.PARAMS, params);
	}

	public MethodDecl withParams(Mutation<NodeList<FormalParameter>> mutation) {
		return location.safeTraversalMutate(SMethodDecl.PARAMS, mutation);
	}

	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(SMethodDecl.DIMS);
	}

	public MethodDecl withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(SMethodDecl.DIMS, dims);
	}

	public MethodDecl withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(SMethodDecl.DIMS, mutation);
	}

	public NodeList<QualifiedType> throwsClause() {
		return location.safeTraversal(SMethodDecl.THROWS_CLAUSE);
	}

	public MethodDecl withThrowsClause(NodeList<QualifiedType> throwsClause) {
		return location.safeTraversalReplace(SMethodDecl.THROWS_CLAUSE, throwsClause);
	}

	public MethodDecl withThrowsClause(Mutation<NodeList<QualifiedType>> mutation) {
		return location.safeTraversalMutate(SMethodDecl.THROWS_CLAUSE, mutation);
	}

	public NodeOption<BlockStmt> body() {
		return location.safeTraversal(SMethodDecl.BODY);
	}

	public MethodDecl withBody(NodeOption<BlockStmt> body) {
		return location.safeTraversalReplace(SMethodDecl.BODY, body);
	}

	public MethodDecl withBody(Mutation<NodeOption<BlockStmt>> mutation) {
		return location.safeTraversalMutate(SMethodDecl.BODY, mutation);
	}
}
