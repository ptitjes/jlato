package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;

import static org.jlato.internal.shapes.LexicalShape.list;
import static org.jlato.internal.shapes.LexicalShape.token;
import static org.jlato.internal.shapes.SpacingConstraint.space;

public interface SExpr extends STree {

	LexicalShape argumentsShape = list(true,
			token(LToken.ParenthesisLeft),
			token(LToken.Comma).withSpacingAfter(space()),
			token(LToken.ParenthesisRight)
	);

	LexicalShape listShape = list(
			token(LToken.Comma).withSpacingAfter(space())
	);
}
