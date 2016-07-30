/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDSingleMemberAnnotationExpr;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a single member annotation expression.
 */
public class SSingleMemberAnnotationExpr extends SNode<SSingleMemberAnnotationExpr> implements SAnnotationExpr {

	/**
	 * Creates a <code>BUTree</code> with a new single member annotation expression.
	 *
	 * @param name        the name child <code>BUTree</code>.
	 * @param memberValue the member value child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a single member annotation expression.
	 */
	public static BUTree<SSingleMemberAnnotationExpr> make(BUTree<SQualifiedName> name, BUTree<? extends SExpr> memberValue) {
		return new BUTree<SSingleMemberAnnotationExpr>(new SSingleMemberAnnotationExpr(name, memberValue));
	}

	/**
	 * The name of this single member annotation expression state.
	 */
	public final BUTree<SQualifiedName> name;

	/**
	 * The member value of this single member annotation expression state.
	 */
	public final BUTree<? extends SExpr> memberValue;

	/**
	 * Constructs a single member annotation expression state.
	 *
	 * @param name        the name child <code>BUTree</code>.
	 * @param memberValue the member value child <code>BUTree</code>.
	 */
	public SSingleMemberAnnotationExpr(BUTree<SQualifiedName> name, BUTree<? extends SExpr> memberValue) {
		this.name = name;
		this.memberValue = memberValue;
	}

	/**
	 * Returns the kind of this single member annotation expression.
	 *
	 * @return the kind of this single member annotation expression.
	 */
	@Override
	public Kind kind() {
		return Kind.SingleMemberAnnotationExpr;
	}

	/**
	 * Replaces the name of this single member annotation expression state.
	 *
	 * @param name the replacement for the name of this single member annotation expression state.
	 * @return the resulting mutated single member annotation expression state.
	 */
	public SSingleMemberAnnotationExpr withName(BUTree<SQualifiedName> name) {
		return new SSingleMemberAnnotationExpr(name, memberValue);
	}

	/**
	 * Replaces the member value of this single member annotation expression state.
	 *
	 * @param memberValue the replacement for the member value of this single member annotation expression state.
	 * @return the resulting mutated single member annotation expression state.
	 */
	public SSingleMemberAnnotationExpr withMemberValue(BUTree<? extends SExpr> memberValue) {
		return new SSingleMemberAnnotationExpr(name, memberValue);
	}

	/**
	 * Builds a single member annotation expression facade for the specified single member annotation expression <code>TDLocation</code>.
	 *
	 * @param location the single member annotation expression <code>TDLocation</code>.
	 * @return a single member annotation expression facade for the specified single member annotation expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SSingleMemberAnnotationExpr> location) {
		return new TDSingleMemberAnnotationExpr(location);
	}

	/**
	 * Returns the shape for this single member annotation expression state.
	 *
	 * @return the shape for this single member annotation expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this single member annotation expression state.
	 *
	 * @return the first child traversal for this single member annotation expression state.
	 */
	@Override
	public STraversal firstChild() {
		return NAME;
	}

	/**
	 * Returns the last child traversal for this single member annotation expression state.
	 *
	 * @return the last child traversal for this single member annotation expression state.
	 */
	@Override
	public STraversal lastChild() {
		return MEMBER_VALUE;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SSingleMemberAnnotationExpr state = (SSingleMemberAnnotationExpr) o;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (memberValue == null ? state.memberValue != null : !memberValue.equals(state.memberValue))
			return false;
		return true;
	}

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		int result = 17;
		if (name != null) result = 37 * result + name.hashCode();
		if (memberValue != null) result = 37 * result + memberValue.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SSingleMemberAnnotationExpr, SQualifiedName, QualifiedName> NAME = new STypeSafeTraversal<SSingleMemberAnnotationExpr, SQualifiedName, QualifiedName>() {

		@Override
		public BUTree<?> doTraverse(SSingleMemberAnnotationExpr state) {
			return state.name;
		}

		@Override
		public SSingleMemberAnnotationExpr doRebuildParentState(SSingleMemberAnnotationExpr state, BUTree<SQualifiedName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return MEMBER_VALUE;
		}
	};

	public static STypeSafeTraversal<SSingleMemberAnnotationExpr, SExpr, Expr> MEMBER_VALUE = new STypeSafeTraversal<SSingleMemberAnnotationExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SSingleMemberAnnotationExpr state) {
			return state.memberValue;
		}

		@Override
		public SSingleMemberAnnotationExpr doRebuildParentState(SSingleMemberAnnotationExpr state, BUTree<SExpr> child) {
			return state.withMemberValue(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			token(LToken.At), child(NAME),
			token(LToken.ParenthesisLeft),
			child(MEMBER_VALUE),
			token(LToken.ParenthesisRight)
	);
}
