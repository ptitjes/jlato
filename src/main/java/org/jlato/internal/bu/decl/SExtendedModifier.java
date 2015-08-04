package org.jlato.internal.bu.decl;

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

public interface SExtendedModifier extends STreeState {

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
					childHas(SNodeListState.lastTraversal(), withKind(Kind.Modifier)),
					none().withSpacingAfter(space()),
					none().withSpacingAfter(newLine())
			)
	);
}
