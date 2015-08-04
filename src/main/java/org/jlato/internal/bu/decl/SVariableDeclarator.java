package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDVariableDeclarator;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.VariableDeclaratorId;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SVariableDeclarator extends SNodeState<SVariableDeclarator> implements STreeState {

	public static STree<SVariableDeclarator> make(STree<SVariableDeclaratorId> id, STree<SNodeOptionState> init) {
		return new STree<SVariableDeclarator>(new SVariableDeclarator(id, init));
	}

	public final STree<SVariableDeclaratorId> id;

	public final STree<SNodeOptionState> init;

	public SVariableDeclarator(STree<SVariableDeclaratorId> id, STree<SNodeOptionState> init) {
		this.id = id;
		this.init = init;
	}

	@Override
	public Kind kind() {
		return Kind.VariableDeclarator;
	}

	public STree<SVariableDeclaratorId> id() {
		return id;
	}

	public SVariableDeclarator withId(STree<SVariableDeclaratorId> id) {
		return new SVariableDeclarator(id, init);
	}

	public STree<SNodeOptionState> init() {
		return init;
	}

	public SVariableDeclarator withInit(STree<SNodeOptionState> init) {
		return new SVariableDeclarator(id, init);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SVariableDeclarator> location) {
		return new TDVariableDeclarator(location);
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
		return INIT;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SVariableDeclarator state = (SVariableDeclarator) o;
		if (id == null ? state.id != null : !id.equals(state.id))
			return false;
		if (!init.equals(state.init))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (id != null) result = 37 * result + id.hashCode();
		result = 37 * result + init.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SVariableDeclarator, SVariableDeclaratorId, VariableDeclaratorId> ID = new STypeSafeTraversal<SVariableDeclarator, SVariableDeclaratorId, VariableDeclaratorId>() {

		@Override
		public STree<?> doTraverse(SVariableDeclarator state) {
			return state.id;
		}

		@Override
		public SVariableDeclarator doRebuildParentState(SVariableDeclarator state, STree<SVariableDeclaratorId> child) {
			return state.withId(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return INIT;
		}
	};

	public static STypeSafeTraversal<SVariableDeclarator, SNodeOptionState, NodeOption<Expr>> INIT = new STypeSafeTraversal<SVariableDeclarator, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(SVariableDeclarator state) {
			return state.init;
		}

		@Override
		public SVariableDeclarator doRebuildParentState(SVariableDeclarator state, STree<SNodeOptionState> child) {
			return state.withInit(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return ID;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape initializerShape = composite(
			token(LToken.Assign).withSpacing(space(), space()),
			element()
	);

	public static final LexicalShape shape = composite(
			child(ID),
			child(INIT, when(some(), initializerShape))
	);

	public static final LexicalShape listShape = list(none(), token(LToken.Comma).withSpacingAfter(space()), none());
}
