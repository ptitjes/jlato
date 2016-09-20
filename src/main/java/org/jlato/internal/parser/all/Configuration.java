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

import org.jlato.internal.parser.all.Grammar.GrammarState;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Didier Villevalois
 */
public class Configuration {
	public final Prediction prediction;
	public final GrammarState state;
	public final CallStack callStack;

	public Configuration(Prediction prediction, GrammarState state, CallStack callStack) {
		this.prediction = prediction;
		this.state = state;
		this.callStack = callStack;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Configuration that = (Configuration) o;

		if (prediction != null ? !prediction.equals(that.prediction) : that.prediction != null) return false;
		if (state != null ? !state.equals(that.state) : that.state != null) return false;
		return callStack != null ? callStack.equals(that.callStack) : that.callStack == null;

	}

	@Override
	public int hashCode() {
		int result = prediction != null ? prediction.hashCode() : 0;
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (callStack != null ? callStack.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Configuration{" +
				"prediction=" + prediction +
				", state=" + state +
				", callStack=" + callStack +
				'}';
	}

	public static class CallStack {

		public static final CallStack EMPTY = new CallStack(null, null);

		public final GrammarState state;
		public final CallStack parent;

		public CallStack(GrammarState state, CallStack parent) {
			this.state = state;
			this.parent = parent;
		}

		public CallStack push(GrammarState state) {
			return new CallStack(state, this);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			CallStack callStack = (CallStack) o;

			if (state != null ? !state.equals(callStack.state) : callStack.state != null) return false;
			return parent != null ? parent.equals(callStack.parent) : callStack.parent == null;

		}

		@Override
		public int hashCode() {
			int result = state != null ? state.hashCode() : 0;
			result = 31 * result + (parent != null ? parent.hashCode() : 0);
			return result;
		}

		@Override
		public String toString() {
			return "" + state + (parent == EMPTY ? "" : ":" + parent);
		}
	}

	public static class Prediction {

		public static final Prediction NIL = new Prediction(null, -1);

		public final int prediction;
		public final Prediction parent;

		public Prediction(Prediction parent, int prediction) {
			this.prediction = prediction;
			this.parent = parent;
		}

		public Prediction append(int prediction) {
			return new Prediction(this, prediction);
		}

		public List<Integer> toList() {
			return toList(this, new ArrayList<Integer>());
		}

		private static List<Integer> toList(Prediction prediction, List<Integer> list) {
			if (prediction == NIL) return list;
			list.add(0, prediction.prediction);
			return toList(prediction.parent, list);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Prediction that = (Prediction) o;

			if (prediction != that.prediction) return false;
			return parent != null ? parent.equals(that.parent) : that.parent == null;
		}

		@Override
		public int hashCode() {
			int result = prediction;
			result = 31 * result + (parent != null ? parent.hashCode() : 0);
			return result;
		}

		@Override
		public String toString() {
			return (parent == NIL ? "" : "" + parent + ".") + prediction;
		}
	}
}