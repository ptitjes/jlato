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

import org.jlato.internal.parser.all.GrammarProduction.ChoicePoint;
import org.jlato.internal.parser.all.GrammarProduction.Expansion;

import java.util.*;

/**
 * @author Didier Villevalois
 */
public abstract class Grammar {

	private int nextStateId = 0;
	private final GrammarState[] states;
	private final GrammarState[] startStates;
	private final Set<GrammarState>[] perNonTerminalUseEndStates;
	private final Map<Integer, Map<Integer, Set<GrammarState>>> perEntryPointNonTerminalUseEndState = new HashMap<Integer, Map<Integer, Set<GrammarState>>>();

	public Grammar(int stateCount, int constantCount) {
		states = new GrammarState[stateCount];
		startStates = new GrammarState[constantCount];
		perNonTerminalUseEndStates = (Set<GrammarState>[]) new Set[constantCount];
		initializeProductions();
	}

	protected abstract void initializeProductions();

	private int newStateId() {
		return nextStateId++;
	}

	GrammarState newGrammarState(Expansion expansion) {
		return new GrammarState(newStateId(), expansion.name);
	}

	protected void addProduction(int nonTerminal, Expansion expansion, boolean entryPoint) {
		GrammarState start = new GrammarState(newStateId(), expansion.name);
		GrammarState end = new GrammarState(newStateId(), expansion.name, nonTerminal);
		expansion.initializeStates(start, end, this, entryPoint ? nonTerminal : -1);

		startStates[nonTerminal] = start;
	}

	protected void addChoicePoint(int choicePoint, ChoicePoint expansion) {
		startStates[choicePoint] = expansion.choice();
	}

	void addNonTerminalEndState(int symbol, GrammarState end) {
		Set<GrammarState> useEndStates = perNonTerminalUseEndStates[symbol];
		if (useEndStates == null) {
			useEndStates = new HashSet<GrammarState>();
			perNonTerminalUseEndStates[symbol] = useEndStates;
		}
		useEndStates.add(end);
	}

	void addNonTerminalEntryPointEndState(int entryPoint, int symbol, GrammarState end) {
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
		return startStates[nonTerminal];
	}

	public Set<GrammarState> getUseEndStates(int nonTerminal) {
		return perNonTerminalUseEndStates[nonTerminal];
	}

	public Set<GrammarState> getEntryPointUseEndStates(int entryPoint, int nonTerminal) {
		Map<Integer, Set<GrammarState>> nonTerminalUseEndStates = perEntryPointNonTerminalUseEndState.get(entryPoint);
		return nonTerminalUseEndStates == null ? null : nonTerminalUseEndStates.get(nonTerminal);
	}
}
