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
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class WildcardType extends Type {

	public final static Tree.Kind kind = new Tree.Kind() {
		public WildcardType instantiate(SLocation location) {
			return new WildcardType(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private WildcardType(SLocation location) {
		super(location);
	}

	public WildcardType(NodeList<AnnotationExpr> annotations, ReferenceType ext, ReferenceType sup) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(annotations, ext, sup)))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.nodeChild(ANNOTATIONS);
	}

	public Type withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.nodeWithChild(ANNOTATIONS, annotations);
	}

	public Type withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.nodeMutateChild(ANNOTATIONS, mutation);
	}

	public ReferenceType ext() {
		return location.nodeChild(EXT);
	}

	public WildcardType withExt(ReferenceType ext) {
		return location.nodeWithChild(EXT, ext);
	}

	public WildcardType withExt(Mutation<ReferenceType> mutation) {
		return location.nodeMutateChild(EXT, mutation);
	}

	public ReferenceType sup() {
		return location.nodeChild(SUP);
	}

	public WildcardType withSup(ReferenceType sup) {
		return location.nodeWithChild(SUP, sup);
	}

	public WildcardType withSup(Mutation<ReferenceType> mutation) {
		return location.nodeMutateChild(SUP, mutation);
	}

	private static final int ANNOTATIONS = 0;
	private static final int EXT = 1;
	private static final int SUP = 2;

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			token(LToken.QuestionMark),
			nonNullChild(EXT, composite(token(LToken.Extends).withSpacingBefore(space()), child(EXT))),
			nonNullChild(SUP, composite(token(LToken.Super).withSpacingBefore(space()), child(SUP)))
	);
}
