package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface CompilationUnit extends Tree, TreeCombinators<CompilationUnit> {

	PackageDecl packageDecl();

	CompilationUnit withPackageDecl(PackageDecl packageDecl);

	CompilationUnit withPackageDecl(Mutation<PackageDecl> mutation);

	NodeList<ImportDecl> imports();

	CompilationUnit withImports(NodeList<ImportDecl> imports);

	CompilationUnit withImports(Mutation<NodeList<ImportDecl>> mutation);

	NodeList<TypeDecl> types();

	CompilationUnit withTypes(NodeList<TypeDecl> types);

	CompilationUnit withTypes(Mutation<NodeList<TypeDecl>> mutation);
}
