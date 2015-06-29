package org.jlato.internal.bu;

import org.jlato.internal.pc.IndexedSeq;
import org.jlato.internal.pc.Mapper;

/**
 * @author Didier Villevalois
 */
public abstract class LElement {

	public abstract boolean isToken();

	public abstract IndexedSeq<? extends LElement> elements();

	public abstract int width();

	public final static Mapper<LElement, Integer> WIDTH = new Mapper<LElement, Integer>() {
		public Integer map(LElement buTree) {
			return buTree.width();
		}
	};
}
