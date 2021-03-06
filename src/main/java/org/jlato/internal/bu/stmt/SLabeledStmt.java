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

package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDLabeledStmt;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.Stmt;

import static org.jlato.internal.shapes.IndentationConstraint.indent;
import static org.jlato.internal.shapes.IndentationConstraint.unIndent;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.spacing;
import static org.jlato.printer.FormattingSettings.IndentationContext.Label;
import static org.jlato.printer.FormattingSettings.SpacingLocation.LabeledStmt_AfterLabel;

/**
 * A state object for a labeled statement.
 */
public class SLabeledStmt extends SNode<SLabeledStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new labeled statement.
	 *
	 * @param label the label child <code>BUTree</code>.
	 * @param stmt  the statement child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a labeled statement.
	 */
	public static BUTree<SLabeledStmt> make(BUTree<SName> label, BUTree<? extends SStmt> stmt) {
		return new BUTree<SLabeledStmt>(new SLabeledStmt(label, stmt));
	}

	/**
	 * The label of this labeled statement state.
	 */
	public final BUTree<SName> label;

	/**
	 * The statement of this labeled statement state.
	 */
	public final BUTree<? extends SStmt> stmt;

	/**
	 * Constructs a labeled statement state.
	 *
	 * @param label the label child <code>BUTree</code>.
	 * @param stmt  the statement child <code>BUTree</code>.
	 */
	public SLabeledStmt(BUTree<SName> label, BUTree<? extends SStmt> stmt) {
		this.label = label;
		this.stmt = stmt;
	}

	/**
	 * Returns the kind of this labeled statement.
	 *
	 * @return the kind of this labeled statement.
	 */
	@Override
	public Kind kind() {
		return Kind.LabeledStmt;
	}

	/**
	 * Replaces the label of this labeled statement state.
	 *
	 * @param label the replacement for the label of this labeled statement state.
	 * @return the resulting mutated labeled statement state.
	 */
	public SLabeledStmt withLabel(BUTree<SName> label) {
		return new SLabeledStmt(label, stmt);
	}

	/**
	 * Replaces the statement of this labeled statement state.
	 *
	 * @param stmt the replacement for the statement of this labeled statement state.
	 * @return the resulting mutated labeled statement state.
	 */
	public SLabeledStmt withStmt(BUTree<? extends SStmt> stmt) {
		return new SLabeledStmt(label, stmt);
	}

	/**
	 * Builds a labeled statement facade for the specified labeled statement <code>TDLocation</code>.
	 *
	 * @param location the labeled statement <code>TDLocation</code>.
	 * @return a labeled statement facade for the specified labeled statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SLabeledStmt> location) {
		return new TDLabeledStmt(location);
	}

	/**
	 * Returns the shape for this labeled statement state.
	 *
	 * @return the shape for this labeled statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this labeled statement state.
	 *
	 * @return the first child traversal for this labeled statement state.
	 */
	@Override
	public STraversal firstChild() {
		return LABEL;
	}

	/**
	 * Returns the last child traversal for this labeled statement state.
	 *
	 * @return the last child traversal for this labeled statement state.
	 */
	@Override
	public STraversal lastChild() {
		return STMT;
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
		SLabeledStmt state = (SLabeledStmt) o;
		if (label == null ? state.label != null : !label.equals(state.label))
			return false;
		if (stmt == null ? state.stmt != null : !stmt.equals(state.stmt))
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
		if (label != null) result = 37 * result + label.hashCode();
		if (stmt != null) result = 37 * result + stmt.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SLabeledStmt, SName, Name> LABEL = new STypeSafeTraversal<SLabeledStmt, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SLabeledStmt state) {
			return state.label;
		}

		@Override
		public SLabeledStmt doRebuildParentState(SLabeledStmt state, BUTree<SName> child) {
			return state.withLabel(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return STMT;
		}
	};

	public static STypeSafeTraversal<SLabeledStmt, SStmt, Stmt> STMT = new STypeSafeTraversal<SLabeledStmt, SStmt, Stmt>() {

		@Override
		public BUTree<?> doTraverse(SLabeledStmt state) {
			return state.stmt;
		}

		@Override
		public SLabeledStmt doRebuildParentState(SLabeledStmt state, BUTree<SStmt> child) {
			return state.withStmt(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return LABEL;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			none().withIndentationAfter(indent(Label)),
			child(LABEL),
			token(LToken.Colon).withSpacingAfter(spacing(LabeledStmt_AfterLabel)),
			none().withIndentationBefore(unIndent(Label)),
			child(STMT)
	);
}
