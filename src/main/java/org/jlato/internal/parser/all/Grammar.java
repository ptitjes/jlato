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

	private int stateCount = 0;
	private final Map<Integer, GrammarState> startStates = new HashMap<Integer, GrammarState>();
	private final Map<Integer, Set<GrammarState>> perNonTerminalUseEndStates = new HashMap<Integer, Set<GrammarState>>();
	private final Map<Integer, Map<Integer, Set<GrammarState>>> perEntryPointNonTerminalUseEndState = new HashMap<Integer, Map<Integer, Set<GrammarState>>>();

	public Grammar() {
		initializeProductions();
	}

	protected abstract void initializeProductions();

	private int newStateId() {
		return stateCount++;
	}

	GrammarState newGrammarState(Expansion location) {
		return new GrammarState(newStateId(), location.name);
	}

	protected void addProduction(int nonTerminal, Expansion expansion, boolean entryPoint) {
		GrammarState start = new GrammarState(newStateId(), expansion.name);
		GrammarState end = new GrammarState(newStateId(), expansion.name, nonTerminal);
		expansion.initializeStates(start, end, this, entryPoint ? nonTerminal : -1);

		startStates.put(nonTerminal, start);
	}

	protected void addChoicePoint(int constant, ChoicePoint expansion) {
		startStates.put(constant, expansion.choice());
	}

	void addNonTerminalEndState(int symbol, GrammarState end) {
		Set<GrammarState> useEndStates = perNonTerminalUseEndStates.get(symbol);
		if (useEndStates == null) {
			useEndStates = new HashSet<GrammarState>();
			perNonTerminalUseEndStates.put(symbol, useEndStates);
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
		return startStates.get(nonTerminal);
	}

	public Set<GrammarState> getUseEndStates(int nonTerminal) {
		return perNonTerminalUseEndStates.get(nonTerminal);
	}

	public Set<GrammarState> getEntryPointUseEndStates(int entryPoint, int nonTerminal) {
		Map<Integer, Set<GrammarState>> nonTerminalUseEndStates = perEntryPointNonTerminalUseEndState.get(entryPoint);
		return nonTerminalUseEndStates == null ? null : nonTerminalUseEndStates.get(nonTerminal);
	}
}
