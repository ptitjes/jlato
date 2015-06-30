package org.jlato.testexpr;

import org.jlato.internal.bu.LLiteral;
import org.jlato.internal.bu.SLeaf;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class UnaryOp extends Tree {

	public final static Tree.Kind type = new Kind() {
		public Tree instantiate(SLocation location) {
			return new UnaryOp(location);
		}
	};

	public final static UnaryOp Minus = new UnaryOp("-");

	private UnaryOp(SLocation location) {
		super(location);
	}

	private UnaryOp(String valueString) {
		super(new SLocation(new SLeaf(type, new LLiteral(literalClass, valueString))));
	}

	@Override
	public String toString() {
		return location.leafToken().toString();
	}
}
