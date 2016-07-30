package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDConstructorDecl;
import org.jlato.internal.parser.TokenType;
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

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a constructor declaration.
 */
public class SConstructorDecl extends SNode<SConstructorDecl> implements SMemberDecl {

	/**
	 * Creates a <code>BUTree</code> with a new constructor declaration.
	 *
	 * @param modifiers    the modifiers child <code>BUTree</code>.
	 * @param typeParams   the type parameters child <code>BUTree</code>.
	 * @param name         the name child <code>BUTree</code>.
	 * @param params       the parameters child <code>BUTree</code>.
	 * @param throwsClause the 'throws' clause child <code>BUTree</code>.
	 * @param body         the body child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a constructor declaration.
	 */
	public static BUTree<SConstructorDecl> make(BUTree<SNodeList> modifiers, BUTree<SNodeList> typeParams, BUTree<SName> name, BUTree<SNodeList> params, BUTree<SNodeList> throwsClause, BUTree<SBlockStmt> body) {
		return new BUTree<SConstructorDecl>(new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body));
	}

	/**
	 * The modifiers of this constructor declaration state.
	 */
	public final BUTree<SNodeList> modifiers;

	/**
	 * The type parameters of this constructor declaration state.
	 */
	public final BUTree<SNodeList> typeParams;

	/**
	 * The name of this constructor declaration state.
	 */
	public final BUTree<SName> name;

	/**
	 * The parameters of this constructor declaration state.
	 */
	public final BUTree<SNodeList> params;

	/**
	 * The 'throws' clause of this constructor declaration state.
	 */
	public final BUTree<SNodeList> throwsClause;

	/**
	 * The body of this constructor declaration state.
	 */
	public final BUTree<SBlockStmt> body;

	/**
	 * Constructs a constructor declaration state.
	 *
	 * @param modifiers    the modifiers child <code>BUTree</code>.
	 * @param typeParams   the type parameters child <code>BUTree</code>.
	 * @param name         the name child <code>BUTree</code>.
	 * @param params       the parameters child <code>BUTree</code>.
	 * @param throwsClause the 'throws' clause child <code>BUTree</code>.
	 * @param body         the body child <code>BUTree</code>.
	 */
	public SConstructorDecl(BUTree<SNodeList> modifiers, BUTree<SNodeList> typeParams, BUTree<SName> name, BUTree<SNodeList> params, BUTree<SNodeList> throwsClause, BUTree<SBlockStmt> body) {
		this.modifiers = modifiers;
		this.typeParams = typeParams;
		this.name = name;
		this.params = params;
		this.throwsClause = throwsClause;
		this.body = body;
	}

	/**
	 * Returns the kind of this constructor declaration.
	 *
	 * @return the kind of this constructor declaration.
	 */
	@Override
	public Kind kind() {
		return Kind.ConstructorDecl;
	}

	/**
	 * Replaces the modifiers of this constructor declaration state.
	 *
	 * @param modifiers the replacement for the modifiers of this constructor declaration state.
	 * @return the resulting mutated constructor declaration state.
	 */
	public SConstructorDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	/**
	 * Replaces the type parameters of this constructor declaration state.
	 *
	 * @param typeParams the replacement for the type parameters of this constructor declaration state.
	 * @return the resulting mutated constructor declaration state.
	 */
	public SConstructorDecl withTypeParams(BUTree<SNodeList> typeParams) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	/**
	 * Replaces the name of this constructor declaration state.
	 *
	 * @param name the replacement for the name of this constructor declaration state.
	 * @return the resulting mutated constructor declaration state.
	 */
	public SConstructorDecl withName(BUTree<SName> name) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	/**
	 * Replaces the parameters of this constructor declaration state.
	 *
	 * @param params the replacement for the parameters of this constructor declaration state.
	 * @return the resulting mutated constructor declaration state.
	 */
	public SConstructorDecl withParams(BUTree<SNodeList> params) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	/**
	 * Replaces the 'throws' clause of this constructor declaration state.
	 *
	 * @param throwsClause the replacement for the 'throws' clause of this constructor declaration state.
	 * @return the resulting mutated constructor declaration state.
	 */
	public SConstructorDecl withThrowsClause(BUTree<SNodeList> throwsClause) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	/**
	 * Replaces the body of this constructor declaration state.
	 *
	 * @param body the replacement for the body of this constructor declaration state.
	 * @return the resulting mutated constructor declaration state.
	 */
	public SConstructorDecl withBody(BUTree<SBlockStmt> body) {
		return new SConstructorDecl(modifiers, typeParams, name, params, throwsClause, body);
	}

	/**
	 * Builds a constructor declaration facade for the specified constructor declaration <code>TDLocation</code>.
	 *
	 * @param location the constructor declaration <code>TDLocation</code>.
	 * @return a constructor declaration facade for the specified constructor declaration <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SConstructorDecl> location) {
		return new TDConstructorDecl(location);
	}

	/**
	 * Returns the shape for this constructor declaration state.
	 *
	 * @return the shape for this constructor declaration state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this constructor declaration state.
	 *
	 * @return the first child traversal for this constructor declaration state.
	 */
	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	/**
	 * Returns the last child traversal for this constructor declaration state.
	 *
	 * @return the last child traversal for this constructor declaration state.
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
