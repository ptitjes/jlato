package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDArrayDimExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an array dimension expression.
 */
public class SArrayDimExpr extends SNode<SArrayDimExpr> implements STree {

	/**
	 * Creates a <code>BUTree</code> with a new array dimension expression.
	 *
	 * @param annotations the annotations child <code>BUTree</code>.
	 * @param expr        the expression child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an array dimension expression.
	 */
	public static BUTree<SArrayDimExpr> make(BUTree<SNodeList> annotations, BUTree<? extends SExpr> expr) {
		return new BUTree<SArrayDimExpr>(new SArrayDimExpr(annotations, expr));
	}

	/**
	 * The annotations of this array dimension expression state.
	 */
	public final BUTree<SNodeList> annotations;

	/**
	 * The expression of this array dimension expression state.
	 */
	public final BUTree<? extends SExpr> expr;

	/**
	 * Constructs an array dimension expression state.
	 *
	 * @param annotations the annotations child <code>BUTree</code>.
	 * @param expr        the expression child <code>BUTree</code>.
	 */
	public SArrayDimExpr(BUTree<SNodeList> annotations, BUTree<? extends SExpr> expr) {
		this.annotations = annotations;
		this.expr = expr;
	}

	/**
	 * Returns the kind of this array dimension expression.
	 *
	 * @return the kind of this array dimension expression.
	 */
	@Override
	public Kind kind() {
		return Kind.ArrayDimExpr;
	}

	/**
	 * Replaces the annotations of this array dimension expression state.
	 *
	 * @param annotations the replacement for the annotations of this array dimension expression state.
	 * @return the resulting mutated array dimension expression state.
	 */
	public SArrayDimExpr withAnnotations(BUTree<SNodeList> annotations) {
		return new SArrayDimExpr(annotations, expr);
	}

	/**
	 * Replaces the expression of this array dimension expression state.
	 *
	 * @param expr the replacement for the expression of this array dimension expression state.
	 * @return the resulting mutated array dimension expression state.
	 */
	public SArrayDimExpr withExpr(BUTree<? extends SExpr> expr) {
		return new SArrayDimExpr(annotations, expr);
	}

	/**
	 * Builds an array dimension expression facade for the specified array dimension expression <code>TDLocation</code>.
	 *
	 * @param location the array dimension expression <code>TDLocation</code>.
	 * @return an array dimension expression facade for the specified array dimension expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SArrayDimExpr> location) {
		return new TDArrayDimExpr(location);
	}

	/**
	 * Returns the shape for this array dimension expression state.
	 *
	 * @return the shape for this array dimension expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this array dimension expression state.
	 *
	 * @return the first child traversal for this array dimension expression state.
	 */
	@Override
	public STraversal firstChild() {
		return ANNOTATIONS;
	}

	/**
	 * Returns the last child traversal for this array dimension expression state.
	 *
	 * @return the last child traversal for this array dimension expression state.
	 */
	@Override
	public STraversal lastChild() {
		return EXPR;
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
		SArrayDimExpr state = (SArrayDimExpr) o;
		if (!annotations.equals(state.annotations))
			return false;
		if (expr == null ? state.expr != null : !expr.equals(state.expr))
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
		result = 37 * result + annotations.hashCode();
		if (expr != null) result = 37 * result + expr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SArrayDimExpr, SNodeList, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<SArrayDimExpr, SNodeList, NodeList<AnnotationExpr>>() {

		@Override
		public BUTree<?> doTraverse(SArrayDimExpr state) {
			return state.annotations;
		}

		@Override
		public SArrayDimExpr doRebuildParentState(SArrayDimExpr state, BUTree<SNodeList> child) {
			return state.withAnnotations(child);
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

	public static STypeSafeTraversal<SArrayDimExpr, SExpr, Expr> EXPR = new STypeSafeTraversal<SArrayDimExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SArrayDimExpr state) {
			return state.expr;
		}

		@Override
		public SArrayDimExpr doRebuildParentState(SArrayDimExpr state, BUTree<SExpr> child) {
			return state.withExpr(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(ANNOTATIONS, org.jlato.internal.bu.expr.SAnnotationExpr.singleLineAnnotationsShapeWithSpaceBefore),
			token(LToken.BracketLeft), child(EXPR), token(LToken.BracketRight)
	);

	public static final LexicalShape listShape = list();
}
