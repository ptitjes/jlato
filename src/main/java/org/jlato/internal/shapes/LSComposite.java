package org.jlato.internal.shapes;

import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public final class LSComposite extends LexicalShape {

	private final LexicalShape[] shapes;

	public LSComposite(LexicalShape... shapes) {
		this.shapes = shapes;
	}

	public void render(STree tree, Printer printer) {
		for (LexicalShape shape : shapes) {
			shape.render(tree, printer);
		}
	}
}
