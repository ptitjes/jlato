package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDInitializerDecl;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.stmt.*;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;

public class SInitializerDecl extends SNodeState<SInitializerDecl> implements SMemberDecl {

	public static BUTree<SInitializerDecl> make(BUTree<SNodeListState> modifiers, BUTree<SBlockStmt> body) {
		return new BUTree<SInitializerDecl>(new SInitializerDecl(modifiers, body));
	}

	public final BUTree<SNodeListState> modifiers;

	public final BUTree<SBlockStmt> body;

	public SInitializerDecl(BUTree<SNodeListState> modifiers, BUTree<SBlockStmt> body) {
		this.modifiers = modifiers;
		this.body = body;
	}

	@Override
	public Kind kind() {
		return Kind.InitializerDecl;
	}

	public BUTree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SInitializerDecl withModifiers(BUTree<SNodeListState> modifiers) {
		return new SInitializerDecl(modifiers, body);
	}

	public BUTree<SBlockStmt> body() {
		return body;
	}

	public SInitializerDecl withBody(BUTree<SBlockStmt> body) {
		return new SInitializerDecl(modifiers, body);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SInitializerDecl> location) {
		return new TDInitializerDecl(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	@Override
	public STraversal lastChild() {
		return BODY;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SInitializerDecl state = (SInitializerDecl) o;
		if (!modifiers.equals(state.modifiers))
			return false;
		if (body == null ? state.body != null : !body.equals(state.body))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + modifiers.hashCode();
		if (body != null) result = 37 * result + body.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SInitializerDecl, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SInitializerDecl, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SInitializerDecl state) {
			return state.modifiers;
		}

		@Override
		public SInitializerDecl doRebuildParentState(SInitializerDecl state, BUTree<SNodeListState> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SInitializerDecl, SBlockStmt, BlockStmt> BODY = new STypeSafeTraversal<SInitializerDecl, SBlockStmt, BlockStmt>() {

		@Override
		public BUTree<?> doTraverse(SInitializerDecl state) {
			return state.body;
		}

		@Override
		public SInitializerDecl doRebuildParentState(SInitializerDecl state, BUTree<SBlockStmt> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(MODIFIERS, SExtendedModifier.multiLineShape),
			child(BODY)
	);
}
