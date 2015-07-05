package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QName;

public class NormalAnnotationExpr extends AnnotationExpr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public NormalAnnotationExpr instantiate(SLocation location) {
			return new NormalAnnotationExpr(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private NormalAnnotationExpr(SLocation location) {
		super(location);
	}

	public NormalAnnotationExpr(QName name, NodeList<MemberValuePair> pairs) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(name, pairs)))));
	}

	public NodeList<MemberValuePair> pairs() {
		return location.nodeChild(PAIRS);
	}

	public NormalAnnotationExpr withPairs(NodeList<MemberValuePair> pairs) {
		return location.nodeWithChild(PAIRS, pairs);
	}

	private static final int PAIRS = 1;
}
