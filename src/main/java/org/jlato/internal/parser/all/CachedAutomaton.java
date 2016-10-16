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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Didier Villevalois
 */
public class CachedAutomaton {

	public final PredictionState initialState;
	private final Map<PredictionState, PredictionState> states = new HashMap<PredictionState, PredictionState>();

	public CachedAutomaton(PredictionState initialState) {
		this.initialState = initialState;
	}

	public PredictionState addState(PredictionState newState) {
		PredictionState state = this.states.get(newState);
		if (state == null) {
			state = newState;
			this.states.put(state, state);
		}
		return state;
	}

	public static void printToString(StringBuilder builder, PredictionState state, int indent) {
		indent(builder, indent);
		builder.append("(" + state.hashCode() + (state.prediction == -1 ? "" : " - " + state.prediction) + ")");
		if (!state.transitions.isEmpty()) {
			builder.append(" \n");
			for (Map.Entry<Integer, PredictionState> entry : state.transitions.entrySet()) {
				indent(builder, indent + 1);
				builder.append("[tok:" + entry.getKey() + "->\n");
				printToString(builder, entry.getValue(), indent + 2);
				builder.append("\n");
				indent(builder, indent + 1);
				builder.append("]\n");
			}
		}
		indent(builder, indent);
		builder.append("");
	}

	public static void indent(StringBuilder builder, int indent) {
		for (int i = 0; i < indent; i++) {
			builder.append("  ");
		}
	}

	@Override
	public String toString() {
		return "CachedAutomaton{" +
				"initialState=" + initialState +
				'}';
	}
}
