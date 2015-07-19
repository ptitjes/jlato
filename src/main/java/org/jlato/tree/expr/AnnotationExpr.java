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

import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Mutation;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.list;
import static org.jlato.internal.shapes.LexicalShape.none;
import static org.jlato.printer.SpacingConstraint.newLine;
import static org.jlato.printer.SpacingConstraint.space;

public interface AnnotationExpr extends Expr, ExtendedModifier {

	QualifiedName name();

	AnnotationExpr withName(QualifiedName name);

	AnnotationExpr withName(Mutation<QualifiedName> mutation);

	interface State extends Expr.State, ExtendedModifier.State {
	}

	LexicalShape singleLineAnnotationsShape = list(
			none(),
			none().withSpacingAfter(space()),
			none().withSpacingAfter(space())
	);

	LexicalShape multiLineAnnotationsShape = list(
			none(),
			none().withSpacingAfter(newLine()),
			none().withSpacingAfter(newLine())
	);
}
