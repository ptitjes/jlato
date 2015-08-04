package org.jlato.internal.td.decl;

import org.jlato.internal.bu.decl.SEmptyTypeDecl;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.decl.EmptyTypeDecl;
import org.jlato.tree.decl.TypeDecl;

public class TDEmptyTypeDecl extends TreeBase<SEmptyTypeDecl, TypeDecl, EmptyTypeDecl> implements EmptyTypeDecl {

	public Kind kind() {
		return Kind.EmptyTypeDecl;
	}

	public TDEmptyTypeDecl(SLocation<SEmptyTypeDecl> location) {
		super(location);
	}

	public TDEmptyTypeDecl() {
		super(new SLocation<SEmptyTypeDecl>(SEmptyTypeDecl.make()));
	}
}
