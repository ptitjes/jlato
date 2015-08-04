package org.jlato.internal.td.type;

import org.jlato.internal.bu.type.SVoidType;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.type.Type;
import org.jlato.tree.type.VoidType;

public class TDVoidType extends TreeBase<SVoidType, Type, VoidType> implements VoidType {

	public Kind kind() {
		return Kind.VoidType;
	}

	public TDVoidType(SLocation<SVoidType> location) {
		super(location);
	}

	public TDVoidType() {
		super(new SLocation<SVoidType>(SVoidType.make()));
	}
}
