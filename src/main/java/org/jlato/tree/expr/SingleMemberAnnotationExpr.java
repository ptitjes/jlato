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
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.*;

import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class SingleMemberAnnotationExpr extends TreeBase<SingleMemberAnnotationExpr.State, AnnotationExpr, SingleMemberAnnotationExpr> implements AnnotationExpr {

	public Kind kind() {
		return Kind.SingleMemberAnnotationExpr;
	}

	private SingleMemberAnnotationExpr(SLocation<SingleMemberAnnotationExpr.State> location) {
		super(location);
	}

	public static STree<SingleMemberAnnotationExpr.State> make(STree<QualifiedName.State> name, STree<? extends Expr.State> memberValue) {
		return new STree<SingleMemberAnnotationExpr.State>(new SingleMemberAnnotationExpr.State(name, memberValue));
	}

	public SingleMemberAnnotationExpr(QualifiedName name, Expr memberValue) {
		super(new SLocation<SingleMemberAnnotationExpr.State>(make(TreeBase.<QualifiedName.State>nodeOf(name), TreeBase.<Expr.State>nodeOf(memberValue))));
	}

	public QualifiedName name() {
		return location.safeTraversal(NAME);
	}

	public SingleMemberAnnotationExpr withName(QualifiedName name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public SingleMemberAnnotationExpr withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public Expr memberValue() {
		return location.safeTraversal(MEMBER_VALUE);
	}

	public SingleMemberAnnotationExpr withMemberValue(Expr memberValue) {
		return location.safeTraversalReplace(MEMBER_VALUE, memberValue);
	}

	public SingleMemberAnnotationExpr withMemberValue(Mutation<Expr> mutation) {
		return location.safeTraversalMutate(MEMBER_VALUE, mutation);
	}

	public final static LexicalShape shape = composite(
			token(LToken.At), child(NAME),
			token(LToken.ParenthesisLeft),
			child(MEMBER_VALUE),
			token(LToken.ParenthesisRight)
	);
}
