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
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class ArrayDimExpr extends TreeBase<ArrayDimExpr.State, Tree, ArrayDimExpr> implements Tree {

	public Kind kind() {
		return Kind.ArrayDimExpr;
	}

	private ArrayDimExpr(SLocation<ArrayDimExpr.State> location) {
		super(location);
	}

	public static STree<ArrayDimExpr.State> make(STree<SNodeListState> annotations, STree<Expr.State> expr) {
		return new STree<ArrayDimExpr.State>(new ArrayDimExpr.State(annotations, expr));
	}

	public ArrayDimExpr(NodeList<AnnotationExpr> annotations, Expr expr) {
		super(new SLocation<ArrayDimExpr.State>(make(TreeBase.<SNodeListState>nodeOf(annotations), TreeBase.<Expr.State>nodeOf(expr))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(ANNOTATIONS);
	}

	public ArrayDimExpr withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(ANNOTATIONS, annotations);
	}

	public ArrayDimExpr withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(ANNOTATIONS, mutation);
	}

	public Expr expr() {
		return location.safeTraversal(EXPR);
	}

	public ArrayDimExpr withExpr(Expr expr) {
		return location.safeTraversalReplace(EXPR, expr);
	}

	public ArrayDimExpr withExpr(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(EXPR, mutation);
	}

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list(
					none().withSpacingBefore(space()),
					none().withSpacingBefore(space()),
					none().withSpacingBefore(space())
			)),
			token(LToken.BracketLeft), child(EXPR), token(LToken.BracketRight)
	);

	public static final LexicalShape listShape = list();
}
