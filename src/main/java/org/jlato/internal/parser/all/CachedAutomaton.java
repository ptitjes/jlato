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
import org.jlato.internal.parser.all.Configuration.CallStack;
import org.jlato.internal.parser.all.Configuration.Prediction;
import org.jlato.internal.parser.all.Grammar.GrammarState;

import java.util.*;

/**
 * @author Didier Villevalois
 */
public class CachedAutomaton {

	public final Grammar grammar;
	public final CachedState initialState;
	private final Map<Set<Configuration>, CachedState> states = new HashMap<Set<Configuration>, CachedState>();

	public CachedAutomaton(Grammar grammar, int nonTerminal) {
		this.grammar = grammar;

		GrammarState state = grammar.getStartState(nonTerminal);

		Set<Configuration> configurations = new HashSet<Configuration>();
		configurations.add(new Configuration(Prediction.NIL, state, CallStack.EMPTY));

		this.initialState = newState(configurations);
	}

	private CachedState newState(Set<Configuration> configurations) {
		CachedState newState = new CachedState(configurations);

		CachedState state = this.states.get(newState.configurations);
		if (state == null) {
			state = newState;
			this.states.put(configurations, state);
		}

		return state;
	}

	public class CachedState {

		public final Set<Configuration> configurations;

		public final Map<Integer, CachedState> transitions = new HashMap<Integer, CachedState>();

		public final List<Integer> prediction;

		public CachedState(Set<Configuration> configurations) {
			this.configurations = closure(configurations);
			this.prediction = longestCommonPredictions();
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
			return lcp.isEmpty() ? null : lcp;
		}

		public CachedState match(Token token) {
			CachedState state = transitions.get(token.kind);
//			System.out.println(state != null ? "Hit" : "Miss");
			if (state != null) return state;

			Set<Configuration> newConfigurations = new HashSet<Configuration>();
			for (Configuration configuration : configurations) {
				Set<GrammarState> targets = configuration.state.match(token);
				for (GrammarState target : targets) {
					newConfigurations.add(new Configuration(configuration.prediction, target, configuration.callStack));
				}
			}

			CachedState nextState = newState(newConfigurations);
			transitions.put(token.kind, nextState);

			return nextState;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			CachedState that = (CachedState) o;

			return configurations != null ? configurations.equals(that.configurations) : that.configurations == null;

		}

		@Override
		public int hashCode() {
			return configurations != null ? configurations.hashCode() : 0;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			printToString(builder, this, 0);
			return builder.toString();
		}
	}

	public static void printToString(StringBuilder builder, CachedState state, int indent) {
		indent(builder, indent);
		builder.append("(" + state.hashCode() + (state.prediction == null ? "" : " - " + state.prediction) + ")");
		if (!state.transitions.isEmpty()) {
			builder.append(" \n");
			for (Map.Entry<Integer, CachedState> entry : state.transitions.entrySet()) {
				indent(builder, indent + 1);
				builder.append("[tok:" + entry.getKey() + "->\n");
				printToString(builder, entry.getValue(), indent + 2);
				builder.append("\n");
				indent(builder, indent + 1);
				builder.append("]\n");
			}
		}
		indent(builder, indent);
		builder.append("");
	}

	public static void indent(StringBuilder builder, int indent) {
		for (int i = 0; i < indent; i++) {
			builder.append("  ");
		}
	}

	public Set<Configuration> closure(Set<Configuration> configurations) {
		Set<Configuration> configurationsToClose = new HashSet<Configuration>(configurations);
		Set<Configuration> newConfigurations = new HashSet<Configuration>();
		do {
			newConfigurations.clear();
			for (Configuration configuration : configurationsToClose) {
				GrammarState state = configuration.state;
				CallStack callStack = configuration.callStack;

				// Return from non-terminal call
				if (state.end && callStack != CallStack.EMPTY) { // Handle LL case
					newConfigurations.add(new Configuration(configuration.prediction, callStack.state, callStack.parent));
					configurations.remove(configuration);
				} else {
					// Handle choice transitions
					for (Map.Entry<Integer, GrammarState> entry : state.choiceTransitions.entrySet()) {
						newConfigurations.add(new Configuration(configuration.prediction.append(entry.getKey()), entry.getValue(), callStack));
						configurations.remove(configuration);
					}
					// Handle non-terminal call
					for (Map.Entry<Integer, Set<GrammarState>> entry : state.nonTerminalTransitions.entrySet()) {
						Integer symbol = entry.getKey();
						GrammarState startState = grammar.getStartState(symbol);
						for (GrammarState targetState : entry.getValue()) {
							newConfigurations.add(new Configuration(configuration.prediction, startState, callStack.push(targetState)));
							configurations.remove(configuration);
						}
					}
				}
			}
			configurations.addAll(newConfigurations);
			configurationsToClose = new HashSet<Configuration>(newConfigurations);
		} while (newConfigurations.size() > 0);

		return configurations;
	}

	@Override
	public String toString() {
		return "CachedAutomaton{" +
				"initialState=" + initialState +
				'}';
	}
}
