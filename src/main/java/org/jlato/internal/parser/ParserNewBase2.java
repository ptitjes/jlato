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

import com.github.andrewoma.dexx.collection.*;
import org.jlato.internal.parser.all.*;

import java.io.Reader;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
		Grammar.GrammarState state = ntCall.end();
		callStack = callStack.push(state);
	}

	protected void popCallStack() {
		callStack.pop(new CallStack.CallStackReader() {
			@Override
			public void handleNext(Grammar.GrammarState state, CallStack parent) {
				callStack = parent;
			}
		});
	}

	protected int predict(int choicePoint) {
		if (currentPredictions.isEmpty()) {
			currentPredictions.addAll(computeNextPredictions(choicePoint));
		}
		if (currentPredictions.isEmpty()) return -1;

		return currentPredictions.remove(0);
	}

	private List<Integer> computeNextPredictions(int choicePoint) {
		return sllPredict(choicePoint);
	}

	private List<Integer> sllPredict(int choicePoint) {
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
				current.setTransitionFor(token, next);
				automaton.addState(next);
			}

			if (next.configurations.isEmpty()) return Collections.emptyList();
			if (next.prediction != null) {
				return next.prediction;
			}
			if (next.stackSensitive) return llPredict(choicePoint);

			current = next;
		}
	}

	private List<Integer> llPredict(int choicePoint) {
		PredictionState current = makeStartState(choicePoint, callStack);

		int index = 0;
		while (true) {
			Token token = getToken(index++);

			Set<Configuration> configurations = moveAlong(current.configurations, token);
			configurations = closure(configurations);
			PredictionState next = new PredictionState(configurations);

			if (next.configurations.isEmpty()) return Collections.emptyList();
			if (next.prediction != null) {
				return next.prediction;
			}

			HashMap<StateCallStackPair, Set<Configuration.Prediction>> conflictSetsPerLoc = next.getConflictSetsPerLoc();
			if (ambiguousAlternatives(conflictSetsPerLoc)) {
				reportAmbiguity(choicePoint, conflictSetsPerLoc);
				return Collections.singletonList(firstAlternative(conflictSetsPerLoc));
			}

			current = next;
		}
	}

	private void reportAmbiguity(int choicePoint, HashMap<StateCallStackPair, Set<Configuration.Prediction>> conflictSetsPerLoc) {
		StringBuilder buffer = new StringBuilder();

		Map.Entry<StateCallStackPair, Set<Configuration.Prediction>> entry = firstConflict(conflictSetsPerLoc);
		Set<Configuration.Prediction> alternatives = entry.getValue();

		Token firstToken = getToken(0);
		buffer.append("At choice point ");
		buffer.append(choicePoint);
		buffer.append(" ambiguous alternatives {");
		boolean first = true;
		for (Configuration.Prediction alternative : alternatives) {
			if (first) first = false;
			else buffer.append(", ");
			buffer.append(alternative.rootPrediction());
		}

		buffer.append("} at (" + firstToken.beginLine + ":" + firstToken.beginColumn + ")");

		System.out.println(buffer.toString());
	}

	private Map.Entry<StateCallStackPair, Set<Configuration.Prediction>> firstConflict(HashMap<StateCallStackPair, Set<Configuration.Prediction>> conflictSetsPerLoc) {
		return conflictSetsPerLoc.entrySet().iterator().next();
	}

	private int firstAlternative(HashMap<StateCallStackPair, Set<Configuration.Prediction>> conflictSetsPerLoc) {
		Set<Configuration.Prediction> alternatives = firstConflict(conflictSetsPerLoc).getValue();
		int min = Integer.MAX_VALUE;
		for (Configuration.Prediction alternative : alternatives) {
			min = Math.min(min, alternative.rootPrediction());
		}
		return min;
	}

	private boolean ambiguousAlternatives(HashMap<StateCallStackPair, Set<Configuration.Prediction>> conflictSetsPerLoc) {
		Set<Configuration.Prediction> predictions = null;
		for (Map.Entry<StateCallStackPair, Set<Configuration.Prediction>> entry : conflictSetsPerLoc.entrySet()) {
			Set<Configuration.Prediction> otherPredictions = entry.getValue();
			if (otherPredictions.size() == 1) return false;

			if (predictions == null) predictions = otherPredictions;
			else if (!predictions.equals(otherPredictions)) return false;
		}
		return true;
	}

	private PredictionState makeStartState(int choicePoint, CallStack callStack) {
		Grammar.GrammarState state = grammar.getStartState(choicePoint);

		Configuration initialConfiguration = new Configuration(Configuration.Prediction.NIL, state, callStack);
		Set<Configuration> configurations = Collections.singleton(initialConfiguration);
		configurations = closure(configurations);

		return new PredictionState(configurations);
	}

	private Set<Configuration> moveAlong(Set<Configuration> configurations, Token token) {
		Set<Configuration> newConfigurations = new HashSet<Configuration>();
		for (Configuration configuration : configurations) {
			Set<Grammar.GrammarState> targets = configuration.state.match(token);
			for (Grammar.GrammarState target : targets) {
				newConfigurations.add(new Configuration(configuration.prediction, target, configuration.callStack));
			}
		}
		return newConfigurations;
	}

