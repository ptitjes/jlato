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
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Decl;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.ClassOrInterfaceType;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.spacing;
import static org.jlato.printer.FormattingSettings.SpacingLocation.CompilationUnit_BetweenTopLevelDecl;

public class AnnotationDecl extends TypeDecl implements TopLevel, Member {

	public final static Kind kind = new Kind() {
		public AnnotationDecl instantiate(SLocation location) {
			return new AnnotationDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	protected AnnotationDecl(SLocation location) {
		super(location);
	}

	public <EM extends Tree & ExtendedModifier, M extends Decl & Member> AnnotationDecl(NodeList<EM> modifiers, Name name, NodeList<M> members) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, name, members)))));
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> AnnotationDecl withModifiers(NodeList<EM> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public TypeKind typeKind() {
		return TypeKind.AnnotationType;
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public AnnotationDecl withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public <M extends Decl & Member> NodeList<M> members() {
		return location.nodeChild(MEMBERS);
	}

	public <M extends Decl & Member> AnnotationDecl withMembers(NodeList<M> members) {
		return location.nodeWithChild(MEMBERS, members);
	}

	private static final int MODIFIERS = 0;
	private static final int NAME = 1;
	private static final int MEMBERS = 6;

	public final static LexicalShape shape = composite(
			child(MODIFIERS),
			token(LToken.At), token(LToken.Interface),
			child(NAME),
			child(MEMBERS, Decl.bodyShape)
	);
}
