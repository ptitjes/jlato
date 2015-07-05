package org.jlato.internal.bu;

import com.github.andrewoma.dexx.collection.IndexedList;

/**
 * @author Didier Villevalois
 */
public class LSpacingRun {

	public final IndexedList<LToken> tokens;

	public LSpacingRun(IndexedList<LToken> tokens) {
		this.tokens = tokens;
	}
}
