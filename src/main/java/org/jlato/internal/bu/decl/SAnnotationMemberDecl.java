package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDAnnotationMemberDecl;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an annotation type member declaration.
 */
public class SAnnotationMemberDecl extends SNode<SAnnotationMemberDecl> implements SMemberDecl {

	/**
	 * Creates a <code>BUTree</code> with a new annotation type member declaration.
	 *
	 * @param modifiers    the modifiers child <code>BUTree</code>.
	 * @param type         the type child <code>BUTree</code>.
	 * @param name         the name child <code>BUTree</code>.
	 * @param dims         the dimensions child <code>BUTree</code>.
	 * @param defaultValue the default value child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an annotation type member declaration.
	 */
	public static BUTree<SAnnotationMemberDecl> make(BUTree<SNodeList> modifiers, BUTree<? extends SType> type, BUTree<SName> name, BUTree<SNodeList> dims, BUTree<SNodeOption> defaultValue) {
		return new BUTree<SAnnotationMemberDecl>(new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue));
	}

	/**
	 * The modifiers of this annotation type member declaration state.
	 */
	public final BUTree<SNodeList> modifiers;

	/**
	 * The type of this annotation type member declaration state.
	 */
	public final BUTree<? extends SType> type;

	/**
	 * The name of this annotation type member declaration state.
	 */
	public final BUTree<SName> name;

	/**
	 * The dimensions of this annotation type member declaration state.
	 */
	public final BUTree<SNodeList> dims;

	/**
	 * The default value of this annotation type member declaration state.
	 */
	public final BUTree<SNodeOption> defaultValue;

	/**
	 * Constructs an annotation type member declaration state.
	 *
	 * @param modifiers    the modifiers child <code>BUTree</code>.
	 * @param type         the type child <code>BUTree</code>.
	 * @param name         the name child <code>BUTree</code>.
	 * @param dims         the dimensions child <code>BUTree</code>.
	 * @param defaultValue the default value child <code>BUTree</code>.
	 */
	public SAnnotationMemberDecl(BUTree<SNodeList> modifiers, BUTree<? extends SType> type, BUTree<SName> name, BUTree<SNodeList> dims, BUTree<SNodeOption> defaultValue) {
		this.modifiers = modifiers;
		this.type = type;
		this.name = name;
		this.dims = dims;
		this.defaultValue = defaultValue;
	}

	/**
	 * Returns the kind of this annotation type member declaration.
	 *
	 * @return the kind of this annotation type member declaration.
	 */
	@Override
	public Kind kind() {
		return Kind.AnnotationMemberDecl;
	}

	/**
	 * Replaces the modifiers of this annotation type member declaration state.
	 *
	 * @param modifiers the replacement for the modifiers of this annotation type member declaration state.
	 * @return the resulting mutated annotation type member declaration state.
	 */
	public SAnnotationMemberDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue);
	}

	/**
	 * Replaces the type of this annotation type member declaration state.
	 *
	 * @param type the replacement for the type of this annotation type member declaration state.
	 * @return the resulting mutated annotation type member declaration state.
	 */
	public SAnnotationMemberDecl withType(BUTree<? extends SType> type) {
		return new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue);
	}

	/**
	 * Replaces the name of this annotation type member declaration state.
	 *
	 * @param name the replacement for the name of this annotation type member declaration state.
	 * @return the resulting mutated annotation type member declaration state.
	 */
	public SAnnotationMemberDecl withName(BUTree<SName> name) {
		return new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue);
	}

	/**
	 * Replaces the dimensions of this annotation type member declaration state.
	 *
	 * @param dims the replacement for the dimensions of this annotation type member declaration state.
	 * @return the resulting mutated annotation type member declaration state.
	 */
	public SAnnotationMemberDecl withDims(BUTree<SNodeList> dims) {
		return new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue);
	}

	/**
	 * Replaces the default value of this annotation type member declaration state.
	 *
	 * @param defaultValue the replacement for the default value of this annotation type member declaration state.
	 * @return the resulting mutated annotation type member declaration state.
	 */
	public SAnnotationMemberDecl withDefaultValue(BUTree<SNodeOption> defaultValue) {
		return new SAnnotationMemberDecl(modifiers, type, name, dims, defaultValue);
	}

	/**
	 * Builds an annotation type member declaration facade for the specified annotation type member declaration <code>TDLocation</code>.
	 *
	 * @param location the annotation type member declaration <code>TDLocation</code>.
	 * @return an annotation type member declaration facade for the specified annotation type member declaration <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SAnnotationMemberDecl> location) {
		return new TDAnnotationMemberDecl(location);
	}

	/**
	 * Returns the shape for this annotation type member declaration state.
	 *
	 * @return the shape for this annotation type member declaration state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this annotation type member declaration state.
	 *
	 * @return the first child traversal for this annotation type member declaration state.
	 */
	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	/**
	 * Returns the last child traversal for this annotation type member declaration state.
	 *
	 * @return the last child traversal for this annotation type member declaration state.
	 */
	@Override
	public STraversal lastChild() {
		return DEFAULT_VALUE;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
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

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
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

	public static final LexicalShape defaultValShape = composite(token(LToken.Default).withSpacing(space(), space()), element());

	public static final LexicalShape shape = composite(
			child(MODIFIERS, SExtendedModifier.multiLineShape),
			child(TYPE),
			child(NAME).withSpacingBefore(space()),
			token(LToken.ParenthesisLeft), token(LToken.ParenthesisRight),
			child(DEFAULT_VALUE, when(some(), defaultValShape)),
			token(LToken.SemiColon)
	);
}
