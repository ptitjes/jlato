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
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;

public class ObjectCreationExpr extends TreeBase<ObjectCreationExpr.State, Expr, ObjectCreationExpr> implements Expr {

	public final static SKind<ObjectCreationExpr.State> kind = new SKind<ObjectCreationExpr.State>() {
		public ObjectCreationExpr instantiate(SLocation<ObjectCreationExpr.State> location) {
			return new ObjectCreationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ObjectCreationExpr(SLocation<ObjectCreationExpr.State> location) {
		super(location);
	}

	public static STree<ObjectCreationExpr.State> make(NodeOption<Expr> scope, NodeList<Type> typeArgs, QualifiedType type, NodeList<Expr> args, NodeOption<NodeList<MemberDecl>> body) {
		return new STree<ObjectCreationExpr.State>(kind, new ObjectCreationExpr.State(TreeBase.<SNodeOptionState>nodeOf(scope), TreeBase.<SNodeListState>nodeOf(typeArgs), TreeBase.<QualifiedType.State>nodeOf(type), TreeBase.<SNodeListState>nodeOf(args), TreeBase.<SNodeOptionState>nodeOf(body)));
	}

	public ObjectCreationExpr(NodeOption<Expr> scope, NodeList<Type> typeArgs, QualifiedType type, NodeList<Expr> args, NodeOption<NodeList<MemberDecl>> body) {
		super(new SLocation<ObjectCreationExpr.State>(make(scope, typeArgs, type, args, body)));
	}

	public NodeOption<Expr> scope() {
		return location.safeTraversal(SCOPE);
	}

	public ObjectCreationExpr withScope(NodeOption<Expr> scope) {
		return location.safeTraversalReplace(SCOPE, scope);
	}

	public ObjectCreationExpr withScope(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SCOPE, mutation);
	}

	public NodeList<Type> typeArgs() {
		return location.safeTraversal(TYPE_ARGS);
	}

	public ObjectCreationExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(TYPE_ARGS, typeArgs);
	}

	public ObjectCreationExpr withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(TYPE_ARGS, mutation);
	}

	public QualifiedType type() {
		return location.safeTraversal(TYPE);
	}

	public ObjectCreationExpr withType(QualifiedType type) {
		return location.safeTraversalReplace(TYPE, type);
	}

	public ObjectCreationExpr withType(Mutation<QualifiedType> mutation) {
		return location.safeTraversalMutate(TYPE, mutation);
	}

	public NodeList<Expr> args() {
		return location.safeTraversal(ARGS);
	}

	public ObjectCreationExpr withArgs(NodeList<Expr> args) {
		return location.safeTraversalReplace(ARGS, args);
	}

	public ObjectCreationExpr withArgs(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(ARGS, mutation);
	}

	public NodeOption<NodeList<MemberDecl>> body() {
		return location.safeTraversal(BODY);
	}

	public ObjectCreationExpr withBody(NodeOption<NodeList<MemberDecl>> body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public ObjectCreationExpr withBody(Mutation<NodeOption<NodeList<MemberDecl>>> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	private static final STraversal<ObjectCreationExpr.State> SCOPE = SNodeState.childTraversal(0);
	private static final STraversal<ObjectCreationExpr.State> TYPE_ARGS = SNodeState.childTraversal(1);
	private static final STraversal<ObjectCreationExpr.State> TYPE = SNodeState.childTraversal(2);
	private static final STraversal<ObjectCreationExpr.State> ARGS = SNodeState.childTraversal(3);
	private static final STraversal<ObjectCreationExpr.State> BODY = SNodeState.childTraversal(4);

	public final static LexicalShape shape = composite(
			when(childIs(SCOPE, some()), composite(child(SCOPE, element()), token(LToken.Dot))),
			token(LToken.New).withSpacingAfter(space()),
			child(TYPE_ARGS, Type.typeArgumentsShape),
			child(TYPE),
			child(ARGS, Expr.argumentsShape),
			child(BODY, when(some(), element(MemberDecl.bodyShape)))
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeOptionState> scope;

		public final STree<SNodeListState> typeArgs;

		public final STree<QualifiedType.State> type;

		public final STree<SNodeListState> args;

		public final STree<SNodeOptionState> body;

		State(STree<SNodeOptionState> scope, STree<SNodeListState> typeArgs, STree<QualifiedType.State> type, STree<SNodeListState> args, STree<SNodeOptionState> body) {
			this.scope = scope;
			this.typeArgs = typeArgs;
			this.type = type;
			this.args = args;
			this.body = body;
		}

		public ObjectCreationExpr.State withScope(STree<SNodeOptionState> scope) {
			return new ObjectCreationExpr.State(scope, typeArgs, type, args, body);
		}

		public ObjectCreationExpr.State withTypeArgs(STree<SNodeListState> typeArgs) {
			return new ObjectCreationExpr.State(scope, typeArgs, type, args, body);
		}

		public ObjectCreationExpr.State withType(STree<QualifiedType.State> type) {
			return new ObjectCreationExpr.State(scope, typeArgs, type, args, body);
		}

		public ObjectCreationExpr.State withArgs(STree<SNodeListState> args) {
			return new ObjectCreationExpr.State(scope, typeArgs, type, args, body);
		}

		public ObjectCreationExpr.State withBody(STree<SNodeOptionState> body) {
			return new ObjectCreationExpr.State(scope, typeArgs, type, args, body);
		}

		public STraversal<ObjectCreationExpr.State> firstChild() {
			return null;
		}

		public STraversal<ObjectCreationExpr.State> lastChild() {
			return null;
		}
	}
}
