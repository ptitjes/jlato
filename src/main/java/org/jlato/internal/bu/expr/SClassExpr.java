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

package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDClassExpr;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a 'class' expression.
 */
public class SClassExpr extends SNode<SClassExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new 'class' expression.
	 *
	 * @param type the type child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a 'class' expression.
	 */
	public static BUTree<SClassExpr> make(BUTree<? extends SType> type) {
		return new BUTree<SClassExpr>(new SClassExpr(type));
	}

	/**
	 * The type of this 'class' expression state.
	 */
	public final BUTree<? extends SType> type;

	/**
	 * Constructs a 'class' expression state.
	 *
	 * @param type the type child <code>BUTree</code>.
	 */
	public SClassExpr(BUTree<? extends SType> type) {
		this.type = type;
	}

	/**
	 * Returns the kind of this 'class' expression.
	 *
	 * @return the kind of this 'class' expression.
	 */
	@Override
	public Kind kind() {
		return Kind.ClassExpr;
	}

	/**
	 * Replaces the type of this 'class' expression state.
	 *
	 * @param type the replacement for the type of this 'class' expression state.
	 * @return the resulting mutated 'class' expression state.
	 */
	public SClassExpr withType(BUTree<? extends SType> type) {
		return new SClassExpr(type);
	}

	/**
	 * Builds a 'class' expression facade for the specified 'class' expression <code>TDLocation</code>.
	 *
	 * @param location the 'class' expression <code>TDLocation</code>.
	 * @return a 'class' expression facade for the specified 'class' expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SClassExpr> location) {
		return new TDClassExpr(location);
	}

	/**
	 * Returns the shape for this 'class' expression state.
	 *
	 * @return the shape for this 'class' expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this 'class' expression state.
	 *
	 * @return the first child traversal for this 'class' expression state.
	 */
	@Override
	public STraversal firstChild() {
		return TYPE;
	}

	/**
	 * Returns the last child traversal for this 'class' expression state.
	 *
	 * @return the last child traversal for this 'class' expression state.
	 */
	@Override
	public STraversal lastChild() {
		return TYPE;
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
		SClassExpr state = (SClassExpr) o;
		if (type == null ? state.type != null : !type.equals(state.type))
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
		if (type != null) result = 37 * result + type.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SClassExpr, SType, Type> TYPE = new STypeSafeTraversal<SClassExpr, SType, Type>() {

		@Override
		public BUTree<?> doTraverse(SClassExpr state) {
			return state.type;
		}

		@Override
		public SClassExpr doRebuildParentState(SClassExpr state, BUTree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(TYPE),
			token(LToken.Dot), token(LToken.Class)
	);
}
