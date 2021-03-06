/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDFormalParameter;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.VariableDeclaratorId;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import java.util.Arrays;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

/**
 * A state object for a formal parameter.
 */
public class SFormalParameter extends SNode<SFormalParameter> implements STree {

	/**
	 * Creates a <code>BUTree</code> with a new formal parameter.
	 *
	 * @param modifiers           the modifiers child <code>BUTree</code>.
	 * @param type                the type child <code>BUTree</code>.
	 * @param isVarArgs           the is a variadic parameter child <code>BUTree</code>.
	 * @param ellipsisAnnotations the ellipsis annotations child <code>BUTree</code>.
	 * @param id                  the identifier child <code>BUTree</code>.
	 * @param isReceiver          the is receiver child <code>BUTree</code>.
	 * @param receiverTypeName    the receiver type name child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a formal parameter.
	 */
	public static BUTree<SFormalParameter> make(BUTree<SNodeList> modifiers, BUTree<? extends SType> type, boolean isVarArgs, BUTree<SNodeList> ellipsisAnnotations, BUTree<SNodeOption> id, boolean isReceiver, BUTree<SNodeOption> receiverTypeName) {
		return new BUTree<SFormalParameter>(new SFormalParameter(modifiers, type, isVarArgs, ellipsisAnnotations, id, isReceiver, receiverTypeName));
	}

	/**
	 * The modifiers of this formal parameter state.
	 */
	public final BUTree<SNodeList> modifiers;

	/**
	 * The type of this formal parameter state.
	 */
	public final BUTree<? extends SType> type;

	/**
	 * The is a variadic parameter of this formal parameter state.
	 */
	public final boolean isVarArgs;

	/**
	 * The ellipsis annotations of this formal parameter state.
	 */
	public final BUTree<SNodeList> ellipsisAnnotations;

	/**
	 * The identifier of this formal parameter state.
	 */
	public final BUTree<SNodeOption> id;

	/**
	 * The is receiver of this formal parameter state.
	 */
	public final boolean isReceiver;

	/**
	 * The receiver type name of this formal parameter state.
	 */
	public final BUTree<SNodeOption> receiverTypeName;

	/**
	 * Constructs a formal parameter state.
	 *
	 * @param modifiers           the modifiers child <code>BUTree</code>.
	 * @param type                the type child <code>BUTree</code>.
	 * @param isVarArgs           the is a variadic parameter child <code>BUTree</code>.
	 * @param ellipsisAnnotations the ellipsis annotations child <code>BUTree</code>.
	 * @param id                  the identifier child <code>BUTree</code>.
	 * @param isReceiver          the is receiver child <code>BUTree</code>.
	 * @param receiverTypeName    the receiver type name child <code>BUTree</code>.
	 */
	public SFormalParameter(BUTree<SNodeList> modifiers, BUTree<? extends SType> type, boolean isVarArgs, BUTree<SNodeList> ellipsisAnnotations, BUTree<SNodeOption> id, boolean isReceiver, BUTree<SNodeOption> receiverTypeName) {
		this.modifiers = modifiers;
		this.type = type;
		this.isVarArgs = isVarArgs;
		this.ellipsisAnnotations = ellipsisAnnotations;
		this.id = id;
		this.isReceiver = isReceiver;
		this.receiverTypeName = receiverTypeName;
	}

	/**
	 * Returns the kind of this formal parameter.
	 *
	 * @return the kind of this formal parameter.
	 */
	@Override
	public Kind kind() {
		return Kind.FormalParameter;
	}

	/**
	 * Replaces the modifiers of this formal parameter state.
	 *
	 * @param modifiers the replacement for the modifiers of this formal parameter state.
	 * @return the resulting mutated formal parameter state.
	 */
	public SFormalParameter withModifiers(BUTree<SNodeList> modifiers) {
		return new SFormalParameter(modifiers, type, isVarArgs, ellipsisAnnotations, id, isReceiver, receiverTypeName);
	}

	/**
	 * Replaces the type of this formal parameter state.
	 *
	 * @param type the replacement for the type of this formal parameter state.
	 * @return the resulting mutated formal parameter state.
	 */
	public SFormalParameter withType(BUTree<? extends SType> type) {
		return new SFormalParameter(modifiers, type, isVarArgs, ellipsisAnnotations, id, isReceiver, receiverTypeName);
	}

