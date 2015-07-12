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

import org.jlato.internal.bu.LToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.type.QualifiedType;

import static org.jlato.internal.shapes.IndentationConstraint.Factory.indent;
import static org.jlato.internal.shapes.IndentationConstraint.Factory.unIndent;
import static org.jlato.internal.shapes.LSCondition.emptyList;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.Factory.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.TYPE_BODY;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

public abstract class MemberDecl extends Decl {

	protected MemberDecl(SLocation location) {
		super(location);
	}

	public static final LexicalShape bodyShape = list(true,
			alternative(emptyList(),
					token(LToken.BraceLeft)
							.withSpacing(space(), newLine())
							.withIndentationAfter(indent(TYPE_BODY)),
					token(LToken.BraceLeft)
							.withSpacing(space(), spacing(ClassBody_BeforeMembers))
							.withIndentationAfter(indent(TYPE_BODY))
			),
			none().withSpacingAfter(spacing(ClassBody_BetweenMembers)),
			alternative(emptyList(),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(TYPE_BODY)),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(TYPE_BODY))
							.withSpacingBefore(spacing(ClassBody_AfterMembers))
			)
	);

	public static final LexicalShape membersShape = list(
			none().withSpacingAfter(spacing(ClassBody_BeforeMembers)),
			none().withSpacingAfter(spacing(ClassBody_BetweenMembers)),
			none().withSpacingAfter(spacing(ClassBody_AfterMembers))
	);
}
