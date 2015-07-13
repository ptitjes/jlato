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
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.*;

public class AnnotationDecl extends TypeDecl {

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

	public <EM extends Tree & ExtendedModifier> AnnotationDecl(NodeList<EM> modifiers, Name name, NodeList<MemberDecl> members) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(modifiers, name, members)))));
	}

	public <EM extends Tree & ExtendedModifier> NodeList<EM> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public <EM extends Tree & ExtendedModifier> AnnotationDecl withModifiers(NodeList<EM> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public <EM extends Tree & ExtendedModifier> AnnotationDecl withModifiers(Rewrite<NodeList<EM>> modifiers) {
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

	public AnnotationDecl withName(Rewrite<Name> name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<MemberDecl> members() {
		return location.nodeChild(MEMBERS);
	}

	public AnnotationDecl withMembers(NodeList<MemberDecl> members) {
		return location.nodeWithChild(MEMBERS, members);
	}

	public AnnotationDecl withMembers(Rewrite<NodeList<MemberDecl>> members) {
		return location.nodeWithChild(MEMBERS, members);
	}

	private static final int MODIFIERS = 0;
	private static final int NAME = 1;
	private static final int MEMBERS = 6;

	public final static LexicalShape shape = composite(
			child(MODIFIERS),
			token(LToken.At), token(LToken.Interface),
			child(NAME),
			child(MEMBERS, MemberDecl.bodyShape)
	);
}
