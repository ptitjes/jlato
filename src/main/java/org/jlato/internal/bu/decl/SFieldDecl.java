package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.decl.TDFieldDecl;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.VariableDeclarator;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SFieldDecl extends SNodeState<SFieldDecl> implements SMemberDecl {

	public static STree<SFieldDecl> make(STree<SNodeListState> modifiers, STree<? extends SType> type, STree<SNodeListState> variables) {
		return new STree<SFieldDecl>(new SFieldDecl(modifiers, type, variables));
	}

	public final STree<SNodeListState> modifiers;

	public final STree<? extends SType> type;

	public final STree<SNodeListState> variables;

	public SFieldDecl(STree<SNodeListState> modifiers, STree<? extends SType> type, STree<SNodeListState> variables) {
		this.modifiers = modifiers;
		this.type = type;
		this.variables = variables;
	}

	@Override
	public Kind kind() {
		return Kind.FieldDecl;
	}

	public STree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SFieldDecl withModifiers(STree<SNodeListState> modifiers) {
		return new SFieldDecl(modifiers, type, variables);
	}

	public STree<? extends SType> type() {
		return type;
	}

	public SFieldDecl withType(STree<? extends SType> type) {
		return new SFieldDecl(modifiers, type, variables);
	}

	public STree<SNodeListState> variables() {
		return variables;
	}

	public SFieldDecl withVariables(STree<SNodeListState> variables) {
		return new SFieldDecl(modifiers, type, variables);
	}

	@Override
	protected Tree doInstantiate(SLocation<SFieldDecl> location) {
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

	public static STypeSafeTraversal<SFieldDecl, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SFieldDecl, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public STree<?> doTraverse(SFieldDecl state) {
			return state.modifiers;
		}

		@Override
		public SFieldDecl doRebuildParentState(SFieldDecl state, STree<SNodeListState> child) {
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

	public static STypeSafeTraversal<SFieldDecl, SType, Type> TYPE = new STypeSafeTraversal<SFieldDecl, SType, Type>() {

		@Override
		public STree<?> doTraverse(SFieldDecl state) {
			return state.type;
		}

		@Override
		public SFieldDecl doRebuildParentState(SFieldDecl state, STree<SType> child) {
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

	public static STypeSafeTraversal<SFieldDecl, SNodeListState, NodeList<VariableDeclarator>> VARIABLES = new STypeSafeTraversal<SFieldDecl, SNodeListState, NodeList<VariableDeclarator>>() {

		@Override
		public STree<?> doTraverse(SFieldDecl state) {
			return state.variables;
		}

		@Override
		public SFieldDecl doRebuildParentState(SFieldDecl state, STree<SNodeListState> child) {
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
			child(MODIFIERS, SExtendedModifier.multiLineShape),
			child(TYPE),
			child(VARIABLES, SVariableDeclarator.listShape).withSpacingBefore(space()),
			token(LToken.SemiColon)
	);
}
