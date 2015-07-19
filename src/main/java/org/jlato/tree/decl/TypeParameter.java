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
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class TypeParameter extends TreeBase<TypeParameter.State, Tree, TypeParameter> implements Tree {

	public Kind kind() {
		return Kind.TypeParameter;
	}

	private TypeParameter(SLocation<TypeParameter.State> location) {
		super(location);
	}

	public static STree<TypeParameter.State> make(STree<SNodeListState> annotations, STree<Name.State> name, STree<SNodeListState> bounds) {
		return new STree<TypeParameter.State>(new TypeParameter.State(annotations, name, bounds));
	}

	public TypeParameter(NodeList<AnnotationExpr> annotations, Name name, NodeList<Type> bounds) {
		super(new SLocation<TypeParameter.State>(make(TreeBase.<SNodeListState>nodeOf(annotations), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(bounds))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(ANNOTATIONS);
	}

	public TypeParameter withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(ANNOTATIONS, annotations);
	}

	public TypeParameter withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(ANNOTATIONS, mutation);
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public TypeParameter withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public TypeParameter withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeList<Type> bounds() {
		return location.safeTraversal(BOUNDS);
	}

	public TypeParameter withBounds(NodeList<Type> bounds) {
		return location.safeTraversalReplace(BOUNDS, bounds);
	}

	public TypeParameter withBounds(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(BOUNDS, mutation);
	}

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
