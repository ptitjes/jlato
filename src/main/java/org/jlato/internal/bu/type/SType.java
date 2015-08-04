package org.jlato.internal.bu.type;

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

public interface SType extends STreeState {

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
