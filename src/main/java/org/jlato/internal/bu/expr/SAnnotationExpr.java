package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.decl.SExtendedModifier;
import org.jlato.internal.shapes.*;
import org.jlato.tree.Kind;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public interface SAnnotationExpr extends SExpr, SExtendedModifier {

	LexicalShape singleLineAnnotationsShape = list(
			none(),
			none().withSpacingAfter(space()),
			none().withSpacingAfter(space())
	);

	LexicalShape singleLineAnnotationsShapeWithSpaceBefore = list(
			none().withSpacingBefore(space()),
			none().withSpacingBefore(space()),
			none().withSpacingBefore(space())
	);

	LexicalShape multiLineAnnotationsShape = list(
			none(),
			none().withSpacingAfter(newLine()),
			none().withSpacingAfter(newLine())
	);
}
