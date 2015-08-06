package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDMethodInvocationExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class SMethodInvocationExpr extends SNode<SMethodInvocationExpr> implements SExpr {

	public static BUTree<SMethodInvocationExpr> make(BUTree<SNodeOption> scope, BUTree<SNodeList> typeArgs, BUTree<SName> name, BUTree<SNodeList> args) {
		return new BUTree<SMethodInvocationExpr>(new SMethodInvocationExpr(scope, typeArgs, name, args));
	}

	public final BUTree<SNodeOption> scope;

	public final BUTree<SNodeList> typeArgs;

	public final BUTree<SName> name;

	public final BUTree<SNodeList> args;

	public SMethodInvocationExpr(BUTree<SNodeOption> scope, BUTree<SNodeList> typeArgs, BUTree<SName> name, BUTree<SNodeList> args) {
		this.scope = scope;
		this.typeArgs = typeArgs;
		this.name = name;
		this.args = args;
	}

	@Override
	public Kind kind() {
		return Kind.MethodInvocationExpr;
	}

	public SMethodInvocationExpr withScope(BUTree<SNodeOption> scope) {
		return new SMethodInvocationExpr(scope, typeArgs, name, args);
	}

	public SMethodInvocationExpr withTypeArgs(BUTree<SNodeList> typeArgs) {
		return new SMethodInvocationExpr(scope, typeArgs, name, args);
	}

	public SMethodInvocationExpr withName(BUTree<SName> name) {
		return new SMethodInvocationExpr(scope, typeArgs, name, args);
	}

	public SMethodInvocationExpr withArgs(BUTree<SNodeList> args) {
		return new SMethodInvocationExpr(scope, typeArgs, name, args);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SMethodInvocationExpr> location) {
		return new TDMethodInvocationExpr(location);
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
		return ARGS;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SMethodInvocationExpr state = (SMethodInvocationExpr) o;
		if (!scope.equals(state.scope))
			return false;
		if (!typeArgs.equals(state.typeArgs))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!args.equals(state.args))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + scope.hashCode();
		result = 37 * result + typeArgs.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + args.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SMethodInvocationExpr, SNodeOption, NodeOption<Expr>> SCOPE = new STypeSafeTraversal<SMethodInvocationExpr, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SMethodInvocationExpr state) {
			return state.scope;
		}

		@Override
		public SMethodInvocationExpr doRebuildParentState(SMethodInvocationExpr state, BUTree<SNodeOption> child) {
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

	public static STypeSafeTraversal<SMethodInvocationExpr, SNodeList, NodeList<Type>> TYPE_ARGS = new STypeSafeTraversal<SMethodInvocationExpr, SNodeList, NodeList<Type>>() {

		@Override
		public BUTree<?> doTraverse(SMethodInvocationExpr state) {
			return state.typeArgs;
		}

		@Override
		public SMethodInvocationExpr doRebuildParentState(SMethodInvocationExpr state, BUTree<SNodeList> child) {
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

	public static STypeSafeTraversal<SMethodInvocationExpr, SName, Name> NAME = new STypeSafeTraversal<SMethodInvocationExpr, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SMethodInvocationExpr state) {
			return state.name;
		}

		@Override
		public SMethodInvocationExpr doRebuildParentState(SMethodInvocationExpr state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE_ARGS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return ARGS;
		}
	};

	public static STypeSafeTraversal<SMethodInvocationExpr, SNodeList, NodeList<Expr>> ARGS = new STypeSafeTraversal<SMethodInvocationExpr, SNodeList, NodeList<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SMethodInvocationExpr state) {
			return state.args;
		}

		@Override
		public SMethodInvocationExpr doRebuildParentState(SMethodInvocationExpr state, BUTree<SNodeList> child) {
			return state.withArgs(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(SCOPE, when(some(), composite(element(), token(LToken.Dot)))),
			child(TYPE_ARGS, org.jlato.internal.bu.type.SType.typeArgumentsShape),
			child(NAME),
			child(ARGS, SExpr.argumentsShape)
	);
}
