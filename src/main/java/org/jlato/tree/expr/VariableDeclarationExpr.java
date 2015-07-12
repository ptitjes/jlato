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

package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.LocalVariableDecl;

import static org.jlato.internal.shapes.IndentationConstraint.Factory.indent;
import static org.jlato.internal.shapes.IndentationConstraint.Factory.unIndent;
import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalShape.Factory.token;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.newLine;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.space;
import static org.jlato.printer.FormattingSettings.IndentationContext.TRY_RESOURCES;

public class VariableDeclarationExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public VariableDeclarationExpr instantiate(SLocation location) {
			return new VariableDeclarationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private VariableDeclarationExpr(SLocation location) {
		super(location);
	}

	public VariableDeclarationExpr(LocalVariableDecl declaration) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(declaration)))));
	}

	public LocalVariableDecl declaration() {
		return location.nodeChild(DECLARATION);
	}

	public VariableDeclarationExpr withDeclaration(LocalVariableDecl declaration) {
		return location.nodeWithChild(DECLARATION, declaration);
	}

	private static final int DECLARATION = 0;

	public final static LexicalShape shape = child(DECLARATION);

	public static final LexicalShape resourcesShape = list(
			token(LToken.ParenthesisLeft)
					.withIndentationAfter(indent(TRY_RESOURCES)),
			token(LToken.SemiColon)
					.withSpacingAfter(newLine()),
			token(LToken.ParenthesisRight)
					.withIndentationBefore(unIndent(TRY_RESOURCES))
					.withSpacingAfter(space())
	);
}
