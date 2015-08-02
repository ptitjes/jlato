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

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Mutation;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.LocalVariableDecl;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.TRY_RESOURCES;
import static org.jlato.printer.IndentationConstraint.indent;
import static org.jlato.printer.IndentationConstraint.unIndent;
import static org.jlato.printer.SpacingConstraint.newLine;
import static org.jlato.printer.SpacingConstraint.space;

public class VariableDeclarationExpr extends TreeBase<VariableDeclarationExpr.State, Expr, VariableDeclarationExpr> implements Expr {

	public Kind kind() {
		return Kind.VariableDeclarationExpr;
	}

	private VariableDeclarationExpr(SLocation<VariableDeclarationExpr.State> location) {
		super(location);
	}

	public static STree<VariableDeclarationExpr.State> make(STree<LocalVariableDecl.State> declaration) {
		return new STree<VariableDeclarationExpr.State>(new VariableDeclarationExpr.State(declaration));
	}

	public VariableDeclarationExpr(LocalVariableDecl declaration) {
		super(new SLocation<VariableDeclarationExpr.State>(make(TreeBase.<LocalVariableDecl.State>treeOf(declaration))));
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

	public static class State extends SNodeState<State> implements Expr.State {

		public final STree<LocalVariableDecl.State> declaration;

		State(STree<LocalVariableDecl.State> declaration) {
			this.declaration = declaration;
		}

		public VariableDeclarationExpr.State withDeclaration(STree<LocalVariableDecl.State> declaration) {
			return new VariableDeclarationExpr.State(declaration);
		}

		@Override
		public Kind kind() {
			return Kind.VariableDeclarationExpr;
		}

		@Override
		protected Tree doInstantiate(SLocation<VariableDeclarationExpr.State> location) {
			return new VariableDeclarationExpr(location);
		}

		@Override
		public LexicalShape shape() {
			return shape;
		}

		@Override
		public STraversal firstChild() {
			return DECLARATION;
		}

		@Override
		public STraversal lastChild() {
			return DECLARATION;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			State state = (State) o;
			if (declaration == null ? state.declaration != null : !declaration.equals(state.declaration))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			int result = 17;
			if (declaration != null) result = 37 * result + declaration.hashCode();
			return result;
		}
	}

	private static STypeSafeTraversal<VariableDeclarationExpr.State, LocalVariableDecl.State, LocalVariableDecl> DECLARATION = new STypeSafeTraversal<VariableDeclarationExpr.State, LocalVariableDecl.State, LocalVariableDecl>() {

		@Override
		public STree<?> doTraverse(VariableDeclarationExpr.State state) {
			return state.declaration;
		}

		@Override
		public VariableDeclarationExpr.State doRebuildParentState(VariableDeclarationExpr.State state, STree<LocalVariableDecl.State> child) {
			return state.withDeclaration(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = child(DECLARATION);
}
