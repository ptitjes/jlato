package org.jlato.internal.td.stmt;

import org.jlato.internal.bu.decl.STypeDecl;
import org.jlato.internal.bu.stmt.STypeDeclarationStmt;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.tree.stmt.Stmt;
import org.jlato.tree.stmt.TypeDeclarationStmt;
import org.jlato.util.Mutation;

public class TDTypeDeclarationStmt extends TreeBase<STypeDeclarationStmt, Stmt, TypeDeclarationStmt> implements TypeDeclarationStmt {

	public Kind kind() {
		return Kind.TypeDeclarationStmt;
	}

	public TDTypeDeclarationStmt(SLocation<STypeDeclarationStmt> location) {
		super(location);
	}

	public TDTypeDeclarationStmt(TypeDecl typeDecl) {
		super(new SLocation<STypeDeclarationStmt>(STypeDeclarationStmt.make(TreeBase.<STypeDecl>treeOf(typeDecl))));
	}

	public TypeDecl typeDecl() {
		return location.safeTraversal(STypeDeclarationStmt.TYPE_DECL);
	}

	public TypeDeclarationStmt withTypeDecl(TypeDecl typeDecl) {
		return location.safeTraversalReplace(STypeDeclarationStmt.TYPE_DECL, typeDecl);
	}

	public TypeDeclarationStmt withTypeDecl(Mutation<TypeDecl> mutation) {
		return location.safeTraversalMutate(STypeDeclarationStmt.TYPE_DECL, mutation);
	}
}
