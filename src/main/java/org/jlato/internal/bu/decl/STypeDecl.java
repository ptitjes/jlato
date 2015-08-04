package org.jlato.internal.bu.decl;

import org.jlato.internal.shapes.LexicalShape;

import static org.jlato.internal.shapes.LexicalShape.list;
import static org.jlato.internal.shapes.LexicalShape.none;
import static org.jlato.printer.FormattingSettings.SpacingLocation.CompilationUnit_BetweenTopLevelDecl;
import static org.jlato.printer.SpacingConstraint.spacing;

public interface STypeDecl extends SMemberDecl {

	LexicalShape listShape = list(
			none().withSpacingAfter(spacing(CompilationUnit_BetweenTopLevelDecl))
	);
}
