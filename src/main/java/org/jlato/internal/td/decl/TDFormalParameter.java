package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.decl.SFormalParameter;
import org.jlato.internal.bu.decl.SVariableDeclaratorId;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.decl.VariableDeclaratorId;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A formal parameter.
 */
public class TDFormalParameter extends TDTree<SFormalParameter, Node, FormalParameter> implements FormalParameter {

	/**
	 * Returns the kind of this formal parameter.
	 *
	 * @return the kind of this formal parameter.
	 */
	public Kind kind() {
		return Kind.FormalParameter;
	}

	/**
	 * Creates a formal parameter for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDFormalParameter(TDLocation<SFormalParameter> location) {
		super(location);
	}

	/**
	 * Creates a formal parameter with the specified child trees.
	 *
	 * @param modifiers the modifiers child tree.
	 * @param type      the type child tree.
	 * @param isVarArgs the is a variadic parameter child tree.
	 * @param id        the identifier child tree.
	 */
	public TDFormalParameter(NodeList<ExtendedModifier> modifiers, Type type, boolean isVarArgs, VariableDeclaratorId id) {
		super(new TDLocation<SFormalParameter>(SFormalParameter.make(TDTree.<SNodeList>treeOf(modifiers), TDTree.<SType>treeOf(type), isVarArgs, TDTree.<SVariableDeclaratorId>treeOf(id))));
	}

	/**
	 * Returns the modifiers of this formal parameter.
	 *
	 * @return the modifiers of this formal parameter.
	 */
	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SFormalParameter.MODIFIERS);
	}

	/**
	 * Replaces the modifiers of this formal parameter.
	 *
	 * @param modifiers the replacement for the modifiers of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SFormalParameter.MODIFIERS, modifiers);
	}

	/**
	 * Mutates the modifiers of this formal parameter.
	 *
	 * @param mutation the mutation to apply to the modifiers of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SFormalParameter.MODIFIERS, mutation);
	}

	/**
	 * Returns the type of this formal parameter.
	 *
	 * @return the type of this formal parameter.
	 */
	public Type type() {
		return location.safeTraversal(SFormalParameter.TYPE);
	}

	/**
	 * Replaces the type of this formal parameter.
	 *
	 * @param type the replacement for the type of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withType(Type type) {
		return location.safeTraversalReplace(SFormalParameter.TYPE, type);
	}

	/**
	 * Mutates the type of this formal parameter.
	 *
	 * @param mutation the mutation to apply to the type of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SFormalParameter.TYPE, mutation);
	}

	/**
	 * Tests whether this formal parameter is a variadic parameter.
	 *
	 * @return <code>true</code> if this formal parameter is a variadic parameter, <code>false</code> otherwise.
	 */
	public boolean isVarArgs() {
		return location.safeProperty(SFormalParameter.VAR_ARGS);
	}

	/**
	 * Sets whether this formal parameter is a variadic parameter.
	 *
	 * @param isVarArgs <code>true</code> if this formal parameter is a variadic parameter, <code>false</code> otherwise.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter setVarArgs(boolean isVarArgs) {
		return location.safePropertyReplace(SFormalParameter.VAR_ARGS, isVarArgs);
	}

	/**
	 * Mutates whether this formal parameter is a variadic parameter.
	 *
	 * @param mutation the mutation to apply to whether this formal parameter is a variadic parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter setVarArgs(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SFormalParameter.VAR_ARGS, mutation);
	}

	/**
	 * Returns the identifier of this formal parameter.
	 *
	 * @return the identifier of this formal parameter.
	 */
	public VariableDeclaratorId id() {
		return location.safeTraversal(SFormalParameter.ID);
	}

	/**
	 * Replaces the identifier of this formal parameter.
	 *
	 * @param id the replacement for the identifier of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withId(VariableDeclaratorId id) {
		return location.safeTraversalReplace(SFormalParameter.ID, id);
	}

	/**
	 * Mutates the identifier of this formal parameter.
	 *
	 * @param mutation the mutation to apply to the identifier of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withId(Mutation<VariableDeclaratorId> mutation) {
		return location.safeTraversalMutate(SFormalParameter.ID, mutation);
	}
}
