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
import org.jlato.internal.parser.TokenType;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author Didier Villevalois
 */
public class GrammarState implements Externalizable {

	public short id;

	public short nonTerminalEnd = -1;

	public short[] choiceTransitions;

	public short nonTerminalTransition = -1;
	public short nonTerminalTransitionEnd;

	public short terminalTransition = -1;
	public short terminalTransitionEnd;

	public GrammarState(int id, int nonTerminalEnd) {
		this((short) id, (short) nonTerminalEnd, new short[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,}, (short) -1, (short) -1, (short) -1, (short) -1);
	}

	public GrammarState() {
	}

	public GrammarState(short id, short nonTerminalEnd, short[] choiceTransitions,
	                    short nonTerminalTransition, short nonTerminalTransitionEnd,
	                    short terminalTransition, short terminalTransitionEnd) {
		this.id = id;
		this.nonTerminalEnd = nonTerminalEnd;
		this.choiceTransitions = choiceTransitions;
		this.nonTerminalTransition = nonTerminalTransition;
		this.nonTerminalTransitionEnd = nonTerminalTransitionEnd;
		this.terminalTransition = terminalTransition;
		this.terminalTransitionEnd = terminalTransitionEnd;
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
		choiceTransitions[index] = target.id;
	}

	void setNonTerminal(int symbol, GrammarState target) {
		if (nonTerminalTransition != -1)
			throw new IllegalStateException("Already defined non-terminal " + symbol);
		nonTerminalTransition = (short) symbol;
		nonTerminalTransitionEnd = target.id;
	}

	void setTerminal(int tokenType, GrammarState target) {
		if (terminalTransition != -1)
			throw new IllegalStateException("Already defined terminal " + tokenType);
		terminalTransition = (short) tokenType;
		terminalTransitionEnd = target.id;
	}

	public int match(Token token) {
		return terminalTransition == token.kind ? terminalTransitionEnd : -1;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("(" + id + ")");
		builder.append(" ");
		for (int choice = 0; choice < choiceTransitions.length; choice++) {
			short target = choiceTransitions[choice];
			if (target != -1) builder.append("[c:" + choice + "->" + target + "]");
		}
		if (terminalTransition != -1) {
			builder.append(" ");
			builder.append("[tok:" + TokenType.tokenImage[terminalTransition] + "->" + terminalTransitionEnd + "]");
		}
		if (nonTerminalTransition != -1) {
			builder.append(" ");
			builder.append("[nt:" + nonTerminalTransition + "->" + nonTerminalTransitionEnd + "]");
		}

		return builder.toString();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeShort(id);
		out.writeShort(nonTerminalEnd);

		out.writeShort(choiceTransitions.length);
		for (int i = 0; i < choiceTransitions.length; i++) {
			out.writeShort(choiceTransitions[i]);
		}

		out.writeShort(nonTerminalTransition);
		out.writeShort(nonTerminalTransitionEnd);
		out.writeShort(terminalTransition);
		out.writeShort(terminalTransitionEnd);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		id = in.readShort();
		nonTerminalEnd = in.readShort();

		int choiceCount = in.readShort();
		choiceTransitions = new short[choiceCount];
		for (int i = 0; i < choiceCount; i++) {
			choiceTransitions[i] = in.readShort();
		}

		nonTerminalTransition = in.readShort();
		nonTerminalTransitionEnd = in.readShort();
		terminalTransition = in.readShort();
		terminalTransitionEnd = in.readShort();
	}
}
