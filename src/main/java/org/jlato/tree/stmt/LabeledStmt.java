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
import org.jlato.tree.Kind;
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
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

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
		super(new SLocation<LabeledStmt.State>(make(TreeBase.<Name.State>nodeOf(label), TreeBase.<Stmt.State>nodeOf(stmt))));
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

	public final static LexicalShape shape = composite(
			none().withIndentationAfter(indent(IndentationContext.LABEL)),
			child(LABEL),
			token(LToken.Colon).withSpacingAfter(spacing(LabeledStmt_AfterLabel)),
			none().withIndentationBefore(unIndent(IndentationContext.LABEL)),
			child(STMT)
	);
}
