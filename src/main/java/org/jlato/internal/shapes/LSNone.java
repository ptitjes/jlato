package org.jlato.internal.shapes;

import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public final class LSNone extends LexicalShape {

	private final LexicalSpacing spacing;

	public LSNone() {
		this(null);
	}

	private LSNone(LexicalSpacing spacing) {
		this.spacing = spacing;
	}

	public LSNone withSpacing(LexicalSpacing spacing) {
		return new LSNone(spacing);
	}

	public void render(STree tree, Printer printer) {
		if (spacing != null) spacing.render(null, printer);
	}
}
