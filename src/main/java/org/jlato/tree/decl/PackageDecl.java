package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * A package declaration.
 */
public interface PackageDecl extends Node, Documentable<PackageDecl>, TreeCombinators<PackageDecl> {

	/**
	 * Returns the annotations of this package declaration.
	 *
	 * @return the annotations of this package declaration.
	 */
	NodeList<AnnotationExpr> annotations();

	/**
	 * Replaces the annotations of this package declaration.
	 *
	 * @param annotations the replacement for the annotations of this package declaration.
	 * @return the resulting mutated package declaration.
	 */
	PackageDecl withAnnotations(NodeList<AnnotationExpr> annotations);

	/**
	 * Mutates the annotations of this package declaration.
	 *
	 * @param mutation the mutation to apply to the annotations of this package declaration.
	 * @return the resulting mutated package declaration.
	 */
	PackageDecl withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	/**
	 * Returns the name of this package declaration.
	 *
	 * @return the name of this package declaration.
	 */
	QualifiedName name();

	/**
	 * Replaces the name of this package declaration.
	 *
	 * @param name the replacement for the name of this package declaration.
	 * @return the resulting mutated package declaration.
	 */
	PackageDecl withName(QualifiedName name);

	/**
	 * Mutates the name of this package declaration.
	 *
	 * @param mutation the mutation to apply to the name of this package declaration.
	 * @return the resulting mutated package declaration.
	 */
	PackageDecl withName(Mutation<QualifiedName> mutation);
}
