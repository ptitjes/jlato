package org.jlato.internal.td.name;

import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

public class TDName extends TDTree<SName, Expr, Name> implements Name {

	public Kind kind() {
		return Kind.Name;
	}

	public TDName(TDLocation<SName> location) {
		super(location);
	}

	public TDName(String id) {
		super(new TDLocation<SName>(SName.make(id)));
	}

	public String id() {
		return location.safeProperty(SName.ID);
	}

	public Name withId(String id) {
		return location.safePropertyReplace(SName.ID, id);
	}

	public Name withId(Mutation<String> mutation) {
		return location.safePropertyMutate(SName.ID, mutation);
	}
}
