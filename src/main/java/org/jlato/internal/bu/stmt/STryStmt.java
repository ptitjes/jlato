package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDTryStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.VariableDeclarationExpr;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.stmt.CatchClause;

import java.util.Collections;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a 'try' statement.
 */
public class STryStmt extends SNode<STryStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new 'try' statement.
	 *
	 * @param resources         the resources child <code>BUTree</code>.
	 * @param trailingSemiColon the has a trailing semi-colon for its resources child <code>BUTree</code>.
	 * @param tryBlock          the 'try' block child <code>BUTree</code>.
	 * @param catchs            the catchs child <code>BUTree</code>.
	 * @param finallyBlock      the 'finally' block child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a 'try' statement.
	 */
	public static BUTree<STryStmt> make(BUTree<SNodeList> resources, boolean trailingSemiColon, BUTree<SBlockStmt> tryBlock, BUTree<SNodeList> catchs, BUTree<SNodeOption> finallyBlock) {
		return new BUTree<STryStmt>(new STryStmt(resources, trailingSemiColon, tryBlock, catchs, finallyBlock));
	}

	/**
	 * The resources of this 'try' statement state.
	 */
	public final BUTree<SNodeList> resources;

	/**
	 * The has a trailing semi-colon for its resources of this 'try' statement state.
	 */
	public final boolean trailingSemiColon;

	/**
	 * The 'try' block of this 'try' statement state.
	 */
	public final BUTree<SBlockStmt> tryBlock;

	/**
	 * The catchs of this 'try' statement state.
	 */
	public final BUTree<SNodeList> catchs;

	/**
	 * The 'finally' block of this 'try' statement state.
	 */
	public final BUTree<SNodeOption> finallyBlock;

	/**
	 * Constructs a 'try' statement state.
	 *
	 * @param resources         the resources child <code>BUTree</code>.
	 * @param trailingSemiColon the has a trailing semi-colon for its resources child <code>BUTree</code>.
	 * @param tryBlock          the 'try' block child <code>BUTree</code>.
	 * @param catchs            the catchs child <code>BUTree</code>.
	 * @param finallyBlock      the 'finally' block child <code>BUTree</code>.
	 */
	public STryStmt(BUTree<SNodeList> resources, boolean trailingSemiColon, BUTree<SBlockStmt> tryBlock, BUTree<SNodeList> catchs, BUTree<SNodeOption> finallyBlock) {
		this.resources = resources;
		this.trailingSemiColon = trailingSemiColon;
		this.tryBlock = tryBlock;
		this.catchs = catchs;
		this.finallyBlock = finallyBlock;
	}

	/**
	 * Returns the kind of this 'try' statement.
	 *
	 * @return the kind of this 'try' statement.
	 */
	@Override
	public Kind kind() {
		return Kind.TryStmt;
	}

	/**
	 * Replaces the resources of this 'try' statement state.
	 *
	 * @param resources the replacement for the resources of this 'try' statement state.
	 * @return the resulting mutated 'try' statement state.
	 */
	public STryStmt withResources(BUTree<SNodeList> resources) {
		return new STryStmt(resources, trailingSemiColon, tryBlock, catchs, finallyBlock);
	}

	/**
	 * Replaces the has a trailing semi-colon for its resources of this 'try' statement state.
	 *
	 * @param trailingSemiColon the replacement for the has a trailing semi-colon for its resources of this 'try' statement state.
	 * @return the resulting mutated 'try' statement state.
	 */
	public STryStmt withTrailingSemiColon(boolean trailingSemiColon) {
		return new STryStmt(resources, trailingSemiColon, tryBlock, catchs, finallyBlock);
	}

	/**
	 * Replaces the 'try' block of this 'try' statement state.
	 *
	 * @param tryBlock the replacement for the 'try' block of this 'try' statement state.
	 * @return the resulting mutated 'try' statement state.
	 */
	public STryStmt withTryBlock(BUTree<SBlockStmt> tryBlock) {
		return new STryStmt(resources, trailingSemiColon, tryBlock, catchs, finallyBlock);
	}

	/**
	 * Replaces the catchs of this 'try' statement state.
	 *
	 * @param catchs the replacement for the catchs of this 'try' statement state.
	 * @return the resulting mutated 'try' statement state.
	 */
	public STryStmt withCatchs(BUTree<SNodeList> catchs) {
		return new STryStmt(resources, trailingSemiColon, tryBlock, catchs, finallyBlock);
	}

	/**
	 * Replaces the 'finally' block of this 'try' statement state.
	 *
	 * @param finallyBlock the replacement for the 'finally' block of this 'try' statement state.
	 * @return the resulting mutated 'try' statement state.
	 */
	public STryStmt withFinallyBlock(BUTree<SNodeOption> finallyBlock) {
		return new STryStmt(resources, trailingSemiColon, tryBlock, catchs, finallyBlock);
	}

	/**
	 * Builds a 'try' statement facade for the specified 'try' statement <code>TDLocation</code>.
	 *
	 * @param location the 'try' statement <code>TDLocation</code>.
	 * @return a 'try' statement facade for the specified 'try' statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<STryStmt> location) {
		return new TDTryStmt(location);
	}

	/**
	 * Returns the shape for this 'try' statement state.
	 *
	 * @return the shape for this 'try' statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the properties for this 'try' statement state.
	 *
	 * @return the properties for this 'try' statement state.
	 */
	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(TRAILING_SEMI_COLON);
	}

	/**
	 * Returns the first child traversal for this 'try' statement state.
	 *
	 * @return the first child traversal for this 'try' statement state.
	 */
	@Override
	public STraversal firstChild() {
		return RESOURCES;
	}

	/**
	 * Returns the last child traversal for this 'try' statement state.
	 *
	 * @return the last child traversal for this 'try' statement state.
	 */
	@Override
	public STraversal lastChild() {
		return FINALLY_BLOCK;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
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

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
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
					token(LToken.ParenthesisLeft)
							.withIndentationAfter(indent(IndentationContext.TryResources))
			),
			child(RESOURCES, list(token(LToken.SemiColon).withSpacingAfter(newLine()))),
			when(childIs(RESOURCES, not(empty())), when(data(TRAILING_SEMI_COLON), token(LToken.SemiColon))),
			when(childIs(RESOURCES, not(empty())),
					token(LToken.ParenthesisRight)
							.withIndentationBefore(unIndent(IndentationContext.TryResources))
							.withSpacingAfter(space())
			),
			child(TRY_BLOCK),
			child(CATCHS, SCatchClause.listShape),
			child(FINALLY_BLOCK, when(some(),
					composite(keyword(LToken.Finally), element())
			))
	);
}
