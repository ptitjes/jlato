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
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.AnnotationExpr;

import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class PrimitiveType extends TreeBase<PrimitiveType.State, Type, PrimitiveType> implements Type {

	public Kind kind() {
		return Kind.PrimitiveType;
	}

	private PrimitiveType(SLocation<PrimitiveType.State> location) {
		super(location);
	}

	public static STree<PrimitiveType.State> make(STree<SNodeListState> annotations, Primitive type) {
		return new STree<PrimitiveType.State>(new PrimitiveType.State(annotations, type));
	}

	public PrimitiveType(NodeList<AnnotationExpr> annotations, Primitive type) {
		super(new SLocation<PrimitiveType.State>(make(TreeBase.<SNodeListState>nodeOf(annotations), type)));
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

	public Primitive type() {
		return location.safeProperty(PRIMITIVE);
	}

	public PrimitiveType withType(Primitive type) {
		return location.safePropertyReplace(PRIMITIVE, type);
	}

	public PrimitiveType withType(Mutation<Primitive> mutation) {
		return location.safePropertyMutate(PRIMITIVE, mutation);
	}

	private static final STraversal ANNOTATIONS = new STraversal() {

		public STree<?> traverse(PrimitiveType.State state) {
			return state.annotations;
		}

		public PrimitiveType.State rebuildParentState(PrimitiveType.State state, STree<?> child) {
			return state.withAnnotations((STree) child);
		}

		public STraversal leftSibling(PrimitiveType.State state) {
			return null;
		}

		public STraversal rightSibling(PrimitiveType.State state) {
			return null;
		}
	};

	private static final SProperty PRIMITIVE = new SProperty() {

		public Object retrieve(PrimitiveType.State state) {
			return state.type;
		}

		public PrimitiveType.State rebuildParentState(PrimitiveType.State state, Object value) {
			return state.withType((Primitive) value);
		}
	};

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			token(new LSToken.Provider() {
				public LToken tokenFor(STree tree) {
					return ((State) tree.state).type.token;
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

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> annotations;

		public final Primitive type;

		State(STree<SNodeListState> annotations, Primitive type) {
			this.annotations = annotations;
			this.type = type;
		}

		public PrimitiveType.State withAnnotations(STree<SNodeListState> annotations) {
			return new PrimitiveType.State(annotations, type);
		}

		public PrimitiveType.State withType(Primitive type) {
			return new PrimitiveType.State(annotations, type);
		}

		public STraversal firstChild() {
			return ANNOTATIONS;
		}

		public STraversal lastChild() {
			return ANNOTATIONS;
		}

		public Tree instantiate(SLocation<PrimitiveType.State> location) {
			return new PrimitiveType(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.PrimitiveType;
		}
	}
}
