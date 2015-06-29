package org.jlato.tree.testexpr;

import org.jlato.internal.bu.LLiteral;
import org.jlato.internal.bu.SContext;
import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.STree;

/**
 * @author Didier Villevalois
 */
public class BinaryOp extends Operator<BinaryOp> {

	public final static BinaryOp Plus = new BinaryOp("+");

	private BinaryOp(SContext context, STree<BinaryOp> content) {
		super(context, content);
	}

	private BinaryOp(String valueString) {
		super(null, new SLeaf<BinaryOp>(TYPE, new LLiteral(valueString)));
	}

	public String asString() {
		return tree.lexicalElement().toString();
	}

	public final static Type<BinaryOp> TYPE = new Type<BinaryOp>() {
		protected BinaryOp instantiateNode(SContext context, STree<BinaryOp> content) {
			return new BinaryOp(context, content);
		}
	};
}
