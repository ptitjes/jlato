package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.decl.SFormalParameter;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDCatchClause;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.stmt.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SCatchClause extends SNodeState<SCatchClause> implements STreeState {

	public static BUTree<SCatchClause> make(BUTree<SFormalParameter> except, BUTree<SBlockStmt> catchBlock) {
		return new BUTree<SCatchClause>(new SCatchClause(except, catchBlock));
	}

	public final BUTree<SFormalParameter> except;

	public final BUTree<SBlockStmt> catchBlock;

	public SCatchClause(BUTree<SFormalParameter> except, BUTree<SBlockStmt> catchBlock) {
		this.except = except;
		this.catchBlock = catchBlock;
	}

	@Override
	public Kind kind() {
		return Kind.CatchClause;
	}

	public BUTree<SFormalParameter> except() {
		return except;
	}

	public SCatchClause withExcept(BUTree<SFormalParameter> except) {
		return new SCatchClause(except, catchBlock);
	}

	public BUTree<SBlockStmt> catchBlock() {
		return catchBlock;
	}

	public SCatchClause withCatchBlock(BUTree<SBlockStmt> catchBlock) {
		return new SCatchClause(except, catchBlock);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SCatchClause> location) {
		return new TDCatchClause(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return EXCEPT;
	}

	@Override
	public STraversal lastChild() {
		return CATCH_BLOCK;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SCatchClause state = (SCatchClause) o;
		if (except == null ? state.except != null : !except.equals(state.except))
			return false;
		if (catchBlock == null ? state.catchBlock != null : !catchBlock.equals(state.catchBlock))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (except != null) result = 37 * result + except.hashCode();
		if (catchBlock != null) result = 37 * result + catchBlock.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SCatchClause, SFormalParameter, FormalParameter> EXCEPT = new STypeSafeTraversal<SCatchClause, SFormalParameter, FormalParameter>() {

		@Override
		public BUTree<?> doTraverse(SCatchClause state) {
			return state.except;
		}

		@Override
		public SCatchClause doRebuildParentState(SCatchClause state, BUTree<SFormalParameter> child) {
			return state.withExcept(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return CATCH_BLOCK;
		}
	};

	public static STypeSafeTraversal<SCatchClause, SBlockStmt, BlockStmt> CATCH_BLOCK = new STypeSafeTraversal<SCatchClause, SBlockStmt, BlockStmt>() {

		@Override
		public BUTree<?> doTraverse(SCatchClause state) {
			return state.catchBlock;
		}

		@Override
		public SCatchClause doRebuildParentState(SCatchClause state, BUTree<SBlockStmt> child) {
			return state.withCatchBlock(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return EXCEPT;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.Catch),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(EXCEPT),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(CATCH_BLOCK)
	);

	public static final LexicalShape listShape = list();
}
