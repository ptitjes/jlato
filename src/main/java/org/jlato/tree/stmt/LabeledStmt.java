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

package org.jlato.tree.stmt;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.LabeledStmt_AfterLabel;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.spacing;

public class LabeledStmt extends TreeBase<LabeledStmt.State, Stmt, LabeledStmt> implements Stmt {

	public Kind kind() {
		return Kind.LabeledStmt;
	}

	private LabeledStmt(SLocation<LabeledStmt.State> location) {
		super(location);
	}

	public static STree<LabeledStmt.State> make(STree<Name.State> label, STree<? extends Stmt.State> stmt) {
		return new STree<LabeledStmt.State>(new LabeledStmt.State(label, stmt));
	}

	public LabeledStmt(Name label, Stmt stmt) {
		super(new SLocation<LabeledStmt.State>(make(TreeBase.<Name.State>treeOf(label), TreeBase.<Stmt.State>treeOf(stmt))));
	}

	public Name label() {
		return location.safeTraversal(LABEL);
	}

	public LabeledStmt withLabel(Name label) {
		return location.safeTraversalReplace(LABEL, label);
	}

	public LabeledStmt withLabel(Mutation<Name> mutation) {
		return location.safeTraversalMutate(LABEL, mutation);
	}

	public Stmt stmt() {
		return location.safeTraversal(STMT);
	}

	public LabeledStmt withStmt(Stmt stmt) {
		return location.safeTraversalReplace(STMT, stmt);
	}

	public LabeledStmt withStmt(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(STMT, mutation);
	}

	public static class State extends SNodeState<State> implements Stmt.State {

		public final STree<Name.State> label;

		public final STree<? extends Stmt.State> stmt;

		State(STree<Name.State> label, STree<? extends Stmt.State> stmt) {
			this.label = label;
			this.stmt = stmt;
		}

		public LabeledStmt.State withLabel(STree<Name.State> label) {
			return new LabeledStmt.State(label, stmt);
		}

		public LabeledStmt.State withStmt(STree<? extends Stmt.State> stmt) {
			return new LabeledStmt.State(label, stmt);
		}

		@Override
		public Kind kind() {
			return Kind.LabeledStmt;
		}

		@Override
		protected Tree doInstantiate(SLocation<LabeledStmt.State> location) {
			return new LabeledStmt(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return LABEL;
		}

		@Override
		public STraversal lastChild() {
			return STMT;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (label == null ? state.label != null : !label.equals(state.label))
				return false;
			if (stmt == null ? state.stmt != null : !stmt.equals(state.stmt))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (label != null) result = 37 * result + label.hashCode();
			if (stmt != null) result = 37 * result + stmt.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<LabeledStmt.State, Name.State, Name> LABEL = new STypeSafeTraversal<LabeledStmt.State, Name.State, Name>() {

		@Override
		public STree<?> doTraverse(LabeledStmt.State state) {
			return state.label;
		}

		@Override
		public LabeledStmt.State doRebuildParentState(LabeledStmt.State state, STree<Name.State> child) {
			return state.withLabel(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return STMT;
		}
	};

	private static STypeSafeTraversal<LabeledStmt.State, Stmt.State, Stmt> STMT = new STypeSafeTraversal<LabeledStmt.State, Stmt.State, Stmt>() {

		@Override
		public STree<?> doTraverse(LabeledStmt.State state) {
			return state.stmt;
		}

		@Override
		public LabeledStmt.State doRebuildParentState(LabeledStmt.State state, STree<Stmt.State> child) {
			return state.withStmt(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return LABEL;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			none().withIndentationAfter(indent(IndentationContext.LABEL)),
			child(LABEL),
			token(LToken.Colon).withSpacingAfter(spacing(LabeledStmt_AfterLabel)),
			none().withIndentationBefore(unIndent(IndentationContext.LABEL)),
			child(STMT)
	);
}
