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
import org.jlato.internal.parser.util.Collections;
import org.jlato.internal.parser.util.ShortPairObjectMap;
import org.jlato.internal.parser.util.ShortPairObjectMap.ShortPairObjectIterator;

import java.util.*;

import static java.util.Collections.singleton;

/**
 * @author Didier Villevalois
 */
public abstract class ParserBaseALL extends ParserBase {

	private static final boolean CACHE_STATS = false;
	private static final boolean MERGE_STATS = false;
	private static final boolean MERGE = true;
	private static final boolean INCREMENTAL_MERGE = false;

	private boolean forceLL = true;

	protected abstract Grammar initializeGrammar();

	private final Grammar grammar = initializeGrammar();

	private final ShortPairObjectMap<CachedAutomaton> automata = Collections.intPairObjectMap();

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

	protected void pushCallStack(int nonTerminalReturnState) {
		if (forceLL) return;

		callStack = callStack.push(nonTerminalReturnState);
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

		// TODO Pre-initialize automata with their final states and the shared error state ?
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
	private Map<Integer, PredictionState> perChoicePointStackSensitiveStates = Collections.hashMap();

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
		int stateId = grammar.getChoicePointState(choicePoint);

		Configuration initialConfiguration = newConfiguration(stateId, -1, callStack);
		Set<Configuration> configurations = singleton(initialConfiguration);
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
		Set<Configuration> newConfigurations = Collections.hashSet();
		for (Configuration configuration : configurations) {
			GrammarState state = grammar.getState(configuration.stateId);
			int targetId = state.match(token);
			if (targetId == -1) continue;

			Configuration newConfiguration = newConfiguration(targetId, configuration.prediction, configuration.callStack);
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

	private ConfigurationSetBuilder newConfigurationSetBuilder() {
		return !MERGE ? new ConfigurationSetBuilderWithoutMerge() :
				INCREMENTAL_MERGE ?
						new ConfigurationSetBuilderWithIncrementalMerge(this) :
						new ConfigurationSetBuilderWithPostMerge(this);
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
				short[] useEndStates = grammar.getUseEndStates(nonTerminalEnd);
				if (useEndStates != null) {
					for (short useEndState : useEndStates) {
						Configuration newConfiguration = newConfiguration(useEndState, prediction, CallStack.WILDCARD);
						closureOf(newConfiguration, newConfigurations);
					}
				}

				// Specific end states for the entry point
				int useEndState = grammar.getEntryPointUseEndStates(entryPoint, nonTerminalEnd);
				if (useEndState != -1) {
					Configuration newConfiguration = newConfiguration(useEndState, prediction, CallStack.WILDCARD);
					closureOf(newConfiguration, newConfigurations);
				}
			} else {

				// Unrolled code without inner class closure
				if (callStack.kind == CallStack.Kind.WILDCARD || callStack.kind == CallStack.Kind.EMPTY) return;
				if (callStack.kind == CallStack.Kind.PUSH) {
					CallStack.Push push = (CallStack.Push) callStack;

					Configuration newConfiguration = newConfiguration(push.head, prediction, push.tails);
					closureOf(newConfiguration, newConfigurations);
				} else {
					CallStack.Merge merge = (CallStack.Merge) callStack;
					// Stacks in a merge are Push stacks by construction
					for (CallStack pushStack : merge.stacks) {
						CallStack.Push push = (CallStack.Push) pushStack;

						Configuration newConfiguration = newConfiguration(push.head, prediction, push.tails);
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

				Configuration newConfiguration = newConfiguration(targetId, realPrediction, callStack);
				closureOf(newConfiguration, newConfigurations);
			}

			// Handle non-terminal call
			int symbol = state.nonTerminalTransition;
			int targetId = state.nonTerminalTransitionEnd;
			if (symbol != -1 && targetId != -1) {
				int startId = grammar.getNonTerminalStartState(symbol);

				Configuration newConfiguration = newConfiguration(startId, prediction, callStack.push(targetId));
				closureOf(newConfiguration, newConfigurations);
			}
		}
	}

	private Configuration newConfiguration(int stateId, int prediction, CallStack callStack) {
		if (configurationPool.isEmpty())
			return new Configuration((short) stateId, (short) prediction, callStack);
		else {
			Configuration configuration = configurationPool.pop();
			configuration.stateId = (short) stateId;
			configuration.prediction = (short) prediction;
			configuration.callStack = callStack;
			return configuration;
		}
	}

	private void releaseConfiguration(Configuration configuration) {
		configurationPool.push(configuration);
	}

	private Deque<Configuration> configurationPool = new ArrayDeque<Configuration>();

	private static class ConfigurationSetBuilderWithoutMerge implements ConfigurationSetBuilder {
		private final Set<Configuration> configurations = Collections.hashSet();

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

		private final ParserBaseALL base;
		private final Set<Configuration> configurations = Collections.hashSet();

		private ConfigurationSetBuilderWithPostMerge(ParserBaseALL base) {
			this.base = base;
		}

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
			ShortPairObjectMap<CallStack> perStatePredictionCallStack = Collections.intPairObjectMap();
			for (Configuration configuration : configurations) {
				CallStack stack = perStatePredictionCallStack.get(configuration.stateId, configuration.prediction);
				CallStack mergedStack = stack == null ? configuration.callStack : stack.merge(configuration.callStack);
				perStatePredictionCallStack.put(configuration.stateId, configuration.prediction, mergedStack);
				base.releaseConfiguration(configuration);
			}

			configurations.clear();

			ShortPairObjectIterator<CallStack> iterator = perStatePredictionCallStack.iterator();
			while (iterator.hasNext()) {
				iterator.advance();
				configurations.add(base.newConfiguration(iterator.getKey1(), iterator.getKey2(), iterator.value()));
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

		private final ParserBaseALL base;
		private final Set<Configuration> configurations = Collections.hashSet();
		private final ShortPairObjectMap<CallStack> perStatePredictionCallStack = Collections.intPairObjectMap();

		private ConfigurationSetBuilderWithIncrementalMerge(ParserBaseALL base) {
			this.base = base;
		}

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
			for (Configuration configuration : configurations) {
				base.releaseConfiguration(configuration);
			}

			Set<Configuration> newConfigurations = Collections.hashSet();

			ShortPairObjectIterator<CallStack> iterator = perStatePredictionCallStack.iterator();
			while (iterator.hasNext()) {
				iterator.advance();
				newConfigurations.add(base.newConfiguration(iterator.getKey1(), iterator.getKey2(), iterator.value()));
			}

			if (MERGE_STATS) {
				if (saved > 0) {
					System.out.println("Was: " + added + " - Now is: " + (added - saved) + " - Saved: " + saved);
				}
			}

			return newConfigurations;
		}
	}
}
