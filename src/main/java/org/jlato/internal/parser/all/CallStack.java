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

import com.github.andrewoma.dexx.collection.*;

import java.lang.Iterable;

/**
 * @author Didier Villevalois
 */
public abstract class CallStack {

	public static final CallStack WILDCARD = new Root(Kind.WILDCARD);
	public static final CallStack EMPTY = new Root(Kind.EMPTY);

	private final int hashCode;

	private CallStack(int hashCode) {
		this.hashCode = hashCode;
	}

	protected abstract Kind kind();

	@Override
	public int hashCode() {
		return hashCode;
	}

	public CallStack push(Grammar.GrammarState state) {
		return new Push(state, this);
	}

	public CallStack merge(CallStack other) {
		Kind kind = kind();
		Kind otherKind = other.kind();

		if (kind == Kind.WILDCARD || otherKind == Kind.WILDCARD) return WILDCARD;

		Builder<CallStack, Set<CallStack>> stacks = Sets.builder();
		if (kind == Kind.MERGE || otherKind == Kind.MERGE) {
			if (kind == Kind.MERGE) stacks.addAll((Iterable<CallStack>) ((Merge) this).stacks);
			else stacks.add(this);

			if (otherKind == Kind.MERGE) stacks.addAll((Iterable<CallStack>) ((Merge) other).stacks);
			else stacks.add(other);
		}
		return new Merge(stacks.build());
	}

	public void pop(CallStackReader reader) {
		switch (kind()) {
			case MERGE:
				Merge merge = (Merge) this;
				for (CallStack stack : merge.stacks) {
					stack.pop(reader);
				}
				break;
			case PUSH:
				Push push = (Push) this;
				reader.handleNext(push.state, push.parent);
				break;
			case WILDCARD:
			case EMPTY:
		}
	}

	public interface CallStackReader {
		public void handleNext(Grammar.GrammarState state, CallStack parent);
	}

	private enum Kind {
		WILDCARD, EMPTY, MERGE, PUSH
	}

	private static class Merge extends CallStack {

		public final Set<CallStack> stacks;

		public Merge(Set<CallStack> stacks) {
			super(computeHashCode(stacks));
			this.stacks = stacks;
		}

		@Override
		protected Kind kind() {
			return Kind.MERGE;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Merge other = (Merge) o;

			return stacks.equals(other.stacks);
		}

		private static int computeHashCode(Set<CallStack> stacks) {
			return stacks.hashCode();
		}

		@Override
		public String toString() {
			return stacks.toString();
		}
	}

	private static class Push extends CallStack {

		public final Grammar.GrammarState state;
		public final CallStack parent;

		public Push(Grammar.GrammarState state, CallStack parent) {
			super(computeHashCode(state, parent));
			this.state = state;
			this.parent = parent;
		}

		@Override
		protected Kind kind() {
			return Kind.PUSH;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Push other = (Push) o;

			return state.equals(other.state) && parent.equals(other.parent);

		}

		private static int computeHashCode(Grammar.GrammarState state, CallStack parent) {
			int result = state.hashCode();
			result = 31 * result + parent.hashCode();
			return result;
		}

		@Override
		public String toString() {
			return state + ":" + parent;
		}
	}

	private static class Root extends CallStack {

		public final Kind kind;

		public Root(Kind kind) {
			super(computeHashCode(kind));
			this.kind = kind;
		}

		@Override
		protected Kind kind() {
			return kind;
		}

		@Override
		public boolean equals(Object o) {
			return o == this;
		}

		private static int computeHashCode(Kind kind) {
			return kind.hashCode();
		}

		@Override
		public String toString() {
			if (kind == Kind.WILDCARD) return "#";
			if (kind == Kind.EMPTY) return "[]";
			throw new IllegalStateException();
		}
	}
}
