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

import java.util.*;

/**
 * @author Didier Villevalois
 */
public abstract class Grammar {

	private final Map<Integer, GrammarState> startStates = new HashMap<Integer, GrammarState>();
	private final Map<Integer, Set<GrammarState>> perNonTerminalUseEndStates = new HashMap<Integer, Set<GrammarState>>();
	private final Map<Integer, Map<Integer, Set<GrammarState>>> perEntryPointNonTerminalUseEndState = new HashMap<Integer, Map<Integer, Set<GrammarState>>>();

	public Grammar() {
		initializeProductions();
	}

	protected abstract void initializeProductions();

	protected void addProduction(int nonTerminal, Expansion expansion, boolean entryPoint) {
		GrammarState start = new GrammarState(expansion);
		GrammarState end = new GrammarState(expansion, nonTerminal);
		expansion.initializeStates(start, end, this, entryPoint ? nonTerminal : -1);

		startStates.put(nonTerminal, start);
	}

	protected void addChoicePoint(int constant, ChoicePoint expansion) {
		startStates.put(constant, expansion.choice());
	}

	private void addNonTerminalEndState(int symbol, GrammarState end) {
		Set<GrammarState> useEndStates = perNonTerminalUseEndStates.get(symbol);
		if (useEndStates == null) {
			useEndStates = new HashSet<GrammarState>();
			perNonTerminalUseEndStates.put(symbol, useEndStates);
		}
		useEndStates.add(end);
	}

	private void addNonTerminalEntryPointEndState(int entryPoint, int symbol, GrammarState end) {
		Map<Integer, Set<GrammarState>> nonTerminalUseEndStates = perEntryPointNonTerminalUseEndState.get(entryPoint);
		if (nonTerminalUseEndStates == null) {
			nonTerminalUseEndStates = new HashMap<Integer, Set<GrammarState>>();
			perEntryPointNonTerminalUseEndState.put(entryPoint, nonTerminalUseEndStates);
		}

		Set<GrammarState> useEndStates = nonTerminalUseEndStates.get(symbol);
		if (useEndStates == null) {
			useEndStates = new HashSet<GrammarState>();
			nonTerminalUseEndStates.put(symbol, useEndStates);
		}
		useEndStates.add(end);
	}

	public GrammarState getStartState(int nonTerminal) {
		return startStates.get(nonTerminal);
	}

	public Set<GrammarState> getUseEndStates(int nonTerminal) {
		return perNonTerminalUseEndStates.get(nonTerminal);
	}

	public Set<GrammarState> getEntryPointUseEndStates(int entryPoint, int nonTerminal) {
		Map<Integer, Set<GrammarState>> nonTerminalUseEndStates = perEntryPointNonTerminalUseEndState.get(entryPoint);
		return nonTerminalUseEndStates == null ? null : nonTerminalUseEndStates.get(nonTerminal);
	}


	public static Choice choice(String name, Expansion... choices) {
		return new Choice(name, choices);
	}

	public static ZeroOrOne zeroOrOne(String name, Expansion child) {
		return new ZeroOrOne(name, child);
	}

	public static ZeroOrMore zeroOrMore(String name, Expansion child) {
		return new ZeroOrMore(name, child);
	}

	public static OneOrMore oneOrMore(String name, Expansion child) {
		return new OneOrMore(name, child);
	}

	public static Sequence sequence(String name, Expansion... elements) {
		return new Sequence(name, elements);
	}

	public static NonTerminal nonTerminal(String name, int symbol) {
		return new NonTerminal(name, symbol);
	}

	public static Terminal terminal(String name, int tokenType) {
		return new Terminal(name, tokenType);
	}

	public static abstract class Expansion {
		public final String name;
		private GrammarState start, end;

		public Expansion(String name) {
			this.name = name;
		}

		protected void initializeStates(GrammarState start, GrammarState end, Grammar grammar, int entryPoint) {
			this.start = start;
			this.end = end;
		}

		public GrammarState start() {
			return start;
		}

		public GrammarState end() {
			return end;
		}
	}

	public static abstract class ChoicePoint extends Expansion {
		public ChoicePoint(String name) {
			super(name);
		}

		public abstract GrammarState choice();
	}

