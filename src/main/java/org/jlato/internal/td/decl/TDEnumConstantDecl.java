package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.decl.SEnumConstantDecl;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.EnumConstantDecl;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * An enum constant declaration.
 */
public class TDEnumConstantDecl extends TDTree<SEnumConstantDecl, MemberDecl, EnumConstantDecl> implements EnumConstantDecl {

	/**
	 * Returns the kind of this enum constant declaration.
	 *
	 * @return the kind of this enum constant declaration.
	 */
	public Kind kind() {
		return Kind.EnumConstantDecl;
	}

	/**
	 * Creates an enum constant declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDEnumConstantDecl(TDLocation<SEnumConstantDecl> location) {
		super(location);
	}

	/**
	 * Creates an enum constant declaration with the specified child trees.
	 *
	 * @param modifiers the modifiers child tree.
	 * @param name      the name child tree.
	 * @param args      the args child tree.
	 * @param classBody the class body child tree.
	 */
	public TDEnumConstantDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeOption<NodeList<Expr>> args, NodeOption<NodeList<MemberDecl>> classBody) {
		super(new TDLocation<SEnumConstantDecl>(SEnumConstantDecl.make(TDTree.<SNodeList>treeOf(modifiers), TDTree.<SName>treeOf(name), TDTree.<SNodeOption>treeOf(args), TDTree.<SNodeOption>treeOf(classBody))));
	}

	/**
	 * Returns the modifiers of this enum constant declaration.
	 *
	 * @return the modifiers of this enum constant declaration.
	 */
	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SEnumConstantDecl.MODIFIERS);
	}

	/**
	 * Replaces the modifiers of this enum constant declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	public EnumConstantDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SEnumConstantDecl.MODIFIERS, modifiers);
	}

	/**
	 * Mutates the modifiers of this enum constant declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	public EnumConstantDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SEnumConstantDecl.MODIFIERS, mutation);
	}

	/**
	 * Returns the name of this enum constant declaration.
	 *
	 * @return the name of this enum constant declaration.
	 */
	public Name name() {
		return location.safeTraversal(SEnumConstantDecl.NAME);
	}

	/**
	 * Replaces the name of this enum constant declaration.
	 *
	 * @param name the replacement for the name of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	public EnumConstantDecl withName(Name name) {
		return location.safeTraversalReplace(SEnumConstantDecl.NAME, name);
	}

	/**
	 * Mutates the name of this enum constant declaration.
	 *
	 * @param mutation the mutation to apply to the name of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	public EnumConstantDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SEnumConstantDecl.NAME, mutation);
	}

	/**
	 * Replaces the name of this enum constant declaration.
	 *
	 * @param name the replacement for the name of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	public EnumConstantDecl withName(String name) {
		return location.safeTraversalReplace(SEnumConstantDecl.NAME, Trees.name(name));
	}

	/**
	 * Returns the args of this enum constant declaration.
	 *
	 * @return the args of this enum constant declaration.
	 */
	public NodeOption<NodeList<Expr>> args() {
		return location.safeTraversal(SEnumConstantDecl.ARGS);
	}

	/**
	 * Replaces the args of this enum constant declaration.
	 *
	 * @param args the replacement for the args of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	public EnumConstantDecl withArgs(NodeOption<NodeList<Expr>> args) {
		return location.safeTraversalReplace(SEnumConstantDecl.ARGS, args);
	}

	/**
	 * Mutates the args of this enum constant declaration.
	 *
	 * @param mutation the mutation to apply to the args of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	public EnumConstantDecl withArgs(Mutation<NodeOption<NodeList<Expr>>> mutation) {
		return location.safeTraversalMutate(SEnumConstantDecl.ARGS, mutation);
	}

	/**
	 * Replaces the args of this enum constant declaration.
	 *
	 * @param args the replacement for the args of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	public EnumConstantDecl withArgs(NodeList<Expr> args) {
		return location.safeTraversalReplace(SEnumConstantDecl.ARGS, Trees.some(args));
	}

	/**
	 * Replaces the args of this enum constant declaration.
	 *
	 * @return the resulting mutated enum constant declaration.
	 */
	public EnumConstantDecl withNoArgs() {
		return location.safeTraversalReplace(SEnumConstantDecl.ARGS, Trees.<NodeList<Expr>>none());
	}

	/**
	 * Returns the class body of this enum constant declaration.
	 *
	 * @return the class body of this enum constant declaration.
	 */
	public NodeOption<NodeList<MemberDecl>> classBody() {
		return location.safeTraversal(SEnumConstantDecl.CLASS_BODY);
	}

	/**
	 * Replaces the class body of this enum constant declaration.
	 *
	 * @param classBody the replacement for the class body of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	public EnumConstantDecl withClassBody(NodeOption<NodeList<MemberDecl>> classBody) {
		return location.safeTraversalReplace(SEnumConstantDecl.CLASS_BODY, classBody);
	}

	/**
	 * Mutates the class body of this enum constant declaration.
	 *
	 * @param mutation the mutation to apply to the class body of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	public EnumConstantDecl withClassBody(Mutation<NodeOption<NodeList<MemberDecl>>> mutation) {
		return location.safeTraversalMutate(SEnumConstantDecl.CLASS_BODY, mutation);
	}

	/**
	 * Replaces the class body of this enum constant declaration.
	 *
	 * @param classBody the replacement for the class body of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	public EnumConstantDecl withClassBody(NodeList<MemberDecl> classBody) {
		return location.safeTraversalReplace(SEnumConstantDecl.CLASS_BODY, Trees.some(classBody));
	}

	/**
	 * Replaces the class body of this enum constant declaration.
	 *
	 * @return the resulting mutated enum constant declaration.
	 */
	public EnumConstantDecl withNoClassBody() {
		return location.safeTraversalReplace(SEnumConstantDecl.CLASS_BODY, Trees.<NodeList<MemberDecl>>none());
	}
}
