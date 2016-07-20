package org.jlato.tree.expr;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * An object creation expression.
 */
public interface ObjectCreationExpr extends Expr, TreeCombinators<ObjectCreationExpr> {

	/**
	 * Returns the scope of this object creation expression.
	 *
	 * @return the scope of this object creation expression.
	 */
	NodeOption<Expr> scope();

	/**
	 * Replaces the scope of this object creation expression.
	 *
	 * @param scope the replacement for the scope of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	ObjectCreationExpr withScope(NodeOption<Expr> scope);

	/**
	 * Mutates the scope of this object creation expression.
	 *
	 * @param mutation the mutation to apply to the scope of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	ObjectCreationExpr withScope(Mutation<NodeOption<Expr>> mutation);

	/**
	 * Replaces the scope of this object creation expression.
	 *
	 * @param scope the replacement for the scope of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	ObjectCreationExpr withScope(Expr scope);

	/**
	 * Replaces the scope of this object creation expression.
	 *
	 * @return the resulting mutated object creation expression.
	 */
	ObjectCreationExpr withNoScope();

	/**
	 * Returns the type args of this object creation expression.
	 *
	 * @return the type args of this object creation expression.
	 */
	NodeList<Type> typeArgs();

	/**
	 * Replaces the type args of this object creation expression.
	 *
	 * @param typeArgs the replacement for the type args of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	ObjectCreationExpr withTypeArgs(NodeList<Type> typeArgs);

	/**
	 * Mutates the type args of this object creation expression.
	 *
	 * @param mutation the mutation to apply to the type args of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	ObjectCreationExpr withTypeArgs(Mutation<NodeList<Type>> mutation);

	/**
	 * Returns the type of this object creation expression.
	 *
	 * @return the type of this object creation expression.
	 */
	QualifiedType type();

	/**
	 * Replaces the type of this object creation expression.
	 *
	 * @param type the replacement for the type of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	ObjectCreationExpr withType(QualifiedType type);

	/**
	 * Mutates the type of this object creation expression.
	 *
	 * @param mutation the mutation to apply to the type of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	ObjectCreationExpr withType(Mutation<QualifiedType> mutation);

	/**
	 * Returns the args of this object creation expression.
	 *
	 * @return the args of this object creation expression.
	 */
	NodeList<Expr> args();

	/**
	 * Replaces the args of this object creation expression.
	 *
	 * @param args the replacement for the args of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	ObjectCreationExpr withArgs(NodeList<Expr> args);

	/**
	 * Mutates the args of this object creation expression.
	 *
	 * @param mutation the mutation to apply to the args of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	ObjectCreationExpr withArgs(Mutation<NodeList<Expr>> mutation);

	/**
	 * Returns the body of this object creation expression.
	 *
	 * @return the body of this object creation expression.
	 */
	NodeOption<NodeList<MemberDecl>> body();

	/**
	 * Replaces the body of this object creation expression.
	 *
	 * @param body the replacement for the body of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	ObjectCreationExpr withBody(NodeOption<NodeList<MemberDecl>> body);

	/**
	 * Mutates the body of this object creation expression.
	 *
	 * @param mutation the mutation to apply to the body of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	ObjectCreationExpr withBody(Mutation<NodeOption<NodeList<MemberDecl>>> mutation);

	/**
	 * Replaces the body of this object creation expression.
	 *
	 * @param body the replacement for the body of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	ObjectCreationExpr withBody(NodeList<MemberDecl> body);

	/**
	 * Replaces the body of this object creation expression.
	 *
	 * @return the resulting mutated object creation expression.
	 */
	ObjectCreationExpr withNoBody();
}
