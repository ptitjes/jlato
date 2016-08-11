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

package org.jlato.tree.decl;

import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A formal parameter.
 */
public interface FormalParameter extends Node, TreeCombinators<FormalParameter> {

	/**
	 * Returns the modifiers of this formal parameter.
	 *
	 * @return the modifiers of this formal parameter.
	 */
	NodeList<ExtendedModifier> modifiers();

	/**
	 * Replaces the modifiers of this formal parameter.
	 *
	 * @param modifiers the replacement for the modifiers of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withModifiers(NodeList<ExtendedModifier> modifiers);

	/**
	 * Mutates the modifiers of this formal parameter.
	 *
	 * @param mutation the mutation to apply to the modifiers of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withModifiers(Mutation<NodeList<ExtendedModifier>> mutation);

	/**
	 * Returns the type of this formal parameter.
	 *
	 * @return the type of this formal parameter.
	 */
	Type type();

	/**
	 * Replaces the type of this formal parameter.
	 *
	 * @param type the replacement for the type of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withType(Type type);

	/**
	 * Mutates the type of this formal parameter.
	 *
	 * @param mutation the mutation to apply to the type of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withType(Mutation<Type> mutation);

	/**
	 * Tests whether this formal parameter is a variadic parameter.
	 *
	 * @return <code>true</code> if this formal parameter is a variadic parameter, <code>false</code> otherwise.
	 */
	boolean isVarArgs();

	/**
	 * Sets whether this formal parameter is a variadic parameter.
	 *
	 * @param isVarArgs <code>true</code> if this formal parameter is a variadic parameter, <code>false</code> otherwise.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter setVarArgs(boolean isVarArgs);

	/**
	 * Mutates whether this formal parameter is a variadic parameter.
	 *
	 * @param mutation the mutation to apply to whether this formal parameter is a variadic parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter setVarArgs(Mutation<Boolean> mutation);

	/**
	 * Returns the ellipsis annotations of this formal parameter.
	 *
	 * @return the ellipsis annotations of this formal parameter.
	 */
	NodeList<AnnotationExpr> ellipsisAnnotations();

	/**
	 * Replaces the ellipsis annotations of this formal parameter.
	 *
	 * @param ellipsisAnnotations the replacement for the ellipsis annotations of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withEllipsisAnnotations(NodeList<AnnotationExpr> ellipsisAnnotations);

	/**
	 * Mutates the ellipsis annotations of this formal parameter.
	 *
	 * @param mutation the mutation to apply to the ellipsis annotations of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withEllipsisAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	/**
	 * Returns the identifier of this formal parameter.
	 *
	 * @return the identifier of this formal parameter.
	 */
	NodeOption<VariableDeclaratorId> id();

	/**
	 * Replaces the identifier of this formal parameter.
	 *
	 * @param id the replacement for the identifier of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withId(NodeOption<VariableDeclaratorId> id);

	/**
	 * Mutates the identifier of this formal parameter.
	 *
	 * @param mutation the mutation to apply to the identifier of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withId(Mutation<NodeOption<VariableDeclaratorId>> mutation);

	/**
	 * Replaces the identifier of this formal parameter.
	 *
	 * @param id the replacement for the identifier of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withId(VariableDeclaratorId id);

	/**
	 * Replaces the identifier of this formal parameter.
	 *
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withNoId();

	/**
	 * Tests whether this formal parameter is receiver.
	 *
	 * @return <code>true</code> if this formal parameter is receiver, <code>false</code> otherwise.
	 */
	boolean isReceiver();

	/**
	 * Sets whether this formal parameter is receiver.
	 *
	 * @param isReceiver <code>true</code> if this formal parameter is receiver, <code>false</code> otherwise.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter setReceiver(boolean isReceiver);

	/**
	 * Mutates whether this formal parameter is receiver.
	 *
	 * @param mutation the mutation to apply to whether this formal parameter is receiver.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter setReceiver(Mutation<Boolean> mutation);

	/**
	 * Returns the receiver type name of this formal parameter.
	 *
	 * @return the receiver type name of this formal parameter.
	 */
	NodeOption<Name> receiverTypeName();

	/**
	 * Replaces the receiver type name of this formal parameter.
	 *
	 * @param receiverTypeName the replacement for the receiver type name of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withReceiverTypeName(NodeOption<Name> receiverTypeName);

	/**
	 * Mutates the receiver type name of this formal parameter.
	 *
	 * @param mutation the mutation to apply to the receiver type name of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withReceiverTypeName(Mutation<NodeOption<Name>> mutation);

	/**
	 * Replaces the receiver type name of this formal parameter.
	 *
	 * @param receiverTypeName the replacement for the receiver type name of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withReceiverTypeName(Name receiverTypeName);

	/**
	 * Replaces the receiver type name of this formal parameter.
	 *
	 * @return the resulting mutated formal parameter.
	 */
	FormalParameter withNoReceiverTypeName();
}
