package org.jlato.internal.pc;

/**
 * @author Didier Villevalois
 */
public interface Mapper<E, F> {
	F map(E e);
}
