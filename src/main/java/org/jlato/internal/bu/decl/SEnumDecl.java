package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDEnumDecl;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.name.*;
import org.jlato.tree.type.*;

import java.util.Collections;

import static org.jlato.internal.shapes.IndentationConstraint.indent;
import static org.jlato.internal.shapes.IndentationConstraint.unIndent;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.TYPE_BODY;
import static org.jlato.printer.FormattingSettings.SpacingLocation.EnumBody_AfterConstants;
import static org.jlato.printer.FormattingSettings.SpacingLocation.EnumBody_BetweenConstants;

public class SEnumDecl extends SNode<SEnumDecl> implements STypeDecl {

	public static BUTree<SEnumDecl> make(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> implementsClause, BUTree<SNodeList> enumConstants, boolean trailingComma, BUTree<SNodeList> members) {
		return new BUTree<SEnumDecl>(new SEnumDecl(modifiers, name, implementsClause, enumConstants, trailingComma, members));
	}

	public final BUTree<SNodeList> modifiers;

	public final BUTree<SName> name;

	public final BUTree<SNodeList> implementsClause;

	public final BUTree<SNodeList> enumConstants;

	public final boolean trailingComma;

	public final BUTree<SNodeList> members;

	public SEnumDecl(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> implementsClause, BUTree<SNodeList> enumConstants, boolean trailingComma, BUTree<SNodeList> members) {
		this.modifiers = modifiers;
		this.name = name;
		this.implementsClause = implementsClause;
		this.enumConstants = enumConstants;
		this.trailingComma = trailingComma;
		this.members = members;
	}

	@Override
	public Kind kind() {
		return Kind.EnumDecl;
	}

	public BUTree<SNodeList> modifiers() {
		return modifiers;
	}

	public SEnumDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SEnumDecl(modifiers, name, implementsClause, enumConstants, trailingComma, members);
	}

	public BUTree<SName> name() {
		return name;
	}

	public SEnumDecl withName(BUTree<SName> name) {
		return new SEnumDecl(modifiers, name, implementsClause, enumConstants, trailingComma, members);
	}

	public BUTree<SNodeList> implementsClause() {
		return implementsClause;
	}

	public SEnumDecl withImplementsClause(BUTree<SNodeList> implementsClause) {
		return new SEnumDecl(modifiers, name, implementsClause, enumConstants, trailingComma, members);
	}

	public BUTree<SNodeList> enumConstants() {
		return enumConstants;
	}

	public SEnumDecl withEnumConstants(BUTree<SNodeList> enumConstants) {
		return new SEnumDecl(modifiers, name, implementsClause, enumConstants, trailingComma, members);
	}

	public boolean trailingComma() {
		return trailingComma;
	}

	public SEnumDecl withTrailingComma(boolean trailingComma) {
		return new SEnumDecl(modifiers, name, implementsClause, enumConstants, trailingComma, members);
	}

	public BUTree<SNodeList> members() {
		return members;
	}

	public SEnumDecl withMembers(BUTree<SNodeList> members) {
		return new SEnumDecl(modifiers, name, implementsClause, enumConstants, trailingComma, members);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SEnumDecl> location) {
		return new TDEnumDecl(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(TRAILING_COMMA);
	}

	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	@Override
	public STraversal lastChild() {
		return MEMBERS;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
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
					.withIndentationAfter(indent(TYPE_BODY)),
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
					.withIndentationBefore(unIndent(TYPE_BODY))
	);
}
