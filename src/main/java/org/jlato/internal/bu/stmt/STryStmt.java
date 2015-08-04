package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDTryStmt;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.stmt.*;

import java.util.Collections;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.TRY_RESOURCES;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.newLine;
import static org.jlato.printer.SpacingConstraint.space;

public class STryStmt extends SNode<STryStmt> implements SStmt {

	public static BUTree<STryStmt> make(BUTree<SNodeList> resources, boolean trailingSemiColon, BUTree<SBlockStmt> tryBlock, BUTree<SNodeList> catchs, BUTree<SNodeOption> finallyBlock) {
		return new BUTree<STryStmt>(new STryStmt(resources, trailingSemiColon, tryBlock, catchs, finallyBlock));
	}

	public final BUTree<SNodeList> resources;

	public final boolean trailingSemiColon;

	public final BUTree<SBlockStmt> tryBlock;

	public final BUTree<SNodeList> catchs;

	public final BUTree<SNodeOption> finallyBlock;

	public STryStmt(BUTree<SNodeList> resources, boolean trailingSemiColon, BUTree<SBlockStmt> tryBlock, BUTree<SNodeList> catchs, BUTree<SNodeOption> finallyBlock) {
		this.resources = resources;
		this.trailingSemiColon = trailingSemiColon;
		this.tryBlock = tryBlock;
		this.catchs = catchs;
		this.finallyBlock = finallyBlock;
	}

	@Override
	public Kind kind() {
		return Kind.TryStmt;
	}

	public BUTree<SNodeList> resources() {
		return resources;
	}

	public STryStmt withResources(BUTree<SNodeList> resources) {
		return new STryStmt(resources, trailingSemiColon, tryBlock, catchs, finallyBlock);
	}

	public boolean trailingSemiColon() {
		return trailingSemiColon;
	}

	public STryStmt withTrailingSemiColon(boolean trailingSemiColon) {
		return new STryStmt(resources, trailingSemiColon, tryBlock, catchs, finallyBlock);
	}

	public BUTree<SBlockStmt> tryBlock() {
		return tryBlock;
	}

	public STryStmt withTryBlock(BUTree<SBlockStmt> tryBlock) {
		return new STryStmt(resources, trailingSemiColon, tryBlock, catchs, finallyBlock);
	}

	public BUTree<SNodeList> catchs() {
		return catchs;
	}

	public STryStmt withCatchs(BUTree<SNodeList> catchs) {
		return new STryStmt(resources, trailingSemiColon, tryBlock, catchs, finallyBlock);
	}

	public BUTree<SNodeOption> finallyBlock() {
		return finallyBlock;
	}

	public STryStmt withFinallyBlock(BUTree<SNodeOption> finallyBlock) {
		return new STryStmt(resources, trailingSemiColon, tryBlock, catchs, finallyBlock);
	}

	@Override
	protected Tree doInstantiate(TDLocation<STryStmt> location) {
		return new TDTryStmt(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(TRAILING_SEMI_COLON);
	}

	@Override
	public STraversal firstChild() {
		return RESOURCES;
	}

	@Override
	public STraversal lastChild() {
		return FINALLY_BLOCK;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		STryStmt state = (STryStmt) o;
		if (!resources.equals(state.resources))
			return false;
		if (trailingSemiColon != state.trailingSemiColon)
			return false;
		if (tryBlock == null ? state.tryBlock != null : !tryBlock.equals(state.tryBlock))
			return false;
		if (!catchs.equals(state.catchs))
			return false;
		if (!finallyBlock.equals(state.finallyBlock))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + resources.hashCode();
		result = 37 * result + (trailingSemiColon ? 1 : 0);
		if (tryBlock != null) result = 37 * result + tryBlock.hashCode();
		result = 37 * result + catchs.hashCode();
		result = 37 * result + finallyBlock.hashCode();
		return result;
	}

	public static STypeSafeTraversal<STryStmt, SNodeList, NodeList<VariableDeclarationExpr>> RESOURCES = new STypeSafeTraversal<STryStmt, SNodeList, NodeList<VariableDeclarationExpr>>() {

		@Override
		public BUTree<?> doTraverse(STryStmt state) {
			return state.resources;
		}

		@Override
		public STryStmt doRebuildParentState(STryStmt state, BUTree<SNodeList> child) {
			return state.withResources(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return TRY_BLOCK;
		}
	};

	public static STypeSafeTraversal<STryStmt, SBlockStmt, BlockStmt> TRY_BLOCK = new STypeSafeTraversal<STryStmt, SBlockStmt, BlockStmt>() {

		@Override
		public BUTree<?> doTraverse(STryStmt state) {
			return state.tryBlock;
		}

		@Override
		public STryStmt doRebuildParentState(STryStmt state, BUTree<SBlockStmt> child) {
			return state.withTryBlock(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return RESOURCES;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return CATCHS;
		}
	};

	public static STypeSafeTraversal<STryStmt, SNodeList, NodeList<CatchClause>> CATCHS = new STypeSafeTraversal<STryStmt, SNodeList, NodeList<CatchClause>>() {

		@Override
		public BUTree<?> doTraverse(STryStmt state) {
			return state.catchs;
		}

		@Override
		public STryStmt doRebuildParentState(STryStmt state, BUTree<SNodeList> child) {
			return state.withCatchs(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TRY_BLOCK;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return FINALLY_BLOCK;
		}
	};

	public static STypeSafeTraversal<STryStmt, SNodeOption, NodeOption<BlockStmt>> FINALLY_BLOCK = new STypeSafeTraversal<STryStmt, SNodeOption, NodeOption<BlockStmt>>() {

		@Override
		public BUTree<?> doTraverse(STryStmt state) {
			return state.finallyBlock;
		}

		@Override
		public STryStmt doRebuildParentState(STryStmt state, BUTree<SNodeOption> child) {
			return state.withFinallyBlock(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return CATCHS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static STypeSafeProperty<STryStmt, Boolean> TRAILING_SEMI_COLON = new STypeSafeProperty<STryStmt, Boolean>() {

		@Override
		public Boolean doRetrieve(STryStmt state) {
			return state.trailingSemiColon;
		}

		@Override
		public STryStmt doRebuildParentState(STryStmt state, Boolean value) {
			return state.withTrailingSemiColon(value);
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.Try),
			when(childIs(RESOURCES, not(empty())),
					composite(
							token(LToken.ParenthesisLeft)
									.withIndentationAfter(indent(TRY_RESOURCES)),
							child(RESOURCES, list(token(LToken.SemiColon).withSpacingAfter(newLine()))),
							when(data(TRAILING_SEMI_COLON), token(LToken.SemiColon)),
							token(LToken.ParenthesisRight)
									.withIndentationBefore(unIndent(TRY_RESOURCES))
									.withSpacingAfter(space())
					)
			),
			child(TRY_BLOCK),
			child(CATCHS, SCatchClause.listShape),
			child(FINALLY_BLOCK, when(some(),
					composite(keyword(LToken.Finally), element())
			))
	);
}