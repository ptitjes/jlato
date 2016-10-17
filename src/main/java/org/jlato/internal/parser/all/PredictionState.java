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

	public final int prediction;

	public final boolean stackSensitive;
	private final int hashCode;

	public PredictionState(Set<Configuration> configurations) {
		this.hashCode = computeHashCode(configurations);
		this.configurations = configurations;
		this.prediction = commonPrediction();

		stackSensitive = checkStackSensitive();
	}

	private int commonPrediction() {
		int prediction = -1;
		for (Configuration configuration : configurations) {
			int aPrediction = configuration.prediction;
			if (prediction == -1) prediction = aPrediction;
			else if (prediction != aPrediction) return -1;
		}
		return prediction;
	}

	public PredictionState transitionFor(Token token) {
		return transitions.get(token.kind);
	}

	public void setTransitionFor(Token token, PredictionState state) {
		transitions.put(token.kind, state);
	}

	private boolean checkStackSensitive() {
		return conflictingAlternatives() && !viableAlternative();
	}

	private boolean conflictingAlternatives() {
		HashMap<StateCallStackPair, Set<Integer>> conflictSetsPerLoc = getConflictSetsPerLoc();
		for (Map.Entry<StateCallStackPair, Set<Integer>> entry : conflictSetsPerLoc.entrySet()) {
			if (entry.getValue().size() > 1) return true;
		}
		return false;
	}

	private boolean viableAlternative() {
		HashMap<GrammarState, Set<Integer>> prodSetsPerState = getProdSetsPerState();
		for (Map.Entry<GrammarState, Set<Integer>> entry : prodSetsPerState.entrySet()) {
			if (entry.getValue().size() == 1) return true;
		}
		return false;
	}

	public HashMap<StateCallStackPair, Set<Integer>> getConflictSetsPerLoc() {
		HashMap<StateCallStackPair, Set<Integer>> stateCallStackToAlts = new HashMap<StateCallStackPair, Set<Integer>>();
		for (Configuration configuration : configurations) {
			GrammarState state = configuration.state;
			CallStack callStack = configuration.callStack;
			Integer prediction = configuration.prediction;

			StateCallStackPair pair = new StateCallStackPair(state, callStack);
			Set<Integer> alts = stateCallStackToAlts.get(pair);
			if (alts == null) {
				alts = new HashSet<Integer>();
				stateCallStackToAlts.put(pair, alts);
			}

			alts.add(prediction);
		}
		return stateCallStackToAlts;
	}

	public HashMap<GrammarState, Set<Integer>> getProdSetsPerState() {
		HashMap<GrammarState, Set<Integer>> stateToAlts = new HashMap<GrammarState, Set<Integer>>();
		for (Configuration configuration : configurations) {
			GrammarState state = configuration.state;
			Integer prediction = configuration.prediction;

			Set<Integer> alts = stateToAlts.get(state);
			if (alts == null) {
				alts = new HashSet<Integer>();
				stateToAlts.put(state, alts);
			}

			alts.add(prediction);
		}
		return stateToAlts;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PredictionState that = (PredictionState) o;

		return configurations.equals(that.configurations);

	}

	private static int computeHashCode(Set<Configuration> configurations) {
		return configurations.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		CachedAutomaton.printToString(builder, this, 0);
		return builder.toString();
	}
}
