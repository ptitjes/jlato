package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDSuperExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a 'super' expression.
 */
public class SSuperExpr extends SNode<SSuperExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new 'super' expression.
	 *
	 * @param classExpr the 'class' expression child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a 'super' expression.
	 */
	public static BUTree<SSuperExpr> make(BUTree<SNodeOption> classExpr) {
		return new BUTree<SSuperExpr>(new SSuperExpr(classExpr));
	}

	/**
	 * The 'class' expression of this 'super' expression state.
	 */
	public final BUTree<SNodeOption> classExpr;

	/**
	 * Constructs a 'super' expression state.
	 *
	 * @param classExpr the 'class' expression child <code>BUTree</code>.
	 */
	public SSuperExpr(BUTree<SNodeOption> classExpr) {
		this.classExpr = classExpr;
	}

	/**
	 * Returns the kind of this 'super' expression.
	 *
	 * @return the kind of this 'super' expression.
	 */
	@Override
	public Kind kind() {
		return Kind.SuperExpr;
	}

	/**
	 * Replaces the 'class' expression of this 'super' expression state.
	 *
	 * @param classExpr the replacement for the 'class' expression of this 'super' expression state.
	 * @return the resulting mutated 'super' expression state.
	 */
	public SSuperExpr withClassExpr(BUTree<SNodeOption> classExpr) {
		return new SSuperExpr(classExpr);
	}

	/**
	 * Builds a 'super' expression facade for the specified 'super' expression <code>TDLocation</code>.
	 *
	 * @param location the 'super' expression <code>TDLocation</code>.
	 * @return a 'super' expression facade for the specified 'super' expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SSuperExpr> location) {
		return new TDSuperExpr(location);
	}

	/**
	 * Returns the shape for this 'super' expression state.
	 *
	 * @return the shape for this 'super' expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'super' expression state.
	 *
	 * @return the first child traversal for this 'super' expression state.
	 */
	@Override
	public STraversal firstChild() {
		return CLASS_EXPR;
	}

	/**
	 * Returns the last child traversal for this 'super' expression state.
	 *
	 * @return the last child traversal for this 'super' expression state.
	 */
	@Override
	public STraversal lastChild() {
		return CLASS_EXPR;
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
		SSuperExpr state = (SSuperExpr) o;
		if (!classExpr.equals(state.classExpr))
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
		result = 37 * result + classExpr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SSuperExpr, SNodeOption, NodeOption<Expr>> CLASS_EXPR = new STypeSafeTraversal<SSuperExpr, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SSuperExpr state) {
			return state.classExpr;
		}

		@Override
		public SSuperExpr doRebuildParentState(SSuperExpr state, BUTree<SNodeOption> child) {
			return state.withClassExpr(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(CLASS_EXPR, when(some(), composite(element(), token(LToken.Dot)))),
			token(LToken.Super)
	);
}
