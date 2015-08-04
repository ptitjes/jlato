package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDMethodReferenceExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SMethodReferenceExpr extends SNode<SMethodReferenceExpr> implements SExpr {

	public static BUTree<SMethodReferenceExpr> make(BUTree<? extends SExpr> scope, BUTree<SNodeList> typeArgs, BUTree<SName> name) {
		return new BUTree<SMethodReferenceExpr>(new SMethodReferenceExpr(scope, typeArgs, name));
	}

	public final BUTree<? extends SExpr> scope;

	public final BUTree<SNodeList> typeArgs;

	public final BUTree<SName> name;

	public SMethodReferenceExpr(BUTree<? extends SExpr> scope, BUTree<SNodeList> typeArgs, BUTree<SName> name) {
		this.scope = scope;
		this.typeArgs = typeArgs;
		this.name = name;
	}

	@Override
	public Kind kind() {
		return Kind.MethodReferenceExpr;
	}

	public BUTree<? extends SExpr> scope() {
		return scope;
	}

	public SMethodReferenceExpr withScope(BUTree<? extends SExpr> scope) {
		return new SMethodReferenceExpr(scope, typeArgs, name);
	}

	public BUTree<SNodeList> typeArgs() {
		return typeArgs;
	}

	public SMethodReferenceExpr withTypeArgs(BUTree<SNodeList> typeArgs) {
		return new SMethodReferenceExpr(scope, typeArgs, name);
	}

	public BUTree<SName> name() {
		return name;
	}

	public SMethodReferenceExpr withName(BUTree<SName> name) {
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
		public BUTree<?> doTraverse(SMethodReferenceExpr state) {
			return state.scope;
		}

		@Override
		public SMethodReferenceExpr doRebuildParentState(SMethodReferenceExpr state, BUTree<SExpr> child) {
			return state.withScope(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return TYPE_ARGS;
		}
	};

	public static STypeSafeTraversal<SMethodReferenceExpr, SNodeList, NodeList<Type>> TYPE_ARGS = new STypeSafeTraversal<SMethodReferenceExpr, SNodeList, NodeList<Type>>() {

		@Override
		public BUTree<?> doTraverse(SMethodReferenceExpr state) {
			return state.typeArgs;
		}

		@Override
		public SMethodReferenceExpr doRebuildParentState(SMethodReferenceExpr state, BUTree<SNodeList> child) {
			return state.withTypeArgs(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return SCOPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SMethodReferenceExpr, SName, Name> NAME = new STypeSafeTraversal<SMethodReferenceExpr, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SMethodReferenceExpr state) {
			return state.name;
		}

		@Override
		public SMethodReferenceExpr doRebuildParentState(SMethodReferenceExpr state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE_ARGS;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
