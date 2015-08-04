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

	public static STree<SInitializerDecl> make(STree<SNodeListState> modifiers, STree<SBlockStmt> body) {
		return new STree<SInitializerDecl>(new SInitializerDecl(modifiers, body));
	}

	public final STree<SNodeListState> modifiers;

	public final STree<SBlockStmt> body;

	public SInitializerDecl(STree<SNodeListState> modifiers, STree<SBlockStmt> body) {
		this.modifiers = modifiers;
		this.body = body;
	}

	@Override
	public Kind kind() {
		return Kind.InitializerDecl;
	}

	public STree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SInitializerDecl withModifiers(STree<SNodeListState> modifiers) {
		return new SInitializerDecl(modifiers, body);
	}

	public STree<SBlockStmt> body() {
		return body;
	}

	public SInitializerDecl withBody(STree<SBlockStmt> body) {
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
		public STree<?> doTraverse(SInitializerDecl state) {
			return state.modifiers;
		}

		@Override
		public SInitializerDecl doRebuildParentState(SInitializerDecl state, STree<SNodeListState> child) {
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
		public STree<?> doTraverse(SInitializerDecl state) {
			return state.body;
		}

		@Override
		public SInitializerDecl doRebuildParentState(SInitializerDecl state, STree<SBlockStmt> child) {
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
