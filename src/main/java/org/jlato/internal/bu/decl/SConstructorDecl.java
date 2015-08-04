package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.decl.TDConstructorDecl;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.decl.TypeParameter;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SConstructorDecl extends SNodeState<SConstructorDecl> implements SMemberDecl {

	public static STree<SConstructorDecl> make(STree<SNodeListState> modifiers, STree<SNodeListState> typeParams, STree<SName> name, STree<SNodeListState> params, STree<SNodeListState> throwsClause, STree<SBlockStmt> body) {
		return new STree<SConstructorDecl>(new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body));
	}

	public final STree<SNodeListState> modifiers;

	public final STree<SNodeListState> typeParams;

	public final STree<SName> name;

	public final STree<SNodeListState> params;

	public final STree<SNodeListState> throwsClause;

	public final STree<SBlockStmt> body;

	public SConstructorDecl(STree<SNodeListState> modifiers, STree<SNodeListState> typeParams, STree<SName> name, STree<SNodeListState> params, STree<SNodeListState> throwsClause, STree<SBlockStmt> body) {
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

	public STree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SConstructorDecl withModifiers(STree<SNodeListState> modifiers) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	public STree<SNodeListState> typeParams() {
		return typeParams;
	}

	public SConstructorDecl withTypeParams(STree<SNodeListState> typeParams) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	public STree<SName> name() {
		return name;
	}

	public SConstructorDecl withName(STree<SName> name) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	public STree<SNodeListState> params() {
		return params;
	}

	public SConstructorDecl withParams(STree<SNodeListState> params) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	public STree<SNodeListState> throwsClause() {
		return throwsClause;
	}

	public SConstructorDecl withThrowsClause(STree<SNodeListState> throwsClause) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	public STree<SBlockStmt> body() {
		return body;
	}

	public SConstructorDecl withBody(STree<SBlockStmt> body) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	@Override
	protected Tree doInstantiate(SLocation<SConstructorDecl> location) {
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
		public STree<?> doTraverse(SConstructorDecl state) {
			return state.modifiers;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, STree<SNodeListState> child) {
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
		public STree<?> doTraverse(SConstructorDecl state) {
			return state.typeParams;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, STree<SNodeListState> child) {
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
		public STree<?> doTraverse(SConstructorDecl state) {
			return state.name;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, STree<SName> child) {
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
		public STree<?> doTraverse(SConstructorDecl state) {
			return state.params;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, STree<SNodeListState> child) {
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
		public STree<?> doTraverse(SConstructorDecl state) {
			return state.throwsClause;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, STree<SNodeListState> child) {
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
		public STree<?> doTraverse(SConstructorDecl state) {
			return state.body;
		}

		@Override
		public SConstructorDecl doRebuildParentState(SConstructorDecl state, STree<SBlockStmt> child) {
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
