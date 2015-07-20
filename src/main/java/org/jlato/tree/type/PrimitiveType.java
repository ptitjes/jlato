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
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;

import static org.jlato.internal.shapes.LexicalShape.*;

public class PrimitiveType extends TreeBase<PrimitiveType.State, Type, PrimitiveType> implements Type {

	public Kind kind() {
		return Kind.PrimitiveType;
	}

	private PrimitiveType(SLocation<PrimitiveType.State> location) {
		super(location);
	}

	public static STree<PrimitiveType.State> make(STree<SNodeListState> annotations, Primitive primitive) {
		return new STree<PrimitiveType.State>(new PrimitiveType.State(annotations, primitive));
	}

	public PrimitiveType(NodeList<AnnotationExpr> annotations, Primitive primitive) {
		super(new SLocation<PrimitiveType.State>(make(TreeBase.<SNodeListState>nodeOf(annotations), primitive)));
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

	public Primitive primitive() {
		return location.safeProperty(PRIMITIVE);
	}

	public PrimitiveType withPrimitive(Primitive primitive) {
		return location.safePropertyReplace(PRIMITIVE, primitive);
	}

	public PrimitiveType withPrimitive(Mutation<Primitive> mutation) {
		return location.safePropertyMutate(PRIMITIVE, mutation);
	}

	public static class State extends SNodeState<State> implements Type.State {

		public final STree<SNodeListState> annotations;

		public final Primitive primitive;

		State(STree<SNodeListState> annotations, Primitive primitive) {
			this.annotations = annotations;
			this.primitive = primitive;
		}

		public PrimitiveType.State withAnnotations(STree<SNodeListState> annotations) {
			return new PrimitiveType.State(annotations, primitive);
		}

		public PrimitiveType.State withPrimitive(Primitive primitive) {
			return new PrimitiveType.State(annotations, primitive);
		}

		@Override
		public Kind kind() {
			return Kind.PrimitiveType;
		}

		@Override
		protected Tree doInstantiate(SLocation<PrimitiveType.State> location) {
			return new PrimitiveType(location);
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
			return ANNOTATIONS;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			PrimitiveType.State state = (PrimitiveType.State) o;
			if (!primitive.equals(state.primitive))
				return false;
			if (!annotations.equals(state.annotations))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 37 * result + primitive.hashCode();
			result = 37 * result + annotations.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<PrimitiveType.State, SNodeListState, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<PrimitiveType.State, SNodeListState, NodeList<AnnotationExpr>>() {

		@Override
		protected STree<?> doTraverse(PrimitiveType.State state) {
			return state.annotations;
		}

		@Override
		protected PrimitiveType.State doRebuildParentState(PrimitiveType.State state, STree<SNodeListState> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	private static STypeSafeProperty<PrimitiveType.State, Primitive> PRIMITIVE = new STypeSafeProperty<PrimitiveType.State, Primitive>() {

		@Override
		protected Primitive doRetrieve(PrimitiveType.State state) {
			return state.primitive;
		}

		@Override
		protected PrimitiveType.State doRebuildParentState(PrimitiveType.State state, Primitive value) {
			return state.withPrimitive(value);
		}
	};

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			token(new LSToken.Provider() {
				public LToken tokenFor(STree tree) {
					return ((State) tree.state).primitive.token;
				}
			})
	);

	public enum Primitive {
		Boolean(LToken.Boolean),
		Char(LToken.Char),
		Byte(LToken.Byte),
		Short(LToken.Short),
		Int(LToken.Int),
		Long(LToken.Long),
		Float(LToken.Float),
		Double(LToken.Double),
		// Keep last comma
		;

		protected final LToken token;

		Primitive(LToken token) {
			this.token = token;
		}

		public String toString() {
			return token.toString();
		}
	}
}