	/**
	 * Replaces the is a variadic parameter of this formal parameter state.
	 *
	 * @param isVarArgs the replacement for the is a variadic parameter of this formal parameter state.
	 * @return the resulting mutated formal parameter state.
	 */
	public SFormalParameter setVarArgs(boolean isVarArgs) {
		return new SFormalParameter(modifiers, type, isVarArgs, ellipsisAnnotations, id, isReceiver, receiverTypeName);
	}

	/**
	 * Replaces the ellipsis annotations of this formal parameter state.
	 *
	 * @param ellipsisAnnotations the replacement for the ellipsis annotations of this formal parameter state.
	 * @return the resulting mutated formal parameter state.
	 */
	public SFormalParameter withEllipsisAnnotations(BUTree<SNodeList> ellipsisAnnotations) {
		return new SFormalParameter(modifiers, type, isVarArgs, ellipsisAnnotations, id, isReceiver, receiverTypeName);
	}

	/**
	 * Replaces the identifier of this formal parameter state.
	 *
	 * @param id the replacement for the identifier of this formal parameter state.
	 * @return the resulting mutated formal parameter state.
	 */
	public SFormalParameter withId(BUTree<SNodeOption> id) {
		return new SFormalParameter(modifiers, type, isVarArgs, ellipsisAnnotations, id, isReceiver, receiverTypeName);
	}

	/**
	 * Replaces the is receiver of this formal parameter state.
	 *
	 * @param isReceiver the replacement for the is receiver of this formal parameter state.
	 * @return the resulting mutated formal parameter state.
	 */
	public SFormalParameter setReceiver(boolean isReceiver) {
		return new SFormalParameter(modifiers, type, isVarArgs, ellipsisAnnotations, id, isReceiver, receiverTypeName);
	}

	/**
	 * Replaces the receiver type name of this formal parameter state.
	 *
	 * @param receiverTypeName the replacement for the receiver type name of this formal parameter state.
	 * @return the resulting mutated formal parameter state.
	 */
	public SFormalParameter withReceiverTypeName(BUTree<SNodeOption> receiverTypeName) {
		return new SFormalParameter(modifiers, type, isVarArgs, ellipsisAnnotations, id, isReceiver, receiverTypeName);
	}

	/**
	 * Builds a formal parameter facade for the specified formal parameter <code>TDLocation</code>.
	 *
	 * @param location the formal parameter <code>TDLocation</code>.
	 * @return a formal parameter facade for the specified formal parameter <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SFormalParameter> location) {
		return new TDFormalParameter(location);
	}

	/**
	 * Returns the shape for this formal parameter state.
	 *
	 * @return the shape for this formal parameter state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the properties for this formal parameter state.
	 *
	 * @return the properties for this formal parameter state.
	 */
	@Override
	public Iterable<SProperty> allProperties() {
		return Arrays.<SProperty>asList(VAR_ARGS, RECEIVER);
	}

	/**
	 * Returns the first child traversal for this formal parameter state.
	 *
	 * @return the first child traversal for this formal parameter state.
	 */
	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	/**
	 * Returns the last child traversal for this formal parameter state.
	 *
	 * @return the last child traversal for this formal parameter state.
	 */
	@Override
	public STraversal lastChild() {
		return RECEIVER_TYPE_NAME;
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
		SFormalParameter state = (SFormalParameter) o;
		if (!modifiers.equals(state.modifiers))
			return false;
		if (type == null ? state.type != null : !type.equals(state.type))
			return false;
		if (isVarArgs != state.isVarArgs)
			return false;
		if (!ellipsisAnnotations.equals(state.ellipsisAnnotations))
			return false;
		if (!id.equals(state.id))
			return false;
		if (isReceiver != state.isReceiver)
			return false;
		if (!receiverTypeName.equals(state.receiverTypeName))
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
		result = 37 * result + (isVarArgs ? 1 : 0);
		result = 37 * result + ellipsisAnnotations.hashCode();
		result = 37 * result + id.hashCode();
		result = 37 * result + (isReceiver ? 1 : 0);
		result = 37 * result + receiverTypeName.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SFormalParameter, SNodeList, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SFormalParameter, SNodeList, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SFormalParameter state) {
			return state.modifiers;
		}

