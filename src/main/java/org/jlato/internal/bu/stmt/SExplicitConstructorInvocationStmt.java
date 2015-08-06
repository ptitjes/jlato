package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDExplicitConstructorInvocationStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.Type;

import java.util.Collections;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an explicit constructor invocation statement.
 */
public class SExplicitConstructorInvocationStmt extends SNode<SExplicitConstructorInvocationStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new explicit constructor invocation statement.
	 *
	 * @param typeArgs the type args child <code>BUTree</code>.
	 * @param isThis   the is 'this' child <code>BUTree</code>.
	 * @param expr     the expression child <code>BUTree</code>.
	 * @param args     the args child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an explicit constructor invocation statement.
	 */
	public static BUTree<SExplicitConstructorInvocationStmt> make(BUTree<SNodeList> typeArgs, boolean isThis, BUTree<SNodeOption> expr, BUTree<SNodeList> args) {
		return new BUTree<SExplicitConstructorInvocationStmt>(new SExplicitConstructorInvocationStmt(typeArgs, isThis, expr, args));
	}

	/**
	 * The type args of this explicit constructor invocation statement state.
	 */
	public final BUTree<SNodeList> typeArgs;

	/**
	 * The is 'this' of this explicit constructor invocation statement state.
	 */
	public final boolean isThis;

	/**
	 * The expression of this explicit constructor invocation statement state.
	 */
	public final BUTree<SNodeOption> expr;

	/**
	 * The args of this explicit constructor invocation statement state.
	 */
	public final BUTree<SNodeList> args;

	/**
	 * Constructs an explicit constructor invocation statement state.
	 *
	 * @param typeArgs the type args child <code>BUTree</code>.
	 * @param isThis   the is 'this' child <code>BUTree</code>.
	 * @param expr     the expression child <code>BUTree</code>.
	 * @param args     the args child <code>BUTree</code>.
	 */
	public SExplicitConstructorInvocationStmt(BUTree<SNodeList> typeArgs, boolean isThis, BUTree<SNodeOption> expr, BUTree<SNodeList> args) {
		this.typeArgs = typeArgs;
		this.isThis = isThis;
		this.expr = expr;
		this.args = args;
	}

	/**
	 * Returns the kind of this explicit constructor invocation statement.
	 *
	 * @return the kind of this explicit constructor invocation statement.
	 */
	@Override
	public Kind kind() {
		return Kind.ExplicitConstructorInvocationStmt;
	}

	/**
	 * Replaces the type args of this explicit constructor invocation statement state.
	 *
	 * @param typeArgs the replacement for the type args of this explicit constructor invocation statement state.
	 * @return the resulting mutated explicit constructor invocation statement state.
	 */
	public SExplicitConstructorInvocationStmt withTypeArgs(BUTree<SNodeList> typeArgs) {
		return new SExplicitConstructorInvocationStmt(typeArgs, isThis, expr, args);
	}

	/**
	 * Replaces the is 'this' of this explicit constructor invocation statement state.
	 *
	 * @param isThis the replacement for the is 'this' of this explicit constructor invocation statement state.
	 * @return the resulting mutated explicit constructor invocation statement state.
	 */
	public SExplicitConstructorInvocationStmt setThis(boolean isThis) {
		return new SExplicitConstructorInvocationStmt(typeArgs, isThis, expr, args);
	}

	/**
	 * Replaces the expression of this explicit constructor invocation statement state.
	 *
	 * @param expr the replacement for the expression of this explicit constructor invocation statement state.
	 * @return the resulting mutated explicit constructor invocation statement state.
	 */
	public SExplicitConstructorInvocationStmt withExpr(BUTree<SNodeOption> expr) {
		return new SExplicitConstructorInvocationStmt(typeArgs, isThis, expr, args);
	}

	/**
	 * Replaces the args of this explicit constructor invocation statement state.
	 *
	 * @param args the replacement for the args of this explicit constructor invocation statement state.
	 * @return the resulting mutated explicit constructor invocation statement state.
	 */
	public SExplicitConstructorInvocationStmt withArgs(BUTree<SNodeList> args) {
		return new SExplicitConstructorInvocationStmt(typeArgs, isThis, expr, args);
	}

	/**
	 * Builds an explicit constructor invocation statement facade for the specified explicit constructor invocation statement <code>TDLocation</code>.
	 *
	 * @param location the explicit constructor invocation statement <code>TDLocation</code>.
	 * @return an explicit constructor invocation statement facade for the specified explicit constructor invocation statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SExplicitConstructorInvocationStmt> location) {
		return new TDExplicitConstructorInvocationStmt(location);
	}

	/**
	 * Returns the shape for this explicit constructor invocation statement state.
	 *
	 * @return the shape for this explicit constructor invocation statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the properties for this explicit constructor invocation statement state.
	 *
	 * @return the properties for this explicit constructor invocation statement state.
	 */
	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(THIS);
	}

	/**
	 * Returns the first child traversal for this explicit constructor invocation statement state.
	 *
	 * @return the first child traversal for this explicit constructor invocation statement state.
	 */
	@Override
	public STraversal firstChild() {
		return TYPE_ARGS;
	}

	/**
	 * Returns the last child traversal for this explicit constructor invocation statement state.
	 *
	 * @return the last child traversal for this explicit constructor invocation statement state.
	 */
	@Override
	public STraversal lastChild() {
		return ARGS;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
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

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
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
