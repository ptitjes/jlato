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

import java.io.*;

/**
 * @author Didier Villevalois
 */
public class GrammarSerialization {

	public interface Format {

		String encode(Grammar grammar) throws IOException;

		Grammar decode(String data) throws IOException;
	}

	public static final Format VERSION_1 = new Format() {

		@Override
		public String encode(Grammar grammar) throws IOException {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			try {
				writeGrammar(grammar, oos);
			} finally {
				baos.close();
			}

			byte[] bytes = baos.toByteArray();

			int length = bytes.length / 2 + (bytes.length % 2);
			char[] chars = new char[length];
			for (int i = 0; i < length; i++) {
				int j = i * 2;
				byte byte1 = bytes[j];
				byte byte2 = j + 1 < bytes.length ? bytes[j + 1] : 0;
				chars[i] = (char) (((int) byte1 << 8 | (int) byte2 & 0xff) + 2 & 0xFFFF);
			}

			return new String(chars);
		}

		@Override
		public Grammar decode(String data) throws IOException {
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
			try {
				Grammar grammar = readGrammar(ois);
				return grammar;
			} finally {
				ois.close();
			}
		}

		private void writeGrammar(Grammar grammar, ObjectOutputStream out) throws IOException {

			int stateCount = grammar.states.length;
			out.writeShort(stateCount);
			for (int i = 0; i < stateCount; i++) {
				writeGrammarState(grammar.states[i], out);
			}

			int nonTerminalCount = grammar.nonTerminalStartStates.length;
			out.writeShort(nonTerminalCount);
			for (int i = 0; i < nonTerminalCount; i++) {
				out.writeShort(grammar.nonTerminalStartStates[i]);
			}

			int choicePointCount = grammar.choicePointStates.length;
			out.writeShort(choicePointCount);
			for (int i = 0; i < choicePointCount; i++) {
				out.writeShort(grammar.choicePointStates[i]);
			}

			for (int i = 0; i < nonTerminalCount; i++) {
				int useCount = grammar.nonTerminalUseEndStates[i].length;
				out.writeShort(useCount);
				for (int j = 0; j < useCount; j++) {
					out.writeShort(grammar.nonTerminalUseEndStates[i][j]);
				}
			}

			int entryPointCount = grammar.entryPointNonTerminalUse.length;
			out.writeShort(entryPointCount);
			for (int i = 0; i < entryPointCount; i++) {
				out.writeShort(grammar.entryPointNonTerminalUse[i]);
				out.writeShort(grammar.entryPointNonTerminalUseEndState[i]);
			}
		}

		private void writeGrammarState(GrammarState state, ObjectOutput out) throws IOException {
			out.writeShort(state.id);
			out.writeShort(state.nonTerminalEnd);

			out.writeShort(state.choiceTransitions.length);
			for (int i = 0; i < state.choiceTransitions.length; i++) {
				out.writeShort(state.choiceTransitions[i]);
			}

			out.writeShort(state.nonTerminalTransition);
			out.writeShort(state.nonTerminalTransitionEnd);
			out.writeShort(state.terminalTransition);
			out.writeShort(state.terminalTransitionEnd);
		}

		private Grammar readGrammar(ObjectInput in) throws IOException {
			Grammar grammar = new Grammar();
			int stateCount = in.readShort();
			grammar.states = new GrammarState[stateCount];
			for (int i = 0; i < stateCount; i++) {
				grammar.states[i] = readGrammarState(in);
			}

			int nonTerminalCount = in.readShort();
			grammar.nonTerminalStartStates = new short[nonTerminalCount];
			for (int i = 0; i < nonTerminalCount; i++) {
				grammar.nonTerminalStartStates[i] = in.readShort();
			}

			int choicePointCount = in.readShort();
			grammar.choicePointStates = new short[choicePointCount];
			for (int i = 0; i < choicePointCount; i++) {
				grammar.choicePointStates[i] = in.readShort();
			}

			grammar.nonTerminalUseEndStates = new short[nonTerminalCount][];
			for (int i = 0; i < nonTerminalCount; i++) {
				int useCount = in.readShort();
				grammar.nonTerminalUseEndStates[i] = new short[useCount];
				for (int j = 0; j < useCount; j++) {
					grammar.nonTerminalUseEndStates[i][j] = in.readShort();
				}
			}

			int entryPointCount = in.readShort();
			grammar.entryPointNonTerminalUse = new short[entryPointCount];
			grammar.entryPointNonTerminalUseEndState = new short[entryPointCount];
			for (int i = 0; i < entryPointCount; i++) {
				grammar.entryPointNonTerminalUse[i] = in.readShort();
				grammar.entryPointNonTerminalUseEndState[i] = in.readShort();
			}

			return grammar;
		}

		private GrammarState readGrammarState(ObjectInput in) throws IOException {
			GrammarState state = new GrammarState();
			state.id = in.readShort();
			state.nonTerminalEnd = in.readShort();

			int choiceCount = in.readShort();
			state.choiceTransitions = new short[choiceCount];
			for (int i = 0; i < choiceCount; i++) {
				state.choiceTransitions[i] = in.readShort();
			}

			state.nonTerminalTransition = in.readShort();
			state.nonTerminalTransitionEnd = in.readShort();
			state.terminalTransition = in.readShort();
			state.terminalTransitionEnd = in.readShort();

			return state;
		}
	};
}
