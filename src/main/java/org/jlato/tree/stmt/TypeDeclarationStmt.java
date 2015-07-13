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

package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Mutator;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.TypeDecl;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;

public class TypeDeclarationStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public TypeDeclarationStmt instantiate(SLocation location) {
			return new TypeDeclarationStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private TypeDeclarationStmt(SLocation location) {
		super(location);
	}

	public TypeDeclarationStmt(TypeDecl typeDecl) {
		super(new SLocation(new STree(kind, new SNodeState(treesOf(typeDecl)))));
	}

	public TypeDecl typeDecl() {
		return location.nodeChild(TYPE_DECL);
	}

	public TypeDeclarationStmt withTypeDecl(TypeDecl typeDecl) {
		return location.nodeWithChild(TYPE_DECL, typeDecl);
	}

	public TypeDeclarationStmt withTypeDecl(Mutator<TypeDecl> typeDecl) {
		return location.nodeWithChild(TYPE_DECL, typeDecl);
	}

	private static final int TYPE_DECL = 0;

	public final static LexicalShape shape = composite(
			child(TYPE_DECL)
	);
}
