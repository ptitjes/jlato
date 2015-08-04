package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.expr.TDThisExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SThisExpr extends SNodeState<SThisExpr> implements SExpr {

	public static STree<SThisExpr> make(STree<SNodeOptionState> classExpr) {
		return new STree<SThisExpr>(new SThisExpr(classExpr));
	}

	public final STree<SNodeOptionState> classExpr;

	public SThisExpr(STree<SNodeOptionState> classExpr) {
		this.classExpr = classExpr;
	}

	@Override
	public Kind kind() {
		return Kind.ThisExpr;
	}

	public STree<SNodeOptionState> classExpr() {
		return classExpr;
	}

	public SThisExpr withClassExpr(STree<SNodeOptionState> classExpr) {
		return new SThisExpr(classExpr);
	}

	@Override
	protected Tree doInstantiate(SLocation<SThisExpr> location) {
		return new TDThisExpr(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return CLASS_EXPR;
	}

	@Override
	public STraversal lastChild() {
		return CLASS_EXPR;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SThisExpr state = (SThisExpr) o;
		if (!classExpr.equals(state.classExpr))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + classExpr.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SThisExpr, SNodeOptionState, NodeOption<Expr>> CLASS_EXPR = new STypeSafeTraversal<SThisExpr, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(SThisExpr state) {
			return state.classExpr;
		}

		@Override
		public SThisExpr doRebuildParentState(SThisExpr state, STree<SNodeOptionState> child) {
			return state.withClassExpr(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(CLASS_EXPR, when(some(), composite(element(), token(LToken.Dot)))),
			token(LToken.This)
	);
}
