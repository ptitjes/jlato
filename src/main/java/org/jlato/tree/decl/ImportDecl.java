package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

public interface ImportDecl extends Node, TreeCombinators<ImportDecl> {

	QualifiedName name();

	ImportDecl withName(QualifiedName name);

	ImportDecl withName(Mutation<QualifiedName> mutation);

	boolean isStatic();

	ImportDecl setStatic(boolean isStatic);

	ImportDecl setStatic(Mutation<Boolean> mutation);

	boolean isOnDemand();

	ImportDecl setOnDemand(boolean isOnDemand);

	ImportDecl setOnDemand(Mutation<Boolean> mutation);
}
