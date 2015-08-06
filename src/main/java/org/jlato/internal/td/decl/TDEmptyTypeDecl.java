package org.jlato.internal.td.decl;

import org.jlato.internal.bu.decl.SEmptyTypeDecl;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.decl.EmptyTypeDecl;
import org.jlato.tree.decl.TypeDecl;

/**
 * An empty type declaration.
 */
public class TDEmptyTypeDecl extends TDTree<SEmptyTypeDecl, TypeDecl, EmptyTypeDecl> implements EmptyTypeDecl {

	/**
	 * Returns the kind of this empty type declaration.
	 *
	 * @return the kind of this empty type declaration.
	 */
	public Kind kind() {
		return Kind.EmptyTypeDecl;
	}

	/**
	 * Creates an empty type declaration for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDEmptyTypeDecl(TDLocation<SEmptyTypeDecl> location) {
		super(location);
	}

	/**
	 * Creates an empty type declaration with the specified child trees.
	 */
	public TDEmptyTypeDecl() {
		super(new TDLocation<SEmptyTypeDecl>(SEmptyTypeDecl.make()));
	}
}
