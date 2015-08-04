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

public class TDFormalParameter extends TDTree<SFormalParameter, Node, FormalParameter> implements FormalParameter {

	public Kind kind() {
		return Kind.FormalParameter;
	}

	public TDFormalParameter(TDLocation<SFormalParameter> location) {
		super(location);
	}

	public TDFormalParameter(NodeList<ExtendedModifier> modifiers, Type type, boolean isVarArgs, VariableDeclaratorId id) {
		super(new TDLocation<SFormalParameter>(SFormalParameter.make(TDTree.<SNodeList>treeOf(modifiers), TDTree.<SType>treeOf(type), isVarArgs, TDTree.<SVariableDeclaratorId>treeOf(id))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SFormalParameter.MODIFIERS);
	}

	public FormalParameter withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SFormalParameter.MODIFIERS, modifiers);
	}

	public FormalParameter withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SFormalParameter.MODIFIERS, mutation);
	}

	public Type type() {
		return location.safeTraversal(SFormalParameter.TYPE);
	}

	public FormalParameter withType(Type type) {
		return location.safeTraversalReplace(SFormalParameter.TYPE, type);
	}

	public FormalParameter withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SFormalParameter.TYPE, mutation);
	}

	public boolean isVarArgs() {
		return location.safeProperty(SFormalParameter.VAR_ARGS);
	}

	public FormalParameter setVarArgs(boolean isVarArgs) {
		return location.safePropertyReplace(SFormalParameter.VAR_ARGS, isVarArgs);
	}

	public FormalParameter setVarArgs(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SFormalParameter.VAR_ARGS, mutation);
	}

	public VariableDeclaratorId id() {
		return location.safeTraversal(SFormalParameter.ID);
	}

	public FormalParameter withId(VariableDeclaratorId id) {
		return location.safeTraversalReplace(SFormalParameter.ID, id);
	}

	public FormalParameter withId(Mutation<VariableDeclaratorId> mutation) {
		return location.safeTraversalMutate(SFormalParameter.ID, mutation);
	}
}
