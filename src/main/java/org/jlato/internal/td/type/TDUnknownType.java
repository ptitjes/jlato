package org.jlato.internal.td.type;

import org.jlato.internal.bu.type.SUnknownType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.type.Type;
import org.jlato.tree.type.UnknownType;

public class TDUnknownType extends TDTree<SUnknownType, Type, UnknownType> implements UnknownType {

	public Kind kind() {
		return Kind.UnknownType;
	}

	public TDUnknownType(TDLocation<SUnknownType> location) {
		super(location);
	}

	public TDUnknownType() {
		super(new TDLocation<SUnknownType>(SUnknownType.make()));
	}
}