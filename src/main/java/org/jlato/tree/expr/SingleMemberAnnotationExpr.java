package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.Tree;

public class SingleMemberAnnotationExpr extends AnnotationExpr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public SingleMemberAnnotationExpr instantiate(SLocation location) {
			return new SingleMemberAnnotationExpr(location);
		}
	};

	private SingleMemberAnnotationExpr(SLocation location) {
		super(location);
	}

	public SingleMemberAnnotationExpr(Expr memberValue) {
		super(new SLocation(new SNode(kind, runOf(memberValue))));
	}

	public Expr memberValue() {
		return location.nodeChild(MEMBER_VALUE);
	}

	public SingleMemberAnnotationExpr withMemberValue(Expr memberValue) {
		return location.nodeWithChild(MEMBER_VALUE, memberValue);
	}

	private static final int MEMBER_VALUE = 1;
}
