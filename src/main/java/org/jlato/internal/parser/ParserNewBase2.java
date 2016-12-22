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

package org.jlato.internal.parser;

import org.jlato.internal.parser.all.*;

import java.io.Reader;
import java.util.*;


/**
 * @author Didier Villevalois
 */
public abstract class ParserNewBase2 extends ParserBase {

	private static final boolean CACHE_STATS = true;
	private static final boolean MERGE_STATS = false;
	private static final boolean MERGE = false;
	private static final boolean INCREMENTAL_MERGE = true;


	protected abstract Grammar initializeGrammar();

	private final Grammar grammar = initializeGrammar();

	private final Map<Integer, CachedAutomaton> automata = new HashMap<Integer, CachedAutomaton>();

	private CallStack callStack = CallStack.EMPTY;

	@Override
	protected void reset(Reader reader) {
		super.reset(reader);
		currentPredictions = new LinkedList<Integer>();
		callStack = CallStack.EMPTY;
	}

	public void clearStats() {
		cacheMiss = 0;
		cacheHits = 0;
		fullLL = 0;
	}

	public void printStats() {
		if (CACHE_STATS) {
			System.out.println("DFA Cache - Hits: " + cacheHits + ", Misses: " + cacheMiss + ", FullLL: " + fullLL);
		}
	}

	private List<Integer> currentPredictions;

	protected void pushCallStack(Grammar.NonTerminal ntCall) {
		GrammarState state = ntCall.end();
		callStack = callStack.push(state);
	}

	protected void popCallStack() {
		callStack.pop(new CallStackReader() {
			@Override
			public void handleNext(GrammarState head, CallStack tail) {
				callStack = tail;
			}
		});
	}

	protected int predict(int choicePoint) {
		return sllPredict(choicePoint);
	}

	private int sllPredict(int choicePoint) {
		PredictionState current;

		// TODO Generate data for the number of choice points and number of alternative predictions
		// TODO Pre-initialize automaton with there final states and the shared error state
		CachedAutomaton automaton = automata.get(choicePoint);
		if (automaton == null) {
			current = makeStartState(choicePoint, CallStack.WILDCARD);
			cacheMiss++;

			automaton = new CachedAutomaton(current);
			automata.put(choicePoint, automaton);
		} else {
			current = automaton.initialState;
			cacheHits++;
		}

		int index = 0;
		while (true) {
			Token token = getToken(index++);

			PredictionState next = current.transitionFor(token);

			if (CACHE_STATS) {
				if (next == null) cacheMiss++;
				else cacheHits++;
			}

			if (next == null) {
				Set<Configuration> configurations = targetConfigurations(current, token);
				next = new PredictionState(configurations, true, true);

				// TODO Reuse a unique error state if closure of configurations is the empty set
				// TODO Reuse a unique final state for prediction if prediction is done
				next = automaton.addState(next);
				current.setTransitionFor(token, next);
			}

			if (next.configurations.isEmpty()) return -1;
			if (next.prediction != -1) return next.prediction;

			if (next.stackSensitive) return llPredict(choicePoint);

			current = next;
		}
	}

	private int cacheHits, cacheMiss, fullLL;

	private int llPredict(int choicePoint) {
		PredictionState current = makeStartState(choicePoint, callStack);
		fullLL++;

		int index = 0;
		while (true) {
			Token token = getToken(index++);

			Set<Configuration> configurations = targetConfigurations(current, token);
			PredictionState next = new PredictionState(configurations, true, false);
			fullLL++;

			if (next.configurations.isEmpty()) return -1;
			if (next.prediction != -1) return next.prediction;

			Collection<BitSet> conflictSets = next.getConflictSets();
			if (ambiguousAlternatives(conflictSets)) {
				reportAmbiguity(choicePoint, conflictSets);
				return minimalAlternative(conflictSets);
			}

			current = next;
		}
	}

	private PredictionState makeStartState(int choicePoint, CallStack callStack) {
		GrammarState state = grammar.getStartState(choicePoint);

		Configuration initialConfiguration = new Configuration(state, -1, callStack);
		Set<Configuration> configurations = Collections.singleton(initialConfiguration);
		configurations = closure(configurations);

		return new PredictionState(configurations, false, false);
	}

	private static final boolean REPORT = false;

