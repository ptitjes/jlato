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
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;

public class MethodReferenceExpr extends TreeBase<SNodeState> implements Expr {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public MethodReferenceExpr instantiate(SLocation<SNodeState> location) {
			return new MethodReferenceExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private MethodReferenceExpr(SLocation<SNodeState> location) {
		super(location);
	}

	public MethodReferenceExpr(Expr scope, NodeList<Type> typeArgs, Name name) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(scope, typeArgs, name)))));
	}

	public Expr scope() {
		return location.safeTraversal(SCOPE);
	}

	public MethodReferenceExpr withScope(Expr scope) {
		return location.safeTraversalReplace(SCOPE, scope);
	}

	public MethodReferenceExpr withScope(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(SCOPE, mutation);
	}

	public NodeList<Type> typeArgs() {
		return location.safeTraversal(TYPE_ARGUMENTS);
	}

	public MethodReferenceExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(TYPE_ARGUMENTS, typeArgs);
	}

	public MethodReferenceExpr withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(TYPE_ARGUMENTS, mutation);
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public MethodReferenceExpr withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public MethodReferenceExpr withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	private static final STraversal<SNodeState> SCOPE = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> TYPE_ARGUMENTS = SNodeState.childTraversal(1);
	private static final STraversal<SNodeState> NAME = SNodeState.childTraversal(2);

	public final static LexicalShape shape = composite(
			child(SCOPE),
			token(LToken.DoubleColon),
			child(TYPE_ARGUMENTS, Type.typeArgumentsShape),
			child(NAME)
	);
}
