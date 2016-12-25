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
import org.jlato.internal.parser.util.*;
import org.jlato.internal.parser.util.Collections;

import java.util.*;

/**
 * @author Didier Villevalois
 */
public abstract class Grammar {

	private int nextStateId = 0;
	private final GrammarState[] states;
	private final int[] startStates;
	private final IntSet[] perNonTerminalUseEndStates;
	private final IntPairObjectMap<IntSet> perEntryPointNonTerminalUseEndState = Collections.intPairObjectMap();

	public Grammar(int stateCount, int constantCount) {
		states = new GrammarState[stateCount];
		startStates = new int[constantCount];
		perNonTerminalUseEndStates = new IntSet[constantCount];
		initializeProductions();
	}

	protected abstract void initializeProductions();

	private int newStateId() {
		return nextStateId++;
	}

	GrammarState newGrammarState(Expansion expansion) {
		return newGrammarState(expansion, -1);
	}

	private GrammarState newGrammarState(Expansion expansion, int nonTerminal) {
		GrammarState state = new GrammarState(newStateId(), expansion.name, nonTerminal);
		states[state.id] = state;
		return state;
	}

	protected void addProduction(int nonTerminal, Expansion expansion, boolean entryPoint) {
		GrammarState start = newGrammarState(expansion);
		GrammarState end = newGrammarState(expansion, nonTerminal);
		expansion.initializeStates(start, end, this, entryPoint ? nonTerminal : -1);

		startStates[nonTerminal] = start.id;
	}

	protected void addChoicePoint(int choicePoint, ChoicePoint expansion) {
		startStates[choicePoint] = expansion.choice().id;
	}

	void addNonTerminalEndState(int symbol, GrammarState end) {
		IntSet useEndStates = perNonTerminalUseEndStates[symbol];
		if (useEndStates == null) {
			useEndStates = Collections.intSet();
			perNonTerminalUseEndStates[symbol] = useEndStates;
		}
		useEndStates.add(end.id);
	}

	void addNonTerminalEntryPointEndState(int entryPoint, int symbol, GrammarState end) {
		IntSet useEndStates = perEntryPointNonTerminalUseEndState.get(entryPoint, symbol);
		if (useEndStates == null) {
			useEndStates = Collections.intSet();
			perEntryPointNonTerminalUseEndState.put(entryPoint, symbol, useEndStates);
		}
		useEndStates.add(end.id);
	}

	public GrammarState getState(int stateId) {
		return states[stateId];
	}

	public int getStartState(int nonTerminal) {
		return startStates[nonTerminal];
	}

	public IntSet getUseEndStates(int nonTerminal) {
		return perNonTerminalUseEndStates[nonTerminal];
	}

	public IntSet getEntryPointUseEndStates(int entryPoint, int nonTerminal) {
		return perEntryPointNonTerminalUseEndState.get(entryPoint, nonTerminal);
	}
}
