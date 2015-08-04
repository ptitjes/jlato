package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.decl.SFormalParameter;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.stmt.TDCatchClause;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SCatchClause extends SNodeState<SCatchClause> implements STreeState {

	public static STree<SCatchClause> make(STree<SFormalParameter> except, STree<SBlockStmt> catchBlock) {
		return new STree<SCatchClause>(new SCatchClause(except, catchBlock));
	}

	public final STree<SFormalParameter> except;

	public final STree<SBlockStmt> catchBlock;

	public SCatchClause(STree<SFormalParameter> except, STree<SBlockStmt> catchBlock) {
		this.except = except;
		this.catchBlock = catchBlock;
	}

	@Override
	public Kind kind() {
		return Kind.CatchClause;
	}

	public STree<SFormalParameter> except() {
		return except;
	}

	public SCatchClause withExcept(STree<SFormalParameter> except) {
		return new SCatchClause(except, catchBlock);
	}

	public STree<SBlockStmt> catchBlock() {
		return catchBlock;
	}

	public SCatchClause withCatchBlock(STree<SBlockStmt> catchBlock) {
		return new SCatchClause(except, catchBlock);
	}

	@Override
	protected Tree doInstantiate(SLocation<SCatchClause> location) {
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
		public STree<?> doTraverse(SCatchClause state) {
			return state.except;
		}

		@Override
		public SCatchClause doRebuildParentState(SCatchClause state, STree<SFormalParameter> child) {
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
		public STree<?> doTraverse(SCatchClause state) {
			return state.catchBlock;
		}

		@Override
		public SCatchClause doRebuildParentState(SCatchClause state, STree<SBlockStmt> child) {
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
