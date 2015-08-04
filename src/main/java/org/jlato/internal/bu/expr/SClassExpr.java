package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDClassExpr;
import org.jlato.tree.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SClassExpr extends SNodeState<SClassExpr> implements SExpr {

	public static STree<SClassExpr> make(STree<? extends SType> type) {
		return new STree<SClassExpr>(new SClassExpr(type));
	}

	public final STree<? extends SType> type;

	public SClassExpr(STree<? extends SType> type) {
		this.type = type;
	}

	@Override
	public Kind kind() {
		return Kind.ClassExpr;
	}

	public STree<? extends SType> type() {
		return type;
	}

	public SClassExpr withType(STree<? extends SType> type) {
		return new SClassExpr(type);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SClassExpr> location) {
		return new TDClassExpr(location);
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
		SClassExpr state = (SClassExpr) o;
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

	public static STypeSafeTraversal<SClassExpr, SType, Type> TYPE = new STypeSafeTraversal<SClassExpr, SType, Type>() {

		@Override
		public STree<?> doTraverse(SClassExpr state) {
			return state.type;
		}

		@Override
		public SClassExpr doRebuildParentState(SClassExpr state, STree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(TYPE),
			token(LToken.Dot), token(LToken.Class)
	);
}
