package org.jlato.internal.shapes;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public abstract class LSChild implements LexicalShape {

	public abstract LexicalShape shape(STree tree);

	public abstract STree select(SNode node);

	public void render(STree tree, Printer printer) {
		final SNode node = (SNode) tree;
		final STree child = select(node);
		shape(child).render(child, printer);
	}
}
