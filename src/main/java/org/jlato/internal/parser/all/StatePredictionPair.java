/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.parser.all;

/**
 * @author Didier Villevalois
 */
public class StatePredictionPair {

	public final GrammarState state;
	public final int prediction;

	public StatePredictionPair(GrammarState state, int prediction) {
		this.state = state;
		this.prediction = prediction;

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StatePredictionPair that = (StatePredictionPair) o;

		if (!state.equals(that.state)) return false;
		return prediction == that.prediction;

	}

	@Override
	public int hashCode() {
		int result = state.hashCode();
		result = 31 * result + prediction;
		return result;
	}
}
