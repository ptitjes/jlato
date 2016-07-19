package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.decl.SInterfaceDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.InterfaceDecl;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.tree.decl.TypeParameter;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

/**
 * An interface declaration.
 */
public class TDInterfaceDecl extends TDTree<SInterfaceDecl, TypeDecl, InterfaceDecl> implements InterfaceDecl {

	/**
	 * Returns the kind of this interface declaration.
	 *
	 * @return the kind of this interface declaration.
	 */
	public Kind kind() {
		return Kind.InterfaceDecl;
	}

	/**
	 * Creates an interface declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDInterfaceDecl(TDLocation<SInterfaceDecl> location) {
		super(location);
	}

	/**
	 * Creates an interface declaration with the specified child trees.
	 *
	 * @param modifiers     the modifiers child tree.
	 * @param name          the name child tree.
	 * @param typeParams    the type parameters child tree.
	 * @param extendsClause the 'extends' clause child tree.
	 * @param members       the members child tree.
	 */
	public TDInterfaceDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<TypeParameter> typeParams, NodeList<QualifiedType> extendsClause, NodeList<MemberDecl> members) {
		super(new TDLocation<SInterfaceDecl>(SInterfaceDecl.make(TDTree.<SNodeList>treeOf(modifiers), TDTree.<SName>treeOf(name), TDTree.<SNodeList>treeOf(typeParams), TDTree.<SNodeList>treeOf(extendsClause), TDTree.<SNodeList>treeOf(members))));
	}

	/**
	 * Returns the modifiers of this interface declaration.
	 *
	 * @return the modifiers of this interface declaration.
	 */
	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SInterfaceDecl.MODIFIERS);
	}

	/**
	 * Replaces the modifiers of this interface declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	public InterfaceDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SInterfaceDecl.MODIFIERS, modifiers);
	}

	/**
	 * Mutates the modifiers of this interface declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	public InterfaceDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SInterfaceDecl.MODIFIERS, mutation);
	}

	/**
	 * Returns the name of this interface declaration.
	 *
	 * @return the name of this interface declaration.
	 */
	public Name name() {
		return location.safeTraversal(SInterfaceDecl.NAME);
	}

	/**
	 * Replaces the name of this interface declaration.
	 *
	 * @param name the replacement for the name of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	public InterfaceDecl withName(Name name) {
		return location.safeTraversalReplace(SInterfaceDecl.NAME, name);
	}

	/**
	 * Mutates the name of this interface declaration.
	 *
	 * @param mutation the mutation to apply to the name of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	public InterfaceDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SInterfaceDecl.NAME, mutation);
	}

	/**
	 * Replaces the name of this interface declaration.
	 *
	 * @param name the replacement for the name of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	public InterfaceDecl withName(String name) {
		return location.safeTraversalReplace(SInterfaceDecl.NAME, Trees.name(name));
	}

	/**
	 * Returns the type parameters of this interface declaration.
	 *
	 * @return the type parameters of this interface declaration.
	 */
	public NodeList<TypeParameter> typeParams() {
		return location.safeTraversal(SInterfaceDecl.TYPE_PARAMS);
	}

	/**
	 * Replaces the type parameters of this interface declaration.
	 *
	 * @param typeParams the replacement for the type parameters of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	public InterfaceDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(SInterfaceDecl.TYPE_PARAMS, typeParams);
	}

	/**
	 * Mutates the type parameters of this interface declaration.
	 *
	 * @param mutation the mutation to apply to the type parameters of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	public InterfaceDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(SInterfaceDecl.TYPE_PARAMS, mutation);
	}

	/**
	 * Returns the 'extends' clause of this interface declaration.
	 *
	 * @return the 'extends' clause of this interface declaration.
	 */
	public NodeList<QualifiedType> extendsClause() {
		return location.safeTraversal(SInterfaceDecl.EXTENDS_CLAUSE);
	}

	/**
	 * Replaces the 'extends' clause of this interface declaration.
	 *
	 * @param extendsClause the replacement for the 'extends' clause of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	public InterfaceDecl withExtendsClause(NodeList<QualifiedType> extendsClause) {
		return location.safeTraversalReplace(SInterfaceDecl.EXTENDS_CLAUSE, extendsClause);
	}

	/**
	 * Mutates the 'extends' clause of this interface declaration.
	 *
	 * @param mutation the mutation to apply to the 'extends' clause of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	public InterfaceDecl withExtendsClause(Mutation<NodeList<QualifiedType>> mutation) {
		return location.safeTraversalMutate(SInterfaceDecl.EXTENDS_CLAUSE, mutation);
	}

	/**
	 * Returns the members of this interface declaration.
	 *
	 * @return the members of this interface declaration.
	 */
	public NodeList<MemberDecl> members() {
		return location.safeTraversal(SInterfaceDecl.MEMBERS);
	}

	/**
	 * Replaces the members of this interface declaration.
	 *
	 * @param members the replacement for the members of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	public InterfaceDecl withMembers(NodeList<MemberDecl> members) {
		return location.safeTraversalReplace(SInterfaceDecl.MEMBERS, members);
	}

	/**
	 * Mutates the members of this interface declaration.
	 *
	 * @param mutation the mutation to apply to the members of this interface declaration.
	 * @return the resulting mutated interface declaration.
	 */
	public InterfaceDecl withMembers(Mutation<NodeList<MemberDecl>> mutation) {
		return location.safeTraversalMutate(SInterfaceDecl.MEMBERS, mutation);
	}
}
