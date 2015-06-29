package org.jlato.internal.pc;

import java.util.Arrays;
import java.util.List;

/**
 * @author Didier Villevalois
 */
public class Array<E> extends IndexedSeq<E> {

	public static <E> Array<E> of() {
		return asArray();
	}

	public static <E> Array<E> of(E e1) {
		return asArray(e1);
	}

	public static <E> Array<E> of(E e1, E e2) {
		return asArray(e1, e2);
	}

	public static <E> Array<E> of(E e1, E e2, E e3) {
		return asArray(e1, e2, e3);
	}

	public static <E> Array<E> of(E e1, E e2, E e3, E e4) {
		return asArray(e1, e2, e3, e4);
	}

	public static <E> Array<E> of(E[] elements) {
		return asArray(elements);
	}

	public static <E> Array<E> of(List<E> elements) {
		return asArray(elements.toArray());
	}

	@SuppressWarnings("unchecked")
	private static <E> Array<E> asArray(Object... elements) {
		return new Array<E>((E[]) elements);
	}

	private final E[] elements;

	private Array(E[] elements) {
		this.elements = elements;
	}

	public int length() {
		return elements.length;
	}

	public E get(int index) {
		return elements[index];
	}

	@Override
	public Array<E> set(int index, E element) {
		E[] copy = Arrays.copyOf(elements, elements.length);
		copy[index] = element;
		return new Array<E>(copy);
	}
}
