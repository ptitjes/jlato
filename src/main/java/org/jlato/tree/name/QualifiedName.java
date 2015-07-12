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

package org.jlato.tree.name;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class QualifiedName extends Tree {

	public final static Kind kind = new Kind() {
		public QualifiedName instantiate(SLocation location) {
			return new QualifiedName(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	public static QualifiedName of(String nameString) {
		final String[] split = nameString.split("\\.");
		QualifiedName name = null;
		for (String part : split) {
			name = new QualifiedName(name, new Name(part));
		}
		return name;
	}

	private QualifiedName(SLocation location) {
		super(location);
	}

	public QualifiedName(QualifiedName qualifier, Name name) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(qualifier, name)))));
	}

	public QualifiedName qualifier() {
		return location.nodeChild(QUALIFIER);
	}

	public QualifiedName withQualifier(QualifiedName qualifier) {
		return location.nodeWithChild(QUALIFIER, qualifier);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public QualifiedName withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public boolean isQualified() {
		return qualifier() != null;
	}

	@Override
	public String toString() {
		final QualifiedName qualifier = qualifier();
		final Name name = name();
		return qualifier != null ? qualifier.toString() + "." + name.toString() : name.toString();
	}

	private static final int QUALIFIER = 0;
	private static final int NAME = 1;

	public final static LexicalShape shape = composite(
			nonNullChild(QUALIFIER, composite(child(QUALIFIER), token(LToken.Dot))), child(NAME)
	);
}
