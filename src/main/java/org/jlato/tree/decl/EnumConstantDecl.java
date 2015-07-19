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
import org.jlato.tree.NodeOption;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.SpacingConstraint.spacing;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

public class EnumConstantDecl extends TreeBase<EnumConstantDecl.State, MemberDecl, EnumConstantDecl> implements MemberDecl {

	public Kind kind() {
		return Kind.EnumConstantDecl;
	}

	private EnumConstantDecl(SLocation<EnumConstantDecl.State> location) {
		super(location);
	}

	public static STree<EnumConstantDecl.State> make(STree<SNodeListState> modifiers, STree<Name.State> name, STree<SNodeOptionState> args, STree<SNodeOptionState> classBody) {
		return new STree<EnumConstantDecl.State>(new EnumConstantDecl.State(modifiers, name, args, classBody));
	}

	public EnumConstantDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeOption<NodeList<Expr>> args, NodeOption<NodeList<MemberDecl>> classBody) {
		super(new SLocation<EnumConstantDecl.State>(make(TreeBase.<SNodeListState>nodeOf(modifiers), TreeBase.<Name.State>nodeOf(name), TreeBase.<SNodeOptionState>nodeOf(args), TreeBase.<SNodeOptionState>nodeOf(classBody))));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.EnumConstant;
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(MODIFIERS);
	}

	public EnumConstantDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(MODIFIERS, modifiers);
	}

	public EnumConstantDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(MODIFIERS, mutation);
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public EnumConstantDecl withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public EnumConstantDecl withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public NodeOption<NodeList<Expr>> args() {
		return location.safeTraversal(ARGS);
	}

	public EnumConstantDecl withArgs(NodeOption<NodeList<Expr>> args) {
		return location.safeTraversalReplace(ARGS, args);
	}

	public EnumConstantDecl withArgs(Mutation<NodeOption<NodeList<Expr>>> mutation) {
		return location.safeTraversalMutate(ARGS, mutation);
	}

	public NodeOption<NodeList<MemberDecl>> classBody() {
		return location.safeTraversal(CLASS_BODY);
	}

	public EnumConstantDecl withClassBody(NodeOption<NodeList<MemberDecl>> classBody) {
		return location.safeTraversalReplace(CLASS_BODY, classBody);
	}

	public EnumConstantDecl withClassBody(Mutation<NodeOption<NodeList<MemberDecl>>> mutation) {
		return location.safeTraversalMutate(CLASS_BODY, mutation);
	}

	private static final STraversal MODIFIERS = new STraversal() {

		public STree<?> traverse(EnumConstantDecl.State state) {
			return state.modifiers;
		}

		public EnumConstantDecl.State rebuildParentState(EnumConstantDecl.State state, STree<?> child) {
			return state.withModifiers((STree) child);
		}

		public STraversal leftSibling(EnumConstantDecl.State state) {
			return null;
		}

		public STraversal rightSibling(EnumConstantDecl.State state) {
			return NAME;
		}
	};
	private static final STraversal NAME = new STraversal() {

		public STree<?> traverse(EnumConstantDecl.State state) {
			return state.name;
		}

		public EnumConstantDecl.State rebuildParentState(EnumConstantDecl.State state, STree<?> child) {
			return state.withName((STree) child);
		}

		public STraversal leftSibling(EnumConstantDecl.State state) {
			return MODIFIERS;
		}

		public STraversal rightSibling(EnumConstantDecl.State state) {
			return ARGS;
		}
	};
	private static final STraversal ARGS = new STraversal() {

		public STree<?> traverse(EnumConstantDecl.State state) {
			return state.args;
		}

		public EnumConstantDecl.State rebuildParentState(EnumConstantDecl.State state, STree<?> child) {
			return state.withArgs((STree) child);
		}

		public STraversal leftSibling(EnumConstantDecl.State state) {
			return NAME;
		}

		public STraversal rightSibling(EnumConstantDecl.State state) {
			return CLASS_BODY;
		}
	};
	private static final STraversal CLASS_BODY = new STraversal() {

		public STree<?> traverse(EnumConstantDecl.State state) {
			return state.classBody;
		}

		public EnumConstantDecl.State rebuildParentState(EnumConstantDecl.State state, STree<?> child) {
			return state.withClassBody((STree) child);
		}

		public STraversal leftSibling(EnumConstantDecl.State state) {
			return ARGS;
		}

		public STraversal rightSibling(EnumConstantDecl.State state) {
			return null;
		}
	};

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(NAME),
			child(ARGS, when(some(), element(Expr.argumentsShape))),
			child(CLASS_BODY, when(some(), element(MemberDecl.bodyShape))),
			when(childIs(CLASS_BODY, some()), none().withSpacingAfter(spacing(EnumConstant_AfterBody)))
	);

	public static final LexicalShape listShape = list(
			none().withSpacingAfter(spacing(EnumBody_BeforeConstants)),
			token(LToken.Comma).withSpacingAfter(spacing(EnumBody_BetweenConstants)),
			null
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeListState> modifiers;

		public final STree<Name.State> name;

		public final STree<SNodeOptionState> args;

		public final STree<SNodeOptionState> classBody;

		State(STree<SNodeListState> modifiers, STree<Name.State> name, STree<SNodeOptionState> args, STree<SNodeOptionState> classBody) {
			this.modifiers = modifiers;
			this.name = name;
			this.args = args;
			this.classBody = classBody;
		}

		public EnumConstantDecl.State withModifiers(STree<SNodeListState> modifiers) {
			return new EnumConstantDecl.State(modifiers, name, args, classBody);
		}

		public EnumConstantDecl.State withName(STree<Name.State> name) {
			return new EnumConstantDecl.State(modifiers, name, args, classBody);
		}

		public EnumConstantDecl.State withArgs(STree<SNodeOptionState> args) {
			return new EnumConstantDecl.State(modifiers, name, args, classBody);
		}

		public EnumConstantDecl.State withClassBody(STree<SNodeOptionState> classBody) {
			return new EnumConstantDecl.State(modifiers, name, args, classBody);
		}

		public STraversal firstChild() {
			return MODIFIERS;
		}

		public STraversal lastChild() {
			return CLASS_BODY;
		}

		public Tree instantiate(SLocation<EnumConstantDecl.State> location) {
			return new EnumConstantDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}

		public Kind kind() {
			return Kind.EnumConstantDecl;
		}
	}
}
