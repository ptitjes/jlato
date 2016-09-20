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

import org.jlato.internal.parser.Token;

import java.util.*;

/**
 * @author Didier Villevalois
 */
public class PredictionState {

	public final Set<Configuration> configurations;

	public final Map<Integer, PredictionState> transitions = new HashMap<Integer, PredictionState>();

	public final List<Integer> prediction;

	public final boolean stackSensitive;

	public PredictionState(Set<Configuration> configurations) {
		this.configurations = configurations;
		this.prediction = longestCommonPredictions();

		HashMap<StateCallStackPair, Set<Configuration.Prediction>> conflictSetsPerLoc = getConflictSetsPerLoc();
		HashMap<Grammar.GrammarState, Set<Configuration.Prediction>> prodSetsPerState = getProdSetsPerState();
		stackSensitive = conflictingAlternatives(conflictSetsPerLoc) && !viableAlternative(prodSetsPerState);
	}

	private boolean conflictingAlternatives(HashMap<StateCallStackPair, Set<Configuration.Prediction>> conflictSetsPerLoc) {
		for (Map.Entry<StateCallStackPair, Set<Configuration.Prediction>> entry : conflictSetsPerLoc.entrySet()) {
			if (entry.getValue().size() > 1) return true;
		}
		return false;
	}

	private boolean viableAlternative(HashMap<Grammar.GrammarState, Set<Configuration.Prediction>> prodSetsPerState) {
		for (Map.Entry<Grammar.GrammarState, Set<Configuration.Prediction>> entry : prodSetsPerState.entrySet()) {
			if (entry.getValue().size() == 1) return true;
		}
		return false;
	}

	private List<Integer> longestCommonPredictions() {
		List<Integer> lcp = null;
		for (Configuration configuration : this.configurations) {
			// Longest first lcp
			List<Integer> predictions = configuration.prediction.toList();

			if (lcp == null) lcp = predictions;
			else {
				int i;
				for (i = 0; i < Math.min(lcp.size(), predictions.size()); i++) {
					if (!lcp.get(i).equals(predictions.get(i))) break;
				}
				for (int j = i; j < lcp.size(); j++) {
					lcp.remove(i);
				}
			}
		}
		return lcp == null || lcp.isEmpty() ? null : lcp;
	}

	public PredictionState transitionFor(Token token) {
		return transitions.get(token.kind);
	}

	public void setTransitionFor(Token token, PredictionState state) {
		transitions.put(token.kind, state);
	}

	public HashMap<StateCallStackPair, Set<Configuration.Prediction>> getConflictSetsPerLoc() {
		HashMap<StateCallStackPair, Set<Configuration.Prediction>> stateCallStackToAlts = new HashMap<StateCallStackPair, Set<Configuration.Prediction>>();
		for (Configuration configuration : configurations) {
			Grammar.GrammarState state = configuration.state;
			CallStack callStack = configuration.callStack;
			Configuration.Prediction prediction = configuration.prediction;

			StateCallStackPair pair = new StateCallStackPair(state, callStack);
			Set<Configuration.Prediction> alts = stateCallStackToAlts.get(pair);
			if (alts == null) {
				alts = new HashSet<Configuration.Prediction>();
				stateCallStackToAlts.put(pair, alts);
			}

			alts.add(prediction);
		}
		return stateCallStackToAlts;
	}

	public HashMap<Grammar.GrammarState, Set<Configuration.Prediction>> getProdSetsPerState() {
		HashMap<Grammar.GrammarState, Set<Configuration.Prediction>> stateToAlts = new HashMap<Grammar.GrammarState, Set<Configuration.Prediction>>();
		for (Configuration configuration : configurations) {
			Grammar.GrammarState state = configuration.state;
			Configuration.Prediction prediction = configuration.prediction;

			Set<Configuration.Prediction> alts = stateToAlts.get(state);
			if (alts == null) {
				alts = new HashSet<Configuration.Prediction>();
				stateToAlts.put(state, alts);
			}

			alts.add(prediction);
		}
		return stateToAlts;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PredictionState that = (PredictionState) o;

		return configurations != null ? configurations.equals(that.configurations) : that.configurations == null;

	}

	@Override
	public int hashCode() {
		return configurations != null ? configurations.hashCode() : 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		CachedAutomaton.printToString(builder, this, 0);
		return builder.toString();
	}
}
