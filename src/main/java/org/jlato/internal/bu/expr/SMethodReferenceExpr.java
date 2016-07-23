package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDMethodReferenceExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
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
 * A state object for a method reference expression.
 */
public class SMethodReferenceExpr extends SNode<SMethodReferenceExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new method reference expression.
	 *
	 * @param scope    the scope child <code>BUTree</code>.
	 * @param typeArgs the type args child <code>BUTree</code>.
	 * @param name     the name child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a method reference expression.
	 */
	public static BUTree<SMethodReferenceExpr> make(BUTree<? extends SExpr> scope, BUTree<SNodeList> typeArgs, BUTree<SName> name) {
		return new BUTree<SMethodReferenceExpr>(new SMethodReferenceExpr(scope, typeArgs, name));
	}

	/**
	 * The scope of this method reference expression state.
	 */
	public final BUTree<? extends SExpr> scope;

	/**
	 * The type args of this method reference expression state.
	 */
	public final BUTree<SNodeList> typeArgs;

	/**
	 * The name of this method reference expression state.
	 */
	public final BUTree<SName> name;

	/**
	 * Constructs a method reference expression state.
	 *
	 * @param scope    the scope child <code>BUTree</code>.
	 * @param typeArgs the type args child <code>BUTree</code>.
	 * @param name     the name child <code>BUTree</code>.
	 */
	public SMethodReferenceExpr(BUTree<? extends SExpr> scope, BUTree<SNodeList> typeArgs, BUTree<SName> name) {
		this.scope = scope;
		this.typeArgs = typeArgs;
		this.name = name;
	}

	/**
	 * Returns the kind of this method reference expression.
	 *
	 * @return the kind of this method reference expression.
	 */
	@Override
	public Kind kind() {
		return Kind.MethodReferenceExpr;
	}

	/**
	 * Replaces the scope of this method reference expression state.
	 *
	 * @param scope the replacement for the scope of this method reference expression state.
	 * @return the resulting mutated method reference expression state.
	 */
	public SMethodReferenceExpr withScope(BUTree<? extends SExpr> scope) {
		return new SMethodReferenceExpr(scope, typeArgs, name);
	}

	/**
	 * Replaces the type args of this method reference expression state.
	 *
	 * @param typeArgs the replacement for the type args of this method reference expression state.
	 * @return the resulting mutated method reference expression state.
	 */
	public SMethodReferenceExpr withTypeArgs(BUTree<SNodeList> typeArgs) {
		return new SMethodReferenceExpr(scope, typeArgs, name);
	}

	/**
	 * Replaces the name of this method reference expression state.
	 *
	 * @param name the replacement for the name of this method reference expression state.
	 * @return the resulting mutated method reference expression state.
	 */
	public SMethodReferenceExpr withName(BUTree<SName> name) {
		return new SMethodReferenceExpr(scope, typeArgs, name);
	}

	/**
	 * Builds a method reference expression facade for the specified method reference expression <code>TDLocation</code>.
	 *
	 * @param location the method reference expression <code>TDLocation</code>.
	 * @return a method reference expression facade for the specified method reference expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SMethodReferenceExpr> location) {
		return new TDMethodReferenceExpr(location);
	}

	/**
	 * Returns the shape for this method reference expression state.
	 *
	 * @return the shape for this method reference expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this method reference expression state.
	 *
	 * @return the first child traversal for this method reference expression state.
	 */
	@Override
	public STraversal firstChild() {
		return SCOPE;
	}

	/**
	 * Returns the last child traversal for this method reference expression state.
	 *
	 * @return the last child traversal for this method reference expression state.
	 */
	@Override
	public STraversal lastChild() {
		return NAME;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SMethodReferenceExpr state = (SMethodReferenceExpr) o;
		if (scope == null ? state.scope != null : !scope.equals(state.scope))
			return false;
		if (!typeArgs.equals(state.typeArgs))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
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
