package org.jlato.internal.pc;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Didier Villevalois
 */
public class IntArray {

	public static IntArray of(int i1) {
		return build(i1);
	}

	public static IntArray of(int i1, int i2) {
		return build(i1, i2);
	}

	public static IntArray of(int i1, int i2, int i3) {
		return build(i1, i2, i3);
	}

	public static IntArray of(int i1, int i2, int i3, int i4) {
		return build(i1, i2, i3, i4);
	}

	public static IntArray of(List<Integer> elements) {
		int length = elements.size();
		int[] ints = new int[length];
		for (int i = 0; i < length; i++) {
			ints[i] = elements.get(i);
		}
		return new IntArray(ints);
	}

	private static IntArray build(int... elements) {
		return new IntArray(elements);
	}

	private final int[] elements;

	private IntArray(int[] elements) {
		this.elements = elements;
	}

	public int length() {
		return elements.length;
	}

	public int get(int index) {
		return elements[index];
	}

	public IntArray set(int index, int element) {
		int[] copy = Arrays.copyOf(elements, elements.length);
		copy[index] = element;
		return new IntArray(copy);
	}
}
