package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
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

public class SConstructorDecl extends SNodeState<SConstructorDecl> implements SMemberDecl {

	public static BUTree<SConstructorDecl> make(BUTree<SNodeListState> modifiers, BUTree<SNodeListState> typeParams, BUTree<SName> name, BUTree<SNodeListState> params, BUTree<SNodeListState> throwsClause, BUTree<SBlockStmt> body) {
		return new BUTree<SConstructorDecl>(new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body));
	}

	public final BUTree<SNodeListState> modifiers;

	public final BUTree<SNodeListState> typeParams;

	public final BUTree<SName> name;

	public final BUTree<SNodeListState> params;

	public final BUTree<SNodeListState> throwsClause;

	public final BUTree<SBlockStmt> body;

	public SConstructorDecl(BUTree<SNodeListState> modifiers, BUTree<SNodeListState> typeParams, BUTree<SName> name, BUTree<SNodeListState> params, BUTree<SNodeListState> throwsClause, BUTree<SBlockStmt> body) {
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

	public BUTree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SConstructorDecl withModifiers(BUTree<SNodeListState> modifiers) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	public BUTree<SNodeListState> typeParams() {
		return typeParams;
	}

	public SConstructorDecl withTypeParams(BUTree<SNodeListState> typeParams) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	public BUTree<SName> name() {
		return name;
	}

	public SConstructorDecl withName(BUTree<SName> name) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	public BUTree<SNodeListState> params() {
		return params;
	}

	public SConstructorDecl withParams(BUTree<SNodeListState> params) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	public BUTree<SNodeListState> throwsClause() {
		return throwsClause;
	}

	public SConstructorDecl withThrowsClause(BUTree<SNodeListState> throwsClause) {
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

	public static STypeSafeTraversal<SConstructorDecl, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SConstructorDecl, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SConstructorDecl state) {
			return state.modifiers;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, BUTree<SNodeListState> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE_PARAMS;
		}
	};

	public static STypeSafeTraversal<SConstructorDecl, SNodeListState, NodeList<TypeParameter>> TYPE_PARAMS = new STypeSafeTraversal<SConstructorDecl, SNodeListState, NodeList<TypeParameter>>() {

		@Override
		public BUTree<?> doTraverse(SConstructorDecl state) {
			return state.typeParams;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, BUTree<SNodeListState> child) {
			return state.withTypeParams(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
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
		public STraversal leftSibling(STreeState state) {
			return TYPE_PARAMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return PARAMS;
		}
	};

	public static STypeSafeTraversal<SConstructorDecl, SNodeListState, NodeList<FormalParameter>> PARAMS = new STypeSafeTraversal<SConstructorDecl, SNodeListState, NodeList<FormalParameter>>() {

		@Override
		public BUTree<?> doTraverse(SConstructorDecl state) {
			return state.params;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, BUTree<SNodeListState> child) {
			return state.withParams(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return THROWS_CLAUSE;
		}
	};

	public static STypeSafeTraversal<SConstructorDecl, SNodeListState, NodeList<QualifiedType>> THROWS_CLAUSE = new STypeSafeTraversal<SConstructorDecl, SNodeListState, NodeList<QualifiedType>>() {

		@Override
		public BUTree<?> doTraverse(SConstructorDecl state) {
			return state.throwsClause;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, BUTree<SNodeListState> child) {
			return state.withThrowsClause(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return PARAMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
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
		public STraversal leftSibling(STreeState state) {
			return THROWS_CLAUSE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
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
