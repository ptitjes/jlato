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

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.shapes.*;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a member declaration.
 */
public interface SMemberDecl extends SDecl {

	LexicalShape bodyShape = list(true,
			alternative(empty(),
					token(LToken.BraceLeft)
							.withSpacing(space(), newLine())
							.withIndentationAfter(indent(IndentationContext.TypeBody)),
					token(LToken.BraceLeft)
							.withSpacing(space(), spacing(ClassBody_BeforeMembers))
							.withIndentationAfter(indent(IndentationContext.TypeBody))
			),
			none().withSpacingAfter(spacing(ClassBody_BetweenMembers)),
			alternative(empty(),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(IndentationContext.TypeBody)),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(IndentationContext.TypeBody))
							.withSpacingBefore(spacing(ClassBody_AfterMembers))
			)
	);

	LexicalShape membersShape = list(
			none().withSpacingAfter(spacing(ClassBody_BeforeMembers)),
			none().withSpacingAfter(spacing(ClassBody_BetweenMembers)),
			none().withSpacingAfter(spacing(ClassBody_AfterMembers))
	);
}
