package org.jlato.internal.shapes;

import org.jlato.internal.bu.STree;
import org.jlato.printer.FormattingSettings.EmptyLineLocation;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public class LSSpacing implements LexicalShape {

	private final String spacing;
	private final EmptyLineLocation location;

	public LSSpacing(String spacing) {
		this(spacing, null);
	}

	public LSSpacing(EmptyLineLocation location) {
		this(null, location);
	}

	public LSSpacing(String spacing, EmptyLineLocation location) {
		this.spacing = spacing;
		this.location = location;
	}

	public void render(STree tree, Printer printer) {
		boolean format = printer.format || true;

		if (format) {
			if (spacing != null) {
				printer.append(spacing);
			} else {
				final int lineCount = printer.formattingSettings.emptyLineCount(location);
				for (int i = 0; i < lineCount; i++) {
					printer.newLine();
				}
			}
		}
	}
}
