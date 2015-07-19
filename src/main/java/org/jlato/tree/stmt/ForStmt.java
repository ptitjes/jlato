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
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.expr.Expr;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class ForStmt extends TreeBase<ForStmt.State, Stmt, ForStmt> implements Stmt {

	public Kind kind() {
		return Kind.ForStmt;
	}

	private ForStmt(SLocation<ForStmt.State> location) {
		super(location);
	}

	public static STree<ForStmt.State> make(STree<SNodeListState> init, STree<Expr.State> compare, STree<SNodeListState> update, STree<Stmt.State> body) {
		return new STree<ForStmt.State>(new ForStmt.State(init, compare, update, body));
	}

	public ForStmt(NodeList<Expr> init, Expr compare, NodeList<Expr> update, Stmt body) {
		super(new SLocation<ForStmt.State>(make(TreeBase.<SNodeListState>nodeOf(init), TreeBase.<Expr.State>nodeOf(compare), TreeBase.<SNodeListState>nodeOf(update), TreeBase.<Stmt.State>nodeOf(body))));
	}

	public NodeList<Expr> init() {
		return location.safeTraversal(INIT);
	}

	public ForStmt withInit(NodeList<Expr> init) {
		return location.safeTraversalReplace(INIT, init);
	}

	public ForStmt withInit(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(INIT, mutation);
	}

	public Expr compare() {
		return location.safeTraversal(COMPARE);
	}

	public ForStmt withCompare(Expr compare) {
		return location.safeTraversalReplace(COMPARE, compare);
	}

	public ForStmt withCompare(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(COMPARE, mutation);
	}

	public NodeList<Expr> update() {
		return location.safeTraversal(UPDATE);
	}

	public ForStmt withUpdate(NodeList<Expr> update) {
		return location.safeTraversalReplace(UPDATE, update);
	}

	public ForStmt withUpdate(Mutation<NodeList<Expr>> mutation) {
		return location.safeTraversalMutate(UPDATE, mutation);
	}

	public Stmt body() {
		return location.safeTraversal(BODY);
	}

	public ForStmt withBody(Stmt body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public ForStmt withBody(Mutation<Stmt> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	public final static LexicalShape shape = composite(
			token(LToken.For), token(LToken.ParenthesisLeft).withSpacingBefore(space()),
			child(INIT, list(token(LToken.Comma).withSpacingAfter(space()))),
			token(LToken.SemiColon).withSpacingAfter(space()),
			child(COMPARE),
			token(LToken.SemiColon).withSpacingAfter(space()),
			child(UPDATE, list(token(LToken.Comma).withSpacingAfter(space()))),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			child(BODY)
	);
}
