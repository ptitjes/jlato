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
import org.jlato.internal.parser.all.CachedAutomaton.CachedState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Didier Villevalois
 */
public class Predictor {

	private final Grammar grammar;

	private final Map<Integer, CachedAutomaton> cachedAutomata = new HashMap<Integer, CachedAutomaton>();

	public Predictor(Grammar grammar) {
		this.grammar = grammar;
	}

	public List<Integer> predict(int nonTerminal, Token[] tokens) {
		CachedAutomaton cachedAutomaton = cachedAutomata.get(nonTerminal);
		if (cachedAutomaton == null) {
			cachedAutomaton = new CachedAutomaton(grammar, nonTerminal);
			cachedAutomata.put(nonTerminal, cachedAutomaton);
		}
		CachedState currentState = cachedAutomaton.initialState;

		int index = 0;
		while (true) {
			if (currentState.configurations.isEmpty()) return null;
			if (currentState.prediction != null) {
//				debugState(currentState);
//				debugAutomaton(cachedAutomaton);
				return currentState.prediction;
			}
			currentState = currentState.match(tokens[index]);

			index++;
		}
	}

	private static void debugState(CachedState state) {
		for (Configuration configuration : state.configurations) {
			System.out.println(configuration);
		}
		System.out.println();
	}

	private void debugAutomaton(CachedAutomaton cachedAutomaton) {
		System.out.println(cachedAutomaton);
		System.out.println();
	}
}
