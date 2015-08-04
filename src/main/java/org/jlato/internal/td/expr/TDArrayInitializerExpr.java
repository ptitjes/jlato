package org.jlato.internal.td.expr;

import org.jlato.internal.bu.SNodeList;
import org.jlato.internal.bu.expr.SArrayInitializerExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.ArrayInitializerExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public class TDArrayInitializerExpr extends TDTree<SArrayInitializerExpr, Expr, ArrayInitializerExpr> implements ArrayInitializerExpr {

	public Kind kind() {
		return Kind.ArrayInitializerExpr;
	}

	public TDArrayInitializerExpr(TDLocation<SArrayInitializerExpr> location) {
		super(location);
	}

	public TDArrayInitializerExpr(NodeList<Expr> values, boolean trailingComma) {
		super(new TDLocation<SArrayInitializerExpr>(SArrayInitializerExpr.make(TDTree.<SNodeList>treeOf(values), trailingComma)));
	}

	public NodeList<Expr> values() {
		return location.safeTraversal(SArrayInitializerExpr.VALUES);
	}

	public ArrayInitializerExpr withValues(NodeList<Expr> values) {
		return location.safeTraversalReplace(SArrayInitializerExpr.VALUES, values);
	}

	public ArrayInitializerExpr withValues(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(SArrayInitializerExpr.VALUES, mutation);
	}

	public boolean trailingComma() {
		return location.safeProperty(SArrayInitializerExpr.TRAILING_COMMA);
	}

	public ArrayInitializerExpr withTrailingComma(boolean trailingComma) {
		return location.safePropertyReplace(SArrayInitializerExpr.TRAILING_COMMA, trailingComma);
	}

	public ArrayInitializerExpr withTrailingComma(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SArrayInitializerExpr.TRAILING_COMMA, mutation);
	}
}
