package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QName;

public class SingleMemberAnnotationExpr extends AnnotationExpr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public SingleMemberAnnotationExpr instantiate(SLocation location) {
			return new SingleMemberAnnotationExpr(location);
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
}
