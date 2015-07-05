package org.jlato.internal.shapes;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeList;
import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public abstract class LSChildren implements LexicalShape {

	public abstract LexicalShape shape();

	public abstract LexicalShape before();

	public abstract LexicalShape after();

	public abstract LexicalShape separator();

	public abstract SNodeList select(SNode node);

	public void render(STree tree, Printer printer) {
		final SNode node = (SNode) tree;
		final SNodeList nodeList = select(node);

		before().render(tree, printer);

		boolean first = true;
		for (STree child : nodeList.run) {
			if (first) first = false;
			else separator().render(child, printer);

			shape().render(nodeList, printer);
		}

		after().render(tree, printer);
	}
}
