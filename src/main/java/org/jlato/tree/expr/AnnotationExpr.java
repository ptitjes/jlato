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
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.name.QName;

import static org.jlato.internal.shapes.LexicalShape.Factory.list;
import static org.jlato.internal.shapes.LexicalShape.Factory.none;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.newLine;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.space;

public abstract class AnnotationExpr extends Expr implements ExtendedModifier {

	protected AnnotationExpr(SLocation location) {
		super(location);
	}

	public QName name() {
		return location.nodeChild(NAME);
	}

	public AnnotationExpr withName(QName name) {
		return location.nodeWithChild(NAME, name);
	}

	protected static final int NAME = 0;

	public static final LexicalShape singleLineAnnotationsShape = list(
			none(),
			none().withSpacing(space()),
			none().withSpacing(space())
	);

	public static final LexicalShape multiLineAnnotationsShape = list(
			none(),
			none().withSpacing(newLine()),
			none().withSpacing(newLine())
	);
}
