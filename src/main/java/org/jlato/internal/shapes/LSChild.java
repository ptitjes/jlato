package org.jlato.internal.shapes;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public final class LSChild extends LexicalShape {

	private final ChildSelector selector;
	private final ShapeProvider shapeProvider;

	public LSChild(ChildSelector selector, ShapeProvider shapeProvider) {
		this.selector = selector;
		this.shapeProvider = shapeProvider;
	}

	public void render(STree tree, Printer printer) {
		final SNode node = (SNode) tree;
		final STree child = selector.select(node);
		if (child == null) return;

		final LexicalShape shape = shapeProvider.shapeFor(child);
		if (shape != null) shape.render(child, printer);
	}

	public interface ChildSelector {
		STree select(SNode node);
	}
}
