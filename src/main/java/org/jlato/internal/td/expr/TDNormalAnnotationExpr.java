package org.jlato.internal.td.expr;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.expr.SNormalAnnotationExpr;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.MemberValuePair;
import org.jlato.tree.expr.NormalAnnotationExpr;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

public class TDNormalAnnotationExpr extends TreeBase<SNormalAnnotationExpr, AnnotationExpr, NormalAnnotationExpr> implements NormalAnnotationExpr {

	public Kind kind() {
		return Kind.NormalAnnotationExpr;
	}

	public TDNormalAnnotationExpr(SLocation<SNormalAnnotationExpr> location) {
		super(location);
	}

	public TDNormalAnnotationExpr(QualifiedName name, NodeList<MemberValuePair> pairs) {
		super(new SLocation<SNormalAnnotationExpr>(SNormalAnnotationExpr.make(TreeBase.<SQualifiedName>treeOf(name), TreeBase.<SNodeListState>treeOf(pairs))));
	}

	public QualifiedName name() {
		return location.safeTraversal(SNormalAnnotationExpr.NAME);
	}

	public NormalAnnotationExpr withName(QualifiedName name) {
		return location.safeTraversalReplace(SNormalAnnotationExpr.NAME, name);
	}

	public NormalAnnotationExpr withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(SNormalAnnotationExpr.NAME, mutation);
	}

	public NodeList<MemberValuePair> pairs() {
		return location.safeTraversal(SNormalAnnotationExpr.PAIRS);
	}

	public NormalAnnotationExpr withPairs(NodeList<MemberValuePair> pairs) {
		return location.safeTraversalReplace(SNormalAnnotationExpr.PAIRS, pairs);
	}

	public NormalAnnotationExpr withPairs(Mutation<NodeList<MemberValuePair>> mutation) {
		return location.safeTraversalMutate(SNormalAnnotationExpr.PAIRS, mutation);
	}
}
