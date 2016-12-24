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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Didier Villevalois
 */
public class GrammarState {

	public final int id;
	public final String name;

	public final int nonTerminalEnd;

	public final int[] choiceTransitions = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,};

	public int nonTerminalTransition = -1;
	public int nonTerminalTransitionEnd;

	public int terminalTransition = -1;
	public int terminalTransitionEnd;

	public GrammarState(int id, String name) {
		this(id, name, -1);
	}

	public GrammarState(int id, String name, int nonTerminalEnd) {
		this.id = id;
		this.name = name;
		this.nonTerminalEnd = nonTerminalEnd;
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
			throw new IllegalStateException("Already defined non-terminal " + symbol + " transition for state " + name);
		nonTerminalTransition = symbol;
		nonTerminalTransitionEnd = target.id;
	}

	void setTerminal(int tokenType, GrammarState target) {
		if (terminalTransition != -1)
			throw new IllegalStateException("Already defined terminal " + tokenType + " transition for state " + name);
		terminalTransition = tokenType;
		terminalTransitionEnd = target.id;
	}

	public int match(Token token) {
		return terminalTransition == token.kind ? terminalTransitionEnd : -1;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("(" + name + ")");
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
}
