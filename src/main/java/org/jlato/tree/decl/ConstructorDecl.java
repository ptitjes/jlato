/*
 * Copyright (C) 2015 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.tree.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class ConstructorDecl extends TreeBase<ConstructorDecl.State, MemberDecl, ConstructorDecl> implements MemberDecl {

	public Kind kind() {
		return Kind.ConstructorDecl;
	}

	private ConstructorDecl(SLocation<ConstructorDecl.State> location) {
		super(location);
	}

	public static STree<ConstructorDecl.State> make(NodeList<ExtendedModifier> modifiers, NodeList<TypeParameter> typeParams, Name name, NodeList<FormalParameter> params, NodeList<QualifiedType> throwsClause, BlockStmt body) {
		return new STree<ConstructorDecl.State>(new ConstructorDecl.State(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<SNodeListState>nodeOf(typeParams), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeListState>nodeOf(params), TreeBase.<SNodeListState>nodeOf(throwsClause), TreeBase.<BlockStmt.State>nodeOf(body)));
	}

	public ConstructorDecl(NodeList<ExtendedModifier> modifiers, NodeList<TypeParameter> typeParams, Name name, NodeList<FormalParameter> params, NodeList<QualifiedType> throwsClause, BlockStmt body) {
		super(new SLocation<ConstructorDecl.State>(make(modifiers, typeParams, name, params, throwsClause, body)));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.Constructor;
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public ConstructorDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public ConstructorDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	public NodeList<TypeParameter> typeParams() {
		return location.safeTraversal(TYPE_PARAMS);
	}

	public ConstructorDecl withTypeParams(NodeList<TypeParameter> typeParams) {
		return location.safeTraversalReplace(TYPE_PARAMS, typeParams);
	}

	public ConstructorDecl withTypeParams(Mutation<NodeList<TypeParameter>> mutation) {
		return location.safeTraversalMutate(TYPE_PARAMS, mutation);
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public ConstructorDecl withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public ConstructorDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeList<FormalParameter> params() {
		return location.safeTraversal(PARAMS);
	}

	public ConstructorDecl withParams(NodeList<FormalParameter> params) {
		return location.safeTraversalReplace(PARAMS, params);
	}

	public ConstructorDecl withParams(Mutation<NodeList<FormalParameter>> mutation) {
		return location.safeTraversalMutate(PARAMS, mutation);
	}

	public NodeList<QualifiedType> throwsClause() {
		return location.safeTraversal(THROWS_CLAUSE);
	}

	public ConstructorDecl withThrowsClause(NodeList<QualifiedType> throwsClause) {
		return location.safeTraversalReplace(THROWS_CLAUSE, throwsClause);
	}

	public ConstructorDecl withThrowsClause(Mutation<NodeList<QualifiedType>> mutation) {
		return location.safeTraversalMutate(THROWS_CLAUSE, mutation);
	}

	public BlockStmt body() {
		return location.safeTraversal(BODY);
	}

	public ConstructorDecl withBody(BlockStmt body) {
		return location.safeTraversalReplace(BODY, body);
	}

	public ConstructorDecl withBody(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(BODY, mutation);
	}

	private static final STraversal<ConstructorDecl.State> MODIFIERS = new STraversal<ConstructorDecl.State>() {

		public STree<?> traverse(ConstructorDecl.State state) {
			return state.modifiers;
		}

		public ConstructorDecl.State rebuildParentState(ConstructorDecl.State state, STree<?> child) {
			return state.withModifiers((STree) child);
		}

		public STraversal<ConstructorDecl.State> leftSibling(ConstructorDecl.State state) {
			return null;
		}

		public STraversal<ConstructorDecl.State> rightSibling(ConstructorDecl.State state) {
			return TYPE_PARAMS;
		}
	};
	private static final STraversal<ConstructorDecl.State> TYPE_PARAMS = new STraversal<ConstructorDecl.State>() {

		public STree<?> traverse(ConstructorDecl.State state) {
			return state.typeParams;
		}

		public ConstructorDecl.State rebuildParentState(ConstructorDecl.State state, STree<?> child) {
			return state.withTypeParams((STree) child);
		}

		public STraversal<ConstructorDecl.State> leftSibling(ConstructorDecl.State state) {
			return MODIFIERS;
		}

		public STraversal<ConstructorDecl.State> rightSibling(ConstructorDecl.State state) {
			return NAME;
		}
	};
	private static final STraversal<ConstructorDecl.State> NAME = new STraversal<ConstructorDecl.State>() {

		public STree<?> traverse(ConstructorDecl.State state) {
			return state.name;
		}

		public ConstructorDecl.State rebuildParentState(ConstructorDecl.State state, STree<?> child) {
			return state.withName((STree) child);
		}

		public STraversal<ConstructorDecl.State> leftSibling(ConstructorDecl.State state) {
			return TYPE_PARAMS;
		}

		public STraversal<ConstructorDecl.State> rightSibling(ConstructorDecl.State state) {
			return PARAMS;
		}
	};
	private static final STraversal<ConstructorDecl.State> PARAMS = new STraversal<ConstructorDecl.State>() {

		public STree<?> traverse(ConstructorDecl.State state) {
			return state.params;
		}

		public ConstructorDecl.State rebuildParentState(ConstructorDecl.State state, STree<?> child) {
			return state.withParams((STree) child);
		}

		public STraversal<ConstructorDecl.State> leftSibling(ConstructorDecl.State state) {
			return NAME;
		}

		public STraversal<ConstructorDecl.State> rightSibling(ConstructorDecl.State state) {
			return THROWS_CLAUSE;
		}
	};
	private static final STraversal<ConstructorDecl.State> THROWS_CLAUSE = new STraversal<ConstructorDecl.State>() {

		public STree<?> traverse(ConstructorDecl.State state) {
			return state.throwsClause;
		}

		public ConstructorDecl.State rebuildParentState(ConstructorDecl.State state, STree<?> child) {
			return state.withThrowsClause((STree) child);
		}

		public STraversal<ConstructorDecl.State> leftSibling(ConstructorDecl.State state) {
			return PARAMS;
		}

		public STraversal<ConstructorDecl.State> rightSibling(ConstructorDecl.State state) {
			return BODY;
		}
	};
	private static final STraversal<ConstructorDecl.State> BODY = new STraversal<ConstructorDecl.State>() {

		public STree<?> traverse(ConstructorDecl.State state) {
			return state.body;
		}

		public ConstructorDecl.State rebuildParentState(ConstructorDecl.State state, STree<?> child) {
			return state.withBody((STree) child);
		}

		public STraversal<ConstructorDecl.State> leftSibling(ConstructorDecl.State state) {
			return THROWS_CLAUSE;
		}

		public STraversal<ConstructorDecl.State> rightSibling(ConstructorDecl.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(TYPE_PARAMS, TypeParameter.listShape),
			child(NAME),
			token(LToken.ParenthesisLeft),
			child(PARAMS, FormalParameter.listShape),
			token(LToken.ParenthesisRight),
			child(THROWS_CLAUSE, QualifiedType.throwsClauseShape),
			none().withSpacingAfter(space()), child(BODY)
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> modifiers;

		public final STree<SNodeListState> typeParams;

		public final STree<Name.State> name;

		public final STree<SNodeListState> params;

		public final STree<SNodeListState> throwsClause;

		public final STree<BlockStmt.State> body;

		State(STree<SNodeListState> modifiers, STree<SNodeListState> typeParams, STree<Name.State> name, STree<SNodeListState> params, STree<SNodeListState> throwsClause, STree<BlockStmt.State> body) {
			this.modifiers = modifiers;
			this.typeParams = typeParams;
			this.name = name;
			this.params = params;
			this.throwsClause = throwsClause;
			this.body = body;
		}

		public ConstructorDecl.State withModifiers(STree<SNodeListState> modifiers) {
			return new ConstructorDecl.State(modifiers, typeParams, name, params, throwsClause, body);
		}

		public ConstructorDecl.State withTypeParams(STree<SNodeListState> typeParams) {
			return new ConstructorDecl.State(modifiers, typeParams, name, params, throwsClause, body);
		}

		public ConstructorDecl.State withName(STree<Name.State> name) {
			return new ConstructorDecl.State(modifiers, typeParams, name, params, throwsClause, body);
		}

		public ConstructorDecl.State withParams(STree<SNodeListState> params) {
			return new ConstructorDecl.State(modifiers, typeParams, name, params, throwsClause, body);
		}

		public ConstructorDecl.State withThrowsClause(STree<SNodeListState> throwsClause) {
			return new ConstructorDecl.State(modifiers, typeParams, name, params, throwsClause, body);
		}

		public ConstructorDecl.State withBody(STree<BlockStmt.State> body) {
			return new ConstructorDecl.State(modifiers, typeParams, name, params, throwsClause, body);
		}

		public STraversal<ConstructorDecl.State> firstChild() {
			return MODIFIERS;
		}

		public STraversal<ConstructorDecl.State> lastChild() {
			return BODY;
		}

		public Tree instantiate(SLocation<ConstructorDecl.State> location) {
			return new ConstructorDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.ConstructorDecl;
		}
	}
}
