package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDExplicitConstructorInvocationStmt;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.Type;

import java.util.Collections;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;

public class SExplicitConstructorInvocationStmt extends SNodeState<SExplicitConstructorInvocationStmt> implements SStmt {

	public static STree<SExplicitConstructorInvocationStmt> make(STree<SNodeListState> typeArgs, boolean isThis, STree<SNodeOptionState> expr, STree<SNodeListState> args) {
		return new STree<SExplicitConstructorInvocationStmt>(new SExplicitConstructorInvocationStmt(typeArgs, isThis, expr, args));
	}

	public final STree<SNodeListState> typeArgs;

	public final boolean isThis;

	public final STree<SNodeOptionState> expr;

	public final STree<SNodeListState> args;

	public SExplicitConstructorInvocationStmt(STree<SNodeListState> typeArgs, boolean isThis, STree<SNodeOptionState> expr, STree<SNodeListState> args) {
		this.typeArgs = typeArgs;
		this.isThis = isThis;
		this.expr = expr;
		this.args = args;
	}

	@Override
	public Kind kind() {
		return Kind.ExplicitConstructorInvocationStmt;
	}

	public STree<SNodeListState> typeArgs() {
		return typeArgs;
	}

	public SExplicitConstructorInvocationStmt withTypeArgs(STree<SNodeListState> typeArgs) {
		return new SExplicitConstructorInvocationStmt(typeArgs, isThis, expr, args);
	}

	public boolean isThis() {
		return isThis;
	}

	public SExplicitConstructorInvocationStmt setThis(boolean isThis) {
		return new SExplicitConstructorInvocationStmt(typeArgs, isThis, expr, args);
	}

	public STree<SNodeOptionState> expr() {
		return expr;
	}

	public SExplicitConstructorInvocationStmt withExpr(STree<SNodeOptionState> expr) {
		return new SExplicitConstructorInvocationStmt(typeArgs, isThis, expr, args);
	}

	public STree<SNodeListState> args() {
		return args;
	}

	public SExplicitConstructorInvocationStmt withArgs(STree<SNodeListState> args) {
		return new SExplicitConstructorInvocationStmt(typeArgs, isThis, expr, args);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SExplicitConstructorInvocationStmt> location) {
		return new TDExplicitConstructorInvocationStmt(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(THIS);
	}

	@Override
	public STraversal firstChild() {
		return TYPE_ARGS;
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
		SExplicitConstructorInvocationStmt state = (SExplicitConstructorInvocationStmt) o;
		if (!typeArgs.equals(state.typeArgs))
			return false;
		if (isThis != state.isThis)
			return false;
		if (!expr.equals(state.expr))
			return false;
		if (!args.equals(state.args))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + typeArgs.hashCode();
		result = 37 * result + (isThis ? 1 : 0);
		result = 37 * result + expr.hashCode();
		result = 37 * result + args.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SExplicitConstructorInvocationStmt, SNodeListState, NodeList<Type>> TYPE_ARGS = new STypeSafeTraversal<SExplicitConstructorInvocationStmt, SNodeListState, NodeList<Type>>() {

		@Override
		public STree<?> doTraverse(SExplicitConstructorInvocationStmt state) {
			return state.typeArgs;
		}

		@Override
		public SExplicitConstructorInvocationStmt doRebuildParentState(SExplicitConstructorInvocationStmt state, STree<SNodeListState> child) {
			return state.withTypeArgs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return EXPR;
		}
	};

	public static STypeSafeTraversal<SExplicitConstructorInvocationStmt, SNodeOptionState, NodeOption<Expr>> EXPR = new STypeSafeTraversal<SExplicitConstructorInvocationStmt, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(SExplicitConstructorInvocationStmt state) {
			return state.expr;
		}

		@Override
		public SExplicitConstructorInvocationStmt doRebuildParentState(SExplicitConstructorInvocationStmt state, STree<SNodeOptionState> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE_ARGS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return ARGS;
		}
	};

	public static STypeSafeTraversal<SExplicitConstructorInvocationStmt, SNodeListState, NodeList<Expr>> ARGS = new STypeSafeTraversal<SExplicitConstructorInvocationStmt, SNodeListState, NodeList<Expr>>() {

		@Override
		public STree<?> doTraverse(SExplicitConstructorInvocationStmt state) {
			return state.args;
		}

		@Override
		public SExplicitConstructorInvocationStmt doRebuildParentState(SExplicitConstructorInvocationStmt state, STree<SNodeListState> child) {
			return state.withArgs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return EXPR;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static STypeSafeProperty<SExplicitConstructorInvocationStmt, Boolean> THIS = new STypeSafeProperty<SExplicitConstructorInvocationStmt, Boolean>() {

		@Override
		public Boolean doRetrieve(SExplicitConstructorInvocationStmt state) {
			return state.isThis;
		}

		@Override
		public SExplicitConstructorInvocationStmt doRebuildParentState(SExplicitConstructorInvocationStmt state, Boolean value) {
			return state.setThis(value);
		}
	};

	public static final LexicalShape shape = composite(
			child(EXPR, when(some(), composite(element(), token(LToken.Dot)))),
			child(TYPE_ARGS, org.jlato.internal.bu.type.SType.typeArgumentsShape),
			token(new LSToken.Provider() {
				public LToken tokenFor(STree tree) {
					return ((SExplicitConstructorInvocationStmt) tree.state).isThis ? LToken.This : LToken.Super;
				}
			}),
			child(ARGS, org.jlato.internal.bu.expr.SExpr.argumentsShape),
			token(LToken.SemiColon)
	);
}
