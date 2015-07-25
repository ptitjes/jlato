/*
 * Copyright (C) 2015 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.tree.type;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.*;
import org.jlato.tree.expr.AnnotationExpr;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class WildcardType extends TreeBase<WildcardType.State, Type, WildcardType> implements Type {

	public Kind kind() {
		return Kind.WildcardType;
	}

	private WildcardType(SLocation<WildcardType.State> location) {
		super(location);
	}

	public static STree<WildcardType.State> make(STree<SNodeListState> annotations, STree<SNodeOptionState> ext, STree<SNodeOptionState> sup) {
		return new STree<WildcardType.State>(new WildcardType.State(annotations, ext, sup));
	}

	public WildcardType(NodeList<AnnotationExpr> annotations, NodeOption<ReferenceType> ext, NodeOption<ReferenceType> sup) {
		super(new SLocation<WildcardType.State>(make(TreeBase.<SNodeListState>treeOf(annotations), TreeBase.<SNodeOptionState>treeOf(ext), TreeBase.<SNodeOptionState>treeOf(sup))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(ANNOTATIONS);
	}

	public WildcardType withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(ANNOTATIONS, annotations);
	}

	public WildcardType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(ANNOTATIONS, mutation);
	}

	public NodeOption<ReferenceType> ext() {
		return location.safeTraversal(EXT);
	}

	public WildcardType withExt(NodeOption<ReferenceType> ext) {
		return location.safeTraversalReplace(EXT, ext);
	}

	public WildcardType withExt(Mutation<NodeOption<ReferenceType>> mutation) {
		return location.safeTraversalMutate(EXT, mutation);
	}

	public NodeOption<ReferenceType> sup() {
		return location.safeTraversal(SUP);
	}

	public WildcardType withSup(NodeOption<ReferenceType> sup) {
		return location.safeTraversalReplace(SUP, sup);
	}

	public WildcardType withSup(Mutation<NodeOption<ReferenceType>> mutation) {
		return location.safeTraversalMutate(SUP, mutation);
	}

	public static class State extends SNodeState<State> implements Type.State {

		public final STree<SNodeListState> annotations;

		public final STree<SNodeOptionState> ext;

		public final STree<SNodeOptionState> sup;

		State(STree<SNodeListState> annotations, STree<SNodeOptionState> ext, STree<SNodeOptionState> sup) {
			this.annotations = annotations;
			this.ext = ext;
			this.sup = sup;
		}

		public WildcardType.State withAnnotations(STree<SNodeListState> annotations) {
			return new WildcardType.State(annotations, ext, sup);
		}

		public WildcardType.State withExt(STree<SNodeOptionState> ext) {
			return new WildcardType.State(annotations, ext, sup);
		}

		public WildcardType.State withSup(STree<SNodeOptionState> sup) {
			return new WildcardType.State(annotations, ext, sup);
		}

		@Override
		public Kind kind() {
			return Kind.WildcardType;
		}

		@Override
		protected Tree doInstantiate(SLocation<WildcardType.State> location) {
			return new WildcardType(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return ANNOTATIONS;
		}

		@Override
		public STraversal lastChild() {
			return SUP;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			WildcardType.State state = (WildcardType.State) o;
			if (!annotations.equals(state.annotations))
				return false;
			if (!ext.equals(state.ext))
				return false;
			if (!sup.equals(state.sup))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + annotations.hashCode();
			result = 37 * result + ext.hashCode();
			result = 37 * result + sup.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<WildcardType.State, SNodeListState, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<WildcardType.State, SNodeListState, NodeList<AnnotationExpr>>() {

		@Override
		protected STree<?> doTraverse(WildcardType.State state) {
			return state.annotations;
		}

		@Override
		protected WildcardType.State doRebuildParentState(WildcardType.State state, STree<SNodeListState> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return EXT;
		}
	};

	private static STypeSafeTraversal<WildcardType.State, SNodeOptionState, NodeOption<ReferenceType>> EXT = new STypeSafeTraversal<WildcardType.State, SNodeOptionState, NodeOption<ReferenceType>>() {

		@Override
		protected STree<?> doTraverse(WildcardType.State state) {
			return state.ext;
		}

		@Override
		protected WildcardType.State doRebuildParentState(WildcardType.State state, STree<SNodeOptionState> child) {
			return state.withExt(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return SUP;
		}
	};

	private static STypeSafeTraversal<WildcardType.State, SNodeOptionState, NodeOption<ReferenceType>> SUP = new STypeSafeTraversal<WildcardType.State, SNodeOptionState, NodeOption<ReferenceType>>() {

		@Override
		protected STree<?> doTraverse(WildcardType.State state) {
			return state.sup;
		}

		@Override
		protected WildcardType.State doRebuildParentState(WildcardType.State state, STree<SNodeOptionState> child) {
			return state.withSup(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return EXT;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			token(LToken.QuestionMark),
			child(EXT, when(some(), composite(token(LToken.Extends).withSpacing(space(), space()), element()))),
			child(SUP, when(some(), composite(token(LToken.Super).withSpacing(space(), space()), element())))
	);
}
