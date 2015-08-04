package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDFieldAccessExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class SFieldAccessExpr extends SNode<SFieldAccessExpr> implements SExpr {

	public static BUTree<SFieldAccessExpr> make(BUTree<SNodeOption> scope, BUTree<SName> name) {
		return new BUTree<SFieldAccessExpr>(new SFieldAccessExpr(scope, name));
	}

	public final BUTree<SNodeOption> scope;

	public final BUTree<SName> name;

	public SFieldAccessExpr(BUTree<SNodeOption> scope, BUTree<SName> name) {
		this.scope = scope;
		this.name = name;
	}

	@Override
	public Kind kind() {
		return Kind.FieldAccessExpr;
	}

	public BUTree<SNodeOption> scope() {
		return scope;
	}

	public SFieldAccessExpr withScope(BUTree<SNodeOption> scope) {
		return new SFieldAccessExpr(scope, name);
	}

	public BUTree<SName> name() {
		return name;
	}

	public SFieldAccessExpr withName(BUTree<SName> name) {
		return new SFieldAccessExpr(scope, name);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SFieldAccessExpr> location) {
		return new TDFieldAccessExpr(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return SCOPE;
	}

	@Override
	public STraversal lastChild() {
		return NAME;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SFieldAccessExpr state = (SFieldAccessExpr) o;
		if (!scope.equals(state.scope))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + scope.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SFieldAccessExpr, SNodeOption, NodeOption<Expr>> SCOPE = new STypeSafeTraversal<SFieldAccessExpr, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SFieldAccessExpr state) {
			return state.scope;
		}

		@Override
		public SFieldAccessExpr doRebuildParentState(SFieldAccessExpr state, BUTree<SNodeOption> child) {
			return state.withScope(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SFieldAccessExpr, SName, Name> NAME = new STypeSafeTraversal<SFieldAccessExpr, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SFieldAccessExpr state) {
			return state.name;
		}

		@Override
		public SFieldAccessExpr doRebuildParentState(SFieldAccessExpr state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return SCOPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(SCOPE, when(some(), composite(element(), token(LToken.Dot)))),
			child(NAME)
	);
}
