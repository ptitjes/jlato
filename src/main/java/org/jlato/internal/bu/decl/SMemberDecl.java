package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.shapes.LexicalShape;

import static org.jlato.internal.shapes.LSCondition.empty;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.TYPE_BODY;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.internal.shapes.IndentationConstraint.indent;
import static org.jlato.internal.shapes.IndentationConstraint.unIndent;
import static org.jlato.internal.shapes.SpacingConstraint.*;

public interface SMemberDecl extends SDecl {

	LexicalShape bodyShape = list(true,
			alternative(empty(),
					token(LToken.BraceLeft)
							.withSpacing(space(), newLine())
							.withIndentationAfter(indent(TYPE_BODY)),
					token(LToken.BraceLeft)
							.withSpacing(space(), spacing(ClassBody_BeforeMembers))
							.withIndentationAfter(indent(TYPE_BODY))
			),
			none().withSpacingAfter(spacing(ClassBody_BetweenMembers)),
			alternative(empty(),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(TYPE_BODY)),
					token(LToken.BraceRight)
							.withIndentationBefore(unIndent(TYPE_BODY))
							.withSpacingBefore(spacing(ClassBody_AfterMembers))
			)
	);

	LexicalShape membersShape = list(
			none().withSpacingAfter(spacing(ClassBody_BeforeMembers)),
			none().withSpacingAfter(spacing(ClassBody_BetweenMembers)),
			none().withSpacingAfter(spacing(ClassBody_AfterMembers))
	);
}
