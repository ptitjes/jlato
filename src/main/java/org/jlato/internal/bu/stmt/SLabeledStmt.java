package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDLabeledStmt;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.tree.*;
import org.jlato.tree.name.*;
import org.jlato.tree.stmt.*;

import static org.jlato.internal.shapes.IndentationConstraint.indent;
import static org.jlato.internal.shapes.IndentationConstraint.unIndent;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.spacing;
import static org.jlato.printer.FormattingSettings.SpacingLocation.LabeledStmt_AfterLabel;

public class SLabeledStmt extends SNode<SLabeledStmt> implements SStmt {

	public static BUTree<SLabeledStmt> make(BUTree<SName> label, BUTree<? extends SStmt> stmt) {
		return new BUTree<SLabeledStmt>(new SLabeledStmt(label, stmt));
	}

	public final BUTree<SName> label;

	public final BUTree<? extends SStmt> stmt;

	public SLabeledStmt(BUTree<SName> label, BUTree<? extends SStmt> stmt) {
		this.label = label;
		this.stmt = stmt;
	}

	@Override
	public Kind kind() {
		return Kind.LabeledStmt;
	}

	public BUTree<SName> label() {
		return label;
	}

	public SLabeledStmt withLabel(BUTree<SName> label) {
		return new SLabeledStmt(label, stmt);
	}

	public BUTree<? extends SStmt> stmt() {
		return stmt;
	}

	public SLabeledStmt withStmt(BUTree<? extends SStmt> stmt) {
		return new SLabeledStmt(label, stmt);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SLabeledStmt> location) {
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
		public BUTree<?> doTraverse(SLabeledStmt state) {
			return state.label;
		}

		@Override
		public SLabeledStmt doRebuildParentState(SLabeledStmt state, BUTree<SName> child) {
			return state.withLabel(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return STMT;
		}
	};

	public static STypeSafeTraversal<SLabeledStmt, SStmt, Stmt> STMT = new STypeSafeTraversal<SLabeledStmt, SStmt, Stmt>() {

		@Override
		public BUTree<?> doTraverse(SLabeledStmt state) {
			return state.stmt;
		}

		@Override
		public SLabeledStmt doRebuildParentState(SLabeledStmt state, BUTree<SStmt> child) {
			return state.withStmt(child);
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
			none().withIndentationAfter(indent(IndentationContext.LABEL)),
			child(LABEL),
			token(LToken.Colon).withSpacingAfter(spacing(LabeledStmt_AfterLabel)),
			none().withIndentationBefore(unIndent(IndentationContext.LABEL)),
			child(STMT)
	);
}
