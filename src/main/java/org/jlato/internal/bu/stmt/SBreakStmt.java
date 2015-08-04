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

public class SBreakStmt extends SNode<SBreakStmt> implements SStmt {

	public static BUTree<SBreakStmt> make(BUTree<SNodeOption> id) {
		return new BUTree<SBreakStmt>(new SBreakStmt(id));
	}

	public final BUTree<SNodeOption> id;

	public SBreakStmt(BUTree<SNodeOption> id) {
		this.id = id;
	}

	@Override
	public Kind kind() {
		return Kind.BreakStmt;
	}

	public BUTree<SNodeOption> id() {
		return id;
	}

	public SBreakStmt withId(BUTree<SNodeOption> id) {
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

	public static STypeSafeTraversal<SBreakStmt, SNodeOption, NodeOption<Name>> ID = new STypeSafeTraversal<SBreakStmt, SNodeOption, NodeOption<Name>>() {

		@Override
		public BUTree<?> doTraverse(SBreakStmt state) {
			return state.id;
		}

		@Override
		public SBreakStmt doRebuildParentState(SBreakStmt state, BUTree<SNodeOption> child) {
			return state.withId(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.Break),
			child(ID, when(some(), element().withSpacingBefore(space()))),
			token(LToken.SemiColon)
	);
}
