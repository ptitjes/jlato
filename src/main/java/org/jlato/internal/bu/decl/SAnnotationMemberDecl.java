package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDAnnotationMemberDecl;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SAnnotationMemberDecl extends SNodeState<SAnnotationMemberDecl> implements SMemberDecl {

	public static BUTree<SAnnotationMemberDecl> make(BUTree<SNodeListState> modifiers, BUTree<? extends SType> type, BUTree<SName> name, BUTree<SNodeListState> dims, BUTree<SNodeOptionState> defaultValue) {
		return new BUTree<SAnnotationMemberDecl>(new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue));
	}

	public final BUTree<SNodeListState> modifiers;

	public final BUTree<? extends SType> type;

	public final BUTree<SName> name;

	public final BUTree<SNodeListState> dims;

	public final BUTree<SNodeOptionState> defaultValue;

	public SAnnotationMemberDecl(BUTree<SNodeListState> modifiers, BUTree<? extends SType> type, BUTree<SName> name, BUTree<SNodeListState> dims, BUTree<SNodeOptionState> defaultValue) {
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

	public BUTree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SAnnotationMemberDecl withModifiers(BUTree<SNodeListState> modifiers) {
		return new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue);
	}

	public BUTree<? extends SType> type() {
		return type;
	}

	public SAnnotationMemberDecl withType(BUTree<? extends SType> type) {
		return new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue);
	}

	public BUTree<SName> name() {
		return name;
	}

	public SAnnotationMemberDecl withName(BUTree<SName> name) {
		return new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue);
	}

	public BUTree<SNodeListState> dims() {
		return dims;
	}

	public SAnnotationMemberDecl withDims(BUTree<SNodeListState> dims) {
		return new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue);
	}

	public BUTree<SNodeOptionState> defaultValue() {
		return defaultValue;
	}

	public SAnnotationMemberDecl withDefaultValue(BUTree<SNodeOptionState> defaultValue) {
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
		public BUTree<?> doTraverse(SAnnotationMemberDecl state) {
			return state.modifiers;
		}

		@Override
		public SAnnotationMemberDecl doRebuildParentState(SAnnotationMemberDecl state, BUTree<SNodeListState> child) {
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
		public BUTree<?> doTraverse(SAnnotationMemberDecl state) {
			return state.type;
		}

		@Override
		public SAnnotationMemberDecl doRebuildParentState(SAnnotationMemberDecl state, BUTree<SType> child) {
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
		public BUTree<?> doTraverse(SAnnotationMemberDecl state) {
			return state.name;
		}

		@Override
		public SAnnotationMemberDecl doRebuildParentState(SAnnotationMemberDecl state, BUTree<SName> child) {
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
		public BUTree<?> doTraverse(SAnnotationMemberDecl state) {
			return state.dims;
		}

		@Override
		public SAnnotationMemberDecl doRebuildParentState(SAnnotationMemberDecl state, BUTree<SNodeListState> child) {
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
		public BUTree<?> doTraverse(SAnnotationMemberDecl state) {
			return state.defaultValue;
		}

		@Override
		public SAnnotationMemberDecl doRebuildParentState(SAnnotationMemberDecl state, BUTree<SNodeOptionState> child) {
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
