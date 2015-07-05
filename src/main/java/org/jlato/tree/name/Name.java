package org.jlato.tree.name;

import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SLeafState;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

public class Name extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public Name instantiate(SLocation location) {
			return new Name(location);
		}
	};

	private Name(SLocation location) {
		super(location);
	}

	public Name(String identifier) {
		super(new SLocation(new SLeaf(kind, new SLeafState(dataOf(identifier)))));
	}

	public String name() {
		return location.leafData(IDENTIFIER).toString();
	}

	public Name withName(String name) {
		return location.leafWithData(IDENTIFIER, name);
	}

	@Override
	public String toString() {
		return name();
	}

	public static final int IDENTIFIER = 0;
}
