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
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDFieldDecl;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.VariableDeclarator;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a field declaration.
 */
public class SFieldDecl extends SNode<SFieldDecl> implements SMemberDecl {

	/**
	 * Creates a <code>BUTree</code> with a new field declaration.
	 *
	 * @param modifiers the modifiers child <code>BUTree</code>.
	 * @param type      the type child <code>BUTree</code>.
	 * @param variables the variables child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a field declaration.
	 */
	public static BUTree<SFieldDecl> make(BUTree<SNodeList> modifiers, BUTree<? extends SType> type, BUTree<SNodeList> variables) {
		return new BUTree<SFieldDecl>(new SFieldDecl(modifiers, type, variables));
	}

	/**
	 * The modifiers of this field declaration state.
	 */
	public final BUTree<SNodeList> modifiers;

	/**
	 * The type of this field declaration state.
	 */
	public final BUTree<? extends SType> type;

	/**
	 * The variables of this field declaration state.
	 */
	public final BUTree<SNodeList> variables;

	/**
	 * Constructs a field declaration state.
	 *
	 * @param modifiers the modifiers child <code>BUTree</code>.
	 * @param type      the type child <code>BUTree</code>.
	 * @param variables the variables child <code>BUTree</code>.
	 */
	public SFieldDecl(BUTree<SNodeList> modifiers, BUTree<? extends SType> type, BUTree<SNodeList> variables) {
		this.modifiers = modifiers;
		this.type = type;
		this.variables = variables;
	}

	/**
	 * Returns the kind of this field declaration.
	 *
	 * @return the kind of this field declaration.
	 */
	@Override
	public Kind kind() {
		return Kind.FieldDecl;
	}

	/**
	 * Replaces the modifiers of this field declaration state.
	 *
	 * @param modifiers the replacement for the modifiers of this field declaration state.
	 * @return the resulting mutated field declaration state.
	 */
	public SFieldDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SFieldDecl(modifiers, type, variables);
	}

	/**
	 * Replaces the type of this field declaration state.
	 *
	 * @param type the replacement for the type of this field declaration state.
	 * @return the resulting mutated field declaration state.
	 */
	public SFieldDecl withType(BUTree<? extends SType> type) {
		return new SFieldDecl(modifiers, type, variables);
	}

	/**
	 * Replaces the variables of this field declaration state.
	 *
	 * @param variables the replacement for the variables of this field declaration state.
	 * @return the resulting mutated field declaration state.
	 */
	public SFieldDecl withVariables(BUTree<SNodeList> variables) {
		return new SFieldDecl(modifiers, type, variables);
	}

	/**
	 * Builds a field declaration facade for the specified field declaration <code>TDLocation</code>.
	 *
	 * @param location the field declaration <code>TDLocation</code>.
	 * @return a field declaration facade for the specified field declaration <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SFieldDecl> location) {
		return new TDFieldDecl(location);
	}

	/**
	 * Returns the shape for this field declaration state.
	 *
	 * @return the shape for this field declaration state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this field declaration state.
	 *
	 * @return the first child traversal for this field declaration state.
	 */
	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	/**
	 * Returns the last child traversal for this field declaration state.
	 *
	 * @return the last child traversal for this field declaration state.
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
		SFieldDecl state = (SFieldDecl) o;
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

	public static STypeSafeTraversal<SFieldDecl, SNodeList, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SFieldDecl, SNodeList, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SFieldDecl state) {
			return state.modifiers;
		}

		@Override
		public SFieldDecl doRebuildParentState(SFieldDecl state, BUTree<SNodeList> child) {
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

	public static STypeSafeTraversal<SFieldDecl, SType, Type> TYPE = new STypeSafeTraversal<SFieldDecl, SType, Type>() {

		@Override
		public BUTree<?> doTraverse(SFieldDecl state) {
			return state.type;
		}

		@Override
		public SFieldDecl doRebuildParentState(SFieldDecl state, BUTree<SType> child) {
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

	public static STypeSafeTraversal<SFieldDecl, SNodeList, NodeList<VariableDeclarator>> VARIABLES = new STypeSafeTraversal<SFieldDecl, SNodeList, NodeList<VariableDeclarator>>() {

		@Override
		public BUTree<?> doTraverse(SFieldDecl state) {
			return state.variables;
		}

		@Override
		public SFieldDecl doRebuildParentState(SFieldDecl state, BUTree<SNodeList> child) {
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
			child(MODIFIERS, SExtendedModifier.multiLineShape),
			child(TYPE),
			child(VARIABLES, SVariableDeclarator.listShape).withSpacingBefore(space()),
			token(LToken.SemiColon)
	);
}
