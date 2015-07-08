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
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.tree.SLocation;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.IndentationConstraint.Factory.indent;
import static org.jlato.internal.shapes.IndentationConstraint.Factory.unIndent;
import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.spacing;
import static org.jlato.printer.FormattingSettings.SpacingLocation.LabeledStmt_AfterLabel;

public class LabeledStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public LabeledStmt instantiate(SLocation location) {
			return new LabeledStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private LabeledStmt(SLocation location) {
		super(location);
	}

	public LabeledStmt(Name label, Stmt stmt) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(label, stmt)))));
	}

	public Name label() {
		return location.nodeChild(LABEL);
	}

	public LabeledStmt withLabel(Name label) {
		return location.nodeWithChild(LABEL, label);
	}

	public Stmt stmt() {
		return location.nodeChild(STMT);
	}

	public LabeledStmt withStmt(Stmt stmt) {
		return location.nodeWithChild(STMT, stmt);
	}

	private static final int LABEL = 0;
	private static final int STMT = 1;

	public final static LexicalShape shape = composite(
			none().withIndentation(indent(IndentationContext.LABEL)),
			child(LABEL),
			token(LToken.Colon).withSpacingAfter(spacing(LabeledStmt_AfterLabel)),
			none().withIndentation(unIndent(IndentationContext.LABEL)),
			child(STMT)
	);
}
