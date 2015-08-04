package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.decl.SClassDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.decl.ClassDecl;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.tree.decl.TypeParameter;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

public class TDClassDecl extends TreeBase<SClassDecl, TypeDecl, ClassDecl> implements ClassDecl {

	public Kind kind() {
		return Kind.ClassDecl;
	}

	public TDClassDecl(SLocation<SClassDecl> location) {
		super(location);
	}

	public TDClassDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<TypeParameter> typeParams, NodeOption<QualifiedType> extendsClause, NodeList<QualifiedType> implementsClause, NodeList<MemberDecl> members) {
		super(new SLocation<SClassDecl>(SClassDecl.make(TreeBase.<SNodeListState>treeOf(modifiers), TreeBase.<SName>treeOf(name), TreeBase.<SNodeListState>treeOf(typeParams), TreeBase.<SNodeOptionState>treeOf(extendsClause), TreeBase.<SNodeListState>treeOf(implementsClause), TreeBase.<SNodeListState>treeOf(members))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SClassDecl.MODIFIERS);
	}

	public ClassDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SClassDecl.MODIFIERS, modifiers);
	}

	public ClassDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SClassDecl.MODIFIERS, mutation);
	}

	public Name name() {
		return location.safeTraversal(SClassDecl.NAME);
	}

	public ClassDecl withName(Name name) {
		return location.safeTraversalReplace(SClassDecl.NAME, name);
	}

	public ClassDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SClassDecl.NAME, mutation);
	}

	public NodeList<TypeParameter> typeParams() {
		return location.safeTraversal(SClassDecl.TYPE_PARAMS);
	}

	public ClassDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(SClassDecl.TYPE_PARAMS, typeParams);
	}

	public ClassDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(SClassDecl.TYPE_PARAMS, mutation);
	}

	public NodeOption<QualifiedType> extendsClause() {
		return location.safeTraversal(SClassDecl.EXTENDS_CLAUSE);
	}

	public ClassDecl withExtendsClause(NodeOption<QualifiedType> extendsClause) {
		return location.safeTraversalReplace(SClassDecl.EXTENDS_CLAUSE, extendsClause);
	}

	public ClassDecl withExtendsClause(Mutation<NodeOption<QualifiedType>> mutation) {
		return location.safeTraversalMutate(SClassDecl.EXTENDS_CLAUSE, mutation);
	}

	public NodeList<QualifiedType> implementsClause() {
		return location.safeTraversal(SClassDecl.IMPLEMENTS_CLAUSE);
	}

	public ClassDecl withImplementsClause(NodeList<QualifiedType> implementsClause) {
		return location.safeTraversalReplace(SClassDecl.IMPLEMENTS_CLAUSE, implementsClause);
	}

	public ClassDecl withImplementsClause(Mutation<NodeList<QualifiedType>> mutation) {
		return location.safeTraversalMutate(SClassDecl.IMPLEMENTS_CLAUSE, mutation);
	}

	public NodeList<MemberDecl> members() {
		return location.safeTraversal(SClassDecl.MEMBERS);
	}

	public ClassDecl withMembers(NodeList<MemberDecl> members) {
		return location.safeTraversalReplace(SClassDecl.MEMBERS, members);
	}

	public ClassDecl withMembers(Mutation<NodeList<MemberDecl>> mutation) {
		return location.safeTraversalMutate(SClassDecl.MEMBERS, mutation);
	}
}
