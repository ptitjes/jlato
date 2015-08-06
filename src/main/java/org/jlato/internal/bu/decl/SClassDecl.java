package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDClassDecl;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
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
 * A state object for a class declaration.
 */
public class SClassDecl extends SNode<SClassDecl> implements STypeDecl {

	/**
	 * Creates a <code>BUTree</code> with a new class declaration.
	 *
	 * @param modifiers        the modifiers child <code>BUTree</code>.
	 * @param name             the name child <code>BUTree</code>.
	 * @param typeParams       the type parameters child <code>BUTree</code>.
	 * @param extendsClause    the 'extends' clause child <code>BUTree</code>.
	 * @param implementsClause the 'implements' clause child <code>BUTree</code>.
	 * @param members          the members child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a class declaration.
	 */
	public static BUTree<SClassDecl> make(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> typeParams, BUTree<SNodeOption> extendsClause, BUTree<SNodeList> implementsClause, BUTree<SNodeList> members) {
		return new BUTree<SClassDecl>(new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members));
	}

	/**
	 * The modifiers of this class declaration state.
	 */
	public final BUTree<SNodeList> modifiers;

	/**
	 * The name of this class declaration state.
	 */
	public final BUTree<SName> name;

	/**
	 * The type parameters of this class declaration state.
	 */
	public final BUTree<SNodeList> typeParams;

	/**
	 * The 'extends' clause of this class declaration state.
	 */
	public final BUTree<SNodeOption> extendsClause;

	/**
	 * The 'implements' clause of this class declaration state.
	 */
	public final BUTree<SNodeList> implementsClause;

	/**
	 * The members of this class declaration state.
	 */
	public final BUTree<SNodeList> members;

	/**
	 * Constructs a class declaration state.
	 *
	 * @param modifiers        the modifiers child <code>BUTree</code>.
	 * @param name             the name child <code>BUTree</code>.
	 * @param typeParams       the type parameters child <code>BUTree</code>.
	 * @param extendsClause    the 'extends' clause child <code>BUTree</code>.
	 * @param implementsClause the 'implements' clause child <code>BUTree</code>.
	 * @param members          the members child <code>BUTree</code>.
	 */
	public SClassDecl(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> typeParams, BUTree<SNodeOption> extendsClause, BUTree<SNodeList> implementsClause, BUTree<SNodeList> members) {
		this.modifiers = modifiers;
		this.name = name;
		this.typeParams = typeParams;
		this.extendsClause = extendsClause;
		this.implementsClause = implementsClause;
		this.members = members;
	}

	/**
	 * Returns the kind of this class declaration.
	 *
	 * @return the kind of this class declaration.
	 */
	@Override
	public Kind kind() {
		return Kind.ClassDecl;
	}

	/**
	 * Replaces the modifiers of this class declaration state.
	 *
	 * @param modifiers the replacement for the modifiers of this class declaration state.
	 * @return the resulting mutated class declaration state.
	 */
	public SClassDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	/**
	 * Replaces the name of this class declaration state.
	 *
	 * @param name the replacement for the name of this class declaration state.
	 * @return the resulting mutated class declaration state.
	 */
	public SClassDecl withName(BUTree<SName> name) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	/**
	 * Replaces the type parameters of this class declaration state.
	 *
	 * @param typeParams the replacement for the type parameters of this class declaration state.
	 * @return the resulting mutated class declaration state.
	 */
	public SClassDecl withTypeParams(BUTree<SNodeList> typeParams) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	/**
	 * Replaces the 'extends' clause of this class declaration state.
	 *
	 * @param extendsClause the replacement for the 'extends' clause of this class declaration state.
	 * @return the resulting mutated class declaration state.
	 */
	public SClassDecl withExtendsClause(BUTree<SNodeOption> extendsClause) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	/**
	 * Replaces the 'implements' clause of this class declaration state.
	 *
	 * @param implementsClause the replacement for the 'implements' clause of this class declaration state.
	 * @return the resulting mutated class declaration state.
	 */
	public SClassDecl withImplementsClause(BUTree<SNodeList> implementsClause) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	/**
	 * Replaces the members of this class declaration state.
	 *
	 * @param members the replacement for the members of this class declaration state.
	 * @return the resulting mutated class declaration state.
	 */
	public SClassDecl withMembers(BUTree<SNodeList> members) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	/**
	 * Builds a class declaration facade for the specified class declaration <code>TDLocation</code>.
	 *
	 * @param location the class declaration <code>TDLocation</code>.
	 * @return a class declaration facade for the specified class declaration <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SClassDecl> location) {
		return new TDClassDecl(location);
	}

	/**
	 * Returns the shape for this class declaration state.
	 *
	 * @return the shape for this class declaration state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this class declaration state.
	 *
	 * @return the first child traversal for this class declaration state.
	 */
	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	/**
	 * Returns the last child traversal for this class declaration state.
	 *
	 * @return the last child traversal for this class declaration state.
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
		SClassDecl state = (SClassDecl) o;
		if (!modifiers.equals(state.modifiers))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!typeParams.equals(state.typeParams))
			return false;
		if (!extendsClause.equals(state.extendsClause))
			return false;
		if (!implementsClause.equals(state.implementsClause))
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
		result = 37 * result + implementsClause.hashCode();
		result = 37 * result + members.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SClassDecl, SNodeList, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SClassDecl, SNodeList, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SClassDecl state) {
			return state.modifiers;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, BUTree<SNodeList> child) {
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

	public static STypeSafeTraversal<SClassDecl, SName, Name> NAME = new STypeSafeTraversal<SClassDecl, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SClassDecl state) {
			return state.name;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, BUTree<SName> child) {
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

	public static STypeSafeTraversal<SClassDecl, SNodeList, NodeList<TypeParameter>> TYPE_PARAMS = new STypeSafeTraversal<SClassDecl, SNodeList, NodeList<TypeParameter>>() {

		@Override
		public BUTree<?> doTraverse(SClassDecl state) {
			return state.typeParams;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, BUTree<SNodeList> child) {
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

	public static STypeSafeTraversal<SClassDecl, SNodeOption, NodeOption<QualifiedType>> EXTENDS_CLAUSE = new STypeSafeTraversal<SClassDecl, SNodeOption, NodeOption<QualifiedType>>() {

		@Override
		public BUTree<?> doTraverse(SClassDecl state) {
			return state.extendsClause;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, BUTree<SNodeOption> child) {
			return state.withExtendsClause(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE_PARAMS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return IMPLEMENTS_CLAUSE;
		}
	};

	public static STypeSafeTraversal<SClassDecl, SNodeList, NodeList<QualifiedType>> IMPLEMENTS_CLAUSE = new STypeSafeTraversal<SClassDecl, SNodeList, NodeList<QualifiedType>>() {

		@Override
		public BUTree<?> doTraverse(SClassDecl state) {
			return state.implementsClause;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, BUTree<SNodeList> child) {
			return state.withImplementsClause(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return EXTENDS_CLAUSE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return MEMBERS;
		}
	};

	public static STypeSafeTraversal<SClassDecl, SNodeList, NodeList<MemberDecl>> MEMBERS = new STypeSafeTraversal<SClassDecl, SNodeList, NodeList<MemberDecl>>() {

		@Override
		public BUTree<?> doTraverse(SClassDecl state) {
			return state.members;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, BUTree<SNodeList> child) {
			return state.withMembers(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return IMPLEMENTS_CLAUSE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(MODIFIERS, SExtendedModifier.multiLineShape),
			keyword(LToken.Class),
			child(NAME),
			child(TYPE_PARAMS, STypeParameter.listShape),
			child(EXTENDS_CLAUSE, when(some(),
					composite(keyword(LToken.Extends), element())
			)),
			child(IMPLEMENTS_CLAUSE, org.jlato.internal.bu.type.SQualifiedType.implementsClauseShape),
			child(MEMBERS, SMemberDecl.bodyShape)
	);
}
