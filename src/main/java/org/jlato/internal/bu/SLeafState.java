package org.jlato.internal.bu;

import com.github.andrewoma.dexx.collection.ArrayList;

/**
 * @author Didier Villevalois
 */
public class SLeafState {

	public final ArrayList<Object> data;

	public SLeafState(ArrayList<Object> data) {
		this.data = data;
	}

	public Object data(int index) {
		return data.get(index);
	}

	public SLeafState withData(int index, Object value) {
		return new SLeafState(data.set(index, value));
	}
}
