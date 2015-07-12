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
import org.jlato.internal.td.SLocation;
import org.jlato.tree.*;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.space;

public class MethodDecl extends Decl implements Member {

	public final static Tree.Kind kind = new Tree.Kind() {
		public MethodDecl instantiate(SLocation location) {
			return new MethodDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private MethodDecl(SLocation location) {
		super(location);
	}

	public <EM extends Tree & ExtendedModifier> MethodDecl(NodeList<EM> modifiers, NodeList<TypeParameter> typeParameters, Type type, Name name, NodeList<Parameter> parameters, NodeList<ArrayDim> dimensions, NodeList<QualifiedType> throwsClause, BlockStmt body/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, typeParameters, type, name, parameters, dimensions, throwsClause, body/*, javadocComment*/)))));
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> MethodDecl withModifiers(NodeList<EM> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public NodeList<TypeParameter> typeParameters() {
		return location.nodeChild(TYPE_PARAMETERS);
	}

	public MethodDecl withTypeParameters(NodeList<TypeParameter> typeParameters) {
		return location.nodeWithChild(TYPE_PARAMETERS, typeParameters);
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public MethodDecl withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public MethodDecl withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Parameter> parameters() {
		return location.nodeChild(PARAMETERS);
	}

	public MethodDecl withParameters(NodeList<Parameter> parameters) {
		return location.nodeWithChild(PARAMETERS, parameters);
	}

	public NodeList<ArrayDim> dimensions() {
		return location.nodeChild(DIMENSIONS);
	}

	public VariableDeclaratorId withDimensions(NodeList<ArrayDim> dimensions) {
		return location.nodeWithChild(DIMENSIONS, dimensions);
	}

	public NodeList<QualifiedType> throwsClause() {
		return location.nodeChild(THROWS_CLAUSE);
	}

	public ConstructorDecl withThrowsClause(NodeList<QualifiedType> throwsClause) {
		return location.nodeWithChild(THROWS_CLAUSE, throwsClause);
	}

	public BlockStmt body() {
		return location.nodeChild(BODY);
	}

	public MethodDecl withBody(BlockStmt body) {
		return location.nodeWithChild(BODY, body);
	}

	private static final int MODIFIERS = 0;
	private static final int TYPE_PARAMETERS = 1;
	private static final int TYPE = 2;
	private static final int NAME = 3;
	private static final int PARAMETERS = 4;
	private static final int DIMENSIONS = 5;
	private static final int THROWS_CLAUSE = 6;
	private static final int BODY = 7;

	public final static LexicalShape shape = composite(
			child(MODIFIERS, ExtendedModifier.multiLineShape),
			child(TYPE_PARAMETERS, TypeParameter.listShape),
			child(TYPE),
			none().withSpacing(space()),
			child(NAME),
			child(PARAMETERS, Parameter.listShape),
			child(DIMENSIONS, ArrayDim.listShape),
			child(THROWS_CLAUSE, QualifiedType.throwsClauseShape),
			nonNullChild(BODY,
					composite(none().withSpacing(space()), child(BODY)),
					token(LToken.SemiColon)
			)
	);
}
