package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDMethodDecl;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.name.*;
import org.jlato.tree.stmt.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.space;

public class SMethodDecl extends SNode<SMethodDecl> implements SMemberDecl {

	public static BUTree<SMethodDecl> make(BUTree<SNodeList> modifiers, BUTree<SNodeList> typeParams, BUTree<? extends SType> type, BUTree<SName> name, BUTree<SNodeList> params, BUTree<SNodeList> dims, BUTree<SNodeList> throwsClause, BUTree<SNodeOption> body) {
		return new BUTree<SMethodDecl>(new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body));
	}

	public final BUTree<SNodeList> modifiers;

	public final BUTree<SNodeList> typeParams;

	public final BUTree<? extends SType> type;

	public final BUTree<SName> name;

	public final BUTree<SNodeList> params;

	public final BUTree<SNodeList> dims;

	public final BUTree<SNodeList> throwsClause;

	public final BUTree<SNodeOption> body;

	public SMethodDecl(BUTree<SNodeList> modifiers, BUTree<SNodeList> typeParams, BUTree<? extends SType> type, BUTree<SName> name, BUTree<SNodeList> params, BUTree<SNodeList> dims, BUTree<SNodeList> throwsClause, BUTree<SNodeOption> body) {
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

	public BUTree<SNodeList> modifiers() {
		return modifiers;
	}

	public SMethodDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	public BUTree<SNodeList> typeParams() {
		return typeParams;
	}

	public SMethodDecl withTypeParams(BUTree<SNodeList> typeParams) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	public BUTree<? extends SType> type() {
		return type;
	}

	public SMethodDecl withType(BUTree<? extends SType> type) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	public BUTree<SName> name() {
		return name;
	}

	public SMethodDecl withName(BUTree<SName> name) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	public BUTree<SNodeList> params() {
		return params;
	}

	public SMethodDecl withParams(BUTree<SNodeList> params) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	public BUTree<SNodeList> dims() {
		return dims;
	}

	public SMethodDecl withDims(BUTree<SNodeList> dims) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	public BUTree<SNodeList> throwsClause() {
		return throwsClause;
	}

	public SMethodDecl withThrowsClause(BUTree<SNodeList> throwsClause) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	public BUTree<SNodeOption> body() {
		return body;
	}

	public SMethodDecl withBody(BUTree<SNodeOption> body) {
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

	public static STypeSafeTraversal<SMethodDecl, SNodeList, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SMethodDecl, SNodeList, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SMethodDecl state) {
			return state.modifiers;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, BUTree<SNodeList> child) {
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

	public static STypeSafeTraversal<SMethodDecl, SNodeList, NodeList<TypeParameter>> TYPE_PARAMS = new STypeSafeTraversal<SMethodDecl, SNodeList, NodeList<TypeParameter>>() {

		@Override
		public BUTree<?> doTraverse(SMethodDecl state) {
			return state.typeParams;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, BUTree<SNodeList> child) {
			return state.withTypeParams(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return TYPE;
		}
	};

	public static STypeSafeTraversal<SMethodDecl, SType, Type> TYPE = new STypeSafeTraversal<SMethodDecl, SType, Type>() {

		@Override
		public BUTree<?> doTraverse(SMethodDecl state) {
			return state.type;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, BUTree<SType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE_PARAMS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SMethodDecl, SName, Name> NAME = new STypeSafeTraversal<SMethodDecl, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SMethodDecl state) {
			return state.name;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return PARAMS;
		}
	};

	public static STypeSafeTraversal<SMethodDecl, SNodeList, NodeList<FormalParameter>> PARAMS = new STypeSafeTraversal<SMethodDecl, SNodeList, NodeList<FormalParameter>>() {

		@Override
		public BUTree<?> doTraverse(SMethodDecl state) {
			return state.params;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, BUTree<SNodeList> child) {
			return state.withParams(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return DIMS;
		}
	};

	public static STypeSafeTraversal<SMethodDecl, SNodeList, NodeList<ArrayDim>> DIMS = new STypeSafeTraversal<SMethodDecl, SNodeList, NodeList<ArrayDim>>() {

		@Override
		public BUTree<?> doTraverse(SMethodDecl state) {
			return state.dims;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, BUTree<SNodeList> child) {
			return state.withDims(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return PARAMS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return THROWS_CLAUSE;
		}
	};

	public static STypeSafeTraversal<SMethodDecl, SNodeList, NodeList<QualifiedType>> THROWS_CLAUSE = new STypeSafeTraversal<SMethodDecl, SNodeList, NodeList<QualifiedType>>() {

		@Override
		public BUTree<?> doTraverse(SMethodDecl state) {
			return state.throwsClause;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, BUTree<SNodeList> child) {
			return state.withThrowsClause(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return DIMS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SMethodDecl, SNodeOption, NodeOption<BlockStmt>> BODY = new STypeSafeTraversal<SMethodDecl, SNodeOption, NodeOption<BlockStmt>>() {

		@Override
		public BUTree<?> doTraverse(SMethodDecl state) {
			return state.body;
		}

		@Override
		public SMethodDecl doRebuildParentState(SMethodDecl state, BUTree<SNodeOption> child) {
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
