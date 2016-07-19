package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.decl.SConstructorDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.ConstructorDecl;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.TypeParameter;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;
import org.jlato.util.Mutation;

/**
 * A constructor declaration.
 */
public class TDConstructorDecl extends TDTree<SConstructorDecl, MemberDecl, ConstructorDecl> implements ConstructorDecl {

	/**
	 * Returns the kind of this constructor declaration.
	 *
	 * @return the kind of this constructor declaration.
	 */
	public Kind kind() {
		return Kind.ConstructorDecl;
	}

	/**
	 * Creates a constructor declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDConstructorDecl(TDLocation<SConstructorDecl> location) {
		super(location);
	}

	/**
	 * Creates a constructor declaration with the specified child trees.
	 *
	 * @param modifiers    the modifiers child tree.
	 * @param typeParams   the type parameters child tree.
	 * @param name         the name child tree.
	 * @param params       the parameters child tree.
	 * @param throwsClause the 'throws' clause child tree.
	 * @param body         the body child tree.
	 */
	public TDConstructorDecl(NodeList<ExtendedModifier> modifiers, NodeList<TypeParameter> typeParams, Name name, NodeList<FormalParameter> params, NodeList<QualifiedType> throwsClause, BlockStmt body) {
		super(new TDLocation<SConstructorDecl>(SConstructorDecl.make(TDTree.<SNodeList>treeOf(modifiers), TDTree.<SNodeList>treeOf(typeParams), TDTree.<SName>treeOf(name), TDTree.<SNodeList>treeOf(params), TDTree.<SNodeList>treeOf(throwsClause), TDTree.<SBlockStmt>treeOf(body))));
	}

	/**
	 * Returns the modifiers of this constructor declaration.
	 *
	 * @return the modifiers of this constructor declaration.
	 */
	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SConstructorDecl.MODIFIERS);
	}

	/**
	 * Replaces the modifiers of this constructor declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	public ConstructorDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SConstructorDecl.MODIFIERS, modifiers);
	}

	/**
	 * Mutates the modifiers of this constructor declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	public ConstructorDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SConstructorDecl.MODIFIERS, mutation);
	}

	/**
	 * Returns the type parameters of this constructor declaration.
	 *
	 * @return the type parameters of this constructor declaration.
	 */
	public NodeList<TypeParameter> typeParams() {
		return location.safeTraversal(SConstructorDecl.TYPE_PARAMS);
	}

	/**
	 * Replaces the type parameters of this constructor declaration.
	 *
	 * @param typeParams the replacement for the type parameters of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	public ConstructorDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(SConstructorDecl.TYPE_PARAMS, typeParams);
	}

	/**
	 * Mutates the type parameters of this constructor declaration.
	 *
	 * @param mutation the mutation to apply to the type parameters of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	public ConstructorDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(SConstructorDecl.TYPE_PARAMS, mutation);
	}

	/**
	 * Returns the name of this constructor declaration.
	 *
	 * @return the name of this constructor declaration.
	 */
	public Name name() {
		return location.safeTraversal(SConstructorDecl.NAME);
	}

	/**
	 * Replaces the name of this constructor declaration.
	 *
	 * @param name the replacement for the name of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	public ConstructorDecl withName(Name name) {
		return location.safeTraversalReplace(SConstructorDecl.NAME, name);
	}

	/**
	 * Mutates the name of this constructor declaration.
	 *
	 * @param mutation the mutation to apply to the name of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	public ConstructorDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SConstructorDecl.NAME, mutation);
	}

	/**
	 * Replaces the name of this constructor declaration.
	 *
	 * @param name the replacement for the name of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	public ConstructorDecl withName(String name) {
		return location.safeTraversalReplace(SConstructorDecl.NAME, Trees.name(name));
	}

	/**
	 * Returns the parameters of this constructor declaration.
	 *
	 * @return the parameters of this constructor declaration.
	 */
	public NodeList<FormalParameter> params() {
		return location.safeTraversal(SConstructorDecl.PARAMS);
	}

	/**
	 * Replaces the parameters of this constructor declaration.
	 *
	 * @param params the replacement for the parameters of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	public ConstructorDecl withParams(NodeList<FormalParameter> params) {
		return location.safeTraversalReplace(SConstructorDecl.PARAMS, params);
	}

	/**
	 * Mutates the parameters of this constructor declaration.
	 *
	 * @param mutation the mutation to apply to the parameters of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	public ConstructorDecl withParams(Mutation<NodeList<FormalParameter>> mutation) {
		return location.safeTraversalMutate(SConstructorDecl.PARAMS, mutation);
	}

	/**
	 * Returns the 'throws' clause of this constructor declaration.
	 *
	 * @return the 'throws' clause of this constructor declaration.
	 */
	public NodeList<QualifiedType> throwsClause() {
		return location.safeTraversal(SConstructorDecl.THROWS_CLAUSE);
	}

	/**
	 * Replaces the 'throws' clause of this constructor declaration.
	 *
	 * @param throwsClause the replacement for the 'throws' clause of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	public ConstructorDecl withThrowsClause(NodeList<QualifiedType> throwsClause) {
		return location.safeTraversalReplace(SConstructorDecl.THROWS_CLAUSE, throwsClause);
	}

	/**
	 * Mutates the 'throws' clause of this constructor declaration.
	 *
	 * @param mutation the mutation to apply to the 'throws' clause of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	public ConstructorDecl withThrowsClause(Mutation<NodeList<QualifiedType>> mutation) {
		return location.safeTraversalMutate(SConstructorDecl.THROWS_CLAUSE, mutation);
	}

	/**
	 * Returns the body of this constructor declaration.
	 *
	 * @return the body of this constructor declaration.
	 */
	public BlockStmt body() {
		return location.safeTraversal(SConstructorDecl.BODY);
	}

	/**
	 * Replaces the body of this constructor declaration.
	 *
	 * @param body the replacement for the body of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	public ConstructorDecl withBody(BlockStmt body) {
		return location.safeTraversalReplace(SConstructorDecl.BODY, body);
	}

	/**
	 * Mutates the body of this constructor declaration.
	 *
	 * @param mutation the mutation to apply to the body of this constructor declaration.
	 * @return the resulting mutated constructor declaration.
	 */
	public ConstructorDecl withBody(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(SConstructorDecl.BODY, mutation);
	}
}
