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
import org.jlato.internal.bu.STree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.STraversal;

public class ObjectCreationExpr extends TreeBase<SNodeState> implements Expr {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public ObjectCreationExpr instantiate(SLocation location) {
			return new ObjectCreationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ObjectCreationExpr(SLocation<SNodeState> location) {
		super(location);
	}

	public ObjectCreationExpr(NodeOption<Expr> scope, NodeList<Type> typeArgs, QualifiedType type, NodeList<Expr> args, NodeOption<NodeList<MemberDecl>> body) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(scope, typeArgs, type, args, body)))));
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
		return location.safeTraversal(TYPE_ARGUMENTS);
	}

	public ObjectCreationExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(TYPE_ARGUMENTS, typeArgs);
	}

	public ObjectCreationExpr withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(TYPE_ARGUMENTS, mutation);
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
		return location.safeTraversal(ARGUMENTS);
	}

	public ObjectCreationExpr withArgs(NodeList<Expr> args) {
		return location.safeTraversalReplace(ARGUMENTS, args);
	}

	public ObjectCreationExpr withArgs(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(ARGUMENTS, mutation);
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

	private static final STraversal<SNodeState> SCOPE = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> TYPE_ARGUMENTS = SNodeState.childTraversal(1);
	private static final STraversal<SNodeState> TYPE = SNodeState.childTraversal(2);
	private static final STraversal<SNodeState> ARGUMENTS = SNodeState.childTraversal(3);
	private static final STraversal<SNodeState> BODY = SNodeState.childTraversal(4);

	public final static LexicalShape shape = composite(
			when(childIs(SCOPE, some()), composite(child(SCOPE, element()), token(LToken.Dot))),
			token(LToken.New).withSpacingAfter(space()),
			child(TYPE_ARGUMENTS, Type.typeArgumentsShape),
			child(TYPE),
			child(ARGUMENTS, Expr.argumentsShape),
			child(BODY, when(some(), element(MemberDecl.bodyShape)))
	);
}
