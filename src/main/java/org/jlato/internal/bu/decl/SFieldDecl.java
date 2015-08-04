package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDFieldDecl;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SFieldDecl extends SNode<SFieldDecl> implements SMemberDecl {

	public static BUTree<SFieldDecl> make(BUTree<SNodeList> modifiers, BUTree<? extends SType> type, BUTree<SNodeList> variables) {
		return new BUTree<SFieldDecl>(new SFieldDecl(modifiers, type, variables));
	}

	public final BUTree<SNodeList> modifiers;

	public final BUTree<? extends SType> type;

	public final BUTree<SNodeList> variables;

	public SFieldDecl(BUTree<SNodeList> modifiers, BUTree<? extends SType> type, BUTree<SNodeList> variables) {
		this.modifiers = modifiers;
		this.type = type;
		this.variables = variables;
	}

	@Override
	public Kind kind() {
		return Kind.FieldDecl;
	}

	public BUTree<SNodeList> modifiers() {
		return modifiers;
	}

	public SFieldDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SFieldDecl(modifiers, type, variables);
	}

	public BUTree<? extends SType> type() {
		return type;
	}

	public SFieldDecl withType(BUTree<? extends SType> type) {
		return new SFieldDecl(modifiers, type, variables);
	}

	public BUTree<SNodeList> variables() {
		return variables;
	}

	public SFieldDecl withVariables(BUTree<SNodeList> variables) {
		return new SFieldDecl(modifiers, type, variables);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SFieldDecl> location) {
		return new TDFieldDecl(location);
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
		SFieldDecl state = (SFieldDecl) o;
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

	public static STypeSafeTraversal<SFieldDecl, SNodeList, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SFieldDecl, SNodeList, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SFieldDecl state) {
			return state.modifiers;
		}

		@Override
		public SFieldDecl doRebuildParentState(SFieldDecl state, BUTree<SNodeList> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return TYPE;
		}
	};

	public static STypeSafeTraversal<SFieldDecl, SType, Type> TYPE = new STypeSafeTraversal<SFieldDecl, SType, Type>() {

		@Override
		public BUTree<?> doTraverse(SFieldDecl state) {
			return state.type;
		}

		@Override
		public SFieldDecl doRebuildParentState(SFieldDecl state, BUTree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return VARIABLES;
		}
	};

	public static STypeSafeTraversal<SFieldDecl, SNodeList, NodeList<VariableDeclarator>> VARIABLES = new STypeSafeTraversal<SFieldDecl, SNodeList, NodeList<VariableDeclarator>>() {

		@Override
		public BUTree<?> doTraverse(SFieldDecl state) {
			return state.variables;
		}

		@Override
		public SFieldDecl doRebuildParentState(SFieldDecl state, BUTree<SNodeList> child) {
			return state.withVariables(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(MODIFIERS, SExtendedModifier.multiLineShape),
			child(TYPE),
			child(VARIABLES, SVariableDeclarator.listShape).withSpacingBefore(space()),
			token(LToken.SemiColon)
	);
}
