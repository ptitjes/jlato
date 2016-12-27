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

		private static final long UUID = 0xa199ceb7f6dbd9aaL;

		@Override
		public String encode(Grammar grammar) throws IOException {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			try {
				dos.writeLong(UUID);
				writeGrammar(grammar, dos);
			} finally {
				baos.close();
			}

			byte[] bytes = baos.toByteArray();

			int length = bytes.length;
			char[] chars = new char[length / 2 + length % 2];
			for (int i = 0; i < length; i += 2) {
				byte byte1 = bytes[i];
				byte byte2 = i + 1 < length ? bytes[i + 1] : 0;
				chars[i / 2] = (char) ((int) byte1 << 8 | (int) byte2 & 0xff);
			}

			return new String(chars);
		}

		@Override
		public Grammar decode(String data) throws IOException {
			char[] chars = data.toCharArray();

			int length = chars.length * 2;
			byte[] bytes = new byte[length];
			for (int i = 0; i < length; i += 2) {
				int c = chars[i / 2];
				bytes[i] = (byte) (c >>> 8);
				bytes[i + 1] = (byte) (c & 0xff);
			}

			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
			try {
				if (UUID != dis.readLong()) throw new IOException("Invalid grammar format version");
				return readGrammar(dis);
			} finally {
				dis.close();
			}
		}

		private void writeGrammar(Grammar grammar, DataOutputStream out) throws IOException {
			int stateCount = grammar.states.length;
			int nonTerminalCount = grammar.nonTerminalStartStates.length;
			int choicePointCount = grammar.choicePointStates.length;
			int entryPointCount = grammar.entryPointNonTerminalUse.length;

			out.writeShort(stateCount);
			out.writeShort(nonTerminalCount);
			out.writeShort(choicePointCount);
			out.writeShort(entryPointCount);

			for (int i = 0; i < stateCount; i++) {
				writeGrammarState(grammar.states[i], out);
			}

			for (int i = 0; i < nonTerminalCount; i++) {
				out.writeShort(grammar.nonTerminalStartStates[i]);
			}

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

			for (int i = 0; i < entryPointCount; i++) {
				out.writeShort(grammar.entryPointNonTerminalUse[i]);
				out.writeShort(grammar.entryPointNonTerminalUseEndState[i]);
			}
		}

		private void writeGrammarState(GrammarState state, DataOutputStream out) throws IOException {
			out.writeShort(state.id);
			out.writeShort(state.nonTerminalEnd);

			int choiceCount = state.choiceTransitions.length;
			out.writeShort(choiceCount);
			for (int i = 0; i < choiceCount; i++) {
				out.writeShort(state.choiceTransitions[i]);
			}

			out.writeShort(state.nonTerminalTransition);
			out.writeShort(state.nonTerminalTransitionEnd);
			out.writeShort(state.terminalTransition);
			out.writeShort(state.terminalTransitionEnd);
		}

		private Grammar readGrammar(DataInputStream in) throws IOException {
			Grammar grammar = new Grammar();

			int stateCount = in.readShort();
			int nonTerminalCount = in.readShort();
			int choicePointCount = in.readShort();
			int entryPointCount = in.readShort();

			grammar.states = new GrammarState[stateCount];
			grammar.nonTerminalStartStates = new short[nonTerminalCount];
			grammar.choicePointStates = new short[choicePointCount];
			grammar.nonTerminalUseEndStates = new short[nonTerminalCount][];
			grammar.entryPointNonTerminalUse = new short[entryPointCount];
			grammar.entryPointNonTerminalUseEndState = new short[entryPointCount];

			for (int i = 0; i < stateCount; i++) {
				grammar.states[i] = readGrammarState(in);
			}

			for (int i = 0; i < nonTerminalCount; i++) {
				grammar.nonTerminalStartStates[i] = in.readShort();
			}

			for (int i = 0; i < choicePointCount; i++) {
				grammar.choicePointStates[i] = in.readShort();
			}

			for (int i = 0; i < nonTerminalCount; i++) {
				int useCount = in.readShort();
				grammar.nonTerminalUseEndStates[i] = new short[useCount];
				for (int j = 0; j < useCount; j++) {
					grammar.nonTerminalUseEndStates[i][j] = in.readShort();
				}
			}

			for (int i = 0; i < entryPointCount; i++) {
				grammar.entryPointNonTerminalUse[i] = in.readShort();
				grammar.entryPointNonTerminalUseEndState[i] = in.readShort();
			}

			return grammar;
		}

		private GrammarState readGrammarState(DataInputStream in) throws IOException {
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
