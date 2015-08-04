package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.decl.SExtendedModifier;
import org.jlato.internal.shapes.LexicalShape;

import static org.jlato.internal.shapes.LexicalShape.list;
import static org.jlato.internal.shapes.LexicalShape.none;
import static org.jlato.printer.SpacingConstraint.newLine;
import static org.jlato.printer.SpacingConstraint.space;

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
