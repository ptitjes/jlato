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

package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDAnnotationDecl;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an annotation type declaration.
 */
public class SAnnotationDecl extends SNode<SAnnotationDecl> implements STypeDecl {

	/**
	 * Creates a <code>BUTree</code> with a new annotation type declaration.
	 *
	 * @param modifiers the modifiers child <code>BUTree</code>.
	 * @param name      the name child <code>BUTree</code>.
	 * @param members   the members child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an annotation type declaration.
	 */
	public static BUTree<SAnnotationDecl> make(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> members) {
		return new BUTree<SAnnotationDecl>(new SAnnotationDecl(modifiers, name, members));
	}

	/**
	 * The modifiers of this annotation type declaration state.
	 */
	public final BUTree<SNodeList> modifiers;

	/**
	 * The name of this annotation type declaration state.
	 */
	public final BUTree<SName> name;

	/**
	 * The members of this annotation type declaration state.
	 */
	public final BUTree<SNodeList> members;

	/**
	 * Constructs an annotation type declaration state.
	 *
	 * @param modifiers the modifiers child <code>BUTree</code>.
	 * @param name      the name child <code>BUTree</code>.
	 * @param members   the members child <code>BUTree</code>.
	 */
	public SAnnotationDecl(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> members) {
		this.modifiers = modifiers;
		this.name = name;
		this.members = members;
	}

	/**
	 * Returns the kind of this annotation type declaration.
	 *
	 * @return the kind of this annotation type declaration.
	 */
	@Override
	public Kind kind() {
		return Kind.AnnotationDecl;
	}

	/**
	 * Replaces the modifiers of this annotation type declaration state.
	 *
	 * @param modifiers the replacement for the modifiers of this annotation type declaration state.
	 * @return the resulting mutated annotation type declaration state.
	 */
	public SAnnotationDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SAnnotationDecl(modifiers, name, members);
	}

	/**
	 * Replaces the name of this annotation type declaration state.
	 *
	 * @param name the replacement for the name of this annotation type declaration state.
	 * @return the resulting mutated annotation type declaration state.
	 */
	public SAnnotationDecl withName(BUTree<SName> name) {
		return new SAnnotationDecl(modifiers, name, members);
	}

	/**
	 * Replaces the members of this annotation type declaration state.
	 *
	 * @param members the replacement for the members of this annotation type declaration state.
	 * @return the resulting mutated annotation type declaration state.
	 */
	public SAnnotationDecl withMembers(BUTree<SNodeList> members) {
		return new SAnnotationDecl(modifiers, name, members);
	}

	/**
	 * Builds an annotation type declaration facade for the specified annotation type declaration <code>TDLocation</code>.
	 *
	 * @param location the annotation type declaration <code>TDLocation</code>.
	 * @return an annotation type declaration facade for the specified annotation type declaration <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SAnnotationDecl> location) {
		return new TDAnnotationDecl(location);
	}

	/**
	 * Returns the shape for this annotation type declaration state.
	 *
	 * @return the shape for this annotation type declaration state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this annotation type declaration state.
	 *
	 * @return the first child traversal for this annotation type declaration state.
	 */
	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	/**
	 * Returns the last child traversal for this annotation type declaration state.
	 *
	 * @return the last child traversal for this annotation type declaration state.
	 */
	@Override
	public STraversal lastChild() {
		return MEMBERS;
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
		SAnnotationDecl state = (SAnnotationDecl) o;
		if (!modifiers.equals(state.modifiers))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!members.equals(state.members))
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
		result = 37 * result + modifiers.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + members.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SAnnotationDecl, SNodeList, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SAnnotationDecl, SNodeList, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SAnnotationDecl state) {
			return state.modifiers;
		}

		@Override
		public SAnnotationDecl doRebuildParentState(SAnnotationDecl state, BUTree<SNodeList> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SAnnotationDecl, SName, Name> NAME = new STypeSafeTraversal<SAnnotationDecl, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SAnnotationDecl state) {
			return state.name;
		}

		@Override
		public SAnnotationDecl doRebuildParentState(SAnnotationDecl state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return MEMBERS;
		}
	};

	public static STypeSafeTraversal<SAnnotationDecl, SNodeList, NodeList<MemberDecl>> MEMBERS = new STypeSafeTraversal<SAnnotationDecl, SNodeList, NodeList<MemberDecl>>() {

		@Override
		public BUTree<?> doTraverse(SAnnotationDecl state) {
			return state.members;
		}

		@Override
		public SAnnotationDecl doRebuildParentState(SAnnotationDecl state, BUTree<SNodeList> child) {
			return state.withMembers(child);
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
			child(MODIFIERS, SExtendedModifier.multiLineShape),
			token(LToken.At), token(LToken.Interface).withSpacingAfter(space()),
			child(NAME),
			child(MEMBERS, SMemberDecl.bodyShape)
	);
}
