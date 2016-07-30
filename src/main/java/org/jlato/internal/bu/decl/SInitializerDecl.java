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
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDInitializerDecl;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an initializer declaration.
 */
public class SInitializerDecl extends SNode<SInitializerDecl> implements SMemberDecl {

	/**
	 * Creates a <code>BUTree</code> with a new initializer declaration.
	 *
	 * @param modifiers the modifiers child <code>BUTree</code>.
	 * @param body      the body child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an initializer declaration.
	 */
	public static BUTree<SInitializerDecl> make(BUTree<SNodeList> modifiers, BUTree<SBlockStmt> body) {
		return new BUTree<SInitializerDecl>(new SInitializerDecl(modifiers, body));
	}

	/**
	 * The modifiers of this initializer declaration state.
	 */
	public final BUTree<SNodeList> modifiers;

	/**
	 * The body of this initializer declaration state.
	 */
	public final BUTree<SBlockStmt> body;

	/**
	 * Constructs an initializer declaration state.
	 *
	 * @param modifiers the modifiers child <code>BUTree</code>.
	 * @param body      the body child <code>BUTree</code>.
	 */
	public SInitializerDecl(BUTree<SNodeList> modifiers, BUTree<SBlockStmt> body) {
		this.modifiers = modifiers;
		this.body = body;
	}

	/**
	 * Returns the kind of this initializer declaration.
	 *
	 * @return the kind of this initializer declaration.
	 */
	@Override
	public Kind kind() {
		return Kind.InitializerDecl;
	}

	/**
	 * Replaces the modifiers of this initializer declaration state.
	 *
	 * @param modifiers the replacement for the modifiers of this initializer declaration state.
	 * @return the resulting mutated initializer declaration state.
	 */
	public SInitializerDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SInitializerDecl(modifiers, body);
	}

	/**
	 * Replaces the body of this initializer declaration state.
	 *
	 * @param body the replacement for the body of this initializer declaration state.
	 * @return the resulting mutated initializer declaration state.
	 */
	public SInitializerDecl withBody(BUTree<SBlockStmt> body) {
		return new SInitializerDecl(modifiers, body);
	}

	/**
	 * Builds an initializer declaration facade for the specified initializer declaration <code>TDLocation</code>.
	 *
	 * @param location the initializer declaration <code>TDLocation</code>.
	 * @return an initializer declaration facade for the specified initializer declaration <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SInitializerDecl> location) {
		return new TDInitializerDecl(location);
	}

	/**
	 * Returns the shape for this initializer declaration state.
	 *
	 * @return the shape for this initializer declaration state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this initializer declaration state.
	 *
	 * @return the first child traversal for this initializer declaration state.
	 */
	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	/**
	 * Returns the last child traversal for this initializer declaration state.
	 *
	 * @return the last child traversal for this initializer declaration state.
	 */
	@Override
	public STraversal lastChild() {
		return BODY;
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
		SInitializerDecl state = (SInitializerDecl) o;
		if (!modifiers.equals(state.modifiers))
			return false;
		if (body == null ? state.body != null : !body.equals(state.body))
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
		if (body != null) result = 37 * result + body.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SInitializerDecl, SNodeList, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SInitializerDecl, SNodeList, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SInitializerDecl state) {
			return state.modifiers;
		}

		@Override
		public SInitializerDecl doRebuildParentState(SInitializerDecl state, BUTree<SNodeList> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SInitializerDecl, SBlockStmt, BlockStmt> BODY = new STypeSafeTraversal<SInitializerDecl, SBlockStmt, BlockStmt>() {

		@Override
		public BUTree<?> doTraverse(SInitializerDecl state) {
			return state.body;
		}

		@Override
		public SInitializerDecl doRebuildParentState(SInitializerDecl state, BUTree<SBlockStmt> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(MODIFIERS, SExtendedModifier.multiLineShape),
			child(BODY)
	);
}
