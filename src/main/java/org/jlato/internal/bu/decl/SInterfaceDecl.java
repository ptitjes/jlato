package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDInterfaceDecl;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.name.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SInterfaceDecl extends SNode<SInterfaceDecl> implements STypeDecl {

	public static BUTree<SInterfaceDecl> make(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> typeParams, BUTree<SNodeList> extendsClause, BUTree<SNodeList> members) {
		return new BUTree<SInterfaceDecl>(new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members));
	}

	public final BUTree<SNodeList> modifiers;

	public final BUTree<SName> name;

	public final BUTree<SNodeList> typeParams;

	public final BUTree<SNodeList> extendsClause;

	public final BUTree<SNodeList> members;

	public SInterfaceDecl(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> typeParams, BUTree<SNodeList> extendsClause, BUTree<SNodeList> members) {
		this.modifiers = modifiers;
		this.name = name;
		this.typeParams = typeParams;
		this.extendsClause = extendsClause;
		this.members = members;
	}

	@Override
	public Kind kind() {
		return Kind.InterfaceDecl;
	}

	public BUTree<SNodeList> modifiers() {
		return modifiers;
	}

	public SInterfaceDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members);
	}

	public BUTree<SName> name() {
		return name;
	}

	public SInterfaceDecl withName(BUTree<SName> name) {
		return new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members);
	}

	public BUTree<SNodeList> typeParams() {
		return typeParams;
	}

	public SInterfaceDecl withTypeParams(BUTree<SNodeList> typeParams) {
		return new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members);
	}

	public BUTree<SNodeList> extendsClause() {
		return extendsClause;
	}

	public SInterfaceDecl withExtendsClause(BUTree<SNodeList> extendsClause) {
		return new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members);
	}

	public BUTree<SNodeList> members() {
		return members;
	}

	public SInterfaceDecl withMembers(BUTree<SNodeList> members) {
		return new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SInterfaceDecl> location) {
		return new TDInterfaceDecl(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
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
