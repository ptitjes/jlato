package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDArrayCreationExpr;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

public class SArrayCreationExpr extends SNode<SArrayCreationExpr> implements SExpr {

	public static BUTree<SArrayCreationExpr> make(BUTree<? extends SType> type, BUTree<SNodeList> dimExprs, BUTree<SNodeList> dims, BUTree<SNodeOption> init) {
		return new BUTree<SArrayCreationExpr>(new SArrayCreationExpr(type, dimExprs, dims, init));
	}

	public final BUTree<? extends SType> type;

	public final BUTree<SNodeList> dimExprs;

	public final BUTree<SNodeList> dims;

	public final BUTree<SNodeOption> init;

	public SArrayCreationExpr(BUTree<? extends SType> type, BUTree<SNodeList> dimExprs, BUTree<SNodeList> dims, BUTree<SNodeOption> init) {
		this.type = type;
		this.dimExprs = dimExprs;
		this.dims = dims;
		this.init = init;
	}

	@Override
	public Kind kind() {
		return Kind.ArrayCreationExpr;
	}

	public SArrayCreationExpr withType(BUTree<? extends SType> type) {
		return new SArrayCreationExpr(type, dimExprs, dims, init);
	}

	public SArrayCreationExpr withDimExprs(BUTree<SNodeList> dimExprs) {
		return new SArrayCreationExpr(type, dimExprs, dims, init);
	}

	public SArrayCreationExpr withDims(BUTree<SNodeList> dims) {
		return new SArrayCreationExpr(type, dimExprs, dims, init);
	}

	public SArrayCreationExpr withInit(BUTree<SNodeOption> init) {
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
		public BUTree<?> doTraverse(SArrayCreationExpr state) {
			return state.type;
		}

		@Override
		public SArrayCreationExpr doRebuildParentState(SArrayCreationExpr state, BUTree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return DIM_EXPRS;
		}
	};

	public static STypeSafeTraversal<SArrayCreationExpr, SNodeList, NodeList<ArrayDimExpr>> DIM_EXPRS = new STypeSafeTraversal<SArrayCreationExpr, SNodeList, NodeList<ArrayDimExpr>>() {

		@Override
		public BUTree<?> doTraverse(SArrayCreationExpr state) {
			return state.dimExprs;
		}

		@Override
		public SArrayCreationExpr doRebuildParentState(SArrayCreationExpr state, BUTree<SNodeList> child) {
			return state.withDimExprs(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return DIMS;
		}
	};

	public static STypeSafeTraversal<SArrayCreationExpr, SNodeList, NodeList<ArrayDim>> DIMS = new STypeSafeTraversal<SArrayCreationExpr, SNodeList, NodeList<ArrayDim>>() {

		@Override
		public BUTree<?> doTraverse(SArrayCreationExpr state) {
			return state.dims;
		}

		@Override
		public SArrayCreationExpr doRebuildParentState(SArrayCreationExpr state, BUTree<SNodeList> child) {
			return state.withDims(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return DIM_EXPRS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return INIT;
		}
	};

	public static STypeSafeTraversal<SArrayCreationExpr, SNodeOption, NodeOption<ArrayInitializerExpr>> INIT = new STypeSafeTraversal<SArrayCreationExpr, SNodeOption, NodeOption<ArrayInitializerExpr>>() {

		@Override
		public BUTree<?> doTraverse(SArrayCreationExpr state) {
			return state.init;
		}

		@Override
		public SArrayCreationExpr doRebuildParentState(SArrayCreationExpr state, BUTree<SNodeOption> child) {
			return state.withInit(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return DIMS;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
