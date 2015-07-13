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
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Rewrite;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class FormalParameter extends Tree {

	public final static Tree.Kind kind = new Tree.Kind() {
		public FormalParameter instantiate(SLocation location) {
			return new FormalParameter(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private FormalParameter(SLocation location) {
		super(location);
	}

	public <EM extends Tree & ExtendedModifier> FormalParameter(NodeList<EM> modifiers, Type type, boolean isVarArgs, VariableDeclaratorId id) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(modifiers, type, id), dataOf(isVarArgs)))));
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> FormalParameter withModifiers(NodeList<EM> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public <EM extends Tree & ExtendedModifier> FormalParameter withModifiers(Rewrite<NodeList<EM>> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public FormalParameter withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public FormalParameter withType(Rewrite<Type> type) {
		return location.nodeWithChild(TYPE, type);
	}

	public boolean isVarArgs() {
		return location.data(VAR_ARG);
	}

	public FormalParameter setVarArgs(boolean isVarArgs) {
		return location.withData(VAR_ARG, isVarArgs);
	}

	public VariableDeclaratorId id() {
		return location.nodeChild(ID);
	}

	public FormalParameter withId(VariableDeclaratorId id) {
		return location.nodeWithChild(ID, id);
	}

	public FormalParameter withId(Rewrite<VariableDeclaratorId> id) {
		return location.nodeWithChild(ID, id);
	}

	private static final int MODIFIERS = 0;
	private static final int TYPE = 1;
	private static final int ID = 2;

	private static final int VAR_ARG = 0;

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.singleLineShape),
			child(TYPE),
			dataOption(VAR_ARG, token(LToken.Ellipsis)),
			none().withSpacingAfter(space()),
			child(ID)
	);

	public static final LexicalShape listShape = list(true,
			token(LToken.ParenthesisLeft),
			token(LToken.Comma).withSpacingAfter(space()),
			token(LToken.ParenthesisRight)
	);
}
