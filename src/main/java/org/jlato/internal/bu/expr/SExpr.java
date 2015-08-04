package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.shapes.LexicalShape;

import static org.jlato.internal.shapes.LexicalShape.list;
import static org.jlato.internal.shapes.LexicalShape.token;
import static org.jlato.printer.SpacingConstraint.space;

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
