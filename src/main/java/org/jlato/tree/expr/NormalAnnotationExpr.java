package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;

public class NormalAnnotationExpr extends AnnotationExpr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public NormalAnnotationExpr instantiate(SLocation location) {
			return new NormalAnnotationExpr(location);
		}
	};

	private NormalAnnotationExpr(SLocation location) {
		super(location);
	}

	public NormalAnnotationExpr(NodeList<MemberValuePair> pairs) {
		super(new SLocation(new SNode(kind, runOf(pairs))));
	}

	public NodeList<MemberValuePair> pairs() {
		return location.nodeChild(PAIRS);
	}

	public NormalAnnotationExpr withPairs(NodeList<MemberValuePair> pairs) {
		return location.nodeWithChild(PAIRS, pairs);
	}

	private static final int PAIRS = 1;
}
