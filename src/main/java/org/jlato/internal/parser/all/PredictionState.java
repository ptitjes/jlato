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

import com.github.andrewoma.dexx.collection.Sets;
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
	private int hashCode;

	public PredictionState(Set<Configuration> configurations) {
		configurations = /*merge*/(configurations);

		this.hashCode = computeHashCode(configurations);
		this.configurations = configurations;
		this.prediction = commonPrediction();

		stackSensitive = checkStackSensitive();
	}

	private static Set<Configuration> merge(Set<Configuration> configurations) {
		if (configurations.isEmpty()) return configurations;

		Set<Configuration> newConfigurations = new HashSet<Configuration>();

		HashSet<Configuration> emptyOrWildcardConfigurations = new HashSet<Configuration>();
		Map<MergeKey, Set<CallStack>> aggrHeadConfigurations = new HashMap<MergeKey, Set<CallStack>>();

		for (Configuration configuration : configurations) {
			CallStack callStack = configuration.callStack;

			if (callStack == CallStack.WILDCARD || callStack == CallStack.EMPTY) {
				emptyOrWildcardConfigurations.add(configuration);
				continue;
			}

			MergeKey key = new MergeKey(configuration);
			Set<CallStack> tails = aggrHeadConfigurations.get(key);
			if (tails == null) {
				tails = new HashSet<CallStack>();
				aggrHeadConfigurations.put(key, tails);
			}

			Set<CallStack> otherTails = configuration.callStack.tails.asSet();
			if (!tails.contains(CallStack.WILDCARD)) {
				if (otherTails.contains(CallStack.WILDCARD)) {
					tails.clear();
					tails.add(CallStack.WILDCARD);
				} else {
					tails.addAll(otherTails);
				}
			}
		}

		for (Configuration configuration : emptyOrWildcardConfigurations) {
			newConfigurations.add(configuration);
		}

		for (Map.Entry<MergeKey, Set<CallStack>> entry : aggrHeadConfigurations.entrySet()) {
			Configuration configuration = entry.getKey().configuration;
			Grammar.GrammarState head = configuration.callStack.head;
			Set<CallStack> oldTails = entry.getValue();

			CallStack mergedStack = new CallStack(head,
					oldTails.contains(CallStack.WILDCARD) ? Sets.of(CallStack.WILDCARD) : Sets.copyOf(oldTails)
			);
			newConfigurations.add(new Configuration(configuration.prediction, configuration.state, mergedStack));
		}
		return newConfigurations;
	}

	private static class MergeKey {
		public final Configuration configuration;

		public MergeKey(Configuration configuration) {
			this.configuration = configuration;
			assert configuration.callStack.head != null;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Configuration that = ((MergeKey) o).configuration;

			if (configuration.prediction != that.prediction) return false;
			if (!configuration.state.equals(that.state)) return false;
			return configuration.callStack.head.equals(that.callStack.head);
		}

		@Override
		public int hashCode() {
			int result = configuration.prediction;
			result = 31 * result + configuration.state.hashCode();
			result = 31 * result + (configuration.callStack.head != null ? configuration.callStack.head.hashCode() : 0);
			return result;
		}
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
		HashMap<Grammar.GrammarState, Set<Integer>> prodSetsPerState = getProdSetsPerState();
		for (Map.Entry<Grammar.GrammarState, Set<Integer>> entry : prodSetsPerState.entrySet()) {
			if (entry.getValue().size() == 1) return true;
		}
		return false;
	}

	public HashMap<StateCallStackPair, Set<Integer>> getConflictSetsPerLoc() {
		HashMap<StateCallStackPair, Set<Integer>> stateCallStackToAlts = new HashMap<StateCallStackPair, Set<Integer>>();
		for (Configuration configuration : configurations) {
			Grammar.GrammarState state = configuration.state;
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

	public HashMap<Grammar.GrammarState, Set<Integer>> getProdSetsPerState() {
		HashMap<Grammar.GrammarState, Set<Integer>> stateToAlts = new HashMap<Grammar.GrammarState, Set<Integer>>();
		for (Configuration configuration : configurations) {
			Grammar.GrammarState state = configuration.state;
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
