package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDClassDecl;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.decl.TypeParameter;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;

public class SClassDecl extends SNodeState<SClassDecl> implements STypeDecl {

	public static STree<SClassDecl> make(STree<SNodeListState> modifiers, STree<SName> name, STree<SNodeListState> typeParams, STree<SNodeOptionState> extendsClause, STree<SNodeListState> implementsClause, STree<SNodeListState> members) {
		return new STree<SClassDecl>(new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members));
	}

	public final STree<SNodeListState> modifiers;

	public final STree<SName> name;

	public final STree<SNodeListState> typeParams;

	public final STree<SNodeOptionState> extendsClause;

	public final STree<SNodeListState> implementsClause;

	public final STree<SNodeListState> members;

	public SClassDecl(STree<SNodeListState> modifiers, STree<SName> name, STree<SNodeListState> typeParams, STree<SNodeOptionState> extendsClause, STree<SNodeListState> implementsClause, STree<SNodeListState> members) {
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

	public STree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SClassDecl withModifiers(STree<SNodeListState> modifiers) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	public STree<SName> name() {
		return name;
	}

	public SClassDecl withName(STree<SName> name) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	public STree<SNodeListState> typeParams() {
		return typeParams;
	}

	public SClassDecl withTypeParams(STree<SNodeListState> typeParams) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	public STree<SNodeOptionState> extendsClause() {
		return extendsClause;
	}

	public SClassDecl withExtendsClause(STree<SNodeOptionState> extendsClause) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	public STree<SNodeListState> implementsClause() {
		return implementsClause;
	}

	public SClassDecl withImplementsClause(STree<SNodeListState> implementsClause) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	public STree<SNodeListState> members() {
		return members;
	}

	public SClassDecl withMembers(STree<SNodeListState> members) {
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
		public STree<?> doTraverse(SClassDecl state) {
			return state.modifiers;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, STree<SNodeListState> child) {
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
		public STree<?> doTraverse(SClassDecl state) {
			return state.name;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, STree<SName> child) {
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
		public STree<?> doTraverse(SClassDecl state) {
			return state.typeParams;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, STree<SNodeListState> child) {
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
		public STree<?> doTraverse(SClassDecl state) {
			return state.extendsClause;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, STree<SNodeOptionState> child) {
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
		public STree<?> doTraverse(SClassDecl state) {
			return state.implementsClause;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, STree<SNodeListState> child) {
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
		public STree<?> doTraverse(SClassDecl state) {
			return state.members;
		}

		@Override
		public SClassDecl doRebuildParentState(SClassDecl state, STree<SNodeListState> child) {
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
