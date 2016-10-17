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

	private List<Integer> currentPredictions;

	protected void pushCallStack(Grammar.NonTerminal ntCall) {
		GrammarState state = ntCall.end();
		callStack = callStack.push(state);
	}

	protected void popCallStack() {
		callStack.pop(new CallStack.CallStackReader() {
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

		CachedAutomaton automaton = automata.get(choicePoint);
		if (automaton == null) {
			current = makeStartState(choicePoint, CallStack.WILDCARD);

			automaton = new CachedAutomaton(current);
			automata.put(choicePoint, automaton);
		} else current = automaton.initialState;

		int index = 0;
		while (true) {
			Token token = getToken(index++);

			PredictionState next = current.transitionFor(token);
//			System.out.println(next != null ? "Hit" : "Miss");

			if (next == null) {
				Set<Configuration> configurations = moveAlong(current.configurations, token);
				configurations = closure(configurations);
				next = new PredictionState(configurations);

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

	private int llPredict(int choicePoint) {
		PredictionState current = makeStartState(choicePoint, callStack);

		int index = 0;
		while (true) {
			Token token = getToken(index++);

			Set<Configuration> configurations = moveAlong(current.configurations, token);
			configurations = closure(configurations);
			PredictionState next = new PredictionState(configurations);

			if (next.configurations.isEmpty()) return -1;
			if (next.prediction != -1) return next.prediction;

			HashMap<StateCallStackPair, Set<Integer>> conflictSetsPerLoc = next.getConflictSetsPerLoc();
			if (ambiguousAlternatives(conflictSetsPerLoc)) {
//				reportAmbiguity(choicePoint, conflictSetsPerLoc);
				return firstAlternative(conflictSetsPerLoc);
			}

			current = next;
		}
	}

	private void reportAmbiguity(int choicePoint, HashMap<StateCallStackPair, Set<Integer>> conflictSetsPerLoc) {
		StringBuilder buffer = new StringBuilder();

		Map.Entry<StateCallStackPair, Set<Integer>> entry = firstConflict(conflictSetsPerLoc);
		Set<Integer> alternatives = entry.getValue();

		Token firstToken = getToken(0);
		buffer.append("At choice point ");
		buffer.append(choicePoint);
		buffer.append(" ambiguous alternatives {");
		boolean first = true;
		for (Integer alternative : alternatives) {
			if (first) first = false;
			else buffer.append(", ");
			buffer.append(alternative);
		}

		buffer.append("} at (" + firstToken.beginLine + ":" + firstToken.beginColumn + ")");

		System.out.println(buffer.toString());
	}

	private Map.Entry<StateCallStackPair, Set<Integer>> firstConflict(HashMap<StateCallStackPair, Set<Integer>> conflictSetsPerLoc) {
		return conflictSetsPerLoc.entrySet().iterator().next();
	}

	private int firstAlternative(HashMap<StateCallStackPair, Set<Integer>> conflictSetsPerLoc) {
		Set<Integer> alternatives = firstConflict(conflictSetsPerLoc).getValue();
		int min = Integer.MAX_VALUE;
		for (Integer alternative : alternatives) {
			min = Math.min(min, alternative);
		}
		return min;
	}

	private boolean ambiguousAlternatives(HashMap<StateCallStackPair, Set<Integer>> conflictSetsPerLoc) {
		Set<Integer> predictions = null;
		for (Map.Entry<StateCallStackPair, Set<Integer>> entry : conflictSetsPerLoc.entrySet()) {
			Set<Integer> otherPredictions = entry.getValue();
			if (otherPredictions.size() == 1) return false;

			if (predictions == null) predictions = otherPredictions;
			else if (!predictions.equals(otherPredictions)) return false;
		}
		return true;
	}

	private PredictionState makeStartState(int choicePoint, CallStack callStack) {
		GrammarState state = grammar.getStartState(choicePoint);

		Configuration initialConfiguration = new Configuration(-1, state, callStack);
		Set<Configuration> configurations = Collections.singleton(initialConfiguration);
		configurations = closure(configurations);

		return new PredictionState(configurations);
	}

	private Set<Configuration> moveAlong(Set<Configuration> configurations, Token token) {
		Set<Configuration> newConfigurations = new HashSet<Configuration>();
		for (Configuration configuration : configurations) {
			GrammarState target = configuration.state.match(token);
			if (target == null) continue;

			Configuration newConfiguration = new Configuration(configuration.prediction, target, configuration.callStack);
			newConfigurations.add(newConfiguration);
		}
		return newConfigurations;
	}

	protected int entryPoint;

	private Set<Configuration> closure(Set<Configuration> configurations) {
		HashSet<Configuration> newConfigurations = new HashSet<Configuration>();
		HashSet<Configuration> busy = new HashSet<Configuration>();
		for (Configuration configuration : configurations) {
			closureOf(configuration, newConfigurations, busy);
		}
		return newConfigurations;
	}

	private void closureOf(final Configuration configuration,
	                       final Set<Configuration> newConfigurations,
	                       final Set<Configuration> busy) {
		if (busy.contains(configuration)) return;
		else busy.add(configuration);

		GrammarState state = configuration.state;
		CallStack callStack = configuration.callStack;

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
						Configuration newConfiguration = new Configuration(configuration.prediction, useEndState, CallStack.WILDCARD);

						closureOf(newConfiguration, newConfigurations, busy);
					}
				}

				// Specific end states for the entry point
				useEndStates = grammar.getEntryPointUseEndStates(entryPoint, nonTerminal);
				if (useEndStates != null) {
					for (GrammarState useEndState : useEndStates) {
						Configuration newConfiguration = new Configuration(configuration.prediction, useEndState, CallStack.WILDCARD);

						closureOf(newConfiguration, newConfigurations, busy);
					}
				}
			} else {
				callStack.pop(new CallStack.CallStackReader() {
					@Override
					public void handleNext(GrammarState head, CallStack tail) {
						Configuration newConfiguration = new Configuration(configuration.prediction, head, tail);

						closureOf(newConfiguration, newConfigurations, busy);
					}
				});
			}
		} else {
			// Handle choice transitions
			for (Map.Entry<Integer, GrammarState> entry : state.choiceTransitions.entrySet()) {
				int prediction = configuration.prediction == -1 ? entry.getKey() : configuration.prediction;
				Configuration newConfiguration = new Configuration(prediction, entry.getValue(), callStack);

				closureOf(newConfiguration, newConfigurations, busy);
			}
			// Handle non-terminal call
			for (Map.Entry<Integer, GrammarState> entry : state.nonTerminalTransitions.entrySet()) {
				GrammarState target = entry.getValue();
				if (target == null) continue;

				Integer symbol = entry.getKey();
				GrammarState start = grammar.getStartState(symbol);
				Configuration newConfiguration = new Configuration(configuration.prediction, start, callStack.push(target));

				closureOf(newConfiguration, newConfigurations, busy);
			}
		}
	}
}