	private void reportAmbiguity(int choicePoint, Collection<BitSet> conflictSets) {
		if (!REPORT) return;

		StringBuilder buffer = new StringBuilder();

		BitSet alternatives = firstConflict(conflictSets);

		Token firstToken = getToken(0);
		buffer.append("At choice point ");
		buffer.append(choicePoint);
		buffer.append(" ambiguous alternatives ");
		buffer.append(alternatives);
		buffer.append(" at (" + firstToken.beginLine + ":" + firstToken.beginColumn + ")");

		System.out.println(buffer.toString());
	}

	private BitSet firstConflict(Collection<BitSet> conflictSets) {
		return conflictSets.iterator().next();
	}

	private int minimalAlternative(Collection<BitSet> conflictSetsPerLoc) {
		BitSet alternatives = firstConflict(conflictSetsPerLoc);
		return alternatives.nextSetBit(0);
	}

	private boolean ambiguousAlternatives(Collection<BitSet> conflictSetsPerLoc) {
		BitSet predictions = null;
		for (BitSet otherPredictions : conflictSetsPerLoc) {
			if (otherPredictions.size() == 1) return false;

			if (predictions == null) predictions = otherPredictions;
			else if (!predictions.equals(otherPredictions)) return false;
		}
		return true;
	}

	private Set<Configuration> targetConfigurations(PredictionState current, Token token) {
		Set<Configuration> configurations = moveAlong(current.configurations, token);
		if (configurations.size() == 1 || commonPrediction(configurations) != -1) return configurations;

		configurations = closure(configurations);
		return configurations;
	}

	private int commonPrediction(Set<Configuration> configurations) {
		int prediction = -1;
		for (Configuration configuration : configurations) {
			int aPrediction = configuration.prediction;
			if (prediction == -1) prediction = aPrediction;
			else if (prediction != aPrediction) return -1;
		}
		return prediction;
	}

	private Set<Configuration> moveAlong(Set<Configuration> configurations, Token token) {
		Set<Configuration> newConfigurations = new HashSet<Configuration>();
		for (Configuration configuration : configurations) {
			GrammarState target = configuration.state.match(token);
			if (target == null) continue;

			Configuration newConfiguration = new Configuration(target, configuration.prediction, configuration.callStack);
			newConfigurations.add(newConfiguration);
		}
		return newConfigurations;
	}

	protected int entryPoint;

	private Set<Configuration> closure(Set<Configuration> configurations) {
		ConfigurationSetBuilder newConfigurations = newConfigurationSetBuilder();
		HashSet<Configuration> busy = new HashSet<Configuration>();
		for (Configuration configuration : configurations) {
			closureOf(configuration, newConfigurations, busy);
		}
		return newConfigurations.build();
	}

	private interface ConfigurationSetBuilder {

		void add(Configuration configuration);

		Set<Configuration> build();
	}

	private static ConfigurationSetBuilder newConfigurationSetBuilder() {
		return !MERGE ? new ConfigurationSetBuilderWithoutMerge() :
				INCREMENTAL_MERGE ?
						new ConfigurationSetBuilderWithIncrementalMerge() :
						new ConfigurationSetBuilderWithPostMerge();
	}

