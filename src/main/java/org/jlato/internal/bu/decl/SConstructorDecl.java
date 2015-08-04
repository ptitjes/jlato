package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDConstructorDecl;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.name.*;
import org.jlato.tree.stmt.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SConstructorDecl extends SNode<SConstructorDecl> implements SMemberDecl {

	public static BUTree<SConstructorDecl> make(BUTree<SNodeList> modifiers, BUTree<SNodeList> typeParams, BUTree<SName> name, BUTree<SNodeList> params, BUTree<SNodeList> throwsClause, BUTree<SBlockStmt> body) {
		return new BUTree<SConstructorDecl>(new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body));
	}

	public final BUTree<SNodeList> modifiers;

	public final BUTree<SNodeList> typeParams;

	public final BUTree<SName> name;

	public final BUTree<SNodeList> params;

	public final BUTree<SNodeList> throwsClause;

	public final BUTree<SBlockStmt> body;

	public SConstructorDecl(BUTree<SNodeList> modifiers, BUTree<SNodeList> typeParams, BUTree<SName> name, BUTree<SNodeList> params, BUTree<SNodeList> throwsClause, BUTree<SBlockStmt> body) {
		this.modifiers = modifiers;
		this.typeParams = typeParams;
		this.name = name;
		this.params = params;
		this.throwsClause = throwsClause;
		this.body = body;
	}

	@Override
	public Kind kind() {
		return Kind.ConstructorDecl;
	}

	public BUTree<SNodeList> modifiers() {
		return modifiers;
	}

	public SConstructorDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	public BUTree<SNodeList> typeParams() {
		return typeParams;
	}

	public SConstructorDecl withTypeParams(BUTree<SNodeList> typeParams) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	public BUTree<SName> name() {
		return name;
	}

	public SConstructorDecl withName(BUTree<SName> name) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	public BUTree<SNodeList> params() {
		return params;
	}

	public SConstructorDecl withParams(BUTree<SNodeList> params) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	public BUTree<SNodeList> throwsClause() {
		return throwsClause;
	}

	public SConstructorDecl withThrowsClause(BUTree<SNodeList> throwsClause) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	public BUTree<SBlockStmt> body() {
		return body;
	}

	public SConstructorDecl withBody(BUTree<SBlockStmt> body) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SConstructorDecl> location) {
		return new TDConstructorDecl(location);
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
		return BODY;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SConstructorDecl state = (SConstructorDecl) o;
		if (!modifiers.equals(state.modifiers))
			return false;
		if (!typeParams.equals(state.typeParams))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!params.equals(state.params))
			return false;
		if (!throwsClause.equals(state.throwsClause))
			return false;
		if (body == null ? state.body != null : !body.equals(state.body))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + modifiers.hashCode();
		result = 37 * result + typeParams.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + params.hashCode();
		result = 37 * result + throwsClause.hashCode();
		if (body != null) result = 37 * result + body.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SConstructorDecl, SNodeList, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SConstructorDecl, SNodeList, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SConstructorDecl state) {
			return state.modifiers;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, BUTree<SNodeList> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return TYPE_PARAMS;
		}
	};

	public static STypeSafeTraversal<SConstructorDecl, SNodeList, NodeList<TypeParameter>> TYPE_PARAMS = new STypeSafeTraversal<SConstructorDecl, SNodeList, NodeList<TypeParameter>>() {

		@Override
		public BUTree<?> doTraverse(SConstructorDecl state) {
			return state.typeParams;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, BUTree<SNodeList> child) {
			return state.withTypeParams(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SConstructorDecl, SName, Name> NAME = new STypeSafeTraversal<SConstructorDecl, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SConstructorDecl state) {
			return state.name;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE_PARAMS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return PARAMS;
		}
	};

	public static STypeSafeTraversal<SConstructorDecl, SNodeList, NodeList<FormalParameter>> PARAMS = new STypeSafeTraversal<SConstructorDecl, SNodeList, NodeList<FormalParameter>>() {

		@Override
		public BUTree<?> doTraverse(SConstructorDecl state) {
			return state.params;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, BUTree<SNodeList> child) {
			return state.withParams(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return THROWS_CLAUSE;
		}
	};

	public static STypeSafeTraversal<SConstructorDecl, SNodeList, NodeList<QualifiedType>> THROWS_CLAUSE = new STypeSafeTraversal<SConstructorDecl, SNodeList, NodeList<QualifiedType>>() {

		@Override
		public BUTree<?> doTraverse(SConstructorDecl state) {
			return state.throwsClause;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, BUTree<SNodeList> child) {
			return state.withThrowsClause(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return PARAMS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SConstructorDecl, SBlockStmt, BlockStmt> BODY = new STypeSafeTraversal<SConstructorDecl, SBlockStmt, BlockStmt>() {

		@Override
		public BUTree<?> doTraverse(SConstructorDecl state) {
			return state.body;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, BUTree<SBlockStmt> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return THROWS_CLAUSE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(MODIFIERS, SExtendedModifier.multiLineShape),
			child(TYPE_PARAMS, STypeParameter.listShape),
			child(NAME),
			token(LToken.ParenthesisLeft),
			child(PARAMS, SFormalParameter.listShape),
			token(LToken.ParenthesisRight),
			child(THROWS_CLAUSE, org.jlato.internal.bu.type.SQualifiedType.throwsClauseShape),
			none().withSpacingAfter(space()), child(BODY)
	);
}