//	private Set<Configuration> closure(Set<Configuration> configurations) {
//		Set<Configuration> configurationsToClose = new HashSet<Configuration>(configurations);
//		Set<Configuration> configurationsInClose = new HashSet<Configuration>(configurations);
//		final Set<Configuration> newConfigurations = new HashSet<Configuration>();
//		do {
//			newConfigurations.clear();
//			for (final Configuration configuration : configurationsToClose) {
////				if (configuration.prediction.toList().size() > 20) {
////					continue;
////				}
//
//				closureOf(configuration, configurations, newConfigurations);
//			}
//			configurations.addAll(newConfigurations);
//			if (newConfigurations.equals(configurationsToClose)) break;
//			configurationsToClose = new HashSet<Configuration>(newConfigurations);
//		} while (newConfigurations.size() > 0);
//
//		return configurations;
//	}

	protected int entryPoint;

	private Set<Configuration> closure(Set<Configuration> configurations) {
		Set<Configuration> newConfigurations = closure(configurations, new HashSet<Configuration>());
		return newConfigurations;
	}

	private Set<Configuration> closure(Set<Configuration> configurations, Set<Configuration> busy) {
		final Set<Configuration> newConfigurations = new HashSet<Configuration>();
		for (final Configuration configuration : configurations) {
			if (busy.contains(configuration)) continue;
			else busy.add(configuration);

			closureOf(configuration, newConfigurations);
		}

		if (newConfigurations.isEmpty()) return configurations;

		newConfigurations.addAll(closure(newConfigurations, busy));
		newConfigurations.addAll(configurations);
		return newConfigurations;
	}

	private void closureOf(final Configuration configuration, final Set<Configuration> newConfigurations) {
		Grammar.GrammarState state = configuration.state;
		CallStack callStack = configuration.callStack;

		// Return from non-terminal call
		if (state.end) {
			if (callStack == CallStack.WILDCARD) {
				int nonTerminal = state.nonTerminal;

				// End states
				Set<Grammar.GrammarState> useEndStates = grammar.getUseEndStates(nonTerminal);
				if (useEndStates != null) {
					for (Grammar.GrammarState useEndState : useEndStates) {
						newConfigurations.add(new Configuration(configuration.prediction, useEndState, CallStack.WILDCARD));
					}
				}

				// Specific end states for the entry point
				useEndStates = grammar.getEntryPointUseEndStates(entryPoint, nonTerminal);
				if (useEndStates != null) {
					for (Grammar.GrammarState useEndState : useEndStates) {
						newConfigurations.add(new Configuration(configuration.prediction, useEndState, CallStack.WILDCARD));
					}
				}
			} else {
				callStack.pop(new CallStack.CallStackReader() {
					@Override
					public void handleNext(Grammar.GrammarState state, CallStack parent) {
						newConfigurations.add(new Configuration(configuration.prediction, state, parent));
					}
				});
			}
		} else {
			// Handle choice transitions
			for (Map.Entry<Integer, Grammar.GrammarState> entry : state.choiceTransitions.entrySet()) {
				newConfigurations.add(new Configuration(configuration.prediction.append(entry.getKey(), state), entry.getValue(), callStack));
			}
			// Handle non-terminal call
			for (Map.Entry<Integer, Set<Grammar.GrammarState>> entry : state.nonTerminalTransitions.entrySet()) {
				Integer symbol = entry.getKey();
				Grammar.GrammarState startState = grammar.getStartState(symbol);
				for (Grammar.GrammarState targetState : entry.getValue()) {
					newConfigurations.add(new Configuration(configuration.prediction, startState, callStack.push(targetState)));
				}
			}
		}
	}
}
