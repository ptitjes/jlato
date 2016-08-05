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

package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.decl.SFormalParameter;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.*;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.decl.VariableDeclaratorId;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;
import org.jlato.util.Mutation;

/**
 * A formal parameter.
 */
public class TDFormalParameter extends TDTree<SFormalParameter, Node, FormalParameter> implements FormalParameter {

	/**
	 * Returns the kind of this formal parameter.
	 *
	 * @return the kind of this formal parameter.
	 */
	public Kind kind() {
		return Kind.FormalParameter;
	}

	/**
	 * Creates a formal parameter for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDFormalParameter(TDLocation<SFormalParameter> location) {
		super(location);
	}

	/**
	 * Creates a formal parameter with the specified child trees.
	 *
	 * @param modifiers        the modifiers child tree.
	 * @param type             the type child tree.
	 * @param isVarArgs        the is a variadic parameter child tree.
	 * @param id               the identifier child tree.
	 * @param isReceiver       the is receiver child tree.
	 * @param receiverTypeName the receiver type name child tree.
	 */
	public TDFormalParameter(NodeList<ExtendedModifier> modifiers, Type type, boolean isVarArgs, NodeOption<VariableDeclaratorId> id, boolean isReceiver, NodeOption<Name> receiverTypeName) {
		super(new TDLocation<SFormalParameter>(SFormalParameter.make(TDTree.<SNodeList>treeOf(modifiers), TDTree.<SType>treeOf(type), isVarArgs, TDTree.<SNodeOption>treeOf(id), isReceiver, TDTree.<SNodeOption>treeOf(receiverTypeName))));
	}

	/**
	 * Returns the modifiers of this formal parameter.
	 *
	 * @return the modifiers of this formal parameter.
	 */
	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SFormalParameter.MODIFIERS);
	}

	/**
	 * Replaces the modifiers of this formal parameter.
	 *
	 * @param modifiers the replacement for the modifiers of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SFormalParameter.MODIFIERS, modifiers);
	}

	/**
	 * Mutates the modifiers of this formal parameter.
	 *
	 * @param mutation the mutation to apply to the modifiers of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SFormalParameter.MODIFIERS, mutation);
	}

	/**
	 * Returns the type of this formal parameter.
	 *
	 * @return the type of this formal parameter.
	 */
	public Type type() {
		return location.safeTraversal(SFormalParameter.TYPE);
	}

	/**
	 * Replaces the type of this formal parameter.
	 *
	 * @param type the replacement for the type of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withType(Type type) {
		return location.safeTraversalReplace(SFormalParameter.TYPE, type);
	}

	/**
	 * Mutates the type of this formal parameter.
	 *
	 * @param mutation the mutation to apply to the type of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withType(Mutation<Type> mutation) {
		return location.safeTraversalMutate(SFormalParameter.TYPE, mutation);
	}

	/**
	 * Tests whether this formal parameter is a variadic parameter.
	 *
	 * @return <code>true</code> if this formal parameter is a variadic parameter, <code>false</code> otherwise.
	 */
	public boolean isVarArgs() {
		return location.safeProperty(SFormalParameter.VAR_ARGS);
	}

	/**
	 * Sets whether this formal parameter is a variadic parameter.
	 *
	 * @param isVarArgs <code>true</code> if this formal parameter is a variadic parameter, <code>false</code> otherwise.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter setVarArgs(boolean isVarArgs) {
		return location.safePropertyReplace(SFormalParameter.VAR_ARGS, isVarArgs);
	}

	/**
	 * Mutates whether this formal parameter is a variadic parameter.
	 *
	 * @param mutation the mutation to apply to whether this formal parameter is a variadic parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter setVarArgs(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SFormalParameter.VAR_ARGS, mutation);
	}

	/**
	 * Returns the identifier of this formal parameter.
	 *
	 * @return the identifier of this formal parameter.
	 */
	public NodeOption<VariableDeclaratorId> id() {
		return location.safeTraversal(SFormalParameter.ID);
	}

	/**
	 * Replaces the identifier of this formal parameter.
	 *
	 * @param id the replacement for the identifier of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withId(NodeOption<VariableDeclaratorId> id) {
		return location.safeTraversalReplace(SFormalParameter.ID, id);
	}

	/**
	 * Mutates the identifier of this formal parameter.
	 *
	 * @param mutation the mutation to apply to the identifier of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withId(Mutation<NodeOption<VariableDeclaratorId>> mutation) {
		return location.safeTraversalMutate(SFormalParameter.ID, mutation);
	}

	/**
	 * Replaces the identifier of this formal parameter.
	 *
	 * @param id the replacement for the identifier of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withId(VariableDeclaratorId id) {
		return location.safeTraversalReplace(SFormalParameter.ID, Trees.some(id));
	}

	/**
	 * Replaces the identifier of this formal parameter.
	 *
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withNoId() {
		return location.safeTraversalReplace(SFormalParameter.ID, Trees.<VariableDeclaratorId>none());
	}

	/**
	 * Tests whether this formal parameter is receiver.
	 *
	 * @return <code>true</code> if this formal parameter is receiver, <code>false</code> otherwise.
	 */
	public boolean isReceiver() {
		return location.safeProperty(SFormalParameter.RECEIVER);
	}

	/**
	 * Sets whether this formal parameter is receiver.
	 *
	 * @param isReceiver <code>true</code> if this formal parameter is receiver, <code>false</code> otherwise.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter setReceiver(boolean isReceiver) {
		return location.safePropertyReplace(SFormalParameter.RECEIVER, isReceiver);
	}

	/**
	 * Mutates whether this formal parameter is receiver.
	 *
	 * @param mutation the mutation to apply to whether this formal parameter is receiver.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter setReceiver(Mutation<Boolean> mutation) {
		return location.safePropertyMutate(SFormalParameter.RECEIVER, mutation);
	}

	/**
	 * Returns the receiver type name of this formal parameter.
	 *
	 * @return the receiver type name of this formal parameter.
	 */
	public NodeOption<Name> receiverTypeName() {
		return location.safeTraversal(SFormalParameter.RECEIVER_TYPE_NAME);
	}

	/**
	 * Replaces the receiver type name of this formal parameter.
	 *
	 * @param receiverTypeName the replacement for the receiver type name of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withReceiverTypeName(NodeOption<Name> receiverTypeName) {
		return location.safeTraversalReplace(SFormalParameter.RECEIVER_TYPE_NAME, receiverTypeName);
	}

	/**
	 * Mutates the receiver type name of this formal parameter.
	 *
	 * @param mutation the mutation to apply to the receiver type name of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withReceiverTypeName(Mutation<NodeOption<Name>> mutation) {
		return location.safeTraversalMutate(SFormalParameter.RECEIVER_TYPE_NAME, mutation);
	}

	/**
	 * Replaces the receiver type name of this formal parameter.
	 *
	 * @param receiverTypeName the replacement for the receiver type name of this formal parameter.
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withReceiverTypeName(Name receiverTypeName) {
		return location.safeTraversalReplace(SFormalParameter.RECEIVER_TYPE_NAME, Trees.some(receiverTypeName));
	}

	/**
	 * Replaces the receiver type name of this formal parameter.
	 *
	 * @return the resulting mutated formal parameter.
	 */
	public FormalParameter withNoReceiverTypeName() {
		return location.safeTraversalReplace(SFormalParameter.RECEIVER_TYPE_NAME, Trees.<Name>none());
	}
}
