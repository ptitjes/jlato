package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDTypeExpr;
import org.jlato.tree.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LexicalShape.child;

public class STypeExpr extends SNode<STypeExpr> implements SExpr {

	public static BUTree<STypeExpr> make(BUTree<? extends SType> type) {
		return new BUTree<STypeExpr>(new STypeExpr(type));
	}

	public final BUTree<? extends SType> type;

	public STypeExpr(BUTree<? extends SType> type) {
		this.type = type;
	}

	@Override
	public Kind kind() {
		return Kind.TypeExpr;
	}

	public STypeExpr withType(BUTree<? extends SType> type) {
		return new STypeExpr(type);
	}

	@Override
	protected Tree doInstantiate(TDLocation<STypeExpr> location) {
		return new TDTypeExpr(location);
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
		return TYPE;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		STypeExpr state = (STypeExpr) o;
		if (type == null ? state.type != null : !type.equals(state.type))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (type != null) result = 37 * result + type.hashCode();
		return result;
	}

	public static STypeSafeTraversal<STypeExpr, SType, Type> TYPE = new STypeSafeTraversal<STypeExpr, SType, Type>() {

		@Override
		public BUTree<?> doTraverse(STypeExpr state) {
			return state.type;
		}

		@Override
		public STypeExpr doRebuildParentState(STypeExpr state, BUTree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = child(TYPE);
}
