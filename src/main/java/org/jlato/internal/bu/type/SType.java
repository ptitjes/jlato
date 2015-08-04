package org.jlato.internal.bu.type;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;

import static org.jlato.internal.shapes.LexicalShape.list;
import static org.jlato.internal.shapes.LexicalShape.token;
import static org.jlato.printer.SpacingConstraint.space;

public interface SType extends STree {

	LexicalShape typeArgumentsShape = list(
			token(LToken.Less),
			token(LToken.Comma).withSpacingAfter(space()),
			token(LToken.Greater)
	);

	LexicalShape typeArgumentsOrDiamondShape = list(true,
			token(LToken.Less),
			token(LToken.Comma).withSpacingAfter(space()),
			token(LToken.Greater)
	);

	LexicalShape intersectionShape = list(token(LToken.BinAnd).withSpacing(space(), space()));

	LexicalShape unionShape = list(token(LToken.BinOr).withSpacing(space(), space()));
}
