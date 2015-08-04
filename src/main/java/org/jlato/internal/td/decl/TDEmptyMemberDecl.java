package org.jlato.internal.td.decl;

import org.jlato.internal.bu.decl.SEmptyMemberDecl;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.decl.EmptyMemberDecl;
import org.jlato.tree.decl.MemberDecl;

public class TDEmptyMemberDecl extends TDTree<SEmptyMemberDecl, MemberDecl, EmptyMemberDecl> implements EmptyMemberDecl {

	public Kind kind() {
		return Kind.EmptyMemberDecl;
	}

	public TDEmptyMemberDecl(TDLocation<SEmptyMemberDecl> location) {
		super(location);
	}

	public TDEmptyMemberDecl() {
		super(new TDLocation<SEmptyMemberDecl>(SEmptyMemberDecl.make()));
	}
}
