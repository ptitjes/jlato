package org.jlato.internal.td.decl;

import org.jlato.internal.bu.decl.SEmptyMemberDecl;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.decl.EmptyMemberDecl;
import org.jlato.tree.decl.MemberDecl;

/**
 * An empty member declaration.
 */
public class TDEmptyMemberDecl extends TDTree<SEmptyMemberDecl, MemberDecl, EmptyMemberDecl> implements EmptyMemberDecl {

	/**
	 * Returns the kind of this empty member declaration.
	 *
	 * @return the kind of this empty member declaration.
	 */
	public Kind kind() {
		return Kind.EmptyMemberDecl;
	}

	/**
	 * Creates an empty member declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDEmptyMemberDecl(TDLocation<SEmptyMemberDecl> location) {
		super(location);
	}

	/**
	 * Creates an empty member declaration with the specified child trees.
	 */
	public TDEmptyMemberDecl() {
		super(new TDLocation<SEmptyMemberDecl>(SEmptyMemberDecl.make()));
	}
}
