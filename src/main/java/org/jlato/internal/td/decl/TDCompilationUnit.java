package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.decl.SCompilationUnit;
import org.jlato.internal.bu.decl.SPackageDecl;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.CompilationUnit;
import org.jlato.tree.decl.ImportDecl;
import org.jlato.tree.decl.PackageDecl;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.util.Mutation;

/**
 * A compilation unit.
 */
public class TDCompilationUnit extends TDTree<SCompilationUnit, Node, CompilationUnit> implements CompilationUnit {

	/**
	 * Returns the kind of this compilation unit.
	 *
	 * @return the kind of this compilation unit.
	 */
	public Kind kind() {
		return Kind.CompilationUnit;
	}

	/**
	 * Creates a compilation unit for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDCompilationUnit(TDLocation<SCompilationUnit> location) {
		super(location);
	}

	/**
	 * Creates a compilation unit with the specified child trees.
	 *
	 * @param packageDecl the package declaration child tree.
	 * @param imports     the import declarations child tree.
	 * @param types       the types child tree.
	 */
	public TDCompilationUnit(PackageDecl packageDecl, NodeList<ImportDecl> imports, NodeList<TypeDecl> types) {
		super(new TDLocation<SCompilationUnit>(SCompilationUnit.make(TDTree.<SPackageDecl>treeOf(packageDecl), TDTree.<SNodeList>treeOf(imports), TDTree.<SNodeList>treeOf(types))));
	}

	/**
	 * Returns the package declaration of this compilation unit.
	 *
	 * @return the package declaration of this compilation unit.
	 */
	public PackageDecl packageDecl() {
		return location.safeTraversal(SCompilationUnit.PACKAGE_DECL);
	}

	/**
	 * Replaces the package declaration of this compilation unit.
	 *
	 * @param packageDecl the replacement for the package declaration of this compilation unit.
	 * @return the resulting mutated compilation unit.
	 */
	public CompilationUnit withPackageDecl(PackageDecl packageDecl) {
		return location.safeTraversalReplace(SCompilationUnit.PACKAGE_DECL, packageDecl);
	}

	/**
	 * Mutates the package declaration of this compilation unit.
	 *
	 * @param mutation the mutation to apply to the package declaration of this compilation unit.
	 * @return the resulting mutated compilation unit.
	 */
	public CompilationUnit withPackageDecl(Mutation<PackageDecl> mutation) {
		return location.safeTraversalMutate(SCompilationUnit.PACKAGE_DECL, mutation);
	}

	/**
	 * Returns the import declarations of this compilation unit.
	 *
	 * @return the import declarations of this compilation unit.
	 */
	public NodeList<ImportDecl> imports() {
		return location.safeTraversal(SCompilationUnit.IMPORTS);
	}

	/**
	 * Replaces the import declarations of this compilation unit.
	 *
	 * @param imports the replacement for the import declarations of this compilation unit.
	 * @return the resulting mutated compilation unit.
	 */
	public CompilationUnit withImports(NodeList<ImportDecl> imports) {
		return location.safeTraversalReplace(SCompilationUnit.IMPORTS, imports);
	}

	/**
	 * Mutates the import declarations of this compilation unit.
	 *
	 * @param mutation the mutation to apply to the import declarations of this compilation unit.
	 * @return the resulting mutated compilation unit.
	 */
	public CompilationUnit withImports(Mutation<NodeList<ImportDecl>> mutation) {
		return location.safeTraversalMutate(SCompilationUnit.IMPORTS, mutation);
	}

	/**
	 * Returns the types of this compilation unit.
	 *
	 * @return the types of this compilation unit.
	 */
	public NodeList<TypeDecl> types() {
		return location.safeTraversal(SCompilationUnit.TYPES);
	}

	/**
	 * Replaces the types of this compilation unit.
	 *
	 * @param types the replacement for the types of this compilation unit.
	 * @return the resulting mutated compilation unit.
	 */
	public CompilationUnit withTypes(NodeList<TypeDecl> types) {
		return location.safeTraversalReplace(SCompilationUnit.TYPES, types);
	}

	/**
	 * Mutates the types of this compilation unit.
	 *
	 * @param mutation the mutation to apply to the types of this compilation unit.
	 * @return the resulting mutated compilation unit.
	 */
	public CompilationUnit withTypes(Mutation<NodeList<TypeDecl>> mutation) {
		return location.safeTraversalMutate(SCompilationUnit.TYPES, mutation);
	}
}
