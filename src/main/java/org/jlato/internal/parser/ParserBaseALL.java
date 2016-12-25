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

import gnu.trove.decorator.TLongObjectMapDecorator;
import gnu.trove.iterator.TLongIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.set.hash.THashSet;
import org.jlato.internal.parser.all.*;
import org.jlato.internal.parser.util.IntObjectMap;
import org.jlato.internal.parser.util.IntPairObjectMap;
import org.jlato.internal.parser.util.IntPairObjectMap.IntPairObjectIterator;

import java.util.*;

/**
 * @author Didier Villevalois
 */
public abstract class ParserBaseALL extends ParserBase {

	private static final boolean CACHE_STATS = false;
	private static final boolean MERGE_STATS = false;
	private static final boolean MERGE = true;
	private static final boolean INCREMENTAL_MERGE = true;

	private boolean forceLL = true;

	protected abstract Grammar initializeGrammar();

	private final Grammar grammar = initializeGrammar();

	private final IntPairObjectMap<CachedAutomaton> automata = new IntPairObjectMap<CachedAutomaton>();

	private CallStack callStack = CallStack.EMPTY;

	@Override
	protected void reset() {
		super.reset();

		callStack = CallStack.EMPTY;
	}

	@Override
	public void clearStats() {
		super.clearStats();

		cacheMiss = 0;
		cacheHits = 0;
		fullLL = 0;
	}

	@Override
	public void printStats() {
		super.printStats();

		if (CACHE_STATS) {
			System.out.println("DFA Cache - Hits: " + cacheHits + ", Misses: " + cacheMiss + ", FullLL: " + fullLL);
			for (Map.Entry<Integer, PredictionState> entry : perChoicePointStackSensitiveStates.entrySet()) {
				System.out.println(entry.getKey());
				PredictionState state = entry.getValue();
				System.out.println(state);
				for (Configuration c : state.configurations) {
					System.out.println("  " + c);
				}
			}
		}
	}

	protected void pushCallStack(GrammarProduction.NonTerminal ntCall) {
		if (forceLL) return;

		int state = ntCall.end().id;
		callStack = callStack.push(state);
	}

