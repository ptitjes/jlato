package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * An enum constant declaration.
 */
public interface EnumConstantDecl extends MemberDecl, TreeCombinators<EnumConstantDecl> {

	/**
	 * Returns the modifiers of this enum constant declaration.
	 *
	 * @return the modifiers of this enum constant declaration.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this enum constant declaration.
	 *
	 * @param modifiers the replacement for the modifiers of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	EnumConstantDecl withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this enum constant declaration.
	 *
	 * @param mutation the mutation to apply to the modifiers of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	EnumConstantDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the name of this enum constant declaration.
	 *
	 * @return the name of this enum constant declaration.
	 */
	Name name();

	/**
	 * Replaces the name of this enum constant declaration.
	 *
	 * @param name the replacement for the name of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	EnumConstantDecl withName(Name name);

	/**
	 * Mutates the name of this enum constant declaration.
	 *
	 * @param mutation the mutation to apply to the name of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	EnumConstantDecl withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this enum constant declaration.
	 *
	 * @param name the replacement for the name of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	EnumConstantDecl withName(String name);

	/**
	 * Returns the args of this enum constant declaration.
	 *
	 * @return the args of this enum constant declaration.
	 */
	NodeOption<NodeList<Expr>> args();

	/**
	 * Replaces the args of this enum constant declaration.
	 *
	 * @param args the replacement for the args of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	EnumConstantDecl withArgs(NodeOption<NodeList<Expr>> args);

	/**
	 * Mutates the args of this enum constant declaration.
	 *
	 * @param mutation the mutation to apply to the args of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	EnumConstantDecl withArgs(Mutation<NodeOption<NodeList<Expr>>> mutation);

	/**
	 * Replaces the args of this enum constant declaration.
	 *
	 * @param args the replacement for the args of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	EnumConstantDecl withArgs(NodeList<Expr> args);

	/**
	 * Replaces the args of this enum constant declaration.
	 *
	 * @return the resulting mutated enum constant declaration.
	 */
	EnumConstantDecl withNoArgs();

	/**
	 * Returns the class body of this enum constant declaration.
	 *
	 * @return the class body of this enum constant declaration.
	 */
	NodeOption<NodeList<MemberDecl>> classBody();

	/**
	 * Replaces the class body of this enum constant declaration.
	 *
	 * @param classBody the replacement for the class body of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	EnumConstantDecl withClassBody(NodeOption<NodeList<MemberDecl>> classBody);

	/**
	 * Mutates the class body of this enum constant declaration.
	 *
	 * @param mutation the mutation to apply to the class body of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	EnumConstantDecl withClassBody(Mutation<NodeOption<NodeList<MemberDecl>>> mutation);

	/**
	 * Replaces the class body of this enum constant declaration.
	 *
	 * @param classBody the replacement for the class body of this enum constant declaration.
	 * @return the resulting mutated enum constant declaration.
	 */
	EnumConstantDecl withClassBody(NodeList<MemberDecl> classBody);

	/**
	 * Replaces the class body of this enum constant declaration.
	 *
	 * @return the resulting mutated enum constant declaration.
	 */
	EnumConstantDecl withNoClassBody();
}
