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

public class SInterfaceDecl extends SNodeState<SInterfaceDecl> implements STypeDecl {

	public static BUTree<SInterfaceDecl> make(BUTree<SNodeListState> modifiers, BUTree<SName> name, BUTree<SNodeListState> typeParams, BUTree<SNodeListState> extendsClause, BUTree<SNodeListState> members) {
		return new BUTree<SInterfaceDecl>(new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members));
	}

	public final BUTree<SNodeListState> modifiers;

	public final BUTree<SName> name;

	public final BUTree<SNodeListState> typeParams;

	public final BUTree<SNodeListState> extendsClause;

	public final BUTree<SNodeListState> members;

	public SInterfaceDecl(BUTree<SNodeListState> modifiers, BUTree<SName> name, BUTree<SNodeListState> typeParams, BUTree<SNodeListState> extendsClause, BUTree<SNodeListState> members) {
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

	public BUTree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SInterfaceDecl withModifiers(BUTree<SNodeListState> modifiers) {
		return new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members);
	}

	public BUTree<SName> name() {
		return name;
	}

	public SInterfaceDecl withName(BUTree<SName> name) {
		return new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members);
	}

	public BUTree<SNodeListState> typeParams() {
		return typeParams;
	}

	public SInterfaceDecl withTypeParams(BUTree<SNodeListState> typeParams) {
		return new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members);
	}

	public BUTree<SNodeListState> extendsClause() {
		return extendsClause;
	}

	public SInterfaceDecl withExtendsClause(BUTree<SNodeListState> extendsClause) {
		return new SInterfaceDecl(modifiers, name, typeParams, extendsClause, members);
	}

	public BUTree<SNodeListState> members() {
		return members;
	}

	public SInterfaceDecl withMembers(BUTree<SNodeListState> members) {
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

	public static STypeSafeTraversal<SInterfaceDecl, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SInterfaceDecl, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SInterfaceDecl state) {
			return state.modifiers;
		}

		@Override
		public SInterfaceDecl doRebuildParentState(SInterfaceDecl state, BUTree<SNodeListState> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
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
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE_PARAMS;
		}
	};

	public static STypeSafeTraversal<SInterfaceDecl, SNodeListState, NodeList<TypeParameter>> TYPE_PARAMS = new STypeSafeTraversal<SInterfaceDecl, SNodeListState, NodeList<TypeParameter>>() {

		@Override
		public BUTree<?> doTraverse(SInterfaceDecl state) {
			return state.typeParams;
		}

		@Override
		public SInterfaceDecl doRebuildParentState(SInterfaceDecl state, BUTree<SNodeListState> child) {
			return state.withTypeParams(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return EXTENDS_CLAUSE;
		}
	};

	public static STypeSafeTraversal<SInterfaceDecl, SNodeListState, NodeList<QualifiedType>> EXTENDS_CLAUSE = new STypeSafeTraversal<SInterfaceDecl, SNodeListState, NodeList<QualifiedType>>() {

		@Override
		public BUTree<?> doTraverse(SInterfaceDecl state) {
			return state.extendsClause;
		}

		@Override
		public SInterfaceDecl doRebuildParentState(SInterfaceDecl state, BUTree<SNodeListState> child) {
			return state.withExtendsClause(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE_PARAMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return MEMBERS;
		}
	};

	public static STypeSafeTraversal<SInterfaceDecl, SNodeListState, NodeList<MemberDecl>> MEMBERS = new STypeSafeTraversal<SInterfaceDecl, SNodeListState, NodeList<MemberDecl>>() {

		@Override
		public BUTree<?> doTraverse(SInterfaceDecl state) {
			return state.members;
		}

		@Override
		public SInterfaceDecl doRebuildParentState(SInterfaceDecl state, BUTree<SNodeListState> child) {
			return state.withMembers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return EXTENDS_CLAUSE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
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
