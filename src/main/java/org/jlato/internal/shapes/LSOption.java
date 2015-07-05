package org.jlato.internal.shapes;

import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public final class LSOption extends LexicalShape {

	private final ShapeProvider shapeProvider;

	public LSOption(ShapeProvider shapeProvider) {
		this.shapeProvider = shapeProvider;
	}

	public void render(STree tree, Printer printer) {
		final LexicalShape shape = shapeProvider.shapeFor(tree);
		if (shape != null) shape.render(tree, printer);
	}
}
