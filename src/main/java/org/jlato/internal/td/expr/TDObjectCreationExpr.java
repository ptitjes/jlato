/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.td.expr;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.expr.SObjectCreationExpr;
import org.jlato.internal.bu.type.SQualifiedType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.ObjectCreationExpr;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * An object creation expression.
 */
public class TDObjectCreationExpr extends TDTree<SObjectCreationExpr, Expr, ObjectCreationExpr> implements ObjectCreationExpr {

	/**
	 * Returns the kind of this object creation expression.
	 *
	 * @return the kind of this object creation expression.
	 */
	public Kind kind() {
		return Kind.ObjectCreationExpr;
	}

	/**
	 * Creates an object creation expression for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDObjectCreationExpr(TDLocation<SObjectCreationExpr> location) {
		super(location);
	}

	/**
	 * Creates an object creation expression with the specified child trees.
	 *
	 * @param scope    the scope child tree.
	 * @param typeArgs the type args child tree.
	 * @param type     the type child tree.
	 * @param args     the args child tree.
	 * @param body     the body child tree.
	 */
	public TDObjectCreationExpr(NodeOption<Expr> scope, NodeList<Type> typeArgs, QualifiedType type, NodeList<Expr> args, NodeOption<NodeList<MemberDecl>> body) {
		super(new TDLocation<SObjectCreationExpr>(SObjectCreationExpr.make(TDTree.<SNodeOption>treeOf(scope), TDTree.<SNodeList>treeOf(typeArgs), TDTree.<SQualifiedType>treeOf(type), TDTree.<SNodeList>treeOf(args), TDTree.<SNodeOption>treeOf(body))));
	}

	/**
	 * Returns the scope of this object creation expression.
	 *
	 * @return the scope of this object creation expression.
	 */
	public NodeOption<Expr> scope() {
		return location.safeTraversal(SObjectCreationExpr.SCOPE);
	}

	/**
	 * Replaces the scope of this object creation expression.
	 *
	 * @param scope the replacement for the scope of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	public ObjectCreationExpr withScope(NodeOption<Expr> scope) {
		return location.safeTraversalReplace(SObjectCreationExpr.SCOPE, scope);
	}

	/**
	 * Mutates the scope of this object creation expression.
	 *
	 * @param mutation the mutation to apply to the scope of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	public ObjectCreationExpr withScope(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SObjectCreationExpr.SCOPE, mutation);
	}

	/**
	 * Replaces the scope of this object creation expression.
	 *
	 * @param scope the replacement for the scope of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	public ObjectCreationExpr withScope(Expr scope) {
		return location.safeTraversalReplace(SObjectCreationExpr.SCOPE, Trees.some(scope));
	}

	/**
	 * Replaces the scope of this object creation expression.
	 *
	 * @return the resulting mutated object creation expression.
	 */
	public ObjectCreationExpr withNoScope() {
		return location.safeTraversalReplace(SObjectCreationExpr.SCOPE, Trees.<Expr>none());
	}

	/**
	 * Returns the type args of this object creation expression.
	 *
	 * @return the type args of this object creation expression.
	 */
	public NodeList<Type> typeArgs() {
		return location.safeTraversal(SObjectCreationExpr.TYPE_ARGS);
	}

	/**
	 * Replaces the type args of this object creation expression.
	 *
	 * @param typeArgs the replacement for the type args of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	public ObjectCreationExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.safeTraversalReplace(SObjectCreationExpr.TYPE_ARGS, typeArgs);
	}

	/**
	 * Mutates the type args of this object creation expression.
	 *
	 * @param mutation the mutation to apply to the type args of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	public ObjectCreationExpr withTypeArgs(Mutation<NodeList<Type>> mutation) {
		return location.safeTraversalMutate(SObjectCreationExpr.TYPE_ARGS, mutation);
	}

	/**
	 * Returns the type of this object creation expression.
	 *
	 * @return the type of this object creation expression.
	 */
	public QualifiedType type() {
		return location.safeTraversal(SObjectCreationExpr.TYPE);
	}

	/**
	 * Replaces the type of this object creation expression.
	 *
	 * @param type the replacement for the type of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	public ObjectCreationExpr withType(QualifiedType type) {
		return location.safeTraversalReplace(SObjectCreationExpr.TYPE, type);
	}

	/**
	 * Mutates the type of this object creation expression.
	 *
	 * @param mutation the mutation to apply to the type of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	public ObjectCreationExpr withType(Mutation<QualifiedType> mutation) {
		return location.safeTraversalMutate(SObjectCreationExpr.TYPE, mutation);
	}

	/**
	 * Returns the args of this object creation expression.
	 *
	 * @return the args of this object creation expression.
	 */
	public NodeList<Expr> args() {
		return location.safeTraversal(SObjectCreationExpr.ARGS);
	}

	/**
	 * Replaces the args of this object creation expression.
	 *
	 * @param args the replacement for the args of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	public ObjectCreationExpr withArgs(NodeList<Expr> args) {
		return location.safeTraversalReplace(SObjectCreationExpr.ARGS, args);
	}

	/**
	 * Mutates the args of this object creation expression.
	 *
	 * @param mutation the mutation to apply to the args of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	public ObjectCreationExpr withArgs(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(SObjectCreationExpr.ARGS, mutation);
	}

	/**
	 * Returns the body of this object creation expression.
	 *
	 * @return the body of this object creation expression.
	 */
	public NodeOption<NodeList<MemberDecl>> body() {
		return location.safeTraversal(SObjectCreationExpr.BODY);
	}

	/**
	 * Replaces the body of this object creation expression.
	 *
	 * @param body the replacement for the body of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	public ObjectCreationExpr withBody(NodeOption<NodeList<MemberDecl>> body) {
		return location.safeTraversalReplace(SObjectCreationExpr.BODY, body);
	}

	/**
	 * Mutates the body of this object creation expression.
	 *
	 * @param mutation the mutation to apply to the body of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	public ObjectCreationExpr withBody(Mutation<NodeOption<NodeList<MemberDecl>>> mutation) {
		return location.safeTraversalMutate(SObjectCreationExpr.BODY, mutation);
	}

	/**
	 * Replaces the body of this object creation expression.
	 *
	 * @param body the replacement for the body of this object creation expression.
	 * @return the resulting mutated object creation expression.
	 */
	public ObjectCreationExpr withBody(NodeList<MemberDecl> body) {
		return location.safeTraversalReplace(SObjectCreationExpr.BODY, Trees.some(body));
	}

	/**
	 * Replaces the body of this object creation expression.
	 *
	 * @return the resulting mutated object creation expression.
	 */
	public ObjectCreationExpr withNoBody() {
		return location.safeTraversalReplace(SObjectCreationExpr.BODY, Trees.<NodeList<MemberDecl>>none());
	}
}
