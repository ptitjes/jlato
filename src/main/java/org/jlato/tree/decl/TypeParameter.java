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
import org.jlato.tree.NodeList;
import org.jlato.tree.Mutator;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class TypeParameter extends Tree {

	public final static Kind kind = new Kind() {
		public TypeParameter instantiate(SLocation location) {
			return new TypeParameter(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private TypeParameter(SLocation location) {
		super(location);
	}

	public TypeParameter(NodeList<AnnotationExpr> annotations, Name name, NodeList<Type> bounds) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(annotations, name, bounds)))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.nodeChild(ANNOTATIONS);
	}

	public TypeParameter withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.nodeWithChild(ANNOTATIONS, annotations);
	}

	public TypeParameter withAnnotations(Mutator<NodeList<AnnotationExpr>> annotations) {
		return location.nodeWithChild(ANNOTATIONS, annotations);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public TypeParameter withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public TypeParameter withName(Mutator<Name> name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Type> bounds() {
		return location.nodeChild(BOUNDS);
	}

	public TypeParameter withBounds(NodeList<Type> bounds) {
		return location.nodeWithChild(BOUNDS, bounds);
	}

	public TypeParameter withBounds(Mutator<NodeList<Type>> bounds) {
		return location.nodeWithChild(BOUNDS, bounds);
	}

	private static final int ANNOTATIONS = 0;
	private static final int NAME = 1;
	private static final int BOUNDS = 2;

	public static final LexicalShape boundsShape = list(
			token(LToken.Extends).withSpacingBefore(space()),
			token(LToken.BinAnd).withSpacing(space(), space()),
			none()
	);

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			child(NAME),
			child(BOUNDS, boundsShape)
	);

	public static final LexicalShape listShape = list(
			token(LToken.Less),
			token(LToken.Comma).withSpacingAfter(space()),
			token(LToken.Greater).withSpacingAfter(space())
	);
}
