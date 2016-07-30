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
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDVariableDeclarator;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.VariableDeclaratorId;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a variable declarator.
 */
public class SVariableDeclarator extends SNode<SVariableDeclarator> implements STree {

	/**
	 * Creates a <code>BUTree</code> with a new variable declarator.
	 *
	 * @param id   the identifier child <code>BUTree</code>.
	 * @param init the init child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a variable declarator.
	 */
	public static BUTree<SVariableDeclarator> make(BUTree<SVariableDeclaratorId> id, BUTree<SNodeOption> init) {
		return new BUTree<SVariableDeclarator>(new SVariableDeclarator(id, init));
	}

	/**
	 * The identifier of this variable declarator state.
	 */
	public final BUTree<SVariableDeclaratorId> id;

	/**
	 * The init of this variable declarator state.
	 */
	public final BUTree<SNodeOption> init;

	/**
	 * Constructs a variable declarator state.
	 *
	 * @param id   the identifier child <code>BUTree</code>.
	 * @param init the init child <code>BUTree</code>.
	 */
	public SVariableDeclarator(BUTree<SVariableDeclaratorId> id, BUTree<SNodeOption> init) {
		this.id = id;
		this.init = init;
	}

	/**
	 * Returns the kind of this variable declarator.
	 *
	 * @return the kind of this variable declarator.
	 */
	@Override
	public Kind kind() {
		return Kind.VariableDeclarator;
	}

	/**
	 * Replaces the identifier of this variable declarator state.
	 *
	 * @param id the replacement for the identifier of this variable declarator state.
	 * @return the resulting mutated variable declarator state.
	 */
	public SVariableDeclarator withId(BUTree<SVariableDeclaratorId> id) {
		return new SVariableDeclarator(id, init);
	}

	/**
	 * Replaces the init of this variable declarator state.
	 *
	 * @param init the replacement for the init of this variable declarator state.
	 * @return the resulting mutated variable declarator state.
	 */
	public SVariableDeclarator withInit(BUTree<SNodeOption> init) {
		return new SVariableDeclarator(id, init);
	}

	/**
	 * Builds a variable declarator facade for the specified variable declarator <code>TDLocation</code>.
	 *
	 * @param location the variable declarator <code>TDLocation</code>.
	 * @return a variable declarator facade for the specified variable declarator <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SVariableDeclarator> location) {
		return new TDVariableDeclarator(location);
	}

	/**
	 * Returns the shape for this variable declarator state.
	 *
	 * @return the shape for this variable declarator state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this variable declarator state.
	 *
	 * @return the first child traversal for this variable declarator state.
	 */
	@Override
	public STraversal firstChild() {
		return ID;
	}

	/**
	 * Returns the last child traversal for this variable declarator state.
	 *
	 * @return the last child traversal for this variable declarator state.
	 */
	@Override
	public STraversal lastChild() {
		return INIT;
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
		SVariableDeclarator state = (SVariableDeclarator) o;
		if (id == null ? state.id != null : !id.equals(state.id))
			return false;
		if (!init.equals(state.init))
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
		if (id != null) result = 37 * result + id.hashCode();
		result = 37 * result + init.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SVariableDeclarator, SVariableDeclaratorId, VariableDeclaratorId> ID = new STypeSafeTraversal<SVariableDeclarator, SVariableDeclaratorId, VariableDeclaratorId>() {

		@Override
		public BUTree<?> doTraverse(SVariableDeclarator state) {
			return state.id;
		}

		@Override
		public SVariableDeclarator doRebuildParentState(SVariableDeclarator state, BUTree<SVariableDeclaratorId> child) {
			return state.withId(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return INIT;
		}
	};

	public static STypeSafeTraversal<SVariableDeclarator, SNodeOption, NodeOption<Expr>> INIT = new STypeSafeTraversal<SVariableDeclarator, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SVariableDeclarator state) {
			return state.init;
		}

		@Override
		public SVariableDeclarator doRebuildParentState(SVariableDeclarator state, BUTree<SNodeOption> child) {
			return state.withInit(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return ID;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape initializerShape = composite(
			token(LToken.Assign).withSpacing(space(), space()),
			element()
	);

	public static final LexicalShape shape = composite(
			child(ID),
			child(INIT, when(some(), initializerShape))
	);

	public static final LexicalShape listShape = list(none(), token(LToken.Comma).withSpacingAfter(space()), none());
}
