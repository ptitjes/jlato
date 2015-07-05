package org.jlato.tree.type;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.Type;

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
		super(new SLocation(new SNode(kind, new SNodeState(treesOf()))));
	}

}
