package org.jlato.internal.td.type;

import org.jlato.internal.bu.type.SUnknownType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.type.Type;
import org.jlato.tree.type.UnknownType;

/**
 * An unknown type.
 */
public class TDUnknownType extends TDTree<SUnknownType, Type, UnknownType> implements UnknownType {

	/**
	 * Returns the kind of this unknown type.
	 *
	 * @return the kind of this unknown type.
	 */
	public Kind kind() {
		return Kind.UnknownType;
	}

	/**
	 * Creates an unknown type for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDUnknownType(TDLocation<SUnknownType> location) {
		super(location);
	}

	/**
	 * Creates an unknown type with the specified child trees.
	 */
	public TDUnknownType() {
		super(new TDLocation<SUnknownType>(SUnknownType.make()));
	}
}
