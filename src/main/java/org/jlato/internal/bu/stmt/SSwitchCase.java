package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDSwitchCase;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.stmt.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.internal.shapes.IndentationConstraint.indent;
import static org.jlato.internal.shapes.IndentationConstraint.unIndent;
import static org.jlato.internal.shapes.SpacingConstraint.newLine;

public class SSwitchCase extends SNode<SSwitchCase> implements STree {

	public static BUTree<SSwitchCase> make(BUTree<SNodeOption> label, BUTree<SNodeList> stmts) {
		return new BUTree<SSwitchCase>(new SSwitchCase(label, stmts));
	}

	public final BUTree<SNodeOption> label;

	public final BUTree<SNodeList> stmts;

	public SSwitchCase(BUTree<SNodeOption> label, BUTree<SNodeList> stmts) {
		this.label = label;
		this.stmts = stmts;
	}

	@Override
	public Kind kind() {
		return Kind.SwitchCase;
	}

	public BUTree<SNodeOption> label() {
		return label;
	}

	public SSwitchCase withLabel(BUTree<SNodeOption> label) {
		return new SSwitchCase(label, stmts);
	}

	public BUTree<SNodeList> stmts() {
		return stmts;
	}

	public SSwitchCase withStmts(BUTree<SNodeList> stmts) {
		return new SSwitchCase(label, stmts);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SSwitchCase> location) {
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

	public static STypeSafeTraversal<SSwitchCase, SNodeOption, NodeOption<Expr>> LABEL = new STypeSafeTraversal<SSwitchCase, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SSwitchCase state) {
			return state.label;
		}

		@Override
		public SSwitchCase doRebuildParentState(SSwitchCase state, BUTree<SNodeOption> child) {
			return state.withLabel(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return STMTS;
		}
	};

	public static STypeSafeTraversal<SSwitchCase, SNodeList, NodeList<Stmt>> STMTS = new STypeSafeTraversal<SSwitchCase, SNodeList, NodeList<Stmt>>() {

		@Override
		public BUTree<?> doTraverse(SSwitchCase state) {
			return state.stmts;
		}

		@Override
		public SSwitchCase doRebuildParentState(SSwitchCase state, BUTree<SNodeList> child) {
			return state.withStmts(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return LABEL;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
