package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.stmt.TDLabeledStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.Stmt;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SLabeledStmt extends SNodeState<SLabeledStmt> implements SStmt {

	public static STree<SLabeledStmt> make(STree<SName> label, STree<? extends SStmt> stmt) {
		return new STree<SLabeledStmt>(new SLabeledStmt(label, stmt));
	}

	public final STree<SName> label;

	public final STree<? extends SStmt> stmt;

	public SLabeledStmt(STree<SName> label, STree<? extends SStmt> stmt) {
		this.label = label;
		this.stmt = stmt;
	}

	@Override
	public Kind kind() {
		return Kind.LabeledStmt;
	}

	public STree<SName> label() {
		return label;
	}

	public SLabeledStmt withLabel(STree<SName> label) {
		return new SLabeledStmt(label, stmt);
	}

	public STree<? extends SStmt> stmt() {
		return stmt;
	}

	public SLabeledStmt withStmt(STree<? extends SStmt> stmt) {
		return new SLabeledStmt(label, stmt);
	}

	@Override
	protected Tree doInstantiate(SLocation<SLabeledStmt> location) {
		return new TDLabeledStmt(location);
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
		return STMT;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SLabeledStmt state = (SLabeledStmt) o;
		if (label == null ? state.label != null : !label.equals(state.label))
			return false;
		if (stmt == null ? state.stmt != null : !stmt.equals(state.stmt))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (label != null) result = 37 * result + label.hashCode();
		if (stmt != null) result = 37 * result + stmt.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SLabeledStmt, SName, Name> LABEL = new STypeSafeTraversal<SLabeledStmt, SName, Name>() {

		@Override
		public STree<?> doTraverse(SLabeledStmt state) {
			return state.label;
		}

		@Override
		public SLabeledStmt doRebuildParentState(SLabeledStmt state, STree<SName> child) {
			return state.withLabel(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return STMT;
		}
	};

	public static STypeSafeTraversal<SLabeledStmt, SStmt, Stmt> STMT = new STypeSafeTraversal<SLabeledStmt, SStmt, Stmt>() {

		@Override
		public STree<?> doTraverse(SLabeledStmt state) {
			return state.stmt;
		}

		@Override
		public SLabeledStmt doRebuildParentState(SLabeledStmt state, STree<SStmt> child) {
			return state.withStmt(child);
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
			none().withIndentationAfter(indent(IndentationContext.LABEL)),
			child(LABEL),
			token(LToken.Colon).withSpacingAfter(spacing(LabeledStmt_AfterLabel)),
			none().withIndentationBefore(unIndent(IndentationContext.LABEL)),
			child(STMT)
	);
}
