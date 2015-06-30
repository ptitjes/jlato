package org.jlato.tree.type;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Tree;

public class UnknownType extends Type {

	public final static Tree.Kind kind = new Tree.Kind() {
		public UnknownType instantiate(SLocation location) {
			return new UnknownType(location);
		}
	};

	private UnknownType(SLocation location) {
		super(location);
	}

	public UnknownType() {
		super(new SLocation(new SNode(kind, runOf())));
	}

}
