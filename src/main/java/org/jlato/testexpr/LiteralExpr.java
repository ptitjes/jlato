package org.jlato.testexpr;

import org.jlato.internal.bu.LLiteral;
import org.jlato.internal.bu.SLeaf;
import org.jlato.tree.Expr;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class LiteralExpr extends Expr {

	public final static Kind kind = new Kind() {
		public Tree instantiate(SLocation location) {
			return new LiteralExpr(location);
		}
	};

	private LiteralExpr(SLocation location) {
		super(location);
	}

	public LiteralExpr(String valueString) {
		super(new SLocation(new SLeaf(kind, new LLiteral<Integer>(Integer.class, valueString))));
	}

	@Override
	public String toString() {
		return location.leafToken().toString();
	}
}
