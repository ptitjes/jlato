package org.jlato.internal.pc;

/**
 * @author Didier Villevalois
 */
public class IndexArray<E> extends IndexedSeq<E> {

	public static <E> IndexArray<E> of(IndexedSeq<E> elements, int i1) {
		return build(elements, i1);
	}

	public static <E> IndexArray<E> of(IndexedSeq<E> elements, int i1, int i2) {
		return build(elements, i1, i2);
	}

	public static <E> IndexArray<E> of(IndexedSeq<E> elements, int i1, int i2, int i3) {
		return build(elements, i1, i2, i3);
	}

	public static <E> IndexArray<E> of(IndexedSeq<E> elements, int i1, int i2, int i3, int i4) {
		return build(elements, i1, i2, i3, i4);
	}

	@SuppressWarnings("unchecked")
	private static <E> IndexArray<E> build(IndexedSeq<E> elements, int... indexes) {
		return new IndexArray<E>(elements, indexes);
	}

	private final IndexedSeq<E> elements;
	private final int[] indexes;

	private IndexArray(IndexedSeq<E> elements, int[] indexes) {
		this.elements = elements;
		this.indexes = indexes;
	}

	public int length() {
		return indexes.length;
	}

	public E get(int index) {
		return elements.get(indexes[index]);
	}

	@Override
	public IndexArray<E> set(int index, E element) {
		return new IndexArray<E>(elements.set(indexes[index], element), indexes);
	}
}
