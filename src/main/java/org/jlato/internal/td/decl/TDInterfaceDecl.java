package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.decl.SInterfaceDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.InterfaceDecl;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.tree.decl.TypeParameter;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

public class TDInterfaceDecl extends TDTree<SInterfaceDecl, TypeDecl, InterfaceDecl> implements InterfaceDecl {

	public Kind kind() {
		return Kind.InterfaceDecl;
	}

	public TDInterfaceDecl(SLocation<SInterfaceDecl> location) {
		super(location);
	}

	public TDInterfaceDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<TypeParameter> typeParams, NodeList<QualifiedType> extendsClause, NodeList<MemberDecl> members) {
		super(new SLocation<SInterfaceDecl>(SInterfaceDecl.make(TDTree.<SNodeListState>treeOf(modifiers), TDTree.<SName>treeOf(name), TDTree.<SNodeListState>treeOf(typeParams), TDTree.<SNodeListState>treeOf(extendsClause), TDTree.<SNodeListState>treeOf(members))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SInterfaceDecl.MODIFIERS);
	}

	public InterfaceDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SInterfaceDecl.MODIFIERS, modifiers);
	}

	public InterfaceDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SInterfaceDecl.MODIFIERS, mutation);
	}

	public Name name() {
		return location.safeTraversal(SInterfaceDecl.NAME);
	}

	public InterfaceDecl withName(Name name) {
		return location.safeTraversalReplace(SInterfaceDecl.NAME, name);
	}

	public InterfaceDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SInterfaceDecl.NAME, mutation);
	}

	public NodeList<TypeParameter> typeParams() {
		return location.safeTraversal(SInterfaceDecl.TYPE_PARAMS);
	}

	public InterfaceDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(SInterfaceDecl.TYPE_PARAMS, typeParams);
	}

	public InterfaceDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(SInterfaceDecl.TYPE_PARAMS, mutation);
	}

	public NodeList<QualifiedType> extendsClause() {
		return location.safeTraversal(SInterfaceDecl.EXTENDS_CLAUSE);
	}

	public InterfaceDecl withExtendsClause(NodeList<QualifiedType> extendsClause) {
		return location.safeTraversalReplace(SInterfaceDecl.EXTENDS_CLAUSE, extendsClause);
	}

	public InterfaceDecl withExtendsClause(Mutation<NodeList<QualifiedType>> mutation) {
		return location.safeTraversalMutate(SInterfaceDecl.EXTENDS_CLAUSE, mutation);
	}

	public NodeList<MemberDecl> members() {
		return location.safeTraversal(SInterfaceDecl.MEMBERS);
	}

	public InterfaceDecl withMembers(NodeList<MemberDecl> members) {
		return location.safeTraversalReplace(SInterfaceDecl.MEMBERS, members);
	}

	public InterfaceDecl withMembers(Mutation<NodeList<MemberDecl>> mutation) {
		return location.safeTraversalMutate(SInterfaceDecl.MEMBERS, mutation);
	}
}
