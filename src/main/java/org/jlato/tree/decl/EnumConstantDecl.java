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
import org.jlato.internal.td.SKind;
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

public class EnumConstantDecl extends TreeBase<SNodeState> implements MemberDecl {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public EnumConstantDecl instantiate(SLocation<SNodeState> location) {
			return new EnumConstantDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private EnumConstantDecl(SLocation<SNodeState> location) {
		super(location);
	}

	public EnumConstantDecl(NodeList<ExtendedModifier> modifiers, Name name, NodeOption<NodeList<Expr>> args, NodeOption<NodeList<MemberDecl>> classBody) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(modifiers, name, args, classBody)))));
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

	private static final STraversal<SNodeState> MODIFIERS = SNodeState.childTraversal(0);
	private static final STraversal<SNodeState> NAME = SNodeState.childTraversal(1);
	private static final STraversal<SNodeState> ARGS = SNodeState.childTraversal(2);
	private static final STraversal<SNodeState> CLASS_BODY = SNodeState.childTraversal(3);

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
}
