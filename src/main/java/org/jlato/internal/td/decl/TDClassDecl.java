package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.decl.SClassDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
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

/**
 * A class declaration.
 */
public class TDClassDecl extends TDTree<SClassDecl, TypeDecl, ClassDecl> implements ClassDecl {

	/**
	 * Returns the kind of this class declaration.
	 *
	 * @return the kind of this class declaration.
	 */
	public Kind kind() {
		return Kind.ClassDecl;
	}

	/**
	 * Creates a class declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDClassDecl(TDLocation<SClassDecl> location) {
		super(location);
	}

	/**
	 * Creates a class declaration with the specified child trees.
	 *
	 * @param modifiers        the modifiers child tree.
	 * @param name             the name child tree.
	 * @param typeParams       the type parameters child tree.
	 * @param extendsClause    the 'extends' clause child tree.
	 * @param implementsClause the 'implements' clause child tree.
	 * @param members          the members child tree.
	 */
	public TDClassDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeList<TypeParameter> typeParams, NodeOption<QualifiedType> extendsClause, NodeList<QualifiedType> implementsClause, NodeList<MemberDecl> members) {
		super(new TDLocation<SClassDecl>(SClassDecl.make(TDTree.<SNodeList>treeOf(modifiers), TDTree.<SName>treeOf(name), TDTree.<SNodeList>treeOf(typeParams), TDTree.<SNodeOption>treeOf(extendsClause), TDTree.<SNodeList>treeOf(implementsClause), TDTree.<SNodeList>treeOf(members))));
	}

	/**
	 * Returns the modifiers of this class declaration.
	 *
	 * @return the modifiers of this class declaration.
	 */
	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SClassDecl.MODIFIERS);
	}

	/**
	 * Replaces the modifiers of this class declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	public ClassDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SClassDecl.MODIFIERS, modifiers);
	}

	/**
	 * Mutates the modifiers of this class declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	public ClassDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SClassDecl.MODIFIERS, mutation);
	}

	/**
	 * Returns the name of this class declaration.
	 *
	 * @return the name of this class declaration.
	 */
	public Name name() {
		return location.safeTraversal(SClassDecl.NAME);
	}

	/**
	 * Replaces the name of this class declaration.
	 *
	 * @param name the replacement for the name of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	public ClassDecl withName(Name name) {
		return location.safeTraversalReplace(SClassDecl.NAME, name);
	}

	/**
	 * Mutates the name of this class declaration.
	 *
	 * @param mutation the mutation to apply to the name of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	public ClassDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SClassDecl.NAME, mutation);
	}

	/**
	 * Returns the type parameters of this class declaration.
	 *
	 * @return the type parameters of this class declaration.
	 */
	public NodeList<TypeParameter> typeParams() {
		return location.safeTraversal(SClassDecl.TYPE_PARAMS);
	}

	/**
	 * Replaces the type parameters of this class declaration.
	 *
	 * @param typeParams the replacement for the type parameters of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	public ClassDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(SClassDecl.TYPE_PARAMS, typeParams);
	}

	/**
	 * Mutates the type parameters of this class declaration.
	 *
	 * @param mutation the mutation to apply to the type parameters of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	public ClassDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(SClassDecl.TYPE_PARAMS, mutation);
	}

	/**
	 * Returns the 'extends' clause of this class declaration.
	 *
	 * @return the 'extends' clause of this class declaration.
	 */
	public NodeOption<QualifiedType> extendsClause() {
		return location.safeTraversal(SClassDecl.EXTENDS_CLAUSE);
	}

	/**
	 * Replaces the 'extends' clause of this class declaration.
	 *
	 * @param extendsClause the replacement for the 'extends' clause of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	public ClassDecl withExtendsClause(NodeOption<QualifiedType> extendsClause) {
		return location.safeTraversalReplace(SClassDecl.EXTENDS_CLAUSE, extendsClause);
	}

	/**
	 * Mutates the 'extends' clause of this class declaration.
	 *
	 * @param mutation the mutation to apply to the 'extends' clause of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	public ClassDecl withExtendsClause(Mutation<NodeOption<QualifiedType>> mutation) {
		return location.safeTraversalMutate(SClassDecl.EXTENDS_CLAUSE, mutation);
	}

	/**
	 * Returns the 'implements' clause of this class declaration.
	 *
	 * @return the 'implements' clause of this class declaration.
	 */
	public NodeList<QualifiedType> implementsClause() {
		return location.safeTraversal(SClassDecl.IMPLEMENTS_CLAUSE);
	}

	/**
	 * Replaces the 'implements' clause of this class declaration.
	 *
	 * @param implementsClause the replacement for the 'implements' clause of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	public ClassDecl withImplementsClause(NodeList<QualifiedType> implementsClause) {
		return location.safeTraversalReplace(SClassDecl.IMPLEMENTS_CLAUSE, implementsClause);
	}

	/**
	 * Mutates the 'implements' clause of this class declaration.
	 *
	 * @param mutation the mutation to apply to the 'implements' clause of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	public ClassDecl withImplementsClause(Mutation<NodeList<QualifiedType>> mutation) {
		return location.safeTraversalMutate(SClassDecl.IMPLEMENTS_CLAUSE, mutation);
	}

	/**
	 * Returns the members of this class declaration.
	 *
	 * @return the members of this class declaration.
	 */
	public NodeList<MemberDecl> members() {
		return location.safeTraversal(SClassDecl.MEMBERS);
	}

	/**
	 * Replaces the members of this class declaration.
	 *
	 * @param members the replacement for the members of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	public ClassDecl withMembers(NodeList<MemberDecl> members) {
		return location.safeTraversalReplace(SClassDecl.MEMBERS, members);
	}

	/**
	 * Mutates the members of this class declaration.
	 *
	 * @param mutation the mutation to apply to the members of this class declaration.
	 * @return the resulting mutated class declaration.
	 */
	public ClassDecl withMembers(Mutation<NodeList<MemberDecl>> mutation) {
		return location.safeTraversalMutate(SClassDecl.MEMBERS, mutation);
	}
}
