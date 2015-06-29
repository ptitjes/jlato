package org.jlato.internal.pc;

/**
 * @author Didier Villevalois
 */
public abstract class IndexedSeq<E> {

	public abstract int length();

	public abstract E get(int index);

	public abstract IndexedSeq<E> set(int index, E element);

	public <A, F> A mapFoldLeft(Mapper<? super E, F> mapper, Folder<F, A> folder, A initial) {
		for (int i = 0; i < length(); i++) {
			initial = folder.fold(initial, mapper.map(get(i)));
		}
		return initial;
	}
}
