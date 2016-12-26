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
import org.jlato.internal.parser.util.Collections;
import org.jlato.internal.parser.util.IntSet;

import java.io.*;

/**
 * @author Didier Villevalois
 */
public class Grammar implements Externalizable {

	public static String encode(Grammar buildGrammar) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(buildGrammar);
		oos.close();
		byte[] bytes = baos.toByteArray();

		int length = bytes.length / 2;
		char[] chars = new char[length];
		for (int i = 0; i < length; i++) {
			int j = i * 2;
			chars[i] = (char) (((int) bytes[j] << 8 | (int) bytes[j + 1] & 0xff) + 2 & 0xFFFF);
		}

		return new String(chars);
	}

	public static Grammar decode(String data) throws IOException, ClassNotFoundException {
		char[] chars = data.toCharArray();

		int length = chars.length;
		byte[] bytes = new byte[length * 2];
		for (int i = 0; i < length; i++) {
			int j = i * 2;
			int c = chars[i] - 2;
			bytes[j] = (byte) (c >>> 8);
			bytes[j + 1] = (byte) (c & 0xff);
		}

		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
		return (Grammar) ois.readObject();
	}

	private int stateCount = 0;
	private GrammarState[] states;
	private int[] nonTerminalStartStates;
	private int[] choicePointStates;
	private IntSet[] perNonTerminalUseEndStates;
	private IntSet[][] perEntryPointNonTerminalUseEndState;

	public Grammar(int stateCount, int constantCount) {
		states = new GrammarState[stateCount];
		nonTerminalStartStates = new int[constantCount];
		choicePointStates = new int[constantCount];
		perNonTerminalUseEndStates = new IntSet[constantCount];
		perEntryPointNonTerminalUseEndState = new IntSet[constantCount][constantCount];
		initializeProductions();
	}

	public Grammar() {
	}

	public Grammar(GrammarState[] states, int[] nonTerminalStartStates, int[] choicePointStates, IntSet[] perNonTerminalUseEndStates,
	               IntSet[][] perEntryPointNonTerminalUseEndState) {
		this.states = states;
		this.nonTerminalStartStates = nonTerminalStartStates;
		this.choicePointStates = choicePointStates;
		this.perNonTerminalUseEndStates = perNonTerminalUseEndStates;
		this.perEntryPointNonTerminalUseEndState = perEntryPointNonTerminalUseEndState;
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
		IntSet useEndStates = perNonTerminalUseEndStates[symbol];
		if (useEndStates == null) {
			useEndStates = Collections.intSet();
			perNonTerminalUseEndStates[symbol] = useEndStates;
		}
		useEndStates.add(end.id);
	}

	void addNonTerminalEntryPointEndState(int entryPoint, int symbol, GrammarState end) {
		IntSet useEndStates = perEntryPointNonTerminalUseEndState[entryPoint][symbol];
		if (useEndStates == null) {
			useEndStates = Collections.intSet();
			perEntryPointNonTerminalUseEndState[entryPoint][symbol] = useEndStates;
		}
		useEndStates.add(end.id);
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

	public IntSet getUseEndStates(int nonTerminal) {
		return perNonTerminalUseEndStates[nonTerminal];
	}

	public IntSet getEntryPointUseEndStates(int entryPoint, int nonTerminal) {
		return perEntryPointNonTerminalUseEndState[entryPoint][nonTerminal];
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(stateCount);
		for (int i = 0; i < stateCount; i++) {
			out.writeObject(states[i]);
		}

		int nonTerminalStateCount = nonTerminalStartStates.length;
		out.writeInt(nonTerminalStateCount);
		for (int i = 0; i < nonTerminalStateCount; i++) {
			out.writeInt(nonTerminalStartStates[i]);
		}

		int choicePointStateCount = choicePointStates.length;
		out.writeInt(choicePointStateCount);
		for (int i = 0; i < choicePointStateCount; i++) {
			out.writeInt(choicePointStates[i]);
		}

		out.writeObject(perNonTerminalUseEndStates);
		out.writeObject(perEntryPointNonTerminalUseEndState);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		stateCount = in.readInt();
		states = new GrammarState[stateCount];
		for (int i = 0; i < stateCount; i++) {
			states[i] = (GrammarState) in.readObject();
		}

		int nonTerminalStateCount = in.readInt();
		nonTerminalStartStates = new int[nonTerminalStateCount];
		for (int i = 0; i < nonTerminalStateCount; i++) {
			nonTerminalStartStates[i] = in.readInt();
		}

		int choicePointStateCount = in.readInt();
		choicePointStates = new int[choicePointStateCount];
		for (int i = 0; i < choicePointStateCount; i++) {
			choicePointStates[i] = in.readInt();
		}

		perNonTerminalUseEndStates = (IntSet[]) in.readObject();
		perEntryPointNonTerminalUseEndState = (IntSet[][]) in.readObject();
	}
}
