package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.decl.SLocalVariableDecl;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.Decl;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.LocalVariableDecl;
import org.jlato.tree.decl.VariableDeclarator;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A local variable declaration.
 */
public class TDLocalVariableDecl extends TDTree<SLocalVariableDecl, Decl, LocalVariableDecl> implements LocalVariableDecl {

	/**
	 * Returns the kind of this local variable declaration.
	 *
	 * @return the kind of this local variable declaration.
	 */
	public Kind kind() {
		return Kind.LocalVariableDecl;
	}

	/**
	 * Creates a local variable declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDLocalVariableDecl(TDLocation<SLocalVariableDecl> location) {
		super(location);
	}

	/**
	 * Creates a local variable declaration with the specified child trees.
	 *
	 * @param modifiers the modifiers child tree.
	 * @param type      the type child tree.
	 * @param variables the variables child tree.
	 */
	public TDLocalVariableDecl(NodeList<ExtendedModifier> modifiers, Type type, NodeList<VariableDeclarator> variables) {
		super(new TDLocation<SLocalVariableDecl>(SLocalVariableDecl.make(TDTree.<SNodeList>treeOf(modifiers), TDTree.<SType>treeOf(type), TDTree.<SNodeList>treeOf(variables))));
	}

	/**
	 * Returns the modifiers of this local variable declaration.
	 *
	 * @return the modifiers of this local variable declaration.
	 */
	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SLocalVariableDecl.MODIFIERS);
	}

	/**
	 * Replaces the modifiers of this local variable declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this local variable declaration.
	 * @return the resulting mutated local variable declaration.
	 */
	public LocalVariableDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SLocalVariableDecl.MODIFIERS, modifiers);
	}

	/**
	 * Mutates the modifiers of this local variable declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this local variable declaration.
	 * @return the resulting mutated local variable declaration.
	 */
	public LocalVariableDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SLocalVariableDecl.MODIFIERS, mutation);
	}

	/**
	 * Returns the type of this local variable declaration.
	 *
	 * @return the type of this local variable declaration.
	 */
	public Type type() {
		return location.safeTraversal(SLocalVariableDecl.TYPE);
	}

	/**
	 * Replaces the type of this local variable declaration.
	 *
	 * @param type the replacement for the type of this local variable declaration.
	 * @return the resulting mutated local variable declaration.
	 */
	public LocalVariableDecl withType(Type type) {
		return location.safeTraversalReplace(SLocalVariableDecl.TYPE, type);
	}

	/**
	 * Mutates the type of this local variable declaration.
	 *
	 * @param mutation the mutation to apply to the type of this local variable declaration.
	 * @return the resulting mutated local variable declaration.
	 */
	public LocalVariableDecl withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SLocalVariableDecl.TYPE, mutation);
	}

	/**
	 * Returns the variables of this local variable declaration.
	 *
	 * @return the variables of this local variable declaration.
	 */
	public NodeList<VariableDeclarator> variables() {
		return location.safeTraversal(SLocalVariableDecl.VARIABLES);
	}

	/**
	 * Replaces the variables of this local variable declaration.
	 *
	 * @param variables the replacement for the variables of this local variable declaration.
	 * @return the resulting mutated local variable declaration.
	 */
	public LocalVariableDecl withVariables(NodeList<VariableDeclarator> variables) {
		return location.safeTraversalReplace(SLocalVariableDecl.VARIABLES, variables);
	}

	/**
	 * Mutates the variables of this local variable declaration.
	 *
	 * @param mutation the mutation to apply to the variables of this local variable declaration.
	 * @return the resulting mutated local variable declaration.
	 */
	public LocalVariableDecl withVariables(Mutation<NodeList<VariableDeclarator>> mutation) {
		return location.safeTraversalMutate(SLocalVariableDecl.VARIABLES, mutation);
	}
}
