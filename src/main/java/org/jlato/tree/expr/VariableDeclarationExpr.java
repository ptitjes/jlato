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
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.decl.LocalVariableDecl;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.TRY_RESOURCES;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.newLine;
import static org.jlato.printer.SpacingConstraint.space;

import org.jlato.tree.Tree;

public class VariableDeclarationExpr extends TreeBase<VariableDeclarationExpr.State, Expr, VariableDeclarationExpr> implements Expr {

	public final static Kind kind = new Kind() {

	};

	private VariableDeclarationExpr(SLocation<VariableDeclarationExpr.State> location) {
		super(location);
	}

	public static STree<VariableDeclarationExpr.State> make(LocalVariableDecl declaration) {
		return new STree<VariableDeclarationExpr.State>(kind, new VariableDeclarationExpr.State(TreeBase.<LocalVariableDecl.State>nodeOf(declaration)));
	}

	public VariableDeclarationExpr(LocalVariableDecl declaration) {
		super(new SLocation<VariableDeclarationExpr.State>(make(declaration)));
	}

	public LocalVariableDecl declaration() {
		return location.safeTraversal(DECLARATION);
	}

	public VariableDeclarationExpr withDeclaration(LocalVariableDecl declaration) {
		return location.safeTraversalReplace(DECLARATION, declaration);
	}

	public VariableDeclarationExpr withDeclaration(Mutation<LocalVariableDecl> mutation) {
		return location.safeTraversalMutate(DECLARATION, mutation);
	}

	private static final STraversal<VariableDeclarationExpr.State> DECLARATION = new STraversal<VariableDeclarationExpr.State>() {

		public STree<?> traverse(VariableDeclarationExpr.State state) {
			return state.declaration;
		}

		public VariableDeclarationExpr.State rebuildParentState(VariableDeclarationExpr.State state, STree<?> child) {
			return state.withDeclaration((STree) child);
		}

		public STraversal<VariableDeclarationExpr.State> leftSibling(VariableDeclarationExpr.State state) {
			return null;
		}

		public STraversal<VariableDeclarationExpr.State> rightSibling(VariableDeclarationExpr.State state) {
			return null;
		}
	};

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

	public static class State extends SNodeState<State> {

		public final STree<LocalVariableDecl.State> declaration;

		State(STree<LocalVariableDecl.State> declaration) {
			this.declaration = declaration;
		}

		public VariableDeclarationExpr.State withDeclaration(STree<LocalVariableDecl.State> declaration) {
			return new VariableDeclarationExpr.State(declaration);
		}

		public STraversal<VariableDeclarationExpr.State> firstChild() {
			return null;
		}

		public STraversal<VariableDeclarationExpr.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<VariableDeclarationExpr.State> location) {
			return new VariableDeclarationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
