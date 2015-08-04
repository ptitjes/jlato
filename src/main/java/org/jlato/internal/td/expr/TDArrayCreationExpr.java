package org.jlato.internal.td.expr;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.expr.SArrayCreationExpr;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.expr.ArrayCreationExpr;
import org.jlato.tree.expr.ArrayDimExpr;
import org.jlato.tree.expr.ArrayInitializerExpr;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

public class TDArrayCreationExpr extends TDTree<SArrayCreationExpr, Expr, ArrayCreationExpr> implements ArrayCreationExpr {

	public Kind kind() {
		return Kind.ArrayCreationExpr;
	}

	public TDArrayCreationExpr(TDLocation<SArrayCreationExpr> location) {
		super(location);
	}

	public TDArrayCreationExpr(Type type, NodeList<ArrayDimExpr> dimExprs, NodeList<ArrayDim> dims, NodeOption<ArrayInitializerExpr> init) {
		super(new TDLocation<SArrayCreationExpr>(SArrayCreationExpr.make(TDTree.<SType>treeOf(type), TDTree.<SNodeList>treeOf(dimExprs), TDTree.<SNodeList>treeOf(dims), TDTree.<SNodeOption>treeOf(init))));
	}

	public Type type() {
		return location.safeTraversal(SArrayCreationExpr.TYPE);
	}

	public ArrayCreationExpr withType(Type type) {
		return location.safeTraversalReplace(SArrayCreationExpr.TYPE, type);
	}

	public ArrayCreationExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SArrayCreationExpr.TYPE, mutation);
	}

	public NodeList<ArrayDimExpr> dimExprs() {
		return location.safeTraversal(SArrayCreationExpr.DIM_EXPRS);
	}

	public ArrayCreationExpr withDimExprs(NodeList<ArrayDimExpr> dimExprs) {
		return location.safeTraversalReplace(SArrayCreationExpr.DIM_EXPRS, dimExprs);
	}

	public ArrayCreationExpr withDimExprs(Mutation<NodeList<ArrayDimExpr>> mutation) {
		return location.safeTraversalMutate(SArrayCreationExpr.DIM_EXPRS, mutation);
	}

	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(SArrayCreationExpr.DIMS);
	}

	public ArrayCreationExpr withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(SArrayCreationExpr.DIMS, dims);
	}

	public ArrayCreationExpr withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(SArrayCreationExpr.DIMS, mutation);
	}

	public NodeOption<ArrayInitializerExpr> init() {
		return location.safeTraversal(SArrayCreationExpr.INIT);
	}

	public ArrayCreationExpr withInit(NodeOption<ArrayInitializerExpr> init) {
		return location.safeTraversalReplace(SArrayCreationExpr.INIT, init);
	}

	public ArrayCreationExpr withInit(Mutation<NodeOption<ArrayInitializerExpr>> mutation) {
		return location.safeTraversalMutate(SArrayCreationExpr.INIT, mutation);
	}
}
