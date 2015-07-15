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
import org.jlato.internal.bu.STree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.child;
import org.jlato.internal.bu.STraversal;

public class TypeExpr extends TreeBase<SNodeState> implements Expr {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public TypeExpr instantiate(SLocation location) {
			return new TypeExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private TypeExpr(SLocation<SNodeState> location) {
		super(location);
	}

	public TypeExpr(Type type) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(type)))));
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

	private static final STraversal<SNodeState> TYPE = SNodeState.childTraversal(0);

	public final static LexicalShape shape = child(TYPE);
}
