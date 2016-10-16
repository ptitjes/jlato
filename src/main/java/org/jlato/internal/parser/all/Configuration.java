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

import org.jlato.internal.parser.all.Grammar.GrammarState;

/**
 * @author Didier Villevalois
 */
public class Configuration {
	public final int prediction;
	public final GrammarState state;
	public final CallStack callStack;

	public Configuration(int prediction, GrammarState state, CallStack callStack) {
		this.prediction = prediction;
		this.state = state;
		this.callStack = callStack;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Configuration that = (Configuration) o;

		if (prediction != that.prediction) return false;
		if (!state.equals(that.state)) return false;
		return callStack.equals(that.callStack);
	}

	@Override
	public int hashCode() {
		int result = prediction;
		result = 31 * result + state.hashCode();
		result = 31 * result + callStack.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Configuration{" +
				"prediction=" + prediction +
				", state=" + state +
				", callStack=" + callStack +
				'}';
	}
}