package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDClassDecl;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.name.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class SClassDecl extends SNodeState<SClassDecl> implements STypeDecl {

	public static BUTree<SClassDecl> make(BUTree<SNodeListState> modifiers, BUTree<SName> name, BUTree<SNodeListState> typeParams, BUTree<SNodeOptionState> extendsClause, BUTree<SNodeListState> implementsClause, BUTree<SNodeListState> members) {
		return new BUTree<SClassDecl>(new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members));
	}

	public final BUTree<SNodeListState> modifiers;

	public final BUTree<SName> name;

	public final BUTree<SNodeListState> typeParams;

	public final BUTree<SNodeOptionState> extendsClause;

	public final BUTree<SNodeListState> implementsClause;

	public final BUTree<SNodeListState> members;

	public SClassDecl(BUTree<SNodeListState> modifiers, BUTree<SName> name, BUTree<SNodeListState> typeParams, BUTree<SNodeOptionState> extendsClause, BUTree<SNodeListState> implementsClause, BUTree<SNodeListState> members) {
		this.modifiers = modifiers;
		this.name = name;
		this.typeParams = typeParams;
		this.extendsClause = extendsClause;
		this.implementsClause = implementsClause;
		this.members = members;
	}

	@Override
	public Kind kind() {
		return Kind.ClassDecl;
	}

	public BUTree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SClassDecl withModifiers(BUTree<SNodeListState> modifiers) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	public BUTree<SName> name() {
		return name;
	}

	public SClassDecl withName(BUTree<SName> name) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	public BUTree<SNodeListState> typeParams() {
		return typeParams;
	}

	public SClassDecl withTypeParams(BUTree<SNodeListState> typeParams) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	public BUTree<SNodeOptionState> extendsClause() {
		return extendsClause;
	}

	public SClassDecl withExtendsClause(BUTree<SNodeOptionState> extendsClause) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	public BUTree<SNodeListState> implementsClause() {
		return implementsClause;
	}

	public SClassDecl withImplementsClause(BUTree<SNodeListState> implementsClause) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	public BUTree<SNodeListState> members() {
		return members;
	}

	public SClassDecl withMembers(BUTree<SNodeListState> members) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SClassDecl> location) {
		return new TDClassDecl(location);
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

	public static STypeSafeTraversal<SClassDecl, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SClassDecl, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SClassDecl state) {
			return state.modifiers;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, BUTree<SNodeListState> child) {
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
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE_PARAMS;
		}
	};

	public static STypeSafeTraversal<SClassDecl, SNodeListState, NodeList<TypeParameter>> TYPE_PARAMS = new STypeSafeTraversal<SClassDecl, SNodeListState, NodeList<TypeParameter>>() {

		@Override
		public BUTree<?> doTraverse(SClassDecl state) {
			return state.typeParams;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, BUTree<SNodeListState> child) {
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

	public static STypeSafeTraversal<SClassDecl, SNodeOptionState, NodeOption<QualifiedType>> EXTENDS_CLAUSE = new STypeSafeTraversal<SClassDecl, SNodeOptionState, NodeOption<QualifiedType>>() {

		@Override
		public BUTree<?> doTraverse(SClassDecl state) {
			return state.extendsClause;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, BUTree<SNodeOptionState> child) {
			return state.withExtendsClause(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE_PARAMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return IMPLEMENTS_CLAUSE;
		}
	};

	public static STypeSafeTraversal<SClassDecl, SNodeListState, NodeList<QualifiedType>> IMPLEMENTS_CLAUSE = new STypeSafeTraversal<SClassDecl, SNodeListState, NodeList<QualifiedType>>() {

		@Override
		public BUTree<?> doTraverse(SClassDecl state) {
			return state.implementsClause;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, BUTree<SNodeListState> child) {
			return state.withImplementsClause(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return EXTENDS_CLAUSE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return MEMBERS;
		}
	};

	public static STypeSafeTraversal<SClassDecl, SNodeListState, NodeList<MemberDecl>> MEMBERS = new STypeSafeTraversal<SClassDecl, SNodeListState, NodeList<MemberDecl>>() {

		@Override
		public BUTree<?> doTraverse(SClassDecl state) {
			return state.members;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, BUTree<SNodeListState> child) {
			return state.withMembers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return IMPLEMENTS_CLAUSE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
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
