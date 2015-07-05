package org.jlato.internal.bu;

import com.github.andrewoma.dexx.collection.ArrayList;

/**
 * @author Didier Villevalois
 */
public class STreeState {

	public final ArrayList<Object> data;

	public STreeState(ArrayList<Object> data) {
		this.data = data;
	}

	public Object data(int index) {
		return data.get(index);
	}
}
