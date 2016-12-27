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

import java.io.*;

/**
 * @author Didier Villevalois
 */
public class Grammar implements Externalizable {

	private int stateCount = 0;
	GrammarState[] states;

	short[] nonTerminalStartStates;
	short[] choicePointStates;

	short[][] nonTerminalUseEndStates;

	short[] entryPointNonTerminalUse;
	short[] entryPointNonTerminalUseEndState;

	short[] nonTerminalUseCount;

	public Grammar(int stateCount, int constantCount) {
		states = new GrammarState[stateCount];
		nonTerminalStartStates = new short[constantCount];
		choicePointStates = new short[constantCount];
		nonTerminalUseEndStates = new short[constantCount][50];
		entryPointNonTerminalUse = new short[constantCount];
		entryPointNonTerminalUseEndState = new short[constantCount];

		nonTerminalUseCount = new short[constantCount];
		initializeProductions();

		for (int i = 0; i < nonTerminalUseEndStates.length; i++) {
			short[] tmp = new short[nonTerminalUseCount[i]];
			System.arraycopy(nonTerminalUseEndStates[i], 0, tmp, 0, nonTerminalUseCount[i]);
			nonTerminalUseEndStates[i] = tmp;
		}
	}

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
		stateCount = states.length;
	}

	protected void initializeProductions() {
	}

	private int newStateId() {
		return stateCount++;
	}

	GrammarState newGrammarState(Expansion expansion) {
		return newGrammarState(expansion, -1);
	}

	private GrammarState newGrammarState(Expansion expansion, int nonTerminal) {
		GrammarState state = new GrammarState(newStateId(), nonTerminal);
		states[state.id] = state;
		return state;
	}

	protected void addProduction(int nonTerminal, Expansion expansion, boolean entryPoint) {
		GrammarState start = newGrammarState(expansion);
		GrammarState end = newGrammarState(expansion, nonTerminal);
		expansion.initializeStates(start, end, this, entryPoint ? nonTerminal : -1);

		nonTerminalStartStates[nonTerminal] = start.id;
	}

	protected void addChoicePoint(int choicePoint, ChoicePoint expansion) {
		choicePointStates[choicePoint] = expansion.choice().id;
	}

	void addNonTerminalEndState(int symbol, GrammarState end) {
		nonTerminalUseEndStates[symbol][nonTerminalUseCount[symbol]++] = end.id;
	}

	void addNonTerminalEntryPointEndState(int entryPoint, int symbol, GrammarState end) {
		if (symbol == 4 /*Epilog*/) return;
		entryPointNonTerminalUse[entryPoint] = (short) symbol;
		entryPointNonTerminalUseEndState[entryPoint] = end.id;
	}

	public GrammarState getState(int stateId) {
		return states[stateId];
	}

	public int getNonTerminalStartState(int nonTerminal) {
		return nonTerminalStartStates[nonTerminal];
	}

	public int getChoicePointState(int nonTerminal) {
		return choicePointStates[nonTerminal];
	}

	public short[] getUseEndStates(int nonTerminal) {
		return nonTerminalUseEndStates[nonTerminal];
	}

	public short getEntryPointUseEndStates(int entryPoint, int nonTerminal) {
		if (entryPointNonTerminalUse[entryPoint] != nonTerminal) return -1;
		return entryPointNonTerminalUseEndState[entryPoint];
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeShort(stateCount);
		for (int i = 0; i < stateCount; i++) {
			out.writeObject(states[i]);
		}

		int nonTerminalCount = nonTerminalStartStates.length;
		out.writeShort(nonTerminalCount);
		for (int i = 0; i < nonTerminalCount; i++) {
			out.writeShort(nonTerminalStartStates[i]);
		}

		int choicePointCount = choicePointStates.length;
		out.writeShort(choicePointCount);
		for (int i = 0; i < choicePointCount; i++) {
			out.writeShort(choicePointStates[i]);
		}

		for (int i = 0; i < nonTerminalCount; i++) {
			int useCount = nonTerminalUseEndStates[i].length;
			out.writeShort(useCount);
			for (int j = 0; j < useCount; j++) {
				out.writeShort(nonTerminalUseEndStates[i][j]);
			}
		}

		int entryPointCount = entryPointNonTerminalUse.length;
		out.writeShort(entryPointCount);
		for (int i = 0; i < entryPointCount; i++) {
			out.writeShort(entryPointNonTerminalUse[i]);
			out.writeShort(entryPointNonTerminalUseEndState[i]);
		}
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		stateCount = in.readShort();
		states = new GrammarState[stateCount];
		for (int i = 0; i < stateCount; i++) {
			states[i] = (GrammarState) in.readObject();
		}

		short nonTerminalCount = in.readShort();
		nonTerminalStartStates = new short[nonTerminalCount];
		for (int i = 0; i < nonTerminalCount; i++) {
			nonTerminalStartStates[i] = in.readShort();
		}

		short choicePointCount = in.readShort();
		choicePointStates = new short[choicePointCount];
		for (int i = 0; i < choicePointCount; i++) {
			choicePointStates[i] = in.readShort();
		}

		nonTerminalUseEndStates = new short[nonTerminalCount][];
		for (int i = 0; i < nonTerminalCount; i++) {
			int useCount = in.readShort();
			nonTerminalUseEndStates[i] = new short[useCount];
			for (int j = 0; j < useCount; j++) {
				nonTerminalUseEndStates[i][j] = in.readShort();
			}
		}

		int entryPointCount = in.readShort();
		entryPointNonTerminalUse = new short[entryPointCount];
		entryPointNonTerminalUseEndState = new short[entryPointCount];
		for (int i = 0; i < entryPointCount; i++) {
			entryPointNonTerminalUse[i] = in.readShort();
			entryPointNonTerminalUseEndState[i] = in.readShort();
		}
	}
}
