package org.jlato.tree.type;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Tree;

public class VoidType extends Type {

	public final static Tree.Kind kind = new Tree.Kind() {
		public VoidType instantiate(SLocation location) {
			return new VoidType(location);
		}
	};

	private VoidType(SLocation location) {
		super(location);
	}

	public VoidType() {
		super(new SLocation(new SNode(kind, runOf())));
	}

}
