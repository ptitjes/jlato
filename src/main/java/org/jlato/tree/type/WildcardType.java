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

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.AnnotationExpr;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class WildcardType extends TreeBase<WildcardType.State, Type, WildcardType> implements Type {

	public final static SKind<WildcardType.State> kind = new SKind<WildcardType.State>() {

	};

	private WildcardType(SLocation<WildcardType.State> location) {
		super(location);
	}

	public static STree<WildcardType.State> make(NodeList<AnnotationExpr> annotations, NodeOption<ReferenceType> ext, NodeOption<ReferenceType> sup) {
		return new STree<WildcardType.State>(kind, new WildcardType.State(TreeBase.<SNodeListState>nodeOf(annotations), TreeBase.<SNodeOptionState>nodeOf(ext), TreeBase.<SNodeOptionState>nodeOf(sup)));
	}

	public WildcardType(NodeList<AnnotationExpr> annotations, NodeOption<ReferenceType> ext, NodeOption<ReferenceType> sup) {
		super(new SLocation<WildcardType.State>(make(annotations, ext, sup)));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(ANNOTATIONS);
	}

	public Type withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(ANNOTATIONS, annotations);
	}

	public Type withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
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

	private static final STraversal<WildcardType.State> ANNOTATIONS = new STraversal<WildcardType.State>() {

		public STree<?> traverse(WildcardType.State state) {
			return state.annotations;
		}

		public WildcardType.State rebuildParentState(WildcardType.State state, STree<?> child) {
			return state.withAnnotations((STree) child);
		}

		public STraversal<WildcardType.State> leftSibling(WildcardType.State state) {
			return null;
		}

		public STraversal<WildcardType.State> rightSibling(WildcardType.State state) {
			return EXT;
		}
	};
	private static final STraversal<WildcardType.State> EXT = new STraversal<WildcardType.State>() {

		public STree<?> traverse(WildcardType.State state) {
			return state.ext;
		}

		public WildcardType.State rebuildParentState(WildcardType.State state, STree<?> child) {
			return state.withExt((STree) child);
		}

		public STraversal<WildcardType.State> leftSibling(WildcardType.State state) {
			return ANNOTATIONS;
		}

		public STraversal<WildcardType.State> rightSibling(WildcardType.State state) {
			return SUP;
		}
	};
	private static final STraversal<WildcardType.State> SUP = new STraversal<WildcardType.State>() {

		public STree<?> traverse(WildcardType.State state) {
			return state.sup;
		}

		public WildcardType.State rebuildParentState(WildcardType.State state, STree<?> child) {
			return state.withSup((STree) child);
		}

		public STraversal<WildcardType.State> leftSibling(WildcardType.State state) {
			return EXT;
		}

		public STraversal<WildcardType.State> rightSibling(WildcardType.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			token(LToken.QuestionMark),
			when(childIs(EXT, some()), composite(token(LToken.Extends).withSpacingBefore(space()), child(EXT, element()))),
			when(childIs(SUP, some()), composite(token(LToken.Super).withSpacingBefore(space()), child(SUP, element())))
	);

	public static class State extends SNodeState<State> {

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

		public STraversal<WildcardType.State> firstChild() {
			return null;
		}

		public STraversal<WildcardType.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<WildcardType.State> location) {
			return new WildcardType(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
