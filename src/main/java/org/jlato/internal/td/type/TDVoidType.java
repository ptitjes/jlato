package org.jlato.internal.td.type;

import org.jlato.internal.bu.type.SVoidType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.type.Type;
import org.jlato.tree.type.VoidType;

public class TDVoidType extends TDTree<SVoidType, Type, VoidType> implements VoidType {

	public Kind kind() {
		return Kind.VoidType;
	}

	public TDVoidType(TDLocation<SVoidType> location) {
		super(location);
	}

	public TDVoidType() {
		super(new TDLocation<SVoidType>(SVoidType.make()));
	}
}
