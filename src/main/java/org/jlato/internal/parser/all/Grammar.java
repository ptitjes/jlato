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
public abstract class Grammar {

	private final Map<Integer, GrammarState> startStates;

	public Grammar() {
		Map<Integer, Expansion> productions = new HashMap<Integer, Expansion>();
		initializeProductions(productions);

		this.startStates = new HashMap<Integer, GrammarState>();
		for (Map.Entry<Integer, Expansion> entry : productions.entrySet()) {
			GrammarState start = new GrammarState(false);
			GrammarState end = new GrammarState(true);
			entry.getValue().initializeStates(start, end);
			startStates.put(entry.getKey(), start);
		}
	}

	protected abstract void initializeProductions(Map<Integer, Expansion> productions);

	public GrammarState getStartState(int nonTerminal) {
		return startStates.get(nonTerminal);
	}


	public static Choice choice(Expansion... choices) {
		return new Choice(choices);
	}

	public static ZeroOrOne zeroOrOne(Expansion child) {
		return new ZeroOrOne(child);
	}

	public static ZeroOrMore zeroOrMore(Expansion child) {
		return new ZeroOrMore(child);
	}

	public static OneOrMore oneOrMore(Expansion child) {
		return new OneOrMore(child);
	}

	public static Sequence sequence(Expansion... elements) {
		return new Sequence(elements);
	}

	public static NonTerminal nonTerminal(int symbol) {
		return new NonTerminal(symbol);
	}

	public static Terminal terminal(int tokenType) {
		return new Terminal(tokenType);
	}

	public static abstract class Expansion {
		private GrammarState start, end;

		protected void initializeStates(GrammarState start, GrammarState end) {
			this.start = start;
			this.end = end;
		}

		public GrammarState start() {
			return start;
		}
	}

	private static int lastLocationId = 0;

	public static class GrammarState {

		public final int id = lastLocationId++;
		public final boolean end;
		public final Map<Integer, GrammarState> choiceTransitions = new HashMap<Integer, GrammarState>();
		public final Map<Integer, Set<GrammarState>> nonTerminalTransitions = new HashMap<Integer, Set<GrammarState>>();
		public final Map<Integer, Set<GrammarState>> terminalTransitions = new HashMap<Integer, Set<GrammarState>>();

		public GrammarState(boolean end) {
			this.end = end;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			GrammarState location = (GrammarState) o;

			return id == location.id;
		}

		@Override
		public int hashCode() {
			return id;
		}

		void addChoice(int index, GrammarState target) {
			choiceTransitions.put(index, target);
		}

		void addNonTerminal(int symbol, GrammarState target) {
			Set<GrammarState> targets = nonTerminalTransitions.get(symbol);
			if (targets == null) {
				targets = new HashSet<GrammarState>();
				nonTerminalTransitions.put(symbol, targets);
			}
			targets.add(target);
		}

		void addTerminal(int tokenType, GrammarState target) {
			Set<GrammarState> targets = terminalTransitions.get(tokenType);
			if (targets == null) {
				targets = new HashSet<GrammarState>();
				terminalTransitions.put(tokenType, targets);
			}
			targets.add(target);
		}

		public Set<GrammarState> match(Token token) {
			Set<GrammarState> targets = terminalTransitions.get(token.kind);
			return targets != null ? targets : Collections.<GrammarState>emptySet();
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();

			builder.append("(" + id + ")");
			if (!terminalTransitions.isEmpty()) {
				builder.append(" ");
				for (Map.Entry<Integer, Set<GrammarState>> entry : terminalTransitions.entrySet()) {
					builder.append("[tok:" + entry.getKey() + "->" + entry.getValue().toString() + "]");
				}
			}
			builder.append("");

			return builder.toString();
		}
	}

	public static class Choice extends Expansion {

		public final Expansion[] choices;

		public Choice(Expansion... choices) {
			this.choices = choices;
		}

		@Override
		protected void initializeStates(GrammarState start, GrammarState end) {
			super.initializeStates(start, end);
			for (int i = 0; i < choices.length; i++) {
				GrammarState choiceStart = new GrammarState(false);
				start.addChoice(i, choiceStart);
				choices[i].initializeStates(choiceStart, end);
			}
		}

	}

	public static class ZeroOrOne extends Expansion {

		public final Expansion child;

		public ZeroOrOne(Expansion child) {
			this.child = child;
		}

		@Override
		protected void initializeStates(GrammarState start, GrammarState end) {
			super.initializeStates(start, end);

			GrammarState childStart = new GrammarState(false);
			child.initializeStates(childStart, end);

			start.addChoice(0, end);
			start.addChoice(1, childStart);
		}

	}

	public static class ZeroOrMore extends Expansion {

		public final Expansion child;

		public ZeroOrMore(Expansion child) {
			this.child = child;
		}

		@Override
		protected void initializeStates(GrammarState start, GrammarState end) {
			super.initializeStates(start, end);

			GrammarState childStart = new GrammarState(false);
			GrammarState childEnd = new GrammarState(false);
			child.initializeStates(childStart, childEnd);

			start.addChoice(0, end);
			start.addChoice(1, childStart);
			childEnd.addChoice(0, end);
			childEnd.addChoice(1, childStart);
		}

	}

	public static class OneOrMore extends Expansion {

		public final Expansion child;

		public OneOrMore(Expansion child) {
			this.child = child;
		}

		@Override
		protected void initializeStates(GrammarState start, GrammarState end) {
			super.initializeStates(start, end);

			GrammarState childEnd = new GrammarState(false);
			child.initializeStates(start, childEnd);

			childEnd.addChoice(0, end);
			childEnd.addChoice(1, start);
		}

	}

	public static class Sequence extends Expansion {

		public final Expansion[] elements;

		public Sequence(Expansion... elements) {
			this.elements = elements;
		}

		@Override
		protected void initializeStates(GrammarState start, GrammarState end) {
			super.initializeStates(start, end);

			GrammarState state;
			int lastIndex = elements.length - 1;
			for (int i = 0; i <= lastIndex; i++) {
				if (i == lastIndex) state = end;
				else state = new GrammarState(false);

				elements[i].initializeStates(start, state);
				start = state;
			}
		}

	}

	public static class NonTerminal extends Expansion {

		public final int symbol;

		public NonTerminal(int symbol) {
			this.symbol = symbol;
		}

		@Override
		protected void initializeStates(GrammarState start, GrammarState end) {
			super.initializeStates(start, end);
			start.addNonTerminal(symbol, end);
		}

	}

	public static class Terminal extends Expansion {

		public final int tokenType;

		public Terminal(int tokenType) {
			this.tokenType = tokenType;
		}

		@Override
		protected void initializeStates(GrammarState start, GrammarState end) {
			super.initializeStates(start, end);
			start.addTerminal(tokenType, end);
		}

	}
}
