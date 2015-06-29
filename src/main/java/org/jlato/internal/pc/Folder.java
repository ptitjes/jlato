package org.jlato.internal.pc;

/**
 * @author Didier Villevalois
 */
public interface Folder<E, A> {
	A fold(A a, E e);

	Folder<Integer, Integer> PLUS = new Folder<Integer, Integer>() {
		public Integer fold(Integer i1, Integer i2) {
			return i1 + i2;
		}
	};
}
