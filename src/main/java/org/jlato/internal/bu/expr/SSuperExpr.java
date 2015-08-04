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
import org.jlato.internal.td.expr.TDSuperExpr;
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

public class SSuperExpr extends SNodeState<SSuperExpr> implements SExpr {

	public static STree<SSuperExpr> make(STree<SNodeOptionState> classExpr) {
		return new STree<SSuperExpr>(new SSuperExpr(classExpr));
	}

	public final STree<SNodeOptionState> classExpr;

	public SSuperExpr(STree<SNodeOptionState> classExpr) {
		this.classExpr = classExpr;
	}

	@Override
	public Kind kind() {
		return Kind.SuperExpr;
	}

	public STree<SNodeOptionState> classExpr() {
		return classExpr;
	}

	public SSuperExpr withClassExpr(STree<SNodeOptionState> classExpr) {
		return new SSuperExpr(classExpr);
	}

	@Override
	protected Tree doInstantiate(SLocation<SSuperExpr> location) {
		return new TDSuperExpr(location);
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
		SSuperExpr state = (SSuperExpr) o;
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

	public static STypeSafeTraversal<SSuperExpr, SNodeOptionState, NodeOption<Expr>> CLASS_EXPR = new STypeSafeTraversal<SSuperExpr, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(SSuperExpr state) {
			return state.classExpr;
		}

		@Override
		public SSuperExpr doRebuildParentState(SSuperExpr state, STree<SNodeOptionState> child) {
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
			token(LToken.Super)
	);
}
