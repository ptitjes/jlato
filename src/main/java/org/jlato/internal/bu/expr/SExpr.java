package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.shapes.*;
import org.jlato.tree.Kind;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public interface SExpr extends STreeState {

	LexicalShape argumentsShape = list(true,
			token(LToken.ParenthesisLeft),
			token(LToken.Comma).withSpacingAfter(space()),
			token(LToken.ParenthesisRight)
	);

	LexicalShape listShape = list(
			token(LToken.Comma).withSpacingAfter(space())
	);
}
