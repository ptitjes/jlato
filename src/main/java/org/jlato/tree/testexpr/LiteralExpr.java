package org.jlato.tree.testexpr;

import org.jlato.internal.bu.LLiteral;
import org.jlato.internal.bu.SContext;
import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.STree;
import org.jlato.tree.Expr;

/**
 * @author Didier Villevalois
 */
public class LiteralExpr extends Expr<LiteralExpr> {

	private LiteralExpr(SContext context, STree<LiteralExpr> content) {
		super(context, content);
	}

	public LiteralExpr(String valueString) {
		super(null, new SLeaf<LiteralExpr>(TYPE, new LLiteral(valueString)));
	}

	public String asString() {
		return tree.lexicalElement().toString();
	}

	public final static Expr.Type<LiteralExpr> TYPE = new Expr.Type<LiteralExpr>() {
		protected LiteralExpr instantiateNode(SContext context, STree<LiteralExpr> content) {
			return new LiteralExpr(context, content);
		}
	};
}
