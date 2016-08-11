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

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDEnumDecl;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.EnumConstantDecl;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;

import java.util.Collections;

import static org.jlato.internal.shapes.IndentationConstraint.indent;
import static org.jlato.internal.shapes.IndentationConstraint.unIndent;
import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.data;
import static org.jlato.internal.shapes.LSCondition.empty;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.newLine;
import static org.jlato.internal.shapes.SpacingConstraint.space;
import static org.jlato.internal.shapes.SpacingConstraint.spacing;
import static org.jlato.printer.FormattingSettings.IndentationContext.TypeBody;
import static org.jlato.printer.FormattingSettings.SpacingLocation.EnumBody_AfterConstants;
import static org.jlato.printer.FormattingSettings.SpacingLocation.EnumBody_BetweenConstants;

/**
 * A state object for an enum declaration.
 */
public class SEnumDecl extends SNode<SEnumDecl> implements STypeDecl {

	/**
	 * Creates a <code>BUTree</code> with a new enum declaration.
	 *
	 * @param modifiers        the modifiers child <code>BUTree</code>.
	 * @param name             the name child <code>BUTree</code>.
	 * @param implementsClause the 'implements' clause child <code>BUTree</code>.
	 * @param enumConstants    the enum constants child <code>BUTree</code>.
	 * @param trailingComma    the has a trailing comma child <code>BUTree</code>.
	 * @param members          the members child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an enum declaration.
	 */
	public static BUTree<SEnumDecl> make(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> implementsClause, BUTree<SNodeList> enumConstants, boolean trailingComma, BUTree<SNodeList> members) {
		return new BUTree<SEnumDecl>(new SEnumDecl(modifiers, name, implementsClause, enumConstants, trailingComma, members));
	}

	/**
	 * The modifiers of this enum declaration state.
	 */
	public final BUTree<SNodeList> modifiers;

	/**
	 * The name of this enum declaration state.
	 */
	public final BUTree<SName> name;

	/**
	 * The 'implements' clause of this enum declaration state.
	 */
	public final BUTree<SNodeList> implementsClause;

	/**
	 * The enum constants of this enum declaration state.
	 */
	public final BUTree<SNodeList> enumConstants;

	/**
	 * The has a trailing comma of this enum declaration state.
	 */
	public final boolean trailingComma;

	/**
	 * The members of this enum declaration state.
	 */
	public final BUTree<SNodeList> members;

	/**
	 * Constructs an enum declaration state.
	 *
	 * @param modifiers        the modifiers child <code>BUTree</code>.
	 * @param name             the name child <code>BUTree</code>.
	 * @param implementsClause the 'implements' clause child <code>BUTree</code>.
	 * @param enumConstants    the enum constants child <code>BUTree</code>.
	 * @param trailingComma    the has a trailing comma child <code>BUTree</code>.
	 * @param members          the members child <code>BUTree</code>.
	 */
	public SEnumDecl(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> implementsClause, BUTree<SNodeList> enumConstants, boolean trailingComma, BUTree<SNodeList> members) {
		this.modifiers = modifiers;
		this.name = name;
		this.implementsClause = implementsClause;
		this.enumConstants = enumConstants;
		this.trailingComma = trailingComma;
		this.members = members;
	}

	/**
	 * Returns the kind of this enum declaration.
	 *
	 * @return the kind of this enum declaration.
	 */
	@Override
	public Kind kind() {
		return Kind.EnumDecl;
	}

