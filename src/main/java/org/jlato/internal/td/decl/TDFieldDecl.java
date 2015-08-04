package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.decl.SFieldDecl;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.FieldDecl;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.VariableDeclarator;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDFieldDecl extends TDTree<SFieldDecl, MemberDecl, FieldDecl> implements FieldDecl {

	public Kind kind() {
		return Kind.FieldDecl;
	}

	public TDFieldDecl(TDLocation<SFieldDecl> location) {
		super(location);
	}

	public TDFieldDecl(NodeList<ExtendedModifier> modifiers, Type type, NodeList<VariableDeclarator> variables) {
		super(new TDLocation<SFieldDecl>(SFieldDecl.make(TDTree.<SNodeListState>treeOf(modifiers), TDTree.<SType>treeOf(type), TDTree.<SNodeListState>treeOf(variables))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SFieldDecl.MODIFIERS);
	}

	public FieldDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SFieldDecl.MODIFIERS, modifiers);
	}

	public FieldDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SFieldDecl.MODIFIERS, mutation);
	}

	public Type type() {
		return location.safeTraversal(SFieldDecl.TYPE);
	}

	public FieldDecl withType(Type type) {
		return location.safeTraversalReplace(SFieldDecl.TYPE, type);
	}

	public FieldDecl withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SFieldDecl.TYPE, mutation);
	}

	public NodeList<VariableDeclarator> variables() {
		return location.safeTraversal(SFieldDecl.VARIABLES);
	}

	public FieldDecl withVariables(NodeList<VariableDeclarator> variables) {
		return location.safeTraversalReplace(SFieldDecl.VARIABLES, variables);
	}

	public FieldDecl withVariables(Mutation<NodeList<VariableDeclarator>> mutation) {
		return location.safeTraversalMutate(SFieldDecl.VARIABLES, mutation);
	}
}
