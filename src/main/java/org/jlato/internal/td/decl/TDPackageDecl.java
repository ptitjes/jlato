package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.decl.SPackageDecl;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.PackageDecl;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * A package declaration.
 */
public class TDPackageDecl extends TDTree<SPackageDecl, Node, PackageDecl> implements PackageDecl {

	/**
	 * Returns the kind of this package declaration.
	 *
	 * @return the kind of this package declaration.
	 */
	public Kind kind() {
		return Kind.PackageDecl;
	}

	/**
	 * Creates a package declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDPackageDecl(TDLocation<SPackageDecl> location) {
		super(location);
	}

	/**
	 * Creates a package declaration with the specified child trees.
	 *
	 * @param annotations the annotations child tree.
	 * @param name        the name child tree.
	 */
	public TDPackageDecl(NodeList<AnnotationExpr> annotations, QualifiedName name) {
		super(new TDLocation<SPackageDecl>(SPackageDecl.make(TDTree.<SNodeList>treeOf(annotations), TDTree.<SQualifiedName>treeOf(name))));
	}

	/**
	 * Returns the annotations of this package declaration.
	 *
	 * @return the annotations of this package declaration.
	 */
	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(SPackageDecl.ANNOTATIONS);
	}

	/**
	 * Replaces the annotations of this package declaration.
	 *
	 * @param annotations the replacement for the annotations of this package declaration.
	 * @return the resulting mutated package declaration.
	 */
	public PackageDecl withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(SPackageDecl.ANNOTATIONS, annotations);
	}

	/**
	 * Mutates the annotations of this package declaration.
	 *
	 * @param mutation the mutation to apply to the annotations of this package declaration.
	 * @return the resulting mutated package declaration.
	 */
	public PackageDecl withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(SPackageDecl.ANNOTATIONS, mutation);
	}

	/**
	 * Returns the name of this package declaration.
	 *
	 * @return the name of this package declaration.
	 */
	public QualifiedName name() {
		return location.safeTraversal(SPackageDecl.NAME);
	}

	/**
	 * Replaces the name of this package declaration.
	 *
	 * @param name the replacement for the name of this package declaration.
	 * @return the resulting mutated package declaration.
	 */
	public PackageDecl withName(QualifiedName name) {
		return location.safeTraversalReplace(SPackageDecl.NAME, name);
	}

	/**
	 * Mutates the name of this package declaration.
	 *
	 * @param mutation the mutation to apply to the name of this package declaration.
	 * @return the resulting mutated package declaration.
	 */
	public PackageDecl withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(SPackageDecl.NAME, mutation);
	}
}
