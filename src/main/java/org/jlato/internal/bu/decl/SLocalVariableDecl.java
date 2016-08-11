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

package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDLocalVariableDecl;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.VariableDeclarator;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;
import static org.jlato.internal.shapes.SpacingConstraint.space;

/**
 * A state object for a local variable declaration.
 */
public class SLocalVariableDecl extends SNode<SLocalVariableDecl> implements SDecl {

	/**
	 * Creates a <code>BUTree</code> with a new local variable declaration.
	 *
	 * @param modifiers the modifiers child <code>BUTree</code>.
	 * @param type      the type child <code>BUTree</code>.
	 * @param variables the variables child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a local variable declaration.
	 */
	public static BUTree<SLocalVariableDecl> make(BUTree<SNodeList> modifiers, BUTree<? extends SType> type, BUTree<SNodeList> variables) {
		return new BUTree<SLocalVariableDecl>(new SLocalVariableDecl(modifiers, type, variables));
	}

	/**
	 * The modifiers of this local variable declaration state.
	 */
	public final BUTree<SNodeList> modifiers;

	/**
	 * The type of this local variable declaration state.
	 */
	public final BUTree<? extends SType> type;

	/**
	 * The variables of this local variable declaration state.
	 */
	public final BUTree<SNodeList> variables;

	/**
	 * Constructs a local variable declaration state.
	 *
	 * @param modifiers the modifiers child <code>BUTree</code>.
	 * @param type      the type child <code>BUTree</code>.
	 * @param variables the variables child <code>BUTree</code>.
	 */
	public SLocalVariableDecl(BUTree<SNodeList> modifiers, BUTree<? extends SType> type, BUTree<SNodeList> variables) {
		this.modifiers = modifiers;
		this.type = type;
		this.variables = variables;
	}

	/**
	 * Returns the kind of this local variable declaration.
	 *
	 * @return the kind of this local variable declaration.
	 */
	@Override
	public Kind kind() {
		return Kind.LocalVariableDecl;
	}

	/**
	 * Replaces the modifiers of this local variable declaration state.
	 *
	 * @param modifiers the replacement for the modifiers of this local variable declaration state.
	 * @return the resulting mutated local variable declaration state.
	 */
	public SLocalVariableDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SLocalVariableDecl(modifiers, type, variables);
	}

	/**
	 * Replaces the type of this local variable declaration state.
	 *
	 * @param type the replacement for the type of this local variable declaration state.
	 * @return the resulting mutated local variable declaration state.
	 */
	public SLocalVariableDecl withType(BUTree<? extends SType> type) {
		return new SLocalVariableDecl(modifiers, type, variables);
	}

	/**
	 * Replaces the variables of this local variable declaration state.
	 *
	 * @param variables the replacement for the variables of this local variable declaration state.
	 * @return the resulting mutated local variable declaration state.
	 */
	public SLocalVariableDecl withVariables(BUTree<SNodeList> variables) {
		return new SLocalVariableDecl(modifiers, type, variables);
	}

	/**
	 * Builds a local variable declaration facade for the specified local variable declaration <code>TDLocation</code>.
	 *
	 * @param location the local variable declaration <code>TDLocation</code>.
	 * @return a local variable declaration facade for the specified local variable declaration <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SLocalVariableDecl> location) {
		return new TDLocalVariableDecl(location);
	}

	/**
	 * Returns the shape for this local variable declaration state.
	 *
	 * @return the shape for this local variable declaration state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this local variable declaration state.
	 *
	 * @return the first child traversal for this local variable declaration state.
	 */
	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	/**
	 * Returns the last child traversal for this local variable declaration state.
	 *
	 * @return the last child traversal for this local variable declaration state.
	 */
	@Override
	public STraversal lastChild() {
		return VARIABLES;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SLocalVariableDecl state = (SLocalVariableDecl) o;
		if (!modifiers.equals(state.modifiers))
			return false;
		if (type == null ? state.type != null : !type.equals(state.type))
			return false;
		if (!variables.equals(state.variables))
			return false;
		return true;
	}

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + modifiers.hashCode();
		if (type != null) result = 37 * result + type.hashCode();
		result = 37 * result + variables.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SLocalVariableDecl, SNodeList, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SLocalVariableDecl, SNodeList, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SLocalVariableDecl state) {
			return state.modifiers;
		}

		@Override
		public SLocalVariableDecl doRebuildParentState(SLocalVariableDecl state, BUTree<SNodeList> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return TYPE;
		}
	};

	public static STypeSafeTraversal<SLocalVariableDecl, SType, Type> TYPE = new STypeSafeTraversal<SLocalVariableDecl, SType, Type>() {

		@Override
		public BUTree<?> doTraverse(SLocalVariableDecl state) {
			return state.type;
		}

		@Override
		public SLocalVariableDecl doRebuildParentState(SLocalVariableDecl state, BUTree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return VARIABLES;
		}
	};

	public static STypeSafeTraversal<SLocalVariableDecl, SNodeList, NodeList<VariableDeclarator>> VARIABLES = new STypeSafeTraversal<SLocalVariableDecl, SNodeList, NodeList<VariableDeclarator>>() {

		@Override
		public BUTree<?> doTraverse(SLocalVariableDecl state) {
			return state.variables;
		}

		@Override
		public SLocalVariableDecl doRebuildParentState(SLocalVariableDecl state, BUTree<SNodeList> child) {
			return state.withVariables(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(MODIFIERS, SExtendedModifier.singleLineShape),
			child(TYPE),
			child(VARIABLES, SVariableDeclarator.listShape).withSpacingBefore(space())
	);
}
