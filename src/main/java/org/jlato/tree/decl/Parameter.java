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
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.Type;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.space;

public class Parameter extends Tree {

	public final static Tree.Kind kind = new Tree.Kind() {
		public Parameter instantiate(SLocation location) {
			return new Parameter(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private Parameter(SLocation location) {
		super(location);
	}

	public Parameter(Modifiers modifiers, Type type, boolean isVarArgs, VariableDeclaratorId id) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, type, id), dataOf(isVarArgs)))));
	}

	public Modifiers modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public Parameter withModifiers(Modifiers modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public Parameter withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public boolean isVarArgs() {
		return location.nodeData(VAR_ARG);
	}

	public Parameter setVarArgs(boolean isVarArgs) {
		return location.nodeWithData(VAR_ARG, isVarArgs);
	}

	public VariableDeclaratorId id() {
		return location.nodeChild(ID);
	}

	public Parameter withId(VariableDeclaratorId id) {
		return location.nodeWithChild(ID, id);
	}

	private static final int MODIFIERS = 0;
	private static final int TYPE = 1;
	private static final int ID = 2;

	private static final int VAR_ARG = 0;

	public final static LexicalShape shape = composite(
			child(MODIFIERS, Modifiers.oneLinerShape),
			child(TYPE),
			dataOption(VAR_ARG, token(LToken.Ellipsis)),
			none().withSpacing(space()),
			child(ID)
	);
}
