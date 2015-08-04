package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.stmt.TDBreakStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SBreakStmt extends SNodeState<SBreakStmt> implements SStmt {

	public static STree<SBreakStmt> make(STree<SNodeOptionState> id) {
		return new STree<SBreakStmt>(new SBreakStmt(id));
	}

	public final STree<SNodeOptionState> id;

	public SBreakStmt(STree<SNodeOptionState> id) {
		this.id = id;
	}

	@Override
	public Kind kind() {
		return Kind.BreakStmt;
	}

	public STree<SNodeOptionState> id() {
		return id;
	}

	public SBreakStmt withId(STree<SNodeOptionState> id) {
		return new SBreakStmt(id);
	}

	@Override
	protected Tree doInstantiate(SLocation<SBreakStmt> location) {
		return new TDBreakStmt(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return ID;
	}

	@Override
	public STraversal lastChild() {
		return ID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SBreakStmt state = (SBreakStmt) o;
		if (!id.equals(state.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + id.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SBreakStmt, SNodeOptionState, NodeOption<Name>> ID = new STypeSafeTraversal<SBreakStmt, SNodeOptionState, NodeOption<Name>>() {

		@Override
		public STree<?> doTraverse(SBreakStmt state) {
			return state.id;
		}

		@Override
		public SBreakStmt doRebuildParentState(SBreakStmt state, STree<SNodeOptionState> child) {
			return state.withId(child);
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
			token(LToken.Break),
			child(ID, when(some(), element().withSpacingBefore(space()))),
			token(LToken.SemiColon)
	);
}
