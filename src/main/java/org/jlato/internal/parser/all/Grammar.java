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

/**
 * @author Didier Villevalois
 */
public class Grammar {

	GrammarState[] states;

	short[] nonTerminalStartStates;
	short[] choicePointStates;

	short[][] nonTerminalUseEndStates;

	short[] entryPointNonTerminalUse;
	short[] entryPointNonTerminalUseEndState;

	public Grammar() {
	}

	public Grammar(GrammarState[] states, short[] nonTerminalStartStates,
	               short[] choicePointStates, short[][] nonTerminalUseEndStates,
	               short[] entryPointNonTerminalUse, short[] entryPointNonTerminalUseEndState) {
		this.states = states;
		this.nonTerminalStartStates = nonTerminalStartStates;
		this.choicePointStates = choicePointStates;
		this.nonTerminalUseEndStates = nonTerminalUseEndStates;
		this.entryPointNonTerminalUse = entryPointNonTerminalUse;
		this.entryPointNonTerminalUseEndState = entryPointNonTerminalUseEndState;
	}

	public GrammarState getState(int stateId) {
		return states[stateId];
	}

	public short getNonTerminalStartState(int nonTerminal) {
		return nonTerminalStartStates[nonTerminal];
	}

	public short getChoicePointState(int nonTerminal) {
		return choicePointStates[nonTerminal];
	}

	public short[] getUseEndStates(int nonTerminal) {
		return nonTerminalUseEndStates[nonTerminal];
	}

	public short getEntryPointUseEndStates(int entryPoint, int nonTerminal) {
		if (entryPointNonTerminalUse[entryPoint] != nonTerminal) return -1;
		return entryPointNonTerminalUseEndState[entryPoint];
	}
}
