package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.stmt.TDSwitchCase;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.stmt.Stmt;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SSwitchCase extends SNodeState<SSwitchCase> implements STreeState {

	public static STree<SSwitchCase> make(STree<SNodeOptionState> label, STree<SNodeListState> stmts) {
		return new STree<SSwitchCase>(new SSwitchCase(label, stmts));
	}

	public final STree<SNodeOptionState> label;

	public final STree<SNodeListState> stmts;

	public SSwitchCase(STree<SNodeOptionState> label, STree<SNodeListState> stmts) {
		this.label = label;
		this.stmts = stmts;
	}

	@Override
	public Kind kind() {
		return Kind.SwitchCase;
	}

	public STree<SNodeOptionState> label() {
		return label;
	}

	public SSwitchCase withLabel(STree<SNodeOptionState> label) {
		return new SSwitchCase(label, stmts);
	}

	public STree<SNodeListState> stmts() {
		return stmts;
	}

	public SSwitchCase withStmts(STree<SNodeListState> stmts) {
		return new SSwitchCase(label, stmts);
	}

	@Override
	protected Tree doInstantiate(SLocation<SSwitchCase> location) {
		return new TDSwitchCase(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return LABEL;
	}

	@Override
	public STraversal lastChild() {
		return STMTS;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SSwitchCase state = (SSwitchCase) o;
		if (!label.equals(state.label))
			return false;
		if (!stmts.equals(state.stmts))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + label.hashCode();
		result = 37 * result + stmts.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SSwitchCase, SNodeOptionState, NodeOption<Expr>> LABEL = new STypeSafeTraversal<SSwitchCase, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(SSwitchCase state) {
			return state.label;
		}

		@Override
		public SSwitchCase doRebuildParentState(SSwitchCase state, STree<SNodeOptionState> child) {
			return state.withLabel(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return STMTS;
		}
	};

	public static STypeSafeTraversal<SSwitchCase, SNodeListState, NodeList<Stmt>> STMTS = new STypeSafeTraversal<SSwitchCase, SNodeListState, NodeList<Stmt>>() {

		@Override
		public STree<?> doTraverse(SSwitchCase state) {
			return state.stmts;
		}

		@Override
		public SSwitchCase doRebuildParentState(SSwitchCase state, STree<SNodeListState> child) {
			return state.withStmts(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return LABEL;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(LABEL, alternative(some(),
					composite(keyword(LToken.Case), element()),
					token(LToken.Default)
			)),
			token(LToken.Colon).withSpacingAfter(newLine()),
			none().withIndentationAfter(indent(BLOCK)),
			child(STMTS, SStmt.listShape),
			none().withIndentationBefore(unIndent(BLOCK))
	);

	public static final LexicalShape listShape = list(none().withSpacingAfter(newLine()));
}
