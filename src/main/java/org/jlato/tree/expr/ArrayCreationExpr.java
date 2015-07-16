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
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class ArrayCreationExpr extends TreeBase<SNodeState, Expr, ArrayCreationExpr> implements Expr {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public ArrayCreationExpr instantiate(SLocation<SNodeState> location) {
			return new ArrayCreationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ArrayCreationExpr(SLocation<SNodeState> location) {
		super(location);
	}

	public ArrayCreationExpr(Type type, NodeList<ArrayDimExpr> dimExprs, NodeList<ArrayDim> dims, NodeOption<ArrayInitializerExpr> initializer) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(type, dimExprs, dims, initializer)))));
	}

	public Type type() {
		return location.safeTraversal(TYPE);
	}

	public ArrayCreationExpr withType(Type type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public ArrayCreationExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	public NodeList<ArrayDimExpr> dimExprs() {
		return location.safeTraversal(DIMENSION_EXPRESSIONS);
	}

	public ArrayCreationExpr withDimExprs(NodeList<ArrayDimExpr> dimExprs) {
		return location.safeTraversalReplace(DIMENSION_EXPRESSIONS, dimExprs);
	}

	public ArrayCreationExpr withDimExprs(Mutation<NodeList<ArrayDimExpr>> mutation) {
		return location.safeTraversalMutate(DIMENSION_EXPRESSIONS, mutation);
	}

	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(DIMENSIONS);
	}

	public ArrayCreationExpr withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(DIMENSIONS, dims);
	}

	public ArrayCreationExpr withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(DIMENSIONS, mutation);
	}

	public NodeOption<ArrayInitializerExpr> init() {
		return location.safeTraversal(INITIALIZER);
	}

	public ArrayCreationExpr withInit(NodeOption<ArrayInitializerExpr> initializer) {
		return location.safeTraversalReplace(INITIALIZER, initializer);
	}

	public ArrayCreationExpr withInit(Mutation<NodeOption<ArrayInitializerExpr>> mutation) {
		return location.safeTraversalMutate(INITIALIZER, mutation);
	}

	private static final STraversal<SNodeState> TYPE = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> DIMENSION_EXPRESSIONS = SNodeState.childTraversal(1);
	private static final STraversal<SNodeState> DIMENSIONS = SNodeState.childTraversal(2);
	private static final STraversal<SNodeState> INITIALIZER = SNodeState.childTraversal(3);

	public final static LexicalShape shape = composite(
			token(LToken.New),
			child(TYPE),
			child(DIMENSION_EXPRESSIONS, list()),
			child(DIMENSIONS, list()),
			when(childIs(INITIALIZER, some()),
					composite(
							none().withSpacingAfter(space()),
							child(INITIALIZER, element())
					)
			)
	);
}