	public static class Choice extends ChoicePoint {

		public final Expansion[] choices;

		public Choice(String name, Expansion... choices) {
			super(name);
			this.choices = choices;
		}

		@Override
		protected void initializeStates(GrammarState start, GrammarState end, Grammar grammar, int entryPoint) {
			super.initializeStates(start, end, grammar, entryPoint);
			for (int i = 0; i < choices.length; i++) {
				GrammarState choiceStart = new GrammarState(this);
				start.addChoice(i + 1, choiceStart);
				choices[i].initializeStates(choiceStart, end, grammar, entryPoint);
			}
		}

		@Override
		public GrammarState choice() {
			return start();
		}
	}

	public static class ZeroOrOne extends ChoicePoint {

		public final Expansion child;

		public ZeroOrOne(String name, Expansion child) {
			super(name);
			this.child = child;
		}

		@Override
		protected void initializeStates(GrammarState start, GrammarState end, Grammar grammar, int entryPoint) {
			super.initializeStates(start, end, grammar, entryPoint);

			GrammarState childStart = new GrammarState(this);
			child.initializeStates(childStart, end, grammar, entryPoint);

			start.addChoice(0, end);
			start.addChoice(1, childStart);
		}

		@Override
		public GrammarState choice() {
			return start();
		}
	}

	public static class ZeroOrMore extends ChoicePoint {

		public final Expansion child;

		public ZeroOrMore(String name, Expansion child) {
			super(name);
			this.child = child;
		}

		@Override
		protected void initializeStates(GrammarState start, GrammarState end, Grammar grammar, int entryPoint) {
			super.initializeStates(start, end, grammar, entryPoint);

			GrammarState childStart = new GrammarState(this);
			child.initializeStates(childStart, start, grammar, entryPoint);

			start.addChoice(0, end);
			start.addChoice(1, childStart);
		}

		@Override
		public GrammarState choice() {
			return start();
		}
	}

	public static class OneOrMore extends ChoicePoint {

		public final Expansion child;
		private GrammarState childEnd;

		public OneOrMore(String name, Expansion child) {
			super(name);
			this.child = child;
		}

		@Override
		protected void initializeStates(GrammarState start, GrammarState end, Grammar grammar, int entryPoint) {
			super.initializeStates(start, end, grammar, entryPoint);

			childEnd = new GrammarState(this);
			child.initializeStates(start, childEnd, grammar, entryPoint);

			childEnd.addChoice(0, end);
			childEnd.addChoice(1, start);
		}

		@Override
		public GrammarState choice() {
			return childEnd;
		}
	}

	public static class Sequence extends Expansion {

		public final Expansion[] elements;

		public Sequence(String name, Expansion... elements) {
			super(name);
			this.elements = elements;
		}

		@Override
		protected void initializeStates(GrammarState start, GrammarState end, Grammar grammar, int entryPoint) {
			super.initializeStates(start, end, grammar, entryPoint);

			GrammarState state;
			int lastIndex = elements.length - 1;
			for (int i = 0; i <= lastIndex; i++) {
				if (i == lastIndex) state = end;
				else state = new GrammarState(this);

				elements[i].initializeStates(start, state, grammar, entryPoint);
				start = state;
			}
		}

	}

	public static class NonTerminal extends Expansion {

		public final int symbol;

		public NonTerminal(String name, int symbol) {
			super(name);
			this.symbol = symbol;
		}

		@Override
		protected void initializeStates(GrammarState start, GrammarState end, Grammar grammar, int entryPoint) {
			super.initializeStates(start, end, grammar, entryPoint);
			start.setNonTerminal(symbol, end);

			if (entryPoint != -1) grammar.addNonTerminalEntryPointEndState(entryPoint, symbol, end);
			else grammar.addNonTerminalEndState(symbol, end);
		}
	}

	public static class Terminal extends Expansion {

		public final int tokenType;

		public Terminal(String name, int tokenType) {
			super(name);
			this.tokenType = tokenType;
		}

		@Override
		protected void initializeStates(GrammarState start, GrammarState end, Grammar grammar, int entryPoint) {
			super.initializeStates(start, end, grammar, entryPoint);
			start.setTerminal(tokenType, end);
		}

	}
}