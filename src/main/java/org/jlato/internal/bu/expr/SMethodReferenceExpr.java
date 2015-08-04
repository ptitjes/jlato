package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDMethodReferenceExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SMethodReferenceExpr extends SNodeState<SMethodReferenceExpr> implements SExpr {

	public static STree<SMethodReferenceExpr> make(STree<? extends SExpr> scope, STree<SNodeListState> typeArgs, STree<SName> name) {
		return new STree<SMethodReferenceExpr>(new SMethodReferenceExpr(scope, typeArgs, name));
	}

	public final STree<? extends SExpr> scope;

	public final STree<SNodeListState> typeArgs;

	public final STree<SName> name;

	public SMethodReferenceExpr(STree<? extends SExpr> scope, STree<SNodeListState> typeArgs, STree<SName> name) {
		this.scope = scope;
		this.typeArgs = typeArgs;
		this.name = name;
	}

	@Override
	public Kind kind() {
		return Kind.MethodReferenceExpr;
	}

	public STree<? extends SExpr> scope() {
		return scope;
	}

	public SMethodReferenceExpr withScope(STree<? extends SExpr> scope) {
		return new SMethodReferenceExpr(scope, typeArgs, name);
	}

	public STree<SNodeListState> typeArgs() {
		return typeArgs;
	}

	public SMethodReferenceExpr withTypeArgs(STree<SNodeListState> typeArgs) {
		return new SMethodReferenceExpr(scope, typeArgs, name);
	}

	public STree<SName> name() {
		return name;
	}

	public SMethodReferenceExpr withName(STree<SName> name) {
		return new SMethodReferenceExpr(scope, typeArgs, name);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SMethodReferenceExpr> location) {
		return new TDMethodReferenceExpr(location);
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
		SMethodReferenceExpr state = (SMethodReferenceExpr) o;
		if (scope == null ? state.scope != null : !scope.equals(state.scope))
			return false;
		if (!typeArgs.equals(state.typeArgs))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (scope != null) result = 37 * result + scope.hashCode();
		result = 37 * result + typeArgs.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SMethodReferenceExpr, SExpr, Expr> SCOPE = new STypeSafeTraversal<SMethodReferenceExpr, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SMethodReferenceExpr state) {
			return state.scope;
		}

		@Override
		public SMethodReferenceExpr doRebuildParentState(SMethodReferenceExpr state, STree<SExpr> child) {
			return state.withScope(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE_ARGS;
		}
	};

	public static STypeSafeTraversal<SMethodReferenceExpr, SNodeListState, NodeList<Type>> TYPE_ARGS = new STypeSafeTraversal<SMethodReferenceExpr, SNodeListState, NodeList<Type>>() {

		@Override
		public STree<?> doTraverse(SMethodReferenceExpr state) {
			return state.typeArgs;
		}

		@Override
		public SMethodReferenceExpr doRebuildParentState(SMethodReferenceExpr state, STree<SNodeListState> child) {
			return state.withTypeArgs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return SCOPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SMethodReferenceExpr, SName, Name> NAME = new STypeSafeTraversal<SMethodReferenceExpr, SName, Name>() {

		@Override
		public STree<?> doTraverse(SMethodReferenceExpr state) {
			return state.name;
		}

		@Override
		public SMethodReferenceExpr doRebuildParentState(SMethodReferenceExpr state, STree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE_ARGS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(SCOPE),
			token(LToken.DoubleColon),
			child(TYPE_ARGS, org.jlato.internal.bu.type.SType.typeArgumentsShape),
			child(NAME)
	);
}