	protected void popCallStack() {
		if (forceLL) return;

		callStack.pop(new CallStackReader() {
			@Override
			public void handleNext(int head, CallStack tail) {
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
		CachedAutomaton automaton = automata.get(entryPoint, choicePoint);

		if (automaton == null) {
			current = makeStartState(choicePoint, CallStack.WILDCARD);
			cacheMiss++;

			automaton = new CachedAutomaton(current);
			automata.put(entryPoint, choicePoint, automaton);
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
				next = new PredictionState(configurations, true, true, forceLL);

				// TODO Reuse a unique error state if closure of configurations is the empty set
				// TODO Reuse a unique final state for prediction if prediction is done
				next = automaton.addState(next);
				current.setTransitionFor(token, next);
			}

			if (next.configurations.isEmpty()) return -1;
			if (next.prediction != -1) return next.prediction;

			if (next.stackSensitive) {
				if (forceLL) throw new IllegalStateException();

				if (CACHE_STATS) {
					perChoicePointStackSensitiveStates.put(choicePoint, next);
				}
				return llPredict(choicePoint);
			}

			current = next;
		}
	}

	private int cacheHits, cacheMiss, fullLL;
	private Map<Integer, PredictionState> perChoicePointStackSensitiveStates = new HashMap<Integer, PredictionState>();

	private int llPredict(int choicePoint) {
		PredictionState current = makeStartState(choicePoint, callStack);
		fullLL++;

		int index = 0;
		while (true) {
			Token token = getToken(index++);

			Set<Configuration> configurations = targetConfigurations(current, token);
			PredictionState next = new PredictionState(configurations, true, false, forceLL);
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
		int stateId = grammar.getStartState(choicePoint);

		Configuration initialConfiguration = new Configuration(stateId, -1, callStack);
		Set<Configuration> configurations = Collections.singleton(initialConfiguration);
		configurations = closure(configurations);

		return new PredictionState(configurations, false, false, forceLL);
	}

	private static final boolean REPORT = true;

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

		System.err.println(buffer.toString());
	}

	private static BitSet firstConflict(Collection<BitSet> conflictSets) {
		return conflictSets.iterator().next();
	}

	public static int minimalAlternative(Collection<BitSet> conflictSetsPerLoc) {
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

		Set<Configuration> closure = closure(configurations);
		return closure;
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
		Set<Configuration> newConfigurations = new THashSet<Configuration>();
		for (Configuration configuration : configurations) {
			GrammarState state = grammar.getState(configuration.stateId);
			int targetId = state.match(token);
			if (targetId == -1) continue;

			Configuration newConfiguration = new Configuration(targetId, configuration.prediction, configuration.callStack);
			newConfigurations.add(newConfiguration);
		}
		return newConfigurations;
	}

	protected int entryPoint;

	private Set<Configuration> closure(Set<Configuration> configurations) {
		ConfigurationSetBuilder newConfigurations = newConfigurationSetBuilder();
		for (Configuration configuration : configurations) {
			closureOf(configuration, newConfigurations);
		}
		return newConfigurations.build();
	}

	private interface ConfigurationSetBuilder {

		void add(Configuration configuration);

		boolean contains(Configuration configuration);

		Set<Configuration> build();
	}

	private static ConfigurationSetBuilder newConfigurationSetBuilder() {
		return !MERGE ? new ConfigurationSetBuilderWithoutMerge() :
				INCREMENTAL_MERGE ?
						new ConfigurationSetBuilderWithIncrementalMerge() :
						new ConfigurationSetBuilderWithPostMerge();
	}

	private void closureOf(final Configuration configuration,
	                       final ConfigurationSetBuilder newConfigurations) {
		if (newConfigurations.contains(configuration)) return;
		else newConfigurations.add(configuration);

		final int stateId = configuration.stateId;
		final CallStack callStack = configuration.callStack;
		final int prediction = configuration.prediction;

		final GrammarState state = grammar.getState(stateId);

		// Return from non-terminal call
		int nonTerminalEnd = state.nonTerminalEnd;
		if (nonTerminalEnd != -1) {
			// SLL wildcard call stack
			if (callStack == CallStack.WILDCARD) {

				// End states
				Set<Integer> useEndStates = grammar.getUseEndStates(nonTerminalEnd);
				if (useEndStates != null) {
					for (int useEndStateId : useEndStates) {
						Configuration newConfiguration = new Configuration(useEndStateId, prediction, CallStack.WILDCARD);
						closureOf(newConfiguration, newConfigurations);
					}
				}

				// Specific end states for the entry point
				useEndStates = grammar.getEntryPointUseEndStates(entryPoint, nonTerminalEnd);
				if (useEndStates != null) {
					for (int useEndStateId : useEndStates) {
						Configuration newConfiguration = new Configuration(useEndStateId, prediction, CallStack.WILDCARD);
						closureOf(newConfiguration, newConfigurations);
					}
				}
			} else {

				// Using inner class closure
//				callStack.pop(new CallStackReader() {
//					@Override
//					public void handleNext(GrammarState head, CallStack tail) {
//						Configuration newConfiguration = new Configuration(head, prediction, tail);
//						closureOf(newConfiguration, newConfigurations, busy);
//					}
//				});

				// Unrolled code without inner class closure
				if (callStack.kind == CallStack.Kind.WILDCARD || callStack.kind == CallStack.Kind.EMPTY) return;
				if (callStack.kind == CallStack.Kind.PUSH) {
					CallStack.Push push = (CallStack.Push) callStack;

					Configuration newConfiguration = new Configuration(push.head, prediction, push.tails);
					closureOf(newConfiguration, newConfigurations);
				} else {
					CallStack.Merge merge = (CallStack.Merge) callStack;
					// Stacks in a merge are Push stacks by construction
					for (CallStack pushStack : merge.stacks) {
						CallStack.Push push = (CallStack.Push) pushStack;

						Configuration newConfiguration = new Configuration(push.head, prediction, push.tails);
						closureOf(newConfiguration, newConfigurations);
					}
				}
			}
		} else {
			// Handle choice transitions
			for (int choice = 0; choice < state.choiceTransitions.length; choice++) {
				int targetId = state.choiceTransitions[choice];
				if (targetId == -1) continue;

				int realPrediction = prediction == -1 ? choice : prediction;

				Configuration newConfiguration = new Configuration(targetId, realPrediction, callStack);
				closureOf(newConfiguration, newConfigurations);
			}

			// Handle non-terminal call
			int symbol = state.nonTerminalTransition;
			int targetId = state.nonTerminalTransitionEnd;
			if (symbol != -1 && targetId != -1) {
				int startId = grammar.getStartState(symbol);

				Configuration newConfiguration = new Configuration(startId, prediction, callStack.push(targetId));
				closureOf(newConfiguration, newConfigurations);
			}
		}
	}

	private static class ConfigurationSetBuilderWithoutMerge implements ConfigurationSetBuilder {
		private final Set<Configuration> configurations = new THashSet<Configuration>();

		@Override
		public void add(Configuration configuration) {
			configurations.add(configuration);
		}

		@Override
		public boolean contains(Configuration configuration) {
			return configurations.contains(configuration);
		}

		@Override
		public Set<Configuration> build() {
			return configurations;
		}
	}

	private static class ConfigurationSetBuilderWithPostMerge implements ConfigurationSetBuilder {
		private final Set<Configuration> configurations = new THashSet<Configuration>();

		@Override
		public void add(Configuration configuration) {
			configurations.add(configuration);
		}

		@Override
		public boolean contains(Configuration configuration) {
			return configurations.contains(configuration);
		}

		@Override
		public Set<Configuration> build() {
			return merge(configurations);
		}

		private Set<Configuration> merge(Set<Configuration> configurations) {
			int previousSize = configurations.size();
			IntPairObjectMap<CallStack> perStatePredictionCallStack = new IntPairObjectMap<CallStack>();
			for (Configuration configuration : configurations) {
				CallStack stack = perStatePredictionCallStack.get(configuration.stateId, configuration.prediction);
				CallStack mergedStack = stack == null ? configuration.callStack : stack.merge(configuration.callStack);
				perStatePredictionCallStack.put(configuration.stateId, configuration.prediction, mergedStack);
			}

			configurations.clear();

			IntPairObjectIterator<CallStack> iterator = perStatePredictionCallStack.iterator();
			while (iterator.hasNext()) {
				iterator.advance();
				configurations.add(new Configuration(iterator.getKey1(), iterator.getKey2(), iterator.value()));
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
		private final Set<Configuration> configurations = new THashSet<Configuration>();
		private final IntPairObjectMap<CallStack> perStatePredictionCallStack = new IntPairObjectMap<CallStack>();

		@Override
		public void add(Configuration configuration) {
			configurations.add(configuration);
			CallStack stack = perStatePredictionCallStack.get(configuration.stateId, configuration.prediction);

			if (MERGE_STATS) {
				added++;
				if (stack != null) saved++;
			}

			CallStack mergedStack = stack == null ? configuration.callStack : stack.merge(configuration.callStack);
			perStatePredictionCallStack.put(configuration.stateId, configuration.prediction, mergedStack);
		}

		@Override
		public boolean contains(Configuration configuration) {
			return configurations.contains(configuration);
		}

		private int added = 0;
		private int saved = 0;

		@Override
		public Set<Configuration> build() {
			Set<Configuration> configurations = new THashSet<Configuration>(perStatePredictionCallStack.size() * 4 / 3 + 1, 3f / 4);

			IntPairObjectIterator<CallStack> iterator = perStatePredictionCallStack.iterator();
			while (iterator.hasNext()) {
				iterator.advance();
				configurations.add(new Configuration(iterator.getKey1(), iterator.getKey2(), iterator.value()));
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
