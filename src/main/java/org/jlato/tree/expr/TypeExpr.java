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

import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.child;

import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class TypeExpr extends TreeBase<TypeExpr.State, Expr, TypeExpr> implements Expr {

	public Kind kind() {
		return Kind.TypeExpr;
	}

	private TypeExpr(SLocation<TypeExpr.State> location) {
		super(location);
	}

	public static STree<TypeExpr.State> make(STree<Type.State> type) {
		return new STree<TypeExpr.State>(new TypeExpr.State(type));
	}

	public TypeExpr(Type type) {
		super(new SLocation<TypeExpr.State>(make(TreeBase.<Type.State>nodeOf(type))));
	}

	public Type type() {
		return location.safeTraversal(TYPE);
	}

	public TypeExpr withType(Type type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public TypeExpr withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	public final static LexicalShape shape = child(TYPE);
}
