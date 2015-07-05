package org.jlato.internal.bu;

import com.github.andrewoma.dexx.collection.ArrayList;

/**
 * @author Didier Villevalois
 */
public class SLeafState extends STreeState {

	public SLeafState(ArrayList<Object> data) {
		super(data);
	}

	public SLeafState withData(int index, Object value) {
		return new SLeafState(data.set(index, value));
	}
}
