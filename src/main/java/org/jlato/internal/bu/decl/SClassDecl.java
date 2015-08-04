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

public class SClassDecl extends SNode<SClassDecl> implements STypeDecl {

	public static BUTree<SClassDecl> make(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> typeParams, BUTree<SNodeOption> extendsClause, BUTree<SNodeList> implementsClause, BUTree<SNodeList> members) {
		return new BUTree<SClassDecl>(new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members));
	}

	public final BUTree<SNodeList> modifiers;

	public final BUTree<SName> name;

	public final BUTree<SNodeList> typeParams;

	public final BUTree<SNodeOption> extendsClause;

	public final BUTree<SNodeList> implementsClause;

	public final BUTree<SNodeList> members;

	public SClassDecl(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> typeParams, BUTree<SNodeOption> extendsClause, BUTree<SNodeList> implementsClause, BUTree<SNodeList> members) {
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

	public BUTree<SNodeList> modifiers() {
		return modifiers;
	}

	public SClassDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	public BUTree<SName> name() {
		return name;
	}

	public SClassDecl withName(BUTree<SName> name) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	public BUTree<SNodeList> typeParams() {
		return typeParams;
	}

	public SClassDecl withTypeParams(BUTree<SNodeList> typeParams) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	public BUTree<SNodeOption> extendsClause() {
		return extendsClause;
	}

	public SClassDecl withExtendsClause(BUTree<SNodeOption> extendsClause) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	public BUTree<SNodeList> implementsClause() {
		return implementsClause;
	}

	public SClassDecl withImplementsClause(BUTree<SNodeList> implementsClause) {
		return new SClassDecl(modifiers, name, typeParams, extendsClause, implementsClause, members);
	}

	public BUTree<SNodeList> members() {
		return members;
	}

	public SClassDecl withMembers(BUTree<SNodeList> members) {
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
