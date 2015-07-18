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

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.tree.Mutation;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.LabeledStmt_AfterLabel;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.spacing;

import org.jlato.tree.Tree;

public class LabeledStmt extends TreeBase<LabeledStmt.State, Stmt, LabeledStmt> implements Stmt {

	public final static SKind<LabeledStmt.State> kind = new SKind<LabeledStmt.State>() {

	};

	private LabeledStmt(SLocation<LabeledStmt.State> location) {
		super(location);
	}

	public static STree<LabeledStmt.State> make(Name label, Stmt stmt) {
		return new STree<LabeledStmt.State>(kind, new LabeledStmt.State(TreeBase.<Name.State>nodeOf(label), TreeBase.<Stmt.State>nodeOf(stmt)));
	}

	public LabeledStmt(Name label, Stmt stmt) {
		super(new SLocation<LabeledStmt.State>(make(label, stmt)));
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

	private static final STraversal<LabeledStmt.State> LABEL = new STraversal<LabeledStmt.State>() {

		public STree<?> traverse(LabeledStmt.State state) {
			return state.label;
		}

		public LabeledStmt.State rebuildParentState(LabeledStmt.State state, STree<?> child) {
			return state.withLabel((STree) child);
		}

		public STraversal<LabeledStmt.State> leftSibling(LabeledStmt.State state) {
			return null;
		}

		public STraversal<LabeledStmt.State> rightSibling(LabeledStmt.State state) {
			return STMT;
		}
	};
	private static final STraversal<LabeledStmt.State> STMT = new STraversal<LabeledStmt.State>() {

		public STree<?> traverse(LabeledStmt.State state) {
			return state.stmt;
		}

		public LabeledStmt.State rebuildParentState(LabeledStmt.State state, STree<?> child) {
			return state.withStmt((STree) child);
		}

		public STraversal<LabeledStmt.State> leftSibling(LabeledStmt.State state) {
			return LABEL;
		}

		public STraversal<LabeledStmt.State> rightSibling(LabeledStmt.State state) {
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

	public static class State extends SNodeState<State> {

		public final STree<Name.State> label;

		public final STree<Stmt.State> stmt;

		State(STree<Name.State> label, STree<Stmt.State> stmt) {
			this.label = label;
			this.stmt = stmt;
		}

		public LabeledStmt.State withLabel(STree<Name.State> label) {
			return new LabeledStmt.State(label, stmt);
		}

		public LabeledStmt.State withStmt(STree<Stmt.State> stmt) {
			return new LabeledStmt.State(label, stmt);
		}

		public STraversal<LabeledStmt.State> firstChild() {
			return null;
		}

		public STraversal<LabeledStmt.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<LabeledStmt.State> location) {
			return new LabeledStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