	/**
	 * Replaces the modifiers of this enum declaration state.
	 *
	 * @param modifiers the replacement for the modifiers of this enum declaration state.
	 * @return the resulting mutated enum declaration state.
	 */
	public SEnumDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SEnumDecl(modifiers, name, implementsClause, enumConstants, trailingComma, members);
	}

	/**
	 * Replaces the name of this enum declaration state.
	 *
	 * @param name the replacement for the name of this enum declaration state.
	 * @return the resulting mutated enum declaration state.
	 */
	public SEnumDecl withName(BUTree<SName> name) {
		return new SEnumDecl(modifiers, name, implementsClause, enumConstants, trailingComma, members);
	}

	/**
	 * Replaces the 'implements' clause of this enum declaration state.
	 *
	 * @param implementsClause the replacement for the 'implements' clause of this enum declaration state.
	 * @return the resulting mutated enum declaration state.
	 */
	public SEnumDecl withImplementsClause(BUTree<SNodeList> implementsClause) {
		return new SEnumDecl(modifiers, name, implementsClause, enumConstants, trailingComma, members);
	}

	/**
	 * Replaces the enum constants of this enum declaration state.
	 *
	 * @param enumConstants the replacement for the enum constants of this enum declaration state.
	 * @return the resulting mutated enum declaration state.
	 */
	public SEnumDecl withEnumConstants(BUTree<SNodeList> enumConstants) {
		return new SEnumDecl(modifiers, name, implementsClause, enumConstants, trailingComma, members);
	}

	/**
	 * Replaces the has a trailing comma of this enum declaration state.
	 *
	 * @param trailingComma the replacement for the has a trailing comma of this enum declaration state.
	 * @return the resulting mutated enum declaration state.
	 */
	public SEnumDecl withTrailingComma(boolean trailingComma) {
		return new SEnumDecl(modifiers, name, implementsClause, enumConstants, trailingComma, members);
	}

	/**
	 * Replaces the members of this enum declaration state.
	 *
	 * @param members the replacement for the members of this enum declaration state.
	 * @return the resulting mutated enum declaration state.
	 */
	public SEnumDecl withMembers(BUTree<SNodeList> members) {
		return new SEnumDecl(modifiers, name, implementsClause, enumConstants, trailingComma, members);
	}

	/**
	 * Builds an enum declaration facade for the specified enum declaration <code>TDLocation</code>.
	 *
	 * @param location the enum declaration <code>TDLocation</code>.
	 * @return an enum declaration facade for the specified enum declaration <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SEnumDecl> location) {
		return new TDEnumDecl(location);
	}

	/**
	 * Returns the shape for this enum declaration state.
	 *
	 * @return the shape for this enum declaration state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the properties for this enum declaration state.
	 *
	 * @return the properties for this enum declaration state.
	 */
	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(TRAILING_COMMA);
	}

	/**
	 * Returns the first child traversal for this enum declaration state.
	 *
	 * @return the first child traversal for this enum declaration state.
	 */
	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	/**
	 * Returns the last child traversal for this enum declaration state.
	 *
	 * @return the last child traversal for this enum declaration state.
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
		SEnumDecl state = (SEnumDecl) o;
		if (!modifiers.equals(state.modifiers))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!implementsClause.equals(state.implementsClause))
			return false;
		if (!enumConstants.equals(state.enumConstants))
			return false;
		if (trailingComma != state.trailingComma)
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
		result = 37 * result + implementsClause.hashCode();
		result = 37 * result + enumConstants.hashCode();
		result = 37 * result + (trailingComma ? 1 : 0);
		result = 37 * result + members.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SEnumDecl, SNodeList, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SEnumDecl, SNodeList, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SEnumDecl state) {
			return state.modifiers;
		}

		@Override
		public SEnumDecl doRebuildParentState(SEnumDecl state, BUTree<SNodeList> child) {
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

	public static STypeSafeTraversal<SEnumDecl, SName, Name> NAME = new STypeSafeTraversal<SEnumDecl, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SEnumDecl state) {
			return state.name;
		}

		@Override
		public SEnumDecl doRebuildParentState(SEnumDecl state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return IMPLEMENTS_CLAUSE;
		}
	};

	public static STypeSafeTraversal<SEnumDecl, SNodeList, NodeList<QualifiedType>> IMPLEMENTS_CLAUSE = new STypeSafeTraversal<SEnumDecl, SNodeList, NodeList<QualifiedType>>() {

		@Override
		public BUTree<?> doTraverse(SEnumDecl state) {
			return state.implementsClause;
		}

		@Override
		public SEnumDecl doRebuildParentState(SEnumDecl state, BUTree<SNodeList> child) {
			return state.withImplementsClause(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return ENUM_CONSTANTS;
		}
	};

	public static STypeSafeTraversal<SEnumDecl, SNodeList, NodeList<EnumConstantDecl>> ENUM_CONSTANTS = new STypeSafeTraversal<SEnumDecl, SNodeList, NodeList<EnumConstantDecl>>() {

		@Override
		public BUTree<?> doTraverse(SEnumDecl state) {
			return state.enumConstants;
		}

		@Override
		public SEnumDecl doRebuildParentState(SEnumDecl state, BUTree<SNodeList> child) {
			return state.withEnumConstants(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return IMPLEMENTS_CLAUSE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return MEMBERS;
		}
	};

	public static STypeSafeTraversal<SEnumDecl, SNodeList, NodeList<MemberDecl>> MEMBERS = new STypeSafeTraversal<SEnumDecl, SNodeList, NodeList<MemberDecl>>() {

		@Override
		public BUTree<?> doTraverse(SEnumDecl state) {
			return state.members;
		}

		@Override
		public SEnumDecl doRebuildParentState(SEnumDecl state, BUTree<SNodeList> child) {
			return state.withMembers(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return ENUM_CONSTANTS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static STypeSafeProperty<SEnumDecl, Boolean> TRAILING_COMMA = new STypeSafeProperty<SEnumDecl, Boolean>() {

		@Override
		public Boolean doRetrieve(SEnumDecl state) {
			return state.trailingComma;
		}

		@Override
		public SEnumDecl doRebuildParentState(SEnumDecl state, Boolean value) {
			return state.withTrailingComma(value);
		}
	};

	public static final LexicalShape shape = composite(
			child(MODIFIERS, SExtendedModifier.multiLineShape),
			keyword(LToken.Enum),
			child(NAME),
			child(IMPLEMENTS_CLAUSE, org.jlato.internal.bu.type.SQualifiedType.implementsClauseShape),
			token(LToken.BraceLeft)
					.withSpacingBefore(space())
					.withIndentationAfter(indent(TypeBody)),
			child(ENUM_CONSTANTS, SEnumConstantDecl.listShape),
			when(data(TRAILING_COMMA), token(LToken.Comma).withSpacingAfter(spacing(EnumBody_BetweenConstants))),
			alternative(childIs(MEMBERS, empty()),
					alternative(childIs(ENUM_CONSTANTS, empty()),
							none().withSpacingAfter(newLine()),
							none().withSpacingAfter(spacing(EnumBody_AfterConstants))
					),
					alternative(childIs(ENUM_CONSTANTS, empty()),
							none(),
							token(LToken.SemiColon).withSpacingAfter(spacing(EnumBody_AfterConstants))
					)
			),
			child(MEMBERS, SMemberDecl.membersShape),
			token(LToken.BraceRight)
					.withIndentationBefore(unIndent(TypeBody))
	);
}
