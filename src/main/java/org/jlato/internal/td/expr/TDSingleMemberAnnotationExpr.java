package org.jlato.internal.td.expr;

import org.jlato.internal.bu.expr.SExpr;
import org.jlato.internal.bu.expr.SSingleMemberAnnotationExpr;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.SingleMemberAnnotationExpr;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

public class TDSingleMemberAnnotationExpr extends TDTree<SSingleMemberAnnotationExpr, AnnotationExpr, SingleMemberAnnotationExpr> implements SingleMemberAnnotationExpr {

	public Kind kind() {
		return Kind.SingleMemberAnnotationExpr;
	}

	public TDSingleMemberAnnotationExpr(TDLocation<SSingleMemberAnnotationExpr> location) {
		super(location);
	}

	public TDSingleMemberAnnotationExpr(QualifiedName name, Expr memberValue) {
		super(new TDLocation<SSingleMemberAnnotationExpr>(SSingleMemberAnnotationExpr.make(TDTree.<SQualifiedName>treeOf(name), TDTree.<SExpr>treeOf(memberValue))));
	}

	public QualifiedName name() {
		return location.safeTraversal(SSingleMemberAnnotationExpr.NAME);
	}

	public SingleMemberAnnotationExpr withName(QualifiedName name) {
		return location.safeTraversalReplace(SSingleMemberAnnotationExpr.NAME, name);
	}

	public SingleMemberAnnotationExpr withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(SSingleMemberAnnotationExpr.NAME, mutation);
	}

	public Expr memberValue() {
		return location.safeTraversal(SSingleMemberAnnotationExpr.MEMBER_VALUE);
	}

	public SingleMemberAnnotationExpr withMemberValue(Expr memberValue) {
		return location.safeTraversalReplace(SSingleMemberAnnotationExpr.MEMBER_VALUE, memberValue);
	}

	public SingleMemberAnnotationExpr withMemberValue(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SSingleMemberAnnotationExpr.MEMBER_VALUE, mutation);
	}
}
