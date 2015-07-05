package org.jlato.internal.shapes;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public abstract class LSToken implements LexicalShape {

	protected abstract LToken token(STree tree);

	public void render(STree tree, Printer printer) {
		printer.append(token(tree).string);
	}
}
