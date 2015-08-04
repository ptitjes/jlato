package org.jlato.internal.td.type;

import org.jlato.internal.bu.type.SUnknownType;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.type.Type;
import org.jlato.tree.type.UnknownType;

public class TDUnknownType extends TreeBase<SUnknownType, Type, UnknownType> implements UnknownType {

	public Kind kind() {
		return Kind.UnknownType;
	}

	public TDUnknownType(SLocation<SUnknownType> location) {
		super(location);
	}

	public TDUnknownType() {
		super(new SLocation<SUnknownType>(SUnknownType.make()));
	}
}