	private void closureOf(final Configuration configuration,
	                       final ConfigurationSetBuilder newConfigurations,
	                       final Set<Configuration> busy) {
		if (busy.contains(configuration)) return;
		else busy.add(configuration);

		final GrammarState state = configuration.state;
		final CallStack callStack = configuration.callStack;
		final int prediction = configuration.prediction;

		newConfigurations.add(configuration);

		// Return from non-terminal call
		if (state.end) {
			// SLL wildcard call stack
			if (callStack == CallStack.WILDCARD) {
				int nonTerminal = state.nonTerminal;

				// End states
				Set<GrammarState> useEndStates = grammar.getUseEndStates(nonTerminal);
				if (useEndStates != null) {
					for (GrammarState useEndState : useEndStates) {
						Configuration newConfiguration = new Configuration(useEndState, prediction, CallStack.WILDCARD);
						closureOf(newConfiguration, newConfigurations, busy);
					}
				}

				// Specific end states for the entry point
				useEndStates = grammar.getEntryPointUseEndStates(entryPoint, nonTerminal);
				if (useEndStates != null) {
					for (GrammarState useEndState : useEndStates) {
						Configuration newConfiguration = new Configuration(useEndState, prediction, CallStack.WILDCARD);
						closureOf(newConfiguration, newConfigurations, busy);
					}
				}
			} else {
				callStack.pop(new CallStackReader() {
					@Override
					public void handleNext(GrammarState head, CallStack tail) {
						Configuration newConfiguration = new Configuration(head, prediction, tail);
						closureOf(newConfiguration, newConfigurations, busy);
					}
				});
			}
		} else {
			// Handle choice transitions
			for (Map.Entry<Integer, GrammarState> entry : state.choiceTransitions.entrySet()) {
				int realPrediction = prediction == -1 ? entry.getKey() : prediction;

				Configuration newConfiguration = new Configuration(entry.getValue(), realPrediction, callStack);
				closureOf(newConfiguration, newConfigurations, busy);
			}
			// Handle non-terminal call
			for (Map.Entry<Integer, GrammarState> entry : state.nonTerminalTransitions.entrySet()) {
				GrammarState target = entry.getValue();
				if (target == null) continue;

				Integer symbol = entry.getKey();
				GrammarState start = grammar.getStartState(symbol);

				Configuration newConfiguration = new Configuration(start, prediction, callStack.push(target));
				closureOf(newConfiguration, newConfigurations, busy);
			}
		}
	}

	private static class ConfigurationSetBuilderWithoutMerge implements ConfigurationSetBuilder {
		HashSet<Configuration> configurations = new HashSet<Configuration>();

		public void add(Configuration configuration) {
			configurations.add(configuration);
		}

		public Set<Configuration> build() {
			return configurations;
		}
	}

	private static class ConfigurationSetBuilderWithPostMerge implements ConfigurationSetBuilder {
		HashSet<Configuration> configurations = new HashSet<Configuration>();

		public void add(Configuration configuration) {
			configurations.add(configuration);
		}

		public Set<Configuration> build() {
			return merge(configurations);
		}

		private Set<Configuration> merge(Set<Configuration> configurations) {
			int previousSize = configurations.size();
			Map<StatePredictionPair, CallStack> perStatePredictionCallStack = new HashMap<StatePredictionPair, CallStack>();
			for (Configuration configuration : configurations) {
				StatePredictionPair key = new StatePredictionPair(configuration.state, configuration.prediction);

				CallStack stack = perStatePredictionCallStack.get(key);
				perStatePredictionCallStack.put(key, stack == null ? configuration.callStack : stack.merge(configuration.callStack));
			}

			configurations.clear();
			for (Map.Entry<StatePredictionPair, CallStack> entry : perStatePredictionCallStack.entrySet()) {
				StatePredictionPair key = entry.getKey();
				configurations.add(new Configuration(key.state, key.prediction, entry.getValue()));
			}

			if (MERGE_STATS) {
				int newSize = configurations.size();
				if (previousSize > newSize) {
					System.out.println("Was: " + previousSize + " - Now is: " + newSize + " - Saved: " + (previousSize - newSize));
				}
			}
			return configurations;
		}
	}

	private static class ConfigurationSetBuilderWithIncrementalMerge implements ConfigurationSetBuilder {
		Map<StatePredictionPair, CallStack> perStatePredictionCallStack = new HashMap<StatePredictionPair, CallStack>();

		public void add(Configuration configuration) {
			StatePredictionPair key = new StatePredictionPair(configuration.state, configuration.prediction);

			CallStack stack = perStatePredictionCallStack.get(key);

			if (MERGE_STATS) {
				added++;
				if (stack != null) saved++;
			}

			CallStack mergedStack = stack == null ? configuration.callStack : stack.merge(configuration.callStack);
			perStatePredictionCallStack.put(key, mergedStack);
		}

		private int added = 0;
		private int saved = 0;

		public Set<Configuration> build() {
			HashSet<Configuration> configurations = new HashSet<Configuration>();
			for (Map.Entry<StatePredictionPair, CallStack> entry : perStatePredictionCallStack.entrySet()) {
				StatePredictionPair key = entry.getKey();
				configurations.add(new Configuration(key.state, key.prediction, entry.getValue()));
			}

			if (MERGE_STATS) {
				if (saved > 0) {
					System.out.println("Was: " + added + " - Now is: " + (added - saved) + " - Saved: " + saved);
				}
			}

			return configurations;
		}
	}
}
