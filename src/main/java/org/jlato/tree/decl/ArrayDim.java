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

package org.jlato.tree.decl;

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

public class ArrayDim extends Tree {

	public final static Kind kind = new Kind() {
		public ArrayDim instantiate(SLocation location) {
			return new ArrayDim(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ArrayDim(SLocation location) {
		super(location);
	}

	public ArrayDim(NodeList<AnnotationExpr> annotations) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(annotations)))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.nodeChild(ANNOTATIONS);
	}

	public ArrayDim withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.nodeWithChild(ANNOTATIONS, annotations);
	}

	public ArrayDim withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.nodeMutateChild(ANNOTATIONS, mutation);
	}

	private static final int ANNOTATIONS = 0;

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			token(LToken.BracketLeft), token(LToken.BracketRight)
	);

	public static final LexicalShape listShape = list();
}
