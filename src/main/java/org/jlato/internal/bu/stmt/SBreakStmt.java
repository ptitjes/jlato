package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDBreakStmt;
import org.jlato.tree.*;
import org.jlato.tree.name.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SBreakStmt extends SNodeState<SBreakStmt> implements SStmt {

	public static BUTree<SBreakStmt> make(BUTree<SNodeOptionState> id) {
		return new BUTree<SBreakStmt>(new SBreakStmt(id));
	}

	public final BUTree<SNodeOptionState> id;

	public SBreakStmt(BUTree<SNodeOptionState> id) {
		this.id = id;
	}

	@Override
	public Kind kind() {
		return Kind.BreakStmt;
	}

	public BUTree<SNodeOptionState> id() {
		return id;
	}

	public SBreakStmt withId(BUTree<SNodeOptionState> id) {
		return new SBreakStmt(id);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SBreakStmt> location) {
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
		public BUTree<?> doTraverse(SBreakStmt state) {
			return state.id;
		}

		@Override
		public SBreakStmt doRebuildParentState(SBreakStmt state, BUTree<SNodeOptionState> child) {
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
