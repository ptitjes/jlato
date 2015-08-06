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
import static org.jlato.internal.shapes.SpacingConstraint.space;

public class SCatchClause extends SNode<SCatchClause> implements STree {

	public static BUTree<SCatchClause> make(BUTree<SFormalParameter> param, BUTree<SBlockStmt> catchBlock) {
		return new BUTree<SCatchClause>(new SCatchClause(param, catchBlock));
	}

	public final BUTree<SFormalParameter> param;

	public final BUTree<SBlockStmt> catchBlock;

	public SCatchClause(BUTree<SFormalParameter> param, BUTree<SBlockStmt> catchBlock) {
		this.param = param;
		this.catchBlock = catchBlock;
	}

	@Override
	public Kind kind() {
		return Kind.CatchClause;
	}

	public SCatchClause withParam(BUTree<SFormalParameter> param) {
		return new SCatchClause(param, catchBlock);
	}

	public SCatchClause withCatchBlock(BUTree<SBlockStmt> catchBlock) {
		return new SCatchClause(param, catchBlock);
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
		return PARAM;
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
		if (param == null ? state.param != null : !param.equals(state.param))
			return false;
		if (catchBlock == null ? state.catchBlock != null : !catchBlock.equals(state.catchBlock))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (param != null) result = 37 * result + param.hashCode();
		if (catchBlock != null) result = 37 * result + catchBlock.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SCatchClause, SFormalParameter, FormalParameter> PARAM = new STypeSafeTraversal<SCatchClause, SFormalParameter, FormalParameter>() {

		@Override
		public BUTree<?> doTraverse(SCatchClause state) {
			return state.param;
		}

		@Override
		public SCatchClause doRebuildParentState(SCatchClause state, BUTree<SFormalParameter> child) {
			return state.withParam(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
		public STraversal leftSibling(STree state) {
			return PARAM;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.Catch),
			token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(PARAM),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(CATCH_BLOCK)
	);

	public static final LexicalShape listShape = list();
}
