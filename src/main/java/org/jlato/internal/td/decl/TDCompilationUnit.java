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

public class TDCompilationUnit extends TDTree<SCompilationUnit, Node, CompilationUnit> implements CompilationUnit {

	public Kind kind() {
		return Kind.CompilationUnit;
	}

	public TDCompilationUnit(TDLocation<SCompilationUnit> location) {
		super(location);
	}

	public TDCompilationUnit(PackageDecl packageDecl, NodeList<ImportDecl> imports, NodeList<TypeDecl> types) {
		super(new TDLocation<SCompilationUnit>(SCompilationUnit.make(TDTree.<SPackageDecl>treeOf(packageDecl), TDTree.<SNodeList>treeOf(imports), TDTree.<SNodeList>treeOf(types))));
	}

	public PackageDecl packageDecl() {
		return location.safeTraversal(SCompilationUnit.PACKAGE_DECL);
	}

	public CompilationUnit withPackageDecl(PackageDecl packageDecl) {
		return location.safeTraversalReplace(SCompilationUnit.PACKAGE_DECL, packageDecl);
	}

	public CompilationUnit withPackageDecl(Mutation<PackageDecl> mutation) {
		return location.safeTraversalMutate(SCompilationUnit.PACKAGE_DECL, mutation);
	}

	public NodeList<ImportDecl> imports() {
		return location.safeTraversal(SCompilationUnit.IMPORTS);
	}

	public CompilationUnit withImports(NodeList<ImportDecl> imports) {
		return location.safeTraversalReplace(SCompilationUnit.IMPORTS, imports);
	}

	public CompilationUnit withImports(Mutation<NodeList<ImportDecl>> mutation) {
		return location.safeTraversalMutate(SCompilationUnit.IMPORTS, mutation);
	}

	public NodeList<TypeDecl> types() {
		return location.safeTraversal(SCompilationUnit.TYPES);
	}

	public CompilationUnit withTypes(NodeList<TypeDecl> types) {
		return location.safeTraversalReplace(SCompilationUnit.TYPES, types);
	}

	public CompilationUnit withTypes(Mutation<NodeList<TypeDecl>> mutation) {
		return location.safeTraversalMutate(SCompilationUnit.TYPES, mutation);
	}
}
