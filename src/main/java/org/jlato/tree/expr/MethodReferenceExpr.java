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
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class MethodReferenceExpr extends TreeBase<MethodReferenceExpr.State, Expr, MethodReferenceExpr> implements Expr {

	public Kind kind() {
		return Kind.MethodReferenceExpr;
	}

	private MethodReferenceExpr(SLocation<MethodReferenceExpr.State> location) {
		super(location);
	}

	public static STree<MethodReferenceExpr.State> make(STree<Expr.State> scope, STree<SNodeListState> typeArgs, STree<Name.State> name) {
		return new STree<MethodReferenceExpr.State>(new MethodReferenceExpr.State(scope, typeArgs, name));
	}

	public MethodReferenceExpr(Expr scope, NodeList<Type> typeArgs, Name name) {
		super(new SLocation<MethodReferenceExpr.State>(make(TreeBase.<Expr.State>nodeOf(scope), TreeBase.<SNodeListState>nodeOf(typeArgs), TreeBase.<Name.State>nodeOf(name))));
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
		return location.safeTraversal(TYPE_ARGS);
	}

	public MethodReferenceExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(TYPE_ARGS, typeArgs);
	}

	public MethodReferenceExpr withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(TYPE_ARGS, mutation);
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

	public final static LexicalShape shape = composite(
			child(SCOPE),
			token(LToken.DoubleColon),
			child(TYPE_ARGS, Type.typeArgumentsShape),
			child(NAME)
	);
}
