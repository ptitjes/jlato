package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.TypeDecl;

public class TypeDeclarationStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public TypeDeclarationStmt instantiate(SLocation location) {
			return new TypeDeclarationStmt(location);
		}
	};

	private TypeDeclarationStmt(SLocation location) {
		super(location);
	}

	public TypeDeclarationStmt(TypeDecl typeDecl) {
		super(new SLocation(new SNode(kind, runOf(typeDecl))));
	}

	public TypeDecl typeDecl() {
		return location.nodeChild(TYPE_DECL);
	}

	public TypeDeclarationStmt withTypeDecl(TypeDecl typeDecl) {
		return location.nodeWithChild(TYPE_DECL, typeDecl);
	}

	private static final int TYPE_DECL = 0;
}
