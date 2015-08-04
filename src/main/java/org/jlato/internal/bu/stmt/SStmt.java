package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.STreeState;
import org.jlato.internal.shapes.LexicalShape;

import static org.jlato.internal.shapes.LexicalShape.list;
import static org.jlato.internal.shapes.LexicalShape.none;
import static org.jlato.printer.SpacingConstraint.newLine;

public interface SStmt extends STreeState {

	LexicalShape listShape = list(none().withSpacingAfter(newLine()));
}
