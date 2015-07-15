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

public class WildcardType extends TreeBase<SNodeState> implements Type {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public WildcardType instantiate(SLocation<SNodeState> location) {
			return new WildcardType(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private WildcardType(SLocation<SNodeState> location) {
		super(location);
	}

	public WildcardType(NodeList<AnnotationExpr> annotations, NodeOption<ReferenceType> ext, NodeOption<ReferenceType> sup) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(annotations, ext, sup)))));
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

	private static final STraversal<SNodeState> ANNOTATIONS = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> EXT = SNodeState.childTraversal(1);
	private static final STraversal<SNodeState> SUP = SNodeState.childTraversal(2);

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			token(LToken.QuestionMark),
			when(childIs(EXT, some()), composite(token(LToken.Extends).withSpacingBefore(space()), child(EXT, element()))),
			when(childIs(SUP, some()), composite(token(LToken.Super).withSpacingBefore(space()), child(SUP, element())))
	);
}
