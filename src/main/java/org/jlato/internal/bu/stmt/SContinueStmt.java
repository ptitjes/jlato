package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDContinueStmt;
import org.jlato.tree.*;
import org.jlato.tree.name.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

public class SContinueStmt extends SNode<SContinueStmt> implements SStmt {

	public static BUTree<SContinueStmt> make(BUTree<SNodeOption> id) {
		return new BUTree<SContinueStmt>(new SContinueStmt(id));
	}

	public final BUTree<SNodeOption> id;

	public SContinueStmt(BUTree<SNodeOption> id) {
		this.id = id;
	}

	@Override
	public Kind kind() {
		return Kind.ContinueStmt;
	}

	public BUTree<SNodeOption> id() {
		return id;
	}

	public SContinueStmt withId(BUTree<SNodeOption> id) {
		return new SContinueStmt(id);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SContinueStmt> location) {
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

	public static STypeSafeTraversal<SContinueStmt, SNodeOption, NodeOption<Name>> ID = new STypeSafeTraversal<SContinueStmt, SNodeOption, NodeOption<Name>>() {

		@Override
		public BUTree<?> doTraverse(SContinueStmt state) {
			return state.id;
		}

		@Override
		public SContinueStmt doRebuildParentState(SContinueStmt state, BUTree<SNodeOption> child) {
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
			token(LToken.Continue),
			child(ID, when(some(), element().withSpacingBefore(space()))),
			token(LToken.SemiColon)
	);
}
