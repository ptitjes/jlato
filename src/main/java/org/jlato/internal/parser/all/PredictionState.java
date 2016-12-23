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

import org.jlato.internal.parser.ParserNewBase2;
import org.jlato.internal.parser.Token;

import java.util.*;

/**
 * @author Didier Villevalois
 */
public class PredictionState {

	private final int hashCode;
	public final Set<Configuration> configurations;
	public final int prediction;
	public final boolean stackSensitive;

	public final Map<Integer, PredictionState> transitions = new HashMap<Integer, PredictionState>();

	public PredictionState(Set<Configuration> configurations, boolean computePrediction, boolean computeConflicts, boolean forceLL) {
		this.hashCode = computeHashCode(configurations);
		this.configurations = configurations;

		this.stackSensitive = computeConflicts && checkStackSensitive();
		this.prediction = (stackSensitive && forceLL) ? ParserNewBase2.minimalAlternative(getConflictSets()) :
				computePrediction ? commonPrediction() : -1;
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
		Collection<BitSet> conflictSets = getConflictSets();
		for (BitSet conflictSet : conflictSets) {
			if (conflictSet.cardinality() > 1) return true;
		}
		return false;
	}

	private boolean viableAlternative() {
		HashMap<GrammarState, BitSet> prodSetsPerState = getProdSetsPerState();
		for (Map.Entry<GrammarState, BitSet> entry : prodSetsPerState.entrySet()) {
			if (entry.getValue().cardinality() == 1) return true;
		}
		return false;
	}

	public Collection<BitSet> getConflictSets() {
		HashMap<StateCallStackPair, BitSet> stateCallStackToAlts = new HashMap<StateCallStackPair, BitSet>();
		for (Configuration configuration : configurations) {
			GrammarState state = configuration.state;
			CallStack callStack = configuration.callStack;
			Integer prediction = configuration.prediction;
			if (prediction == -1) continue;

			StateCallStackPair pair = new StateCallStackPair(state, callStack);
			BitSet alts = stateCallStackToAlts.get(pair);
			if (alts == null) {
				alts = new BitSet();
				stateCallStackToAlts.put(pair, alts);
			}

			alts.set(prediction);
		}
		return stateCallStackToAlts.values();
	}

	public HashMap<GrammarState, BitSet> getProdSetsPerState() {
		HashMap<GrammarState, BitSet> stateToAlts = new HashMap<GrammarState, BitSet>();
		for (Configuration configuration : configurations) {
			GrammarState state = configuration.state;
			Integer prediction = configuration.prediction;
			if (prediction == -1) continue;

			BitSet alts = stateToAlts.get(state);
			if (alts == null) {
				alts = new BitSet();
				stateToAlts.put(state, alts);
			}

			alts.set(prediction);
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
