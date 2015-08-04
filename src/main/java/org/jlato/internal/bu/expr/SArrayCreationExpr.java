package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDArrayCreationExpr;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.expr.ArrayDimExpr;
import org.jlato.tree.expr.ArrayInitializerExpr;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SArrayCreationExpr extends SNodeState<SArrayCreationExpr> implements SExpr {

	public static STree<SArrayCreationExpr> make(STree<? extends SType> type, STree<SNodeListState> dimExprs, STree<SNodeListState> dims, STree<SNodeOptionState> init) {
		return new STree<SArrayCreationExpr>(new SArrayCreationExpr(type, dimExprs, dims, init));
	}

	public final STree<? extends SType> type;

	public final STree<SNodeListState> dimExprs;

	public final STree<SNodeListState> dims;

	public final STree<SNodeOptionState> init;

	public SArrayCreationExpr(STree<? extends SType> type, STree<SNodeListState> dimExprs, STree<SNodeListState> dims, STree<SNodeOptionState> init) {
		this.type = type;
		this.dimExprs = dimExprs;
		this.dims = dims;
		this.init = init;
	}

	@Override
	public Kind kind() {
		return Kind.ArrayCreationExpr;
	}

	public STree<? extends SType> type() {
		return type;
	}

	public SArrayCreationExpr withType(STree<? extends SType> type) {
		return new SArrayCreationExpr(type, dimExprs, dims, init);
	}

	public STree<SNodeListState> dimExprs() {
		return dimExprs;
	}

	public SArrayCreationExpr withDimExprs(STree<SNodeListState> dimExprs) {
		return new SArrayCreationExpr(type, dimExprs, dims, init);
	}

	public STree<SNodeListState> dims() {
		return dims;
	}

	public SArrayCreationExpr withDims(STree<SNodeListState> dims) {
		return new SArrayCreationExpr(type, dimExprs, dims, init);
	}

	public STree<SNodeOptionState> init() {
		return init;
	}

	public SArrayCreationExpr withInit(STree<SNodeOptionState> init) {
		return new SArrayCreationExpr(type, dimExprs, dims, init);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SArrayCreationExpr> location) {
		return new TDArrayCreationExpr(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return TYPE;
	}

	@Override
	public STraversal lastChild() {
		return INIT;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SArrayCreationExpr state = (SArrayCreationExpr) o;
		if (type == null ? state.type != null : !type.equals(state.type))
			return false;
		if (!dimExprs.equals(state.dimExprs))
			return false;
		if (!dims.equals(state.dims))
			return false;
		if (!init.equals(state.init))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (type != null) result = 37 * result + type.hashCode();
		result = 37 * result + dimExprs.hashCode();
		result = 37 * result + dims.hashCode();
		result = 37 * result + init.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SArrayCreationExpr, SType, Type> TYPE = new STypeSafeTraversal<SArrayCreationExpr, SType, Type>() {

		@Override
		public STree<?> doTraverse(SArrayCreationExpr state) {
			return state.type;
		}

		@Override
		public SArrayCreationExpr doRebuildParentState(SArrayCreationExpr state, STree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return DIM_EXPRS;
		}
	};

	public static STypeSafeTraversal<SArrayCreationExpr, SNodeListState, NodeList<ArrayDimExpr>> DIM_EXPRS = new STypeSafeTraversal<SArrayCreationExpr, SNodeListState, NodeList<ArrayDimExpr>>() {

		@Override
		public STree<?> doTraverse(SArrayCreationExpr state) {
			return state.dimExprs;
		}

		@Override
		public SArrayCreationExpr doRebuildParentState(SArrayCreationExpr state, STree<SNodeListState> child) {
			return state.withDimExprs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return DIMS;
		}
	};

	public static STypeSafeTraversal<SArrayCreationExpr, SNodeListState, NodeList<ArrayDim>> DIMS = new STypeSafeTraversal<SArrayCreationExpr, SNodeListState, NodeList<ArrayDim>>() {

		@Override
		public STree<?> doTraverse(SArrayCreationExpr state) {
			return state.dims;
		}

		@Override
		public SArrayCreationExpr doRebuildParentState(SArrayCreationExpr state, STree<SNodeListState> child) {
			return state.withDims(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return DIM_EXPRS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return INIT;
		}
	};

	public static STypeSafeTraversal<SArrayCreationExpr, SNodeOptionState, NodeOption<ArrayInitializerExpr>> INIT = new STypeSafeTraversal<SArrayCreationExpr, SNodeOptionState, NodeOption<ArrayInitializerExpr>>() {

		@Override
		public STree<?> doTraverse(SArrayCreationExpr state) {
			return state.init;
		}

		@Override
		public SArrayCreationExpr doRebuildParentState(SArrayCreationExpr state, STree<SNodeOptionState> child) {
			return state.withInit(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return DIMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.New),
			child(TYPE),
			child(DIM_EXPRS, list()),
			child(DIMS, list()),
			child(INIT, when(some(),
					element().withSpacingBefore(space())
			))
	);
}
