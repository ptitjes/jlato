package org.jlato.internal.shapes;

import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public abstract class LSOption implements LexicalShape {

	private final LexicalShape shape;

	public LSOption(LexicalShape shape) {
		this.shape = shape;
	}

	public abstract boolean decide(STree tree);

	public void render(STree tree, Printer printer) {
		if (decide(tree)) {
			shape.render(tree, printer);
		}
	}
}
