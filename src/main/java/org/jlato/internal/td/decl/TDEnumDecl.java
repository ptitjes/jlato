package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.decl.SEnumDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.EnumConstantDecl;
import org.jlato.tree.decl.EnumDecl;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

public class TDEnumDecl extends TDTree<SEnumDecl, TypeDecl, EnumDecl> implements EnumDecl {

	public Kind kind() {
		return Kind.EnumDecl;
	}

	public TDEnumDecl(SLocation<SEnumDecl> location) {
		super(location);
	}

	public TDEnumDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<QualifiedType> implementsClause, NodeList<EnumConstantDecl> enumConstants, boolean trailingComma, NodeList<MemberDecl> members) {
		super(new SLocation<SEnumDecl>(SEnumDecl.make(TDTree.<SNodeListState>treeOf(modifiers), TDTree.<SName>treeOf(name), TDTree.<SNodeListState>treeOf(implementsClause), TDTree.<SNodeListState>treeOf(enumConstants), trailingComma, TDTree.<SNodeListState>treeOf(members))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SEnumDecl.MODIFIERS);
	}

	public EnumDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SEnumDecl.MODIFIERS, modifiers);
	}

	public EnumDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SEnumDecl.MODIFIERS, mutation);
	}

	public Name name() {
		return location.safeTraversal(SEnumDecl.NAME);
	}

	public EnumDecl withName(Name name) {
		return location.safeTraversalReplace(SEnumDecl.NAME, name);
	}

	public EnumDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SEnumDecl.NAME, mutation);
	}

	public NodeList<QualifiedType> implementsClause() {
		return location.safeTraversal(SEnumDecl.IMPLEMENTS_CLAUSE);
	}

	public EnumDecl withImplementsClause(NodeList<QualifiedType> implementsClause) {
		return location.safeTraversalReplace(SEnumDecl.IMPLEMENTS_CLAUSE, implementsClause);
	}

	public EnumDecl withImplementsClause(Mutation<NodeList<QualifiedType>> mutation) {
		return location.safeTraversalMutate(SEnumDecl.IMPLEMENTS_CLAUSE, mutation);
	}

	public NodeList<EnumConstantDecl> enumConstants() {
		return location.safeTraversal(SEnumDecl.ENUM_CONSTANTS);
	}

	public EnumDecl withEnumConstants(NodeList<EnumConstantDecl> enumConstants) {
		return location.safeTraversalReplace(SEnumDecl.ENUM_CONSTANTS, enumConstants);
	}

	public EnumDecl withEnumConstants(Mutation<NodeList<EnumConstantDecl>> mutation) {
		return location.safeTraversalMutate(SEnumDecl.ENUM_CONSTANTS, mutation);
	}

	public boolean trailingComma() {
		return location.safeProperty(SEnumDecl.TRAILING_COMMA);
	}

	public EnumDecl withTrailingComma(boolean trailingComma) {
		return location.safePropertyReplace(SEnumDecl.TRAILING_COMMA, trailingComma);
	}

	public EnumDecl withTrailingComma(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SEnumDecl.TRAILING_COMMA, mutation);
	}

	public NodeList<MemberDecl> members() {
		return location.safeTraversal(SEnumDecl.MEMBERS);
	}

	public EnumDecl withMembers(NodeList<MemberDecl> members) {
		return location.safeTraversalReplace(SEnumDecl.MEMBERS, members);
	}

	public EnumDecl withMembers(Mutation<NodeList<MemberDecl>> mutation) {
		return location.safeTraversalMutate(SEnumDecl.MEMBERS, mutation);
	}
}
