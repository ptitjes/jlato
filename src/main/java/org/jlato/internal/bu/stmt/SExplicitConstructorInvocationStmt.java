package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDExplicitConstructorInvocationStmt;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.type.*;

import java.util.Collections;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class SExplicitConstructorInvocationStmt extends SNode<SExplicitConstructorInvocationStmt> implements SStmt {

	public static BUTree<SExplicitConstructorInvocationStmt> make(BUTree<SNodeList> typeArgs, boolean isThis, BUTree<SNodeOption> expr, BUTree<SNodeList> args) {
		return new BUTree<SExplicitConstructorInvocationStmt>(new SExplicitConstructorInvocationStmt(typeArgs, isThis, expr, args));
	}

	public final BUTree<SNodeList> typeArgs;

	public final boolean isThis;

	public final BUTree<SNodeOption> expr;

	public final BUTree<SNodeList> args;

	public SExplicitConstructorInvocationStmt(BUTree<SNodeList> typeArgs, boolean isThis, BUTree<SNodeOption> expr, BUTree<SNodeList> args) {
		this.typeArgs = typeArgs;
		this.isThis = isThis;
		this.expr = expr;
		this.args = args;
	}

	@Override
	public Kind kind() {
		return Kind.ExplicitConstructorInvocationStmt;
	}

	public BUTree<SNodeList> typeArgs() {
		return typeArgs;
	}

	public SExplicitConstructorInvocationStmt withTypeArgs(BUTree<SNodeList> typeArgs) {
		return new SExplicitConstructorInvocationStmt(typeArgs, isThis, expr, args);
	}

	public boolean isThis() {
		return isThis;
	}

	public SExplicitConstructorInvocationStmt setThis(boolean isThis) {
		return new SExplicitConstructorInvocationStmt(typeArgs, isThis, expr, args);
	}

	public BUTree<SNodeOption> expr() {
		return expr;
	}

	public SExplicitConstructorInvocationStmt withExpr(BUTree<SNodeOption> expr) {
		return new SExplicitConstructorInvocationStmt(typeArgs, isThis, expr, args);
	}

	public BUTree<SNodeList> args() {
		return args;
	}

	public SExplicitConstructorInvocationStmt withArgs(BUTree<SNodeList> args) {
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

	public static STypeSafeTraversal<SExplicitConstructorInvocationStmt, SNodeList, NodeList<Type>> TYPE_ARGS = new STypeSafeTraversal<SExplicitConstructorInvocationStmt, SNodeList, NodeList<Type>>() {

		@Override
		public BUTree<?> doTraverse(SExplicitConstructorInvocationStmt state) {
			return state.typeArgs;
		}

		@Override
		public SExplicitConstructorInvocationStmt doRebuildParentState(SExplicitConstructorInvocationStmt state, BUTree<SNodeList> child) {
			return state.withTypeArgs(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return EXPR;
		}
	};

	public static STypeSafeTraversal<SExplicitConstructorInvocationStmt, SNodeOption, NodeOption<Expr>> EXPR = new STypeSafeTraversal<SExplicitConstructorInvocationStmt, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SExplicitConstructorInvocationStmt state) {
			return state.expr;
		}

		@Override
		public SExplicitConstructorInvocationStmt doRebuildParentState(SExplicitConstructorInvocationStmt state, BUTree<SNodeOption> child) {
			return state.withExpr(child);
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

	public static STypeSafeTraversal<SExplicitConstructorInvocationStmt, SNodeList, NodeList<Expr>> ARGS = new STypeSafeTraversal<SExplicitConstructorInvocationStmt, SNodeList, NodeList<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SExplicitConstructorInvocationStmt state) {
			return state.args;
		}

		@Override
		public SExplicitConstructorInvocationStmt doRebuildParentState(SExplicitConstructorInvocationStmt state, BUTree<SNodeList> child) {
			return state.withArgs(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return EXPR;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
				public LToken tokenFor(BUTree tree) {
					return ((SExplicitConstructorInvocationStmt) tree.state).isThis ? LToken.This : LToken.Super;
				}
			}),
			child(ARGS, org.jlato.internal.bu.expr.SExpr.argumentsShape),
			token(LToken.SemiColon)
	);
}
