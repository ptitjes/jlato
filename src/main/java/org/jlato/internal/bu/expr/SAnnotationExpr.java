package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.decl.SExtendedModifier;
import org.jlato.internal.shapes.*;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an annotation expression.
 */
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
