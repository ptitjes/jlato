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
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LSCondition;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.SpacingConstraint.spacing;

public class EnumConstantDecl extends MemberDecl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public EnumConstantDecl instantiate(SLocation location) {
			return new EnumConstantDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private EnumConstantDecl(SLocation location) {
		super(location);
	}

	public <EM extends Tree & ExtendedModifier> EnumConstantDecl(NodeList<EM> modifiers, Name name, NodeOption<NodeList<Expr>> args, NodeOption<NodeList<MemberDecl>> classBody) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(modifiers, name, args, classBody)))));
	}

	@Override
	public MemberKind memberKind() {
		return MemberKind.EnumConstant;
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> EnumConstantDecl withModifiers(NodeList<EM> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public <EM extends Tree & ExtendedModifier> EnumConstantDecl withModifiers(Mutation<NodeList<EM>> mutation) {
		return location.nodeMutateChild(MODIFIERS, mutation);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public EnumConstantDecl withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public EnumConstantDecl withName(Mutation<Name> mutation) {
		return location.nodeMutateChild(NAME, mutation);
	}

	public NodeOption<NodeList<Expr>> args() {
		return location.nodeChild(ARGS);
	}

	public EnumConstantDecl withArgs(NodeOption<NodeList<Expr>> args) {
		return location.nodeWithChild(ARGS, args);
	}

	public EnumConstantDecl withArgs(Mutation<NodeOption<NodeList<Expr>>> mutation) {
		return location.nodeMutateChild(ARGS, mutation);
	}

	public NodeOption<NodeList<MemberDecl>> classBody() {
		return location.nodeChild(CLASS_BODY);
	}

	public EnumConstantDecl withClassBody(NodeOption<NodeList<MemberDecl>> classBody) {
		return location.nodeWithChild(CLASS_BODY, classBody);
	}

	public EnumConstantDecl withClassBody(Mutation<NodeOption<NodeList<MemberDecl>>> mutation) {
		return location.nodeMutateChild(CLASS_BODY, mutation);
	}

	private static final int MODIFIERS = 0;
	private static final int NAME = 1;
	private static final int ARGS = 2;
	private static final int CLASS_BODY = 3;

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
