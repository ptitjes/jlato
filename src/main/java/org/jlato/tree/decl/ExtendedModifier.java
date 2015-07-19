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

import org.jlato.internal.bu.STreeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LSCondition.kind;
import static org.jlato.internal.shapes.LSCondition.lastChildKind;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.newLine;
import static org.jlato.printer.SpacingConstraint.space;

/**
 * @author Didier Villevalois
 */
public interface ExtendedModifier extends Tree {

	public static final LexicalShape singleLineShape = list(
			none().withSpacingAfter(space())
	);

	public static final LexicalShape multiLineShape = list(
			none(),
			alternative(
					kind(Kind.Modifier),
					none().withSpacingAfter(space()),
					none().withSpacingAfter(newLine())
			),
			alternative(
					lastChildKind(Kind.Modifier),
					none().withSpacingAfter(space()),
					none().withSpacingAfter(newLine())
			)
	);
}
