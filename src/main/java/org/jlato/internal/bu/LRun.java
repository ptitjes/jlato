package org.jlato.internal.bu;

import org.jlato.internal.pc.Folder;
import org.jlato.internal.pc.IndexedSeq;

/**
 * @author Didier Villevalois
 */
public class LRun extends LElement {

	public IndexedSeq<LElement> elements;
	public final int width;

	public LRun(IndexedSeq<LElement> elements) {
		this.elements = elements;
		this.width = elements.mapFoldLeft(WIDTH, Folder.PLUS, 0);
	}

	@Override
	public boolean isToken() {
		return false;
	}

	public IndexedSeq<? extends LElement> elements() {
		return elements;
	}

	public int width() {
		return width;
	}

	public LRun copyWith(int index, LElement element) {
		return new LRun(elements.set(index, element));
	}
}
