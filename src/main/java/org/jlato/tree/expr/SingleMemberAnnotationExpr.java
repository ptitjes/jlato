package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QName;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class SingleMemberAnnotationExpr extends AnnotationExpr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public SingleMemberAnnotationExpr instantiate(SLocation location) {
			return new SingleMemberAnnotationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private SingleMemberAnnotationExpr(SLocation location) {
		super(location);
	}

	public SingleMemberAnnotationExpr(QName name, Expr memberValue) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(name, memberValue)))));
	}

	public Expr memberValue() {
		return location.nodeChild(MEMBER_VALUE);
	}

	public SingleMemberAnnotationExpr withMemberValue(Expr memberValue) {
		return location.nodeWithChild(MEMBER_VALUE, memberValue);
	}

	private static final int MEMBER_VALUE = 1;

	public final static LexicalShape shape = composite(
			token(LToken.At), child(NAME),
			token(LToken.ParenthesisLeft),
			child(MEMBER_VALUE),
			token(LToken.ParenthesisRight)
	);
}
