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

package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class ArrayDimExpr extends Tree {

	public final static Kind kind = new Kind() {
		public ArrayDimExpr instantiate(SLocation location) {
			return new ArrayDimExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ArrayDimExpr(SLocation location) {
		super(location);
	}

	public ArrayDimExpr(NodeList<AnnotationExpr> annotations, Expr expression) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(annotations, expression)))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.nodeChild(ANNOTATIONS);
	}

	public ArrayDimExpr withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.nodeWithChild(ANNOTATIONS, annotations);
	}

	public Expr expression() {
		return location.nodeChild(EXPRESSION);
	}

	public ArrayDimExpr withExpression(Expr expression) {
		return location.nodeWithChild(EXPRESSION, expression);
	}

	private static final int ANNOTATIONS = 0;
	private static final int EXPRESSION = 1;

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			token(LToken.BracketLeft), child(EXPRESSION), token(LToken.BracketRight)
	);

	public static final LexicalShape listShape = list();
}
