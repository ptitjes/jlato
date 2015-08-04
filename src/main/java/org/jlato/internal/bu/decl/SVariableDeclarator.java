package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDVariableDeclarator;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SVariableDeclarator extends SNode<SVariableDeclarator> implements STree {

	public static BUTree<SVariableDeclarator> make(BUTree<SVariableDeclaratorId> id, BUTree<SNodeOption> init) {
		return new BUTree<SVariableDeclarator>(new SVariableDeclarator(id, init));
	}

	public final BUTree<SVariableDeclaratorId> id;

	public final BUTree<SNodeOption> init;

	public SVariableDeclarator(BUTree<SVariableDeclaratorId> id, BUTree<SNodeOption> init) {
		this.id = id;
		this.init = init;
	}

	@Override
	public Kind kind() {
		return Kind.VariableDeclarator;
	}

	public BUTree<SVariableDeclaratorId> id() {
		return id;
	}

	public SVariableDeclarator withId(BUTree<SVariableDeclaratorId> id) {
		return new SVariableDeclarator(id, init);
	}

	public BUTree<SNodeOption> init() {
		return init;
	}

	public SVariableDeclarator withInit(BUTree<SNodeOption> init) {
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
		public BUTree<?> doTraverse(SVariableDeclarator state) {
			return state.id;
		}

		@Override
		public SVariableDeclarator doRebuildParentState(SVariableDeclarator state, BUTree<SVariableDeclaratorId> child) {
			return state.withId(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return INIT;
		}
	};

	public static STypeSafeTraversal<SVariableDeclarator, SNodeOption, NodeOption<Expr>> INIT = new STypeSafeTraversal<SVariableDeclarator, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SVariableDeclarator state) {
			return state.init;
		}

		@Override
		public SVariableDeclarator doRebuildParentState(SVariableDeclarator state, BUTree<SNodeOption> child) {
			return state.withInit(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return ID;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
