package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.expr.TDFieldAccessExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SFieldAccessExpr extends SNodeState<SFieldAccessExpr> implements SExpr {

	public static STree<SFieldAccessExpr> make(STree<SNodeOptionState> scope, STree<SName> name) {
		return new STree<SFieldAccessExpr>(new SFieldAccessExpr(scope, name));
	}

	public final STree<SNodeOptionState> scope;

	public final STree<SName> name;

	public SFieldAccessExpr(STree<SNodeOptionState> scope, STree<SName> name) {
		this.scope = scope;
		this.name = name;
	}

	@Override
	public Kind kind() {
		return Kind.FieldAccessExpr;
	}

	public STree<SNodeOptionState> scope() {
		return scope;
	}

	public SFieldAccessExpr withScope(STree<SNodeOptionState> scope) {
		return new SFieldAccessExpr(scope, name);
	}

	public STree<SName> name() {
		return name;
	}

	public SFieldAccessExpr withName(STree<SName> name) {
		return new SFieldAccessExpr(scope, name);
	}

	@Override
	protected Tree doInstantiate(SLocation<SFieldAccessExpr> location) {
		return new TDFieldAccessExpr(location);
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
		return NAME;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SFieldAccessExpr state = (SFieldAccessExpr) o;
		if (!scope.equals(state.scope))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + scope.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SFieldAccessExpr, SNodeOptionState, NodeOption<Expr>> SCOPE = new STypeSafeTraversal<SFieldAccessExpr, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(SFieldAccessExpr state) {
			return state.scope;
		}

		@Override
		public SFieldAccessExpr doRebuildParentState(SFieldAccessExpr state, STree<SNodeOptionState> child) {
			return state.withScope(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SFieldAccessExpr, SName, Name> NAME = new STypeSafeTraversal<SFieldAccessExpr, SName, Name>() {

		@Override
		public STree<?> doTraverse(SFieldAccessExpr state) {
			return state.name;
		}

		@Override
		public SFieldAccessExpr doRebuildParentState(SFieldAccessExpr state, STree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return SCOPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(SCOPE, when(some(), composite(element(), token(LToken.Dot)))),
			child(NAME)
	);
}
