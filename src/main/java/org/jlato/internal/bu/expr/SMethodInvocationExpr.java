package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDMethodInvocationExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a method invocation expression.
 */
public class SMethodInvocationExpr extends SNode<SMethodInvocationExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new method invocation expression.
	 *
	 * @param scope    the scope child <code>BUTree</code>.
	 * @param typeArgs the type args child <code>BUTree</code>.
	 * @param name     the name child <code>BUTree</code>.
	 * @param args     the args child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a method invocation expression.
	 */
	public static BUTree<SMethodInvocationExpr> make(BUTree<SNodeOption> scope, BUTree<SNodeList> typeArgs, BUTree<SName> name, BUTree<SNodeList> args) {
		return new BUTree<SMethodInvocationExpr>(new SMethodInvocationExpr(scope, typeArgs, name, args));
	}

	/**
	 * The scope of this method invocation expression state.
	 */
	public final BUTree<SNodeOption> scope;

	/**
	 * The type args of this method invocation expression state.
	 */
	public final BUTree<SNodeList> typeArgs;

	/**
	 * The name of this method invocation expression state.
	 */
	public final BUTree<SName> name;

	/**
	 * The args of this method invocation expression state.
	 */
	public final BUTree<SNodeList> args;

	/**
	 * Constructs a method invocation expression state.
	 *
	 * @param scope    the scope child <code>BUTree</code>.
	 * @param typeArgs the type args child <code>BUTree</code>.
	 * @param name     the name child <code>BUTree</code>.
	 * @param args     the args child <code>BUTree</code>.
	 */
	public SMethodInvocationExpr(BUTree<SNodeOption> scope, BUTree<SNodeList> typeArgs, BUTree<SName> name, BUTree<SNodeList> args) {
		this.scope = scope;
		this.typeArgs = typeArgs;
		this.name = name;
		this.args = args;
	}

	/**
	 * Returns the kind of this method invocation expression.
	 *
	 * @return the kind of this method invocation expression.
	 */
	@Override
	public Kind kind() {
		return Kind.MethodInvocationExpr;
	}

	/**
	 * Replaces the scope of this method invocation expression state.
	 *
	 * @param scope the replacement for the scope of this method invocation expression state.
	 * @return the resulting mutated method invocation expression state.
	 */
	public SMethodInvocationExpr withScope(BUTree<SNodeOption> scope) {
		return new SMethodInvocationExpr(scope, typeArgs, name, args);
	}

	/**
	 * Replaces the type args of this method invocation expression state.
	 *
	 * @param typeArgs the replacement for the type args of this method invocation expression state.
	 * @return the resulting mutated method invocation expression state.
	 */
	public SMethodInvocationExpr withTypeArgs(BUTree<SNodeList> typeArgs) {
		return new SMethodInvocationExpr(scope, typeArgs, name, args);
	}

	/**
	 * Replaces the name of this method invocation expression state.
	 *
	 * @param name the replacement for the name of this method invocation expression state.
	 * @return the resulting mutated method invocation expression state.
	 */
	public SMethodInvocationExpr withName(BUTree<SName> name) {
		return new SMethodInvocationExpr(scope, typeArgs, name, args);
	}

	/**
	 * Replaces the args of this method invocation expression state.
	 *
	 * @param args the replacement for the args of this method invocation expression state.
	 * @return the resulting mutated method invocation expression state.
	 */
	public SMethodInvocationExpr withArgs(BUTree<SNodeList> args) {
		return new SMethodInvocationExpr(scope, typeArgs, name, args);
	}

	/**
	 * Builds a method invocation expression facade for the specified method invocation expression <code>TDLocation</code>.
	 *
	 * @param location the method invocation expression <code>TDLocation</code>.
	 * @return a method invocation expression facade for the specified method invocation expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SMethodInvocationExpr> location) {
		return new TDMethodInvocationExpr(location);
	}

	/**
	 * Returns the shape for this method invocation expression state.
	 *
	 * @return the shape for this method invocation expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this method invocation expression state.
	 *
	 * @return the first child traversal for this method invocation expression state.
	 */
	@Override
	public STraversal firstChild() {
		return SCOPE;
	}

	/**
	 * Returns the last child traversal for this method invocation expression state.
	 *
	 * @return the last child traversal for this method invocation expression state.
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

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
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
