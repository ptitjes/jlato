package org.jlato.internal.td.decl;

import org.jlato.internal.bu.decl.SEmptyTypeDecl;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.decl.EmptyTypeDecl;
import org.jlato.tree.decl.TypeDecl;

public class TDEmptyTypeDecl extends TDTree<SEmptyTypeDecl, TypeDecl, EmptyTypeDecl> implements EmptyTypeDecl {

	public Kind kind() {
		return Kind.EmptyTypeDecl;
	}

	public TDEmptyTypeDecl(TDLocation<SEmptyTypeDecl> location) {
		super(location);
	}

	public TDEmptyTypeDecl() {
		super(new TDLocation<SEmptyTypeDecl>(SEmptyTypeDecl.make()));
	}
}
