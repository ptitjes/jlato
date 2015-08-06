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
import org.jlato.internal.td.decl.TDInterfaceDecl;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.TypeParameter;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an interface declaration.
 */
public class SInterfaceDecl extends SNode<SInterfaceDecl> implements STypeDecl {

	/**
	 * Creates a <code>BUTree</code> with a new interface declaration.
	 *
	 * @param modifiers     the modifiers child <code>BUTree</code>.
	 * @param name          the name child <code>BUTree</code>.
	 * @param typeParams    the type parameters child <code>BUTree</code>.
	 * @param extendsClause the 'extends' clause child <code>BUTree</code>.
	 * @param members       the members child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an interface declaration.
	 */
	public static BUTree<SInterfaceDecl> make(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> typeParams, BUTree<SNodeList> extendsClause, BUTree<SNodeList> members) {
		return new BUTree<SInterfaceDecl>(new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members));
	}

	/**
	 * The modifiers of this interface declaration state.
	 */
	public final BUTree<SNodeList> modifiers;

	/**
	 * The name of this interface declaration state.
	 */
	public final BUTree<SName> name;

	/**
	 * The type parameters of this interface declaration state.
	 */
	public final BUTree<SNodeList> typeParams;

	/**
	 * The 'extends' clause of this interface declaration state.
	 */
	public final BUTree<SNodeList> extendsClause;

	/**
	 * The members of this interface declaration state.
	 */
	public final BUTree<SNodeList> members;

	/**
	 * Constructs an interface declaration state.
	 *
	 * @param modifiers     the modifiers child <code>BUTree</code>.
	 * @param name          the name child <code>BUTree</code>.
	 * @param typeParams    the type parameters child <code>BUTree</code>.
	 * @param extendsClause the 'extends' clause child <code>BUTree</code>.
	 * @param members       the members child <code>BUTree</code>.
	 */
	public SInterfaceDecl(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> typeParams, BUTree<SNodeList> extendsClause, BUTree<SNodeList> members) {
		this.modifiers = modifiers;
		this.name = name;
		this.typeParams = typeParams;
		this.extendsClause = extendsClause;
		this.members = members;
	}

	/**
	 * Returns the kind of this interface declaration.
	 *
	 * @return the kind of this interface declaration.
	 */
	@Override
	public Kind kind() {
		return Kind.InterfaceDecl;
	}

	/**
	 * Replaces the modifiers of this interface declaration state.
	 *
	 * @param modifiers the replacement for the modifiers of this interface declaration state.
	 * @return the resulting mutated interface declaration state.
	 */
	public SInterfaceDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members);
	}

	/**
	 * Replaces the name of this interface declaration state.
	 *
	 * @param name the replacement for the name of this interface declaration state.
	 * @return the resulting mutated interface declaration state.
	 */
	public SInterfaceDecl withName(BUTree<SName> name) {
		return new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members);
	}

	/**
	 * Replaces the type parameters of this interface declaration state.
	 *
	 * @param typeParams the replacement for the type parameters of this interface declaration state.
	 * @return the resulting mutated interface declaration state.
	 */
	public SInterfaceDecl withTypeParams(BUTree<SNodeList> typeParams) {
		return new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members);
	}

	/**
	 * Replaces the 'extends' clause of this interface declaration state.
	 *
	 * @param extendsClause the replacement for the 'extends' clause of this interface declaration state.
	 * @return the resulting mutated interface declaration state.
	 */
	public SInterfaceDecl withExtendsClause(BUTree<SNodeList> extendsClause) {
		return new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members);
	}

	/**
	 * Replaces the members of this interface declaration state.
	 *
	 * @param members the replacement for the members of this interface declaration state.
	 * @return the resulting mutated interface declaration state.
	 */
	public SInterfaceDecl withMembers(BUTree<SNodeList> members) {
		return new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members);
	}

	/**
	 * Builds an interface declaration facade for the specified interface declaration <code>TDLocation</code>.
	 *
	 * @param location the interface declaration <code>TDLocation</code>.
	 * @return an interface declaration facade for the specified interface declaration <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SInterfaceDecl> location) {
		return new TDInterfaceDecl(location);
	}

	/**
	 * Returns the shape for this interface declaration state.
	 *
	 * @return the shape for this interface declaration state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this interface declaration state.
	 *
	 * @return the first child traversal for this interface declaration state.
	 */
	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	/**
	 * Returns the last child traversal for this interface declaration state.
	 *
	 * @return the last child traversal for this interface declaration state.
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
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SInterfaceDecl state = (SInterfaceDecl) o;
		if (!modifiers.equals(state.modifiers))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!typeParams.equals(state.typeParams))
			return false;
		if (!extendsClause.equals(state.extendsClause))
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
		result = 37 * result + typeParams.hashCode();
		result = 37 * result + extendsClause.hashCode();
		result = 37 * result + members.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SInterfaceDecl, SNodeList, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SInterfaceDecl, SNodeList, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SInterfaceDecl state) {
			return state.modifiers;
		}

		@Override
		public SInterfaceDecl doRebuildParentState(SInterfaceDecl state, BUTree<SNodeList> child) {
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

	public static STypeSafeTraversal<SInterfaceDecl, SName, Name> NAME = new STypeSafeTraversal<SInterfaceDecl, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SInterfaceDecl state) {
			return state.name;
		}

		@Override
		public SInterfaceDecl doRebuildParentState(SInterfaceDecl state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return TYPE_PARAMS;
		}
	};

	public static STypeSafeTraversal<SInterfaceDecl, SNodeList, NodeList<TypeParameter>> TYPE_PARAMS = new STypeSafeTraversal<SInterfaceDecl, SNodeList, NodeList<TypeParameter>>() {

		@Override
		public BUTree<?> doTraverse(SInterfaceDecl state) {
			return state.typeParams;
		}

		@Override
		public SInterfaceDecl doRebuildParentState(SInterfaceDecl state, BUTree<SNodeList> child) {
			return state.withTypeParams(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return EXTENDS_CLAUSE;
		}
	};

	public static STypeSafeTraversal<SInterfaceDecl, SNodeList, NodeList<QualifiedType>> EXTENDS_CLAUSE = new STypeSafeTraversal<SInterfaceDecl, SNodeList, NodeList<QualifiedType>>() {

		@Override
		public BUTree<?> doTraverse(SInterfaceDecl state) {
			return state.extendsClause;
		}

		@Override
		public SInterfaceDecl doRebuildParentState(SInterfaceDecl state, BUTree<SNodeList> child) {
			return state.withExtendsClause(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE_PARAMS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return MEMBERS;
		}
	};

	public static STypeSafeTraversal<SInterfaceDecl, SNodeList, NodeList<MemberDecl>> MEMBERS = new STypeSafeTraversal<SInterfaceDecl, SNodeList, NodeList<MemberDecl>>() {

		@Override
		public BUTree<?> doTraverse(SInterfaceDecl state) {
			return state.members;
		}

		@Override
		public SInterfaceDecl doRebuildParentState(SInterfaceDecl state, BUTree<SNodeList> child) {
			return state.withMembers(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return EXTENDS_CLAUSE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(MODIFIERS, SExtendedModifier.multiLineShape),
			keyword(LToken.Interface),
			child(NAME),
			child(TYPE_PARAMS, STypeParameter.listShape),
			child(EXTENDS_CLAUSE, org.jlato.internal.bu.type.SQualifiedType.extendsClauseShape),
			child(MEMBERS, SMemberDecl.bodyShape)
	);
}
