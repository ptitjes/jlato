package org.jlato.internal.bu;

import org.jlato.internal.pc.Array;
import org.jlato.internal.pc.IndexedSeq;

/**
 * @author Didier Villevalois
 */
public abstract class LToken extends LElement {

	@Override
	public boolean isToken() {
		return true;
	}

	@Override
	public IndexedSeq<LElement> elements() {
		return EMPTY_ELEMENTS;
	}

	private final static IndexedSeq<LElement> EMPTY_ELEMENTS = Array.of();
}
