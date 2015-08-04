package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.decl.SEnumConstantDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.decl.EnumConstantDecl;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

public class TDEnumConstantDecl extends TreeBase<SEnumConstantDecl, MemberDecl, EnumConstantDecl> implements EnumConstantDecl {

	public Kind kind() {
		return Kind.EnumConstantDecl;
	}

	public TDEnumConstantDecl(SLocation<SEnumConstantDecl> location) {
		super(location);
	}

	public TDEnumConstantDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeOption<NodeList<Expr>> args, NodeOption<NodeList<MemberDecl>> classBody) {
		super(new SLocation<SEnumConstantDecl>(SEnumConstantDecl.make(TreeBase.<SNodeListState>treeOf(modifiers), TreeBase.<SName>treeOf(name), TreeBase.<SNodeOptionState>treeOf(args), TreeBase.<SNodeOptionState>treeOf(classBody))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SEnumConstantDecl.MODIFIERS);
	}

	public EnumConstantDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SEnumConstantDecl.MODIFIERS, modifiers);
	}

	public EnumConstantDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SEnumConstantDecl.MODIFIERS, mutation);
	}

	public Name name() {
		return location.safeTraversal(SEnumConstantDecl.NAME);
	}

	public EnumConstantDecl withName(Name name) {
		return location.safeTraversalReplace(SEnumConstantDecl.NAME, name);
	}

	public EnumConstantDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SEnumConstantDecl.NAME, mutation);
	}

	public NodeOption<NodeList<Expr>> args() {
		return location.safeTraversal(SEnumConstantDecl.ARGS);
	}

	public EnumConstantDecl withArgs(NodeOption<NodeList<Expr>> args) {
		return location.safeTraversalReplace(SEnumConstantDecl.ARGS, args);
	}

	public EnumConstantDecl withArgs(Mutation<NodeOption<NodeList<Expr>>> mutation) {
		return location.safeTraversalMutate(SEnumConstantDecl.ARGS, mutation);
	}

	public NodeOption<NodeList<MemberDecl>> classBody() {
		return location.safeTraversal(SEnumConstantDecl.CLASS_BODY);
	}

	public EnumConstantDecl withClassBody(NodeOption<NodeList<MemberDecl>> classBody) {
		return location.safeTraversalReplace(SEnumConstantDecl.CLASS_BODY, classBody);
	}

	public EnumConstantDecl withClassBody(Mutation<NodeOption<NodeList<MemberDecl>>> mutation) {
		return location.safeTraversalMutate(SEnumConstantDecl.CLASS_BODY, mutation);
	}
}
