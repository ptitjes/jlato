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

package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class MarkerAnnotationExpr extends AnnotationExpr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public MarkerAnnotationExpr instantiate(SLocation location) {
			return new MarkerAnnotationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private MarkerAnnotationExpr(SLocation location) {
		super(location);
	}

	public MarkerAnnotationExpr(QualifiedName name) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(name)))));
	}

	public final static LexicalShape shape = composite(
			token(LToken.At), child(NAME)
	);
}
