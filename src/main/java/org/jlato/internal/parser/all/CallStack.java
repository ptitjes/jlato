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

import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.HashSet;
import com.github.andrewoma.dexx.collection.Set;
import com.github.andrewoma.dexx.collection.Sets;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Didier Villevalois
 */
public class CallStack {

	public static final CallStack WILDCARD = new Root(Kind.WILDCARD);
	public static final CallStack EMPTY = new Root(Kind.EMPTY);

	public enum Kind {
		WILDCARD, EMPTY, MERGE, PUSH
	}

	private final Kind kind;
	private final int hashCode;

	private CallStack(Kind kind, int hashCode) {
		this.kind = kind;
		this.hashCode = hashCode;
	}

	public CallStack push(GrammarState state) {
		return new Push(state, this);
	}

	public void pop(CallStackReader reader) {
		if (this.kind == Kind.WILDCARD || this.kind == Kind.EMPTY) return;
		if (this.kind == Kind.PUSH) {
			Push push = (Push) this;
			reader.handleNext(push.head, push.tails);
		} else {
			Merge merge = (Merge) this;
			// Stacks in a merge are Push stacks by construction
			for (CallStack pushStack : merge.stacks) {
				Push push = (Push) pushStack;
				reader.handleNext(push.head, push.tails);
			}
		}
	}

	public CallStack merge(CallStack other) {
		Kind thisKind = this.kind;
		Kind otherKind = other.kind;

		if (thisKind == Kind.WILDCARD || otherKind == Kind.WILDCARD) return WILDCARD;
		if (thisKind == Kind.EMPTY) {
			if (otherKind == Kind.EMPTY) return EMPTY;
			if (otherKind == Kind.PUSH) return new Merge(Sets.of(EMPTY, other));
			if (otherKind == Kind.MERGE) return new Merge(((Merge) other).stacks.add(EMPTY));
		}
		if (otherKind == Kind.EMPTY) {
			if (thisKind == Kind.PUSH) return new Merge(Sets.of(EMPTY, this));
			if (thisKind == Kind.MERGE) return new Merge(((Merge) this).stacks.add(EMPTY));
		}

		if (this.hashCode == other.hashCode && this.equals(other)) return this;

		// The following is to avoid merging deeply for simple cases

		if (thisKind == Kind.PUSH) {
			if (otherKind == Kind.PUSH) return new Merge(Sets.of(this, other));
			if (otherKind == Kind.MERGE) return new Merge(((Merge) other).stacks.add(this));
		}
		if (thisKind == Kind.MERGE) {
			if (otherKind == Kind.PUSH) return new Merge(((Merge) this).stacks.add(other));
			if (otherKind == Kind.MERGE) {
				Set<CallStack> stacks = ((Merge) this).stacks;
				for (CallStack stack : ((Merge) other).stacks) {
					stacks = stacks.add(stack);
				}
				return new Merge(stacks);
			}
		}

		// The deep merge part
		// THIS SEEMS TO NEVER BE REACHED !!!
		// thisKind == Kind.MERGE && otherKind == Kind.MERGE

		System.out.println("Yoooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");

		// Implement MERGE-MERGE by set copy/addAll

		Map<GrammarState, CallStack> perHeadTails = new HashMap<GrammarState, CallStack>();
		merge(thisKind, this, perHeadTails);
		merge(otherKind, other, perHeadTails);

		if (perHeadTails.size() == 1) {
			Map.Entry<GrammarState, CallStack> entry = perHeadTails.entrySet().iterator().next();
			return new Push(entry.getKey(), entry.getValue());
		} else {
			Builder<CallStack, Set<CallStack>> builder = Sets.builder();
			for (Map.Entry<GrammarState, CallStack> entry : perHeadTails.entrySet()) {
				builder.add(new Push(entry.getKey(), entry.getValue()));
			}
			return new Merge(builder.build());
		}
	}

	private void merge(Kind kind, CallStack stacks, Map<GrammarState, CallStack> perHead) {
		if (kind == Kind.PUSH) {
			Push push = (Push) stacks;
			merge(push.head, push.tails, perHead);
		} else {
			Merge merge = (Merge) stacks;
			// Stacks in a merge are Push stacks by construction
			for (CallStack pushStack : merge.stacks) {
				Push push = (Push) pushStack;
				merge(push.head, push.tails, perHead);
			}
		}
	}

	private void merge(GrammarState head, CallStack tails, Map<GrammarState, CallStack> perHeadTails) {
		perHeadTails.put(head, perHeadTails.containsKey(head) ? perHeadTails.get(head).merge(tails) : tails);
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	private static class Root extends CallStack {

		private Root(Kind kind) {
			super(kind, kind.hashCode());
		}

		@Override
		public boolean equals(Object o) {
			return this == o;
		}

		@Override
		public String toString() {
			return this == WILDCARD ? "#" : "[]";
		}
	}

	private static class Push extends CallStack {

		private final GrammarState head;
		private final CallStack tails;

		private Push(GrammarState head, CallStack tails) {
			super(Kind.PUSH, computeHashCode(head, tails));
			this.head = head;
			this.tails = tails;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Push other = (Push) o;

			return head.equals(other.head) && tails.equals(other.tails);
		}

		private static int computeHashCode(GrammarState head, CallStack tails) {
			int result = 3 + 31 * Kind.PUSH.hashCode();
			result = 31 * result + head.hashCode();
			result = 31 * result + tails.hashCode();
			return result;
		}

		@Override
		public String toString() {
			return "(" + head.location.name + "):" + tails.toString();
		}
	}

	private static class Merge extends CallStack {

		private final Set<CallStack> stacks;

		private Merge(Set<CallStack> stacks) {
			super(Kind.MERGE, computeHashCode(stacks));
			this.stacks = stacks;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Merge other = (Merge) o;

			return stacks.equals(other.stacks);
		}

		private static int computeHashCode(Set<CallStack> stacks) {
			int result = 3 + 31 * Kind.MERGE.hashCode();
			result = 31 * result + stacks.hashCode();
			return result;
		}

		@Override
		public String toString() {
			return stacks.makeString(",", "{", "}", -1, "");
		}
	}
}
