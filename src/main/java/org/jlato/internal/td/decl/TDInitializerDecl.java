package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.decl.SInitializerDecl;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.InitializerDecl;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.util.Mutation;

/**
 * An initializer declaration.
 */
public class TDInitializerDecl extends TDTree<SInitializerDecl, MemberDecl, InitializerDecl> implements InitializerDecl {

	/**
	 * Returns the kind of this initializer declaration.
	 *
	 * @return the kind of this initializer declaration.
	 */
	public Kind kind() {
		return Kind.InitializerDecl;
	}

	/**
	 * Creates an initializer declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDInitializerDecl(TDLocation<SInitializerDecl> location) {
		super(location);
	}

	/**
	 * Creates an initializer declaration with the specified child trees.
	 *
	 * @param modifiers the modifiers child tree.
	 * @param body      the body child tree.
	 */
	public TDInitializerDecl(NodeList<ExtendedModifier> modifiers, BlockStmt body) {
		super(new TDLocation<SInitializerDecl>(SInitializerDecl.make(TDTree.<SNodeList>treeOf(modifiers), TDTree.<SBlockStmt>treeOf(body))));
	}

	/**
	 * Returns the modifiers of this initializer declaration.
	 *
	 * @return the modifiers of this initializer declaration.
	 */
	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SInitializerDecl.MODIFIERS);
	}

	/**
	 * Replaces the modifiers of this initializer declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this initializer declaration.
	 * @return the resulting mutated initializer declaration.
	 */
	public InitializerDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SInitializerDecl.MODIFIERS, modifiers);
	}

	/**
	 * Mutates the modifiers of this initializer declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this initializer declaration.
	 * @return the resulting mutated initializer declaration.
	 */
	public InitializerDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SInitializerDecl.MODIFIERS, mutation);
	}

	/**
	 * Returns the body of this initializer declaration.
	 *
	 * @return the body of this initializer declaration.
	 */
	public BlockStmt body() {
		return location.safeTraversal(SInitializerDecl.BODY);
	}

	/**
	 * Replaces the body of this initializer declaration.
	 *
	 * @param body the replacement for the body of this initializer declaration.
	 * @return the resulting mutated initializer declaration.
	 */
	public InitializerDecl withBody(BlockStmt body) {
		return location.safeTraversalReplace(SInitializerDecl.BODY, body);
	}

	/**
	 * Mutates the body of this initializer declaration.
	 *
	 * @param mutation the mutation to apply to the body of this initializer declaration.
	 * @return the resulting mutated initializer declaration.
	 */
	public InitializerDecl withBody(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(SInitializerDecl.BODY, mutation);
	}
}
