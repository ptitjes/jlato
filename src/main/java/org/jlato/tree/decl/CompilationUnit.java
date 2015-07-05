package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

public class CompilationUnit extends Tree {

	public final static Kind kind = new Kind() {
		public CompilationUnit instantiate(SLocation location) {
			return new CompilationUnit(location);
		}
	};

	private CompilationUnit(SLocation location) {
		super(location);
	}

	public CompilationUnit(PackageDecl packageDecl, NodeList<ImportDecl> imports, NodeList<TypeDecl> types) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(packageDecl, imports, types)))));
	}

	public PackageDecl packageDecl() {
		return location.nodeChild(PACKAGE_DECL);
	}

	public CompilationUnit withPackageDecl(PackageDecl packageDecl) {
		return location.nodeWithChild(PACKAGE_DECL, packageDecl);
	}

	public NodeList<ImportDecl> imports() {
		return location.nodeChild(IMPORTS);
	}

	public CompilationUnit withImports(NodeList<ImportDecl> imports) {
		return location.nodeWithChild(IMPORTS, imports);
	}

	public NodeList<TypeDecl> types() {
		return location.nodeChild(TYPES);
	}

	public CompilationUnit withTypes(NodeList<TypeDecl> types) {
		return location.nodeWithChild(TYPES, types);
	}

	private static final int PACKAGE_DECL = 0;
	private static final int IMPORTS = 1;
	private static final int TYPES = 2;
}
