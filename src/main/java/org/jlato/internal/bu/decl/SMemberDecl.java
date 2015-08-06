package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.LToken;
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
 * A state object for a member declaration.
 */
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
