package org.jlato.internal.td.type;

import org.jlato.internal.bu.type.SVoidType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.type.Type;
import org.jlato.tree.type.VoidType;

/**
 * A void type.
 */
public class TDVoidType extends TDTree<SVoidType, Type, VoidType> implements VoidType {

	/**
	 * Returns the kind of this void type.
	 *
	 * @return the kind of this void type.
	 */
	public Kind kind() {
		return Kind.VoidType;
	}

	/**
	 * Creates a void type for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDVoidType(TDLocation<SVoidType> location) {
		super(location);
	}

	/**
	 * Creates a void type with the specified child trees.
	 */
	public TDVoidType() {
		super(new TDLocation<SVoidType>(SVoidType.make()));
	}
}
