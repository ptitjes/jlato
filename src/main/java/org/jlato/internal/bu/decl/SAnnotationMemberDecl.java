package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDAnnotationMemberDecl;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SAnnotationMemberDecl extends SNodeState<SAnnotationMemberDecl> implements SMemberDecl {

	public static STree<SAnnotationMemberDecl> make(STree<SNodeListState> modifiers, STree<? extends SType> type, STree<SName> name, STree<SNodeListState> dims, STree<SNodeOptionState> defaultValue) {
		return new STree<SAnnotationMemberDecl>(new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue));
	}

	public final STree<SNodeListState> modifiers;

	public final STree<? extends SType> type;

	public final STree<SName> name;

	public final STree<SNodeListState> dims;

	public final STree<SNodeOptionState> defaultValue;

	public SAnnotationMemberDecl(STree<SNodeListState> modifiers, STree<? extends SType> type, STree<SName> name, STree<SNodeListState> dims, STree<SNodeOptionState> defaultValue) {
		this.modifiers = modifiers;
		this.type = type;
		this.name = name;
		this.dims = dims;
		this.defaultValue = defaultValue;
	}

	@Override
	public Kind kind() {
		return Kind.AnnotationMemberDecl;
	}

	public STree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SAnnotationMemberDecl withModifiers(STree<SNodeListState> modifiers) {
		return new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue);
	}

	public STree<? extends SType> type() {
		return type;
	}

	public SAnnotationMemberDecl withType(STree<? extends SType> type) {
		return new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue);
	}

	public STree<SName> name() {
		return name;
	}

	public SAnnotationMemberDecl withName(STree<SName> name) {
		return new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue);
	}

	public STree<SNodeListState> dims() {
		return dims;
	}

	public SAnnotationMemberDecl withDims(STree<SNodeListState> dims) {
		return new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue);
	}

	public STree<SNodeOptionState> defaultValue() {
		return defaultValue;
	}

	public SAnnotationMemberDecl withDefaultValue(STree<SNodeOptionState> defaultValue) {
		return new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SAnnotationMemberDecl> location) {
		return new TDAnnotationMemberDecl(location);
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
		return DEFAULT_VALUE;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SAnnotationMemberDecl state = (SAnnotationMemberDecl) o;
		if (!modifiers.equals(state.modifiers))
			return false;
		if (type == null ? state.type != null : !type.equals(state.type))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!dims.equals(state.dims))
			return false;
		if (!defaultValue.equals(state.defaultValue))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + modifiers.hashCode();
		if (type != null) result = 37 * result + type.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + dims.hashCode();
		result = 37 * result + defaultValue.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SAnnotationMemberDecl, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SAnnotationMemberDecl, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public STree<?> doTraverse(SAnnotationMemberDecl state) {
			return state.modifiers;
		}

		@Override
		public SAnnotationMemberDecl doRebuildParentState(SAnnotationMemberDecl state, STree<SNodeListState> child) {
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

	public static STypeSafeTraversal<SAnnotationMemberDecl, SType, Type> TYPE = new STypeSafeTraversal<SAnnotationMemberDecl, SType, Type>() {

		@Override
		public STree<?> doTraverse(SAnnotationMemberDecl state) {
			return state.type;
		}

		@Override
		public SAnnotationMemberDecl doRebuildParentState(SAnnotationMemberDecl state, STree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SAnnotationMemberDecl, SName, Name> NAME = new STypeSafeTraversal<SAnnotationMemberDecl, SName, Name>() {

		@Override
		public STree<?> doTraverse(SAnnotationMemberDecl state) {
			return state.name;
		}

		@Override
		public SAnnotationMemberDecl doRebuildParentState(SAnnotationMemberDecl state, STree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return DIMS;
		}
	};

	public static STypeSafeTraversal<SAnnotationMemberDecl, SNodeListState, NodeList<ArrayDim>> DIMS = new STypeSafeTraversal<SAnnotationMemberDecl, SNodeListState, NodeList<ArrayDim>>() {

		@Override
		public STree<?> doTraverse(SAnnotationMemberDecl state) {
			return state.dims;
		}

		@Override
		public SAnnotationMemberDecl doRebuildParentState(SAnnotationMemberDecl state, STree<SNodeListState> child) {
			return state.withDims(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return DEFAULT_VALUE;
		}
	};

	public static STypeSafeTraversal<SAnnotationMemberDecl, SNodeOptionState, NodeOption<Expr>> DEFAULT_VALUE = new STypeSafeTraversal<SAnnotationMemberDecl, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(SAnnotationMemberDecl state) {
			return state.defaultValue;
		}

		@Override
		public SAnnotationMemberDecl doRebuildParentState(SAnnotationMemberDecl state, STree<SNodeOptionState> child) {
			return state.withDefaultValue(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return DIMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape defaultValShape = composite(token(LToken.Default).withSpacingBefore(space()), element());

	public static final LexicalShape shape = composite(
			child(MODIFIERS, SExtendedModifier.multiLineShape),
			child(TYPE), child(NAME),
			token(LToken.ParenthesisLeft), token(LToken.ParenthesisRight),
			child(DEFAULT_VALUE, when(some(), defaultValShape)),
			token(LToken.SemiColon)
	);
}
