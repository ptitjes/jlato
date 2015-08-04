package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
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

public class SAnnotationMemberDecl extends SNode<SAnnotationMemberDecl> implements SMemberDecl {

	public static BUTree<SAnnotationMemberDecl> make(BUTree<SNodeList> modifiers, BUTree<? extends SType> type, BUTree<SName> name, BUTree<SNodeList> dims, BUTree<SNodeOption> defaultValue) {
		return new BUTree<SAnnotationMemberDecl>(new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue));
	}

	public final BUTree<SNodeList> modifiers;

	public final BUTree<? extends SType> type;

	public final BUTree<SName> name;

	public final BUTree<SNodeList> dims;

	public final BUTree<SNodeOption> defaultValue;

	public SAnnotationMemberDecl(BUTree<SNodeList> modifiers, BUTree<? extends SType> type, BUTree<SName> name, BUTree<SNodeList> dims, BUTree<SNodeOption> defaultValue) {
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

	public BUTree<SNodeList> modifiers() {
		return modifiers;
	}

	public SAnnotationMemberDecl withModifiers(BUTree<SNodeList> modifiers) {
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

	public BUTree<SNodeList> dims() {
		return dims;
	}

	public SAnnotationMemberDecl withDims(BUTree<SNodeList> dims) {
		return new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue);
	}

	public BUTree<SNodeOption> defaultValue() {
		return defaultValue;
	}

	public SAnnotationMemberDecl withDefaultValue(BUTree<SNodeOption> defaultValue) {
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

	public static STypeSafeTraversal<SAnnotationMemberDecl, SNodeList, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SAnnotationMemberDecl, SNodeList, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SAnnotationMemberDecl state) {
			return state.modifiers;
		}

		@Override
		public SAnnotationMemberDecl doRebuildParentState(SAnnotationMemberDecl state, BUTree<SNodeList> child) {
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
		public STraversal leftSibling(STree state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
		public STraversal leftSibling(STree state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return DIMS;
		}
	};

	public static STypeSafeTraversal<SAnnotationMemberDecl, SNodeList, NodeList<ArrayDim>> DIMS = new STypeSafeTraversal<SAnnotationMemberDecl, SNodeList, NodeList<ArrayDim>>() {

		@Override
		public BUTree<?> doTraverse(SAnnotationMemberDecl state) {
			return state.dims;
		}

		@Override
		public SAnnotationMemberDecl doRebuildParentState(SAnnotationMemberDecl state, BUTree<SNodeList> child) {
			return state.withDims(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return DEFAULT_VALUE;
		}
	};

	public static STypeSafeTraversal<SAnnotationMemberDecl, SNodeOption, NodeOption<Expr>> DEFAULT_VALUE = new STypeSafeTraversal<SAnnotationMemberDecl, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SAnnotationMemberDecl state) {
			return state.defaultValue;
		}

		@Override
		public SAnnotationMemberDecl doRebuildParentState(SAnnotationMemberDecl state, BUTree<SNodeOption> child) {
			return state.withDefaultValue(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return DIMS;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
