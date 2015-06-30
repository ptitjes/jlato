package org.jlato.testexpr;

import org.jlato.internal.bu.LLiteral;
import org.jlato.tree.*;
import org.jlato.internal.bu.SLeaf;

/**
 * @author Didier Villevalois
 */
public class BinaryOp extends Tree {

	public final static Kind kind = new Kind() {
		public Tree instantiate(SLocation location) {
			return new BinaryOp(location);
		}
	};

	public final static BinaryOp Plus = new BinaryOp("+");

	private BinaryOp(SLocation location) {
		super(location);
	}

	private BinaryOp(String valueString) {
		super(new SLocation(new SLeaf(kind, new LLiteral<Integer>(Integer.class, valueString))));
	}

	@Override
	public String toString() {
		return location.leafToken().toString();
	}
}
