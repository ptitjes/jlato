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
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.*;

public class MarkerAnnotationExpr extends TreeBase<SNodeState, AnnotationExpr, MarkerAnnotationExpr> implements AnnotationExpr {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public MarkerAnnotationExpr instantiate(SLocation<SNodeState> location) {
			return new MarkerAnnotationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private MarkerAnnotationExpr(SLocation<SNodeState> location) {
		super(location);
	}

	public MarkerAnnotationExpr(QualifiedName name) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(name)))));
	}

	public QualifiedName name() {
		return location.safeTraversal(NAME);
	}

	public MarkerAnnotationExpr withName(QualifiedName name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public MarkerAnnotationExpr withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	protected static final STraversal<SNodeState> NAME = SNodeState.childTraversal(0);

	public final static LexicalShape shape = composite(
			token(LToken.At), child(NAME)
	);
}
