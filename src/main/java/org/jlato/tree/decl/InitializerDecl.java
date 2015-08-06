package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.util.Mutation;

/**
 * An initializer declaration.
 */
public interface InitializerDecl extends MemberDecl, TreeCombinators<InitializerDecl> {

	/**
	 * Returns the modifiers of this initializer declaration.
	 *
	 * @return the modifiers of this initializer declaration.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this initializer declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this initializer declaration.
	 * @return the resulting mutated initializer declaration.
	 */
	InitializerDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this initializer declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this initializer declaration.
	 * @return the resulting mutated initializer declaration.
	 */
	InitializerDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the body of this initializer declaration.
	 *
	 * @return the body of this initializer declaration.
	 */
	BlockStmt body();

	/**
	 * Replaces the body of this initializer declaration.
	 *
	 * @param body the replacement for the body of this initializer declaration.
	 * @return the resulting mutated initializer declaration.
	 */
	InitializerDecl withBody(BlockStmt body);

	/**
	 * Mutates the body of this initializer declaration.
	 *
	 * @param mutation the mutation to apply to the body of this initializer declaration.
	 * @return the resulting mutated initializer declaration.
	 */
	InitializerDecl withBody(Mutation<BlockStmt> mutation);
}
