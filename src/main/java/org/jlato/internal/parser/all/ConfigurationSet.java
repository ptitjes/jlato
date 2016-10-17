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

import com.github.andrewoma.dexx.collection.Set;

import java.util.BitSet;

/**
 * @author Didier Villevalois
 */
public class ConfigurationSet {

	public final Set<StateConfiguration> stateConfigurations;

	public ConfigurationSet(Set<StateConfiguration> stateConfigurations) {
		this.stateConfigurations = stateConfigurations;
	}

	public static class StateConfiguration {
		public final GrammarState state;
		public final CallStack callStacks;
		public final BitSet predictions;

		public StateConfiguration(GrammarState state, CallStack callStacks, BitSet predictions) {
			this.state = state;
			this.callStacks = callStacks;
			this.predictions = predictions;
		}

		public StateConfiguration with(GrammarState state) {
			return new StateConfiguration(state, callStacks, predictions);
		}

		public StateConfiguration enterNonTerminal(GrammarState startState, GrammarState returnState) {
			return new StateConfiguration(startState, callStacks.push(returnState), predictions);
		}

		public StateConfiguration with(BitSet predictions) {
			return new StateConfiguration(state, callStacks, predictions);
		}

		public boolean hasPredictions() {
			return !predictions.isEmpty();
		}
	}

	private static BitSet bitSetWith(int bit) {
		BitSet predictions = new BitSet();
		predictions.set(bit);
		return predictions;
	}
}
