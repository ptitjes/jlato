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
import org.jlato.internal.td.stmt.TDContinueStmt;
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

public class SContinueStmt extends SNodeState<SContinueStmt> implements SStmt {

	public static STree<SContinueStmt> make(STree<SNodeOptionState> id) {
		return new STree<SContinueStmt>(new SContinueStmt(id));
	}

	public final STree<SNodeOptionState> id;

	public SContinueStmt(STree<SNodeOptionState> id) {
		this.id = id;
	}

	@Override
	public Kind kind() {
		return Kind.ContinueStmt;
	}

	public STree<SNodeOptionState> id() {
		return id;
	}

	public SContinueStmt withId(STree<SNodeOptionState> id) {
		return new SContinueStmt(id);
	}

	@Override
	protected Tree doInstantiate(SLocation<SContinueStmt> location) {
		return new TDContinueStmt(location);
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
		SContinueStmt state = (SContinueStmt) o;
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

	public static STypeSafeTraversal<SContinueStmt, SNodeOptionState, NodeOption<Name>> ID = new STypeSafeTraversal<SContinueStmt, SNodeOptionState, NodeOption<Name>>() {

		@Override
		public STree<?> doTraverse(SContinueStmt state) {
			return state.id;
		}

		@Override
		public SContinueStmt doRebuildParentState(SContinueStmt state, STree<SNodeOptionState> child) {
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
			token(LToken.Continue),
			child(ID, when(some(), element().withSpacingBefore(space()))),
			token(LToken.SemiColon)
	);
}
