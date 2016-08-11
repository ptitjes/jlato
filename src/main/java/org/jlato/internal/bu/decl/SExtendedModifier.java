/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.shapes.*;
import org.jlato.tree.Kind;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an extended modifier.
 */
public interface SExtendedModifier extends STree {

	LexicalShape singleLineShape = list(
			none().withSpacingAfter(space())
	);

	LexicalShape multiLineShape = list(
			none(),
			alternative(
					withKind(Kind.Modifier),
					none().withSpacingAfter(space()),
					none().withSpacingAfter(newLine())
			),
			alternative(
					childHas(SNodeList.lastTraversal(), withKind(Kind.Modifier)),
					none().withSpacingAfter(space()),
					none().withSpacingAfter(newLine())
			)
	);
}
