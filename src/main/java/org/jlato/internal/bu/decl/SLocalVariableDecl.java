package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDLocalVariableDecl;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;
import static org.jlato.printer.SpacingConstraint.space;

public class SLocalVariableDecl extends SNodeState<SLocalVariableDecl> implements SDecl {

	public static STree<SLocalVariableDecl> make(STree<SNodeListState> modifiers, STree<? extends SType> type, STree<SNodeListState> variables) {
		return new STree<SLocalVariableDecl>(new SLocalVariableDecl(modifiers, type, variables));
	}

	public final STree<SNodeListState> modifiers;

	public final STree<? extends SType> type;

	public final STree<SNodeListState> variables;

	public SLocalVariableDecl(STree<SNodeListState> modifiers, STree<? extends SType> type, STree<SNodeListState> variables) {
		this.modifiers = modifiers;
		this.type = type;
		this.variables = variables;
	}

	@Override
	public Kind kind() {
		return Kind.LocalVariableDecl;
	}

	public STree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SLocalVariableDecl withModifiers(STree<SNodeListState> modifiers) {
		return new SLocalVariableDecl(modifiers, type, variables);
	}

	public STree<? extends SType> type() {
		return type;
	}

	public SLocalVariableDecl withType(STree<? extends SType> type) {
		return new SLocalVariableDecl(modifiers, type, variables);
	}

	public STree<SNodeListState> variables() {
		return variables;
	}

	public SLocalVariableDecl withVariables(STree<SNodeListState> variables) {
		return new SLocalVariableDecl(modifiers, type, variables);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SLocalVariableDecl> location) {
		return new TDLocalVariableDecl(location);
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
		return VARIABLES;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SLocalVariableDecl state = (SLocalVariableDecl) o;
		if (!modifiers.equals(state.modifiers))
			return false;
		if (type == null ? state.type != null : !type.equals(state.type))
			return false;
		if (!variables.equals(state.variables))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + modifiers.hashCode();
		if (type != null) result = 37 * result + type.hashCode();
		result = 37 * result + variables.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SLocalVariableDecl, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SLocalVariableDecl, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public STree<?> doTraverse(SLocalVariableDecl state) {
			return state.modifiers;
		}

		@Override
		public SLocalVariableDecl doRebuildParentState(SLocalVariableDecl state, STree<SNodeListState> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE;
		}
	};

	public static STypeSafeTraversal<SLocalVariableDecl, SType, Type> TYPE = new STypeSafeTraversal<SLocalVariableDecl, SType, Type>() {

		@Override
		public STree<?> doTraverse(SLocalVariableDecl state) {
			return state.type;
		}

		@Override
		public SLocalVariableDecl doRebuildParentState(SLocalVariableDecl state, STree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return VARIABLES;
		}
	};

	public static STypeSafeTraversal<SLocalVariableDecl, SNodeListState, NodeList<VariableDeclarator>> VARIABLES = new STypeSafeTraversal<SLocalVariableDecl, SNodeListState, NodeList<VariableDeclarator>>() {

		@Override
		public STree<?> doTraverse(SLocalVariableDecl state) {
			return state.variables;
		}

		@Override
		public SLocalVariableDecl doRebuildParentState(SLocalVariableDecl state, STree<SNodeListState> child) {
			return state.withVariables(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(MODIFIERS, SExtendedModifier.singleLineShape),
			child(TYPE),
			child(VARIABLES, SVariableDeclarator.listShape).withSpacingBefore(space())
	);
}