		@Override
		public SFormalParameter doRebuildParentState(SFormalParameter state, BUTree<SNodeList> child) {
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

	public static STypeSafeTraversal<SFormalParameter, SType, Type> TYPE = new STypeSafeTraversal<SFormalParameter, SType, Type>() {

		@Override
		public BUTree<?> doTraverse(SFormalParameter state) {
			return state.type;
		}

		@Override
		public SFormalParameter doRebuildParentState(SFormalParameter state, BUTree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return ELLIPSIS_ANNOTATIONS;
		}
	};

	public static STypeSafeTraversal<SFormalParameter, SNodeList, NodeList<AnnotationExpr>> ELLIPSIS_ANNOTATIONS = new STypeSafeTraversal<SFormalParameter, SNodeList, NodeList<AnnotationExpr>>() {

		@Override
		public BUTree<?> doTraverse(SFormalParameter state) {
			return state.ellipsisAnnotations;
		}

		@Override
		public SFormalParameter doRebuildParentState(SFormalParameter state, BUTree<SNodeList> child) {
			return state.withEllipsisAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return ID;
		}
	};

	public static STypeSafeTraversal<SFormalParameter, SNodeOption, NodeOption<VariableDeclaratorId>> ID = new STypeSafeTraversal<SFormalParameter, SNodeOption, NodeOption<VariableDeclaratorId>>() {

		@Override
		public BUTree<?> doTraverse(SFormalParameter state) {
			return state.id;
		}

		@Override
		public SFormalParameter doRebuildParentState(SFormalParameter state, BUTree<SNodeOption> child) {
			return state.withId(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return ELLIPSIS_ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return RECEIVER_TYPE_NAME;
		}
	};

	public static STypeSafeTraversal<SFormalParameter, SNodeOption, NodeOption<Name>> RECEIVER_TYPE_NAME = new STypeSafeTraversal<SFormalParameter, SNodeOption, NodeOption<Name>>() {

		@Override
		public BUTree<?> doTraverse(SFormalParameter state) {
			return state.receiverTypeName;
		}

		@Override
		public SFormalParameter doRebuildParentState(SFormalParameter state, BUTree<SNodeOption> child) {
			return state.withReceiverTypeName(child);
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

	public static STypeSafeProperty<SFormalParameter, Boolean> VAR_ARGS = new STypeSafeProperty<SFormalParameter, Boolean>() {

		@Override
		public Boolean doRetrieve(SFormalParameter state) {
			return state.isVarArgs;
		}

		@Override
		public SFormalParameter doRebuildParentState(SFormalParameter state, Boolean value) {
			return state.setVarArgs(value);
		}
	};

	public static STypeSafeProperty<SFormalParameter, Boolean> RECEIVER = new STypeSafeProperty<SFormalParameter, Boolean>() {

		@Override
		public Boolean doRetrieve(SFormalParameter state) {
			return state.isReceiver;
		}

		@Override
		public SFormalParameter doRebuildParentState(SFormalParameter state, Boolean value) {
			return state.setReceiver(value);
		}
	};

	public static final LexicalShape shape = composite(
			child(MODIFIERS, SExtendedModifier.singleLineShape),
			child(TYPE),
			when(data(VAR_ARGS),
					alternative(childIs(ELLIPSIS_ANNOTATIONS, not(empty())),
							composite(
									child(ELLIPSIS_ANNOTATIONS, org.jlato.internal.bu.expr.SAnnotationExpr.singleLineAnnotationsShapeWithSpaceBefore),
									token(LToken.Ellipsis)
							),
							token(LToken.Ellipsis)
					)
			),
			when(not(childIs(TYPE, withKind(Kind.UnknownType))), none().withSpacingAfter(space())),
			alternative(data(RECEIVER),
					composite(
							child(RECEIVER_TYPE_NAME, when(some(), composite(element(), token(LToken.Dot)))),
							token(LToken.This)
					),
					child(ID, element())
			)
	);

	public static final LexicalShape listShape = list(true,
			none(),
			token(LToken.Comma).withSpacingAfter(space()),
			none()
	);
}
