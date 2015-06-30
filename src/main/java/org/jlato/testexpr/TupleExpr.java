package org.jlato.testexpr;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class TupleExpr extends Expr {

	public final static Kind kind = new Kind() {
		public Tree instantiate(SLocation location) {
			return new TupleExpr(location);
		}
	};

	private TupleExpr(SLocation location) {
		super(location);
	}

	public TupleExpr(NodeList<Expr> expressions) {
		super(new SLocation(new SNode(kind, runOf(expressions))));
	}

	public NodeList<Expr> expressions() {
		return location.nodeChild(EXPRESSIONS);
	}

	public TupleExpr withExpressions(NodeList<Expr> expressions) {
		return location.nodeWithChild(EXPRESSIONS, expressions);
	}

	private static final int EXPRESSIONS = 0;

	@Override
	public String toString() {
		return expressions().mkString("(", ",", ")");
	}
}
