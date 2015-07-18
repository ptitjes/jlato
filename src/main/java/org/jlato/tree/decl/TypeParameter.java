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
import org.jlato.internal.td.SKind;
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
import org.jlato.internal.td.*;

public class TypeParameter extends TreeBase<TypeParameter.State, Tree, TypeParameter> implements Tree {

	public final static SKind<TypeParameter.State> kind = new SKind<TypeParameter.State>() {
		public TypeParameter instantiate(SLocation<TypeParameter.State> location) {
			return new TypeParameter(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private TypeParameter(SLocation<TypeParameter.State> location) {
		super(location);
	}

	public static STree<TypeParameter.State> make(NodeList<AnnotationExpr> annotations, Name name, NodeList<Type> bounds) {
		return new STree<TypeParameter.State>(kind, new TypeParameter.State(TreeBase.<SNodeListState>nodeOf(annotations), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(bounds)));
	}

	public TypeParameter(NodeList<AnnotationExpr> annotations, Name name, NodeList<Type> bounds) {
		super(new SLocation<TypeParameter.State>(make(annotations, name, bounds)));
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

	private static final STraversal<TypeParameter.State> ANNOTATIONS = SNodeState.childTraversal(0);
	private static final STraversal<TypeParameter.State> NAME = SNodeState.childTraversal(1);
	private static final STraversal<TypeParameter.State> BOUNDS = SNodeState.childTraversal(2);

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

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> annotations;

		public final STree<Name.State> name;

		public final STree<SNodeListState> bounds;

		State(STree<SNodeListState> annotations, STree<Name.State> name, STree<SNodeListState> bounds) {
			this.annotations = annotations;
			this.name = name;
			this.bounds = bounds;
		}

		public TypeParameter.State withAnnotations(STree<SNodeListState> annotations) {
			return new TypeParameter.State(annotations, name, bounds);
		}

		public TypeParameter.State withName(STree<Name.State> name) {
			return new TypeParameter.State(annotations, name, bounds);
		}

		public TypeParameter.State withBounds(STree<SNodeListState> bounds) {
			return new TypeParameter.State(annotations, name, bounds);
		}

		public STraversal<TypeParameter.State> firstChild() {
			return null;
		}

		public STraversal<TypeParameter.State> lastChild() {
			return null;
		}
	}
}
