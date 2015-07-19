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
import org.jlato.tree.NodeList;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class NormalAnnotationExpr extends TreeBase<NormalAnnotationExpr.State, AnnotationExpr, NormalAnnotationExpr> implements AnnotationExpr {

	public Kind kind() {
		return Kind.NormalAnnotationExpr;
	}

	private NormalAnnotationExpr(SLocation<NormalAnnotationExpr.State> location) {
		super(location);
	}

	public static STree<NormalAnnotationExpr.State> make(STree<QualifiedName.State> name, STree<SNodeListState> pairs) {
		return new STree<NormalAnnotationExpr.State>(new NormalAnnotationExpr.State(name, pairs));
	}

	public NormalAnnotationExpr(QualifiedName name, NodeList<MemberValuePair> pairs) {
		super(new SLocation<NormalAnnotationExpr.State>(make(TreeBase.<QualifiedName.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(pairs))));
	}

	public QualifiedName name() {
		return location.safeTraversal(NAME);
	}

	public NormalAnnotationExpr withName(QualifiedName name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public NormalAnnotationExpr withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeList<MemberValuePair> pairs() {
		return location.safeTraversal(PAIRS);
	}

	public NormalAnnotationExpr withPairs(NodeList<MemberValuePair> pairs) {
		return location.safeTraversalReplace(PAIRS, pairs);
	}

	public NormalAnnotationExpr withPairs(Mutation<NodeList<MemberValuePair>> mutation) {
		return location.safeTraversalMutate(PAIRS, mutation);
	}

	public final static LexicalShape shape = composite(
			token(LToken.At), child(NAME),
			token(LToken.ParenthesisLeft),
			child(PAIRS, list(token(LToken.Comma).withSpacingAfter(space()))),
			token(LToken.ParenthesisRight)
	);
}
