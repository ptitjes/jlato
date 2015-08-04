package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.SNodeList;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.*;

import static org.jlato.internal.shapes.LSCondition.childHas;
import static org.jlato.internal.shapes.LSCondition.withKind;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.newLine;
import static org.jlato.printer.SpacingConstraint.space;

public interface SExtendedModifier extends STree {

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
					childHas(SNodeList.lastTraversal(), withKind(Kind.Modifier)),
					none().withSpacingAfter(space()),
					none().withSpacingAfter(newLine())
			)
	);
}
