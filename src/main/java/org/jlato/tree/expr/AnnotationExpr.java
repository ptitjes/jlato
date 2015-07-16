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

import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.list;
import static org.jlato.internal.shapes.LexicalShape.none;
import static org.jlato.printer.SpacingConstraint.newLine;
import static org.jlato.printer.SpacingConstraint.space;

public abstract class AnnotationExpr extends TreeBase<SNodeState, Expr, AnnotationExpr> implements Expr, ExtendedModifier {

	protected AnnotationExpr(SLocation<SNodeState> location) {
		super(location);
	}

	public QualifiedName name() {
		return location.safeTraversal(NAME);
	}

	public AnnotationExpr withName(QualifiedName name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public AnnotationExpr withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	protected static final STraversal<SNodeState> NAME = SNodeState.childTraversal(0);

	public static final LexicalShape singleLineAnnotationsShape = list(
			none(),
			none().withSpacingAfter(space()),
			none().withSpacingAfter(space())
	);

	public static final LexicalShape multiLineAnnotationsShape = list(
			none(),
			none().withSpacingAfter(newLine()),
			none().withSpacingAfter(newLine())
	);
}
