package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeListState;
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

public class TDLocalVariableDecl extends TDTree<SLocalVariableDecl, Decl, LocalVariableDecl> implements LocalVariableDecl {

	public Kind kind() {
		return Kind.LocalVariableDecl;
	}

	public TDLocalVariableDecl(TDLocation<SLocalVariableDecl> location) {
		super(location);
	}

	public TDLocalVariableDecl(NodeList<ExtendedModifier> modifiers, Type type, NodeList<VariableDeclarator> variables) {
		super(new TDLocation<SLocalVariableDecl>(SLocalVariableDecl.make(TDTree.<SNodeListState>treeOf(modifiers), TDTree.<SType>treeOf(type), TDTree.<SNodeListState>treeOf(variables))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SLocalVariableDecl.MODIFIERS);
	}

	public LocalVariableDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SLocalVariableDecl.MODIFIERS, modifiers);
	}

	public LocalVariableDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SLocalVariableDecl.MODIFIERS, mutation);
	}

	public Type type() {
		return location.safeTraversal(SLocalVariableDecl.TYPE);
	}

	public LocalVariableDecl withType(Type type) {
		return location.safeTraversalReplace(SLocalVariableDecl.TYPE, type);
	}

	public LocalVariableDecl withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SLocalVariableDecl.TYPE, mutation);
	}

	public NodeList<VariableDeclarator> variables() {
		return location.safeTraversal(SLocalVariableDecl.VARIABLES);
	}

	public LocalVariableDecl withVariables(NodeList<VariableDeclarator> variables) {
		return location.safeTraversalReplace(SLocalVariableDecl.VARIABLES, variables);
	}

	public LocalVariableDecl withVariables(Mutation<NodeList<VariableDeclarator>> mutation) {
		return location.safeTraversalMutate(SLocalVariableDecl.VARIABLES, mutation);
	}
}
