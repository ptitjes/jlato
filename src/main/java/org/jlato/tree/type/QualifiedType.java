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

package org.jlato.tree.type;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class QualifiedType extends ReferenceType {

	public final static Tree.Kind kind = new Tree.Kind() {
		public QualifiedType instantiate(SLocation location) {
			return new QualifiedType(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private QualifiedType(SLocation location) {
		super(location);
	}

	public QualifiedType(NodeList<AnnotationExpr> annotations, QualifiedType scope, Name name, NodeList<Type> typeArgs) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(annotations, scope, name, typeArgs)))));
	}

	public QualifiedType scope() {
		return location.nodeChild(SCOPE);
	}

	public QualifiedType withScope(QualifiedType scope) {
		return location.nodeWithChild(SCOPE, scope);
	}

	public QualifiedType withScope(Mutation<QualifiedType> scope) {
		return location.nodeWithChild(SCOPE, scope);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public QualifiedType withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public QualifiedType withName(Mutation<Name> name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Type> typeArgs() {
		return location.nodeChild(TYPE_ARGUMENTS);
	}

	public QualifiedType withTypeArgs(NodeList<Type> typeArgs) {
		return location.nodeWithChild(TYPE_ARGUMENTS, typeArgs);
	}

	public QualifiedType withTypeArgs(Mutation<NodeList<Type>> typeArgs) {
		return location.nodeWithChild(TYPE_ARGUMENTS, typeArgs);
	}

	private static final int SCOPE = 1;
	private static final int NAME = 2;
	private static final int TYPE_ARGUMENTS = 3;

	public final static LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			nonNullChild(SCOPE, composite(child(SCOPE), token(LToken.Dot))),
			child(NAME),
			nonNullChild(TYPE_ARGUMENTS,
//					nonEmptyChildren(TYPE_ARGUMENTS,
							child(TYPE_ARGUMENTS, Type.typeArgumentsShape)/*,
							composite(token(LToken.Less), token(LToken.Greater))*/
//					)
			)
	);

	public static final LexicalShape extendsClauseShape = list(
			token(LToken.Extends),
			token(LToken.Comma).withSpacingAfter(space()),
			null
	);

	public static final LexicalShape implementsClauseShape = list(
			token(LToken.Implements),
			token(LToken.Comma).withSpacingAfter(space()),
			null
	);

	public static final LexicalShape throwsClauseShape = list(
			token(LToken.Throws).withSpacingBefore(space()),
			token(LToken.Comma).withSpacingAfter(space()),
			null
	);
}
