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
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDMethodDecl;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.decl.TypeParameter;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SMethodDecl extends SNodeState<SMethodDecl> implements SMemberDecl {

	public static STree<SMethodDecl> make(STree<SNodeListState> modifiers, STree<SNodeListState> typeParams, STree<? extends SType> type, STree<SName> name, STree<SNodeListState> params, STree<SNodeListState> dims, STree<SNodeListState> throwsClause, STree<SNodeOptionState> body) {
		return new STree<SMethodDecl>(new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body));
	}

	public final STree<SNodeListState> modifiers;

	public final STree<SNodeListState> typeParams;

	public final STree<? extends SType> type;

	public final STree<SName> name;

	public final STree<SNodeListState> params;

	public final STree<SNodeListState> dims;

	public final STree<SNodeListState> throwsClause;

	public final STree<SNodeOptionState> body;

	public SMethodDecl(STree<SNodeListState> modifiers, STree<SNodeListState> typeParams, STree<? extends SType> type, STree<SName> name, STree<SNodeListState> params, STree<SNodeListState> dims, STree<SNodeListState> throwsClause, STree<SNodeOptionState> body) {
		this.modifiers = modifiers;
		this.typeParams = typeParams;
		this.type = type;
		this.name = name;
		this.params = params;
		this.dims = dims;
		this.throwsClause = throwsClause;
		this.body = body;
	}

	@Override
	public Kind kind() {
		return Kind.MethodDecl;
	}

	public STree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SMethodDecl withModifiers(STree<SNodeListState> modifiers) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	public STree<SNodeListState> typeParams() {
		return typeParams;
	}

	public SMethodDecl withTypeParams(STree<SNodeListState> typeParams) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	public STree<? extends SType> type() {
		return type;
	}

	public SMethodDecl withType(STree<? extends SType> type) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	public STree<SName> name() {
		return name;
	}

	public SMethodDecl withName(STree<SName> name) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	public STree<SNodeListState> params() {
		return params;
	}

	public SMethodDecl withParams(STree<SNodeListState> params) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	public STree<SNodeListState> dims() {
		return dims;
	}

	public SMethodDecl withDims(STree<SNodeListState> dims) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	public STree<SNodeListState> throwsClause() {
		return throwsClause;
	}

	public SMethodDecl withThrowsClause(STree<SNodeListState> throwsClause) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	public STree<SNodeOptionState> body() {
		return body;
	}

	public SMethodDecl withBody(STree<SNodeOptionState> body) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SMethodDecl> location) {
		return new TDMethodDecl(location);
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
		SMethodDecl state = (SMethodDecl) o;
		if (!modifiers.equals(state.modifiers))
			return false;
		if (!typeParams.equals(state.typeParams))
			return false;
		if (type == null ? state.type != null : !type.equals(state.type))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!params.equals(state.params))
			return false;
		if (!dims.equals(state.dims))
			return false;
		if (!throwsClause.equals(state.throwsClause))
			return false;
		if (!body.equals(state.body))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + modifiers.hashCode();
		result = 37 * result + typeParams.hashCode();
		if (type != null) result = 37 * result + type.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + params.hashCode();
		result = 37 * result + dims.hashCode();
		result = 37 * result + throwsClause.hashCode();
		result = 37 * result + body.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SMethodDecl, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SMethodDecl, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public STree<?> doTraverse(SMethodDecl state) {
			return state.modifiers;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, STree<SNodeListState> child) {
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

	public static STypeSafeTraversal<SMethodDecl, SNodeListState, NodeList<TypeParameter>> TYPE_PARAMS = new STypeSafeTraversal<SMethodDecl, SNodeListState, NodeList<TypeParameter>>() {

		@Override
		public STree<?> doTraverse(SMethodDecl state) {
			return state.typeParams;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, STree<SNodeListState> child) {
			return state.withTypeParams(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE;
		}
	};

	public static STypeSafeTraversal<SMethodDecl, SType, Type> TYPE = new STypeSafeTraversal<SMethodDecl, SType, Type>() {

		@Override
		public STree<?> doTraverse(SMethodDecl state) {
			return state.type;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, STree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE_PARAMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SMethodDecl, SName, Name> NAME = new STypeSafeTraversal<SMethodDecl, SName, Name>() {

		@Override
		public STree<?> doTraverse(SMethodDecl state) {
			return state.name;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, STree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return PARAMS;
		}
	};

	public static STypeSafeTraversal<SMethodDecl, SNodeListState, NodeList<FormalParameter>> PARAMS = new STypeSafeTraversal<SMethodDecl, SNodeListState, NodeList<FormalParameter>>() {

		@Override
		public STree<?> doTraverse(SMethodDecl state) {
			return state.params;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, STree<SNodeListState> child) {
			return state.withParams(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return DIMS;
		}
	};

	public static STypeSafeTraversal<SMethodDecl, SNodeListState, NodeList<ArrayDim>> DIMS = new STypeSafeTraversal<SMethodDecl, SNodeListState, NodeList<ArrayDim>>() {

		@Override
		public STree<?> doTraverse(SMethodDecl state) {
			return state.dims;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, STree<SNodeListState> child) {
			return state.withDims(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return PARAMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return THROWS_CLAUSE;
		}
	};

	public static STypeSafeTraversal<SMethodDecl, SNodeListState, NodeList<QualifiedType>> THROWS_CLAUSE = new STypeSafeTraversal<SMethodDecl, SNodeListState, NodeList<QualifiedType>>() {

		@Override
		public STree<?> doTraverse(SMethodDecl state) {
			return state.throwsClause;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, STree<SNodeListState> child) {
			return state.withThrowsClause(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return DIMS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SMethodDecl, SNodeOptionState, NodeOption<BlockStmt>> BODY = new STypeSafeTraversal<SMethodDecl, SNodeOptionState, NodeOption<BlockStmt>>() {

		@Override
		public STree<?> doTraverse(SMethodDecl state) {
			return state.body;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, STree<SNodeOptionState> child) {
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
			child(TYPE),
			none().withSpacingAfter(space()),
			child(NAME),
			token(LToken.ParenthesisLeft),
			child(PARAMS, SFormalParameter.listShape),
			token(LToken.ParenthesisRight),
			child(DIMS, SArrayDim.listShape),
			child(THROWS_CLAUSE, org.jlato.internal.bu.type.SQualifiedType.throwsClauseShape),
			child(BODY, alternative(some(),
					element().withSpacingBefore(space()),
					token(LToken.SemiColon)
			))
	);
}
