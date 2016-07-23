package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDMethodDecl;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
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

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a method declaration.
 */
public class SMethodDecl extends SNode<SMethodDecl> implements SMemberDecl {

	/**
	 * Creates a <code>BUTree</code> with a new method declaration.
	 *
	 * @param modifiers    the modifiers child <code>BUTree</code>.
	 * @param typeParams   the type parameters child <code>BUTree</code>.
	 * @param type         the type child <code>BUTree</code>.
	 * @param name         the name child <code>BUTree</code>.
	 * @param params       the parameters child <code>BUTree</code>.
	 * @param dims         the dimensions child <code>BUTree</code>.
	 * @param throwsClause the 'throws' clause child <code>BUTree</code>.
	 * @param body         the body child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a method declaration.
	 */
	public static BUTree<SMethodDecl> make(BUTree<SNodeList> modifiers, BUTree<SNodeList> typeParams, BUTree<? extends SType> type, BUTree<SName> name, BUTree<SNodeList> params, BUTree<SNodeList> dims, BUTree<SNodeList> throwsClause, BUTree<SNodeOption> body) {
		return new BUTree<SMethodDecl>(new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body));
	}

	/**
	 * The modifiers of this method declaration state.
	 */
	public final BUTree<SNodeList> modifiers;

	/**
	 * The type parameters of this method declaration state.
	 */
	public final BUTree<SNodeList> typeParams;

	/**
	 * The type of this method declaration state.
	 */
	public final BUTree<? extends SType> type;

	/**
	 * The name of this method declaration state.
	 */
	public final BUTree<SName> name;

	/**
	 * The parameters of this method declaration state.
	 */
	public final BUTree<SNodeList> params;

	/**
	 * The dimensions of this method declaration state.
	 */
	public final BUTree<SNodeList> dims;

	/**
	 * The 'throws' clause of this method declaration state.
	 */
	public final BUTree<SNodeList> throwsClause;

	/**
	 * The body of this method declaration state.
	 */
	public final BUTree<SNodeOption> body;

	/**
	 * Constructs a method declaration state.
	 *
	 * @param modifiers    the modifiers child <code>BUTree</code>.
	 * @param typeParams   the type parameters child <code>BUTree</code>.
	 * @param type         the type child <code>BUTree</code>.
	 * @param name         the name child <code>BUTree</code>.
	 * @param params       the parameters child <code>BUTree</code>.
	 * @param dims         the dimensions child <code>BUTree</code>.
	 * @param throwsClause the 'throws' clause child <code>BUTree</code>.
	 * @param body         the body child <code>BUTree</code>.
	 */
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

	/**
	 * Returns the kind of this method declaration.
	 *
	 * @return the kind of this method declaration.
	 */
	@Override
	public Kind kind() {
		return Kind.MethodDecl;
	}

	/**
	 * Replaces the modifiers of this method declaration state.
	 *
	 * @param modifiers the replacement for the modifiers of this method declaration state.
	 * @return the resulting mutated method declaration state.
	 */
	public SMethodDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	/**
	 * Replaces the type parameters of this method declaration state.
	 *
	 * @param typeParams the replacement for the type parameters of this method declaration state.
	 * @return the resulting mutated method declaration state.
	 */
	public SMethodDecl withTypeParams(BUTree<SNodeList> typeParams) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	/**
	 * Replaces the type of this method declaration state.
	 *
	 * @param type the replacement for the type of this method declaration state.
	 * @return the resulting mutated method declaration state.
	 */
	public SMethodDecl withType(BUTree<? extends SType> type) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	/**
	 * Replaces the name of this method declaration state.
	 *
	 * @param name the replacement for the name of this method declaration state.
	 * @return the resulting mutated method declaration state.
	 */
	public SMethodDecl withName(BUTree<SName> name) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	/**
	 * Replaces the parameters of this method declaration state.
	 *
	 * @param params the replacement for the parameters of this method declaration state.
	 * @return the resulting mutated method declaration state.
	 */
	public SMethodDecl withParams(BUTree<SNodeList> params) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	/**
	 * Replaces the dimensions of this method declaration state.
	 *
	 * @param dims the replacement for the dimensions of this method declaration state.
	 * @return the resulting mutated method declaration state.
	 */
	public SMethodDecl withDims(BUTree<SNodeList> dims) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	/**
	 * Replaces the 'throws' clause of this method declaration state.
	 *
	 * @param throwsClause the replacement for the 'throws' clause of this method declaration state.
	 * @return the resulting mutated method declaration state.
	 */
	public SMethodDecl withThrowsClause(BUTree<SNodeList> throwsClause) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	/**
	 * Replaces the body of this method declaration state.
	 *
	 * @param body the replacement for the body of this method declaration state.
	 * @return the resulting mutated method declaration state.
	 */
	public SMethodDecl withBody(BUTree<SNodeOption> body) {
		return new SMethodDecl(modifiers, typeParams, type, name, params, dims, throwsClause, body);
	}

	/**
	 * Builds a method declaration facade for the specified method declaration <code>TDLocation</code>.
	 *
	 * @param location the method declaration <code>TDLocation</code>.
	 * @return a method declaration facade for the specified method declaration <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SMethodDecl> location) {
		return new TDMethodDecl(location);
	}

	/**
	 * Returns the shape for this method declaration state.
	 *
	 * @return the shape for this method declaration state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this method declaration state.
	 *
	 * @return the first child traversal for this method declaration state.
	 */
	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	/**
	 * Returns the last child traversal for this method declaration state.
	 *
	 * @return the last child traversal for this method declaration state.
	 */
	@Override
	public STraversal lastChild() {
		return BODY;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
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

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
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
			child(NAME).withSpacingBefore(space()),
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
