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
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDNormalAnnotationExpr;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.MemberValuePair;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a normal annotation expression.
 */
public class SNormalAnnotationExpr extends SNode<SNormalAnnotationExpr> implements SAnnotationExpr {

	/**
	 * Creates a <code>BUTree</code> with a new normal annotation expression.
	 *
	 * @param name  the name child <code>BUTree</code>.
	 * @param pairs the pairs child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a normal annotation expression.
	 */
	public static BUTree<SNormalAnnotationExpr> make(BUTree<SQualifiedName> name, BUTree<SNodeList> pairs) {
		return new BUTree<SNormalAnnotationExpr>(new SNormalAnnotationExpr(name, pairs));
	}

	/**
	 * The name of this normal annotation expression state.
	 */
	public final BUTree<SQualifiedName> name;

	/**
	 * The pairs of this normal annotation expression state.
	 */
	public final BUTree<SNodeList> pairs;

	/**
	 * Constructs a normal annotation expression state.
	 *
	 * @param name  the name child <code>BUTree</code>.
	 * @param pairs the pairs child <code>BUTree</code>.
	 */
	public SNormalAnnotationExpr(BUTree<SQualifiedName> name, BUTree<SNodeList> pairs) {
		this.name = name;
		this.pairs = pairs;
	}

	/**
	 * Returns the kind of this normal annotation expression.
	 *
	 * @return the kind of this normal annotation expression.
	 */
	@Override
	public Kind kind() {
		return Kind.NormalAnnotationExpr;
	}

	/**
	 * Replaces the name of this normal annotation expression state.
	 *
	 * @param name the replacement for the name of this normal annotation expression state.
	 * @return the resulting mutated normal annotation expression state.
	 */
	public SNormalAnnotationExpr withName(BUTree<SQualifiedName> name) {
		return new SNormalAnnotationExpr(name, pairs);
	}

	/**
	 * Replaces the pairs of this normal annotation expression state.
	 *
	 * @param pairs the replacement for the pairs of this normal annotation expression state.
	 * @return the resulting mutated normal annotation expression state.
	 */
	public SNormalAnnotationExpr withPairs(BUTree<SNodeList> pairs) {
		return new SNormalAnnotationExpr(name, pairs);
	}

	/**
	 * Builds a normal annotation expression facade for the specified normal annotation expression <code>TDLocation</code>.
	 *
	 * @param location the normal annotation expression <code>TDLocation</code>.
	 * @return a normal annotation expression facade for the specified normal annotation expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SNormalAnnotationExpr> location) {
		return new TDNormalAnnotationExpr(location);
	}

	/**
	 * Returns the shape for this normal annotation expression state.
	 *
	 * @return the shape for this normal annotation expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this normal annotation expression state.
	 *
	 * @return the first child traversal for this normal annotation expression state.
	 */
	@Override
	public STraversal firstChild() {
		return NAME;
	}

	/**
	 * Returns the last child traversal for this normal annotation expression state.
	 *
	 * @return the last child traversal for this normal annotation expression state.
	 */
	@Override
	public STraversal lastChild() {
		return PAIRS;
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
		SNormalAnnotationExpr state = (SNormalAnnotationExpr) o;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!pairs.equals(state.pairs))
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
		result = 37 * result + pairs.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SNormalAnnotationExpr, SQualifiedName, QualifiedName> NAME = new STypeSafeTraversal<SNormalAnnotationExpr, SQualifiedName, QualifiedName>() {

		@Override
		public BUTree<?> doTraverse(SNormalAnnotationExpr state) {
			return state.name;
		}

		@Override
		public SNormalAnnotationExpr doRebuildParentState(SNormalAnnotationExpr state, BUTree<SQualifiedName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return PAIRS;
		}
	};

	public static STypeSafeTraversal<SNormalAnnotationExpr, SNodeList, NodeList<MemberValuePair>> PAIRS = new STypeSafeTraversal<SNormalAnnotationExpr, SNodeList, NodeList<MemberValuePair>>() {

		@Override
		public BUTree<?> doTraverse(SNormalAnnotationExpr state) {
			return state.pairs;
		}

		@Override
		public SNormalAnnotationExpr doRebuildParentState(SNormalAnnotationExpr state, BUTree<SNodeList> child) {
			return state.withPairs(child);
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
			child(PAIRS, list(token(LToken.Comma).withSpacingAfter(space()))),
			token(LToken.ParenthesisRight)
	);
}
