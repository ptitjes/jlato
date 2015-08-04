package org.jlato.internal.td.decl;

import org.jlato.internal.bu.decl.SImportDecl;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ImportDecl;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

public class TDImportDecl extends TreeBase<SImportDecl, Tree, ImportDecl> implements ImportDecl {

	public Kind kind() {
		return Kind.ImportDecl;
	}

	public TDImportDecl(SLocation<SImportDecl> location) {
		super(location);
	}

	public TDImportDecl(QualifiedName name, boolean isStatic, boolean isOnDemand) {
		super(new SLocation<SImportDecl>(SImportDecl.make(TreeBase.<SQualifiedName>treeOf(name), isStatic, isOnDemand)));
	}

	public QualifiedName name() {
		return location.safeTraversal(SImportDecl.NAME);
	}

	public ImportDecl withName(QualifiedName name) {
		return location.safeTraversalReplace(SImportDecl.NAME, name);
	}

	public ImportDecl withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(SImportDecl.NAME, mutation);
	}

	public boolean isStatic() {
		return location.safeProperty(SImportDecl.STATIC);
	}

	public ImportDecl setStatic(boolean isStatic) {
		return location.safePropertyReplace(SImportDecl.STATIC, isStatic);
	}

	public ImportDecl setStatic(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SImportDecl.STATIC, mutation);
	}

	public boolean isOnDemand() {
		return location.safeProperty(SImportDecl.ON_DEMAND);
	}

	public ImportDecl setOnDemand(boolean isOnDemand) {
		return location.safePropertyReplace(SImportDecl.ON_DEMAND, isOnDemand);
	}

	public ImportDecl setOnDemand(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SImportDecl.ON_DEMAND, mutation);
	}
}
