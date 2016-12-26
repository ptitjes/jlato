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

	public int id;

	public int nonTerminalEnd;

	public int[] choiceTransitions;

	public int nonTerminalTransition = -1;
	public int nonTerminalTransitionEnd;

	public int terminalTransition = -1;
	public int terminalTransitionEnd;

	public GrammarState(int id, int nonTerminalEnd) {
		this(id, nonTerminalEnd, new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,}, -1, -1, -1, -1);
	}

	public GrammarState() {
	}

	public GrammarState(int id, int nonTerminalEnd, int[] choiceTransitions,
	                    int nonTerminalTransition, int nonTerminalTransitionEnd,
	                    int terminalTransition, int terminalTransitionEnd) {
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
		nonTerminalTransition = symbol;
		nonTerminalTransitionEnd = target.id;
	}

	void setTerminal(int tokenType, GrammarState target) {
		if (terminalTransition != -1)
			throw new IllegalStateException("Already defined terminal " + tokenType);
		terminalTransition = tokenType;
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
			int target = choiceTransitions[choice];
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
		out.writeInt(id);
		out.writeInt(nonTerminalEnd);

		out.writeInt(choiceTransitions.length);
		for (int i = 0; i < choiceTransitions.length; i++) {
			out.writeInt(choiceTransitions[i]);
		}

		out.writeInt(nonTerminalTransition);
		out.writeInt(nonTerminalTransitionEnd);
		out.writeInt(terminalTransition);
		out.writeInt(terminalTransitionEnd);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		id = in.readInt();
		nonTerminalEnd = in.readInt();

		int choiceCount = in.readInt();
		choiceTransitions = new int[choiceCount];
		for (int i = 0; i < choiceCount; i++) {
			choiceTransitions[i] = in.readInt();
		}

		nonTerminalTransition = in.readInt();
		nonTerminalTransitionEnd = in.readInt();
		terminalTransition = in.readInt();
		terminalTransitionEnd = in.readInt();
	}
}
