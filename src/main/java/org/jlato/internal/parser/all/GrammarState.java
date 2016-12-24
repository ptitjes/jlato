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
	public final boolean end;
	public final int nonTerminal;
	public final Map<Integer, GrammarState> choiceTransitions = new HashMap<Integer, GrammarState>();
	public final Map<Integer, GrammarState> nonTerminalTransitions = new HashMap<Integer, GrammarState>();
	public final Map<Integer, GrammarState> terminalTransitions = new HashMap<Integer, GrammarState>();

	public GrammarState(int id, String name) {
		this.id = id;
		this.name = name;
		this.nonTerminal = -1;
		this.end = false;
	}

	public GrammarState(int id, String name, int nonTerminal) {
		this.id = id;
		this.name = name;
		this.nonTerminal = nonTerminal;
		this.end = true;
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
		choiceTransitions.put(index, target);
	}

	void setNonTerminal(int symbol, GrammarState target) {
		if (nonTerminalTransitions.containsKey(symbol))
			throw new IllegalStateException("Already defined non-terminal " + symbol + " transition for state " + name);
		nonTerminalTransitions.put(symbol, target);
	}

	void setTerminal(int tokenType, GrammarState target) {
		if (terminalTransitions.containsKey(tokenType))
			throw new IllegalStateException("Already defined terminal " + tokenType + " transition for state " + name);
		terminalTransitions.put(tokenType, target);
	}

	public GrammarState match(Token token) {
		return terminalTransitions.get(token.kind);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("(" + name + ")");
		if (!choiceTransitions.isEmpty()) {
			builder.append(" ");
			for (Map.Entry<Integer, GrammarState> entry : choiceTransitions.entrySet()) {
				builder.append("[c:" + entry.getKey() + "->" + entry.getValue().name + "]");
			}
		}
		if (!terminalTransitions.isEmpty()) {
			builder.append(" ");
			for (Map.Entry<Integer, GrammarState> entry : terminalTransitions.entrySet()) {
				builder.append("[tok:" + TokenType.tokenImage[entry.getKey()] + "->" + entry.getValue().name + "]");
			}
		}
		if (!nonTerminalTransitions.isEmpty()) {
			builder.append(" ");
			for (Map.Entry<Integer, GrammarState> entry : nonTerminalTransitions.entrySet()) {
				builder.append("[nt:" + entry.getKey() + "->" + entry.getValue().name + "]");
			}
		}

		return builder.toString();
	}
}
