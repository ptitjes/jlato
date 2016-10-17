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

import com.github.andrewoma.dexx.collection.HashSet;
import com.github.andrewoma.dexx.collection.Set;

/**
 * @author Didier Villevalois
 */
public class CallStack {

	public static final CallStack WILDCARD = new CallStack(0);
	public static final CallStack EMPTY = new CallStack(1);

	private final int hashCode;
	public final GrammarState head;
	public final Set<CallStack> tails;

	private CallStack(int hashCode) {
		this.hashCode = hashCode;
		this.head = null;
		this.tails = null;
	}

	public CallStack(GrammarState head, Set<CallStack> tails) {
		this.hashCode = computeHashCode(head, tails);
		this.head = head;
		this.tails = tails;
	}

	public CallStack push(GrammarState state) {
		return new CallStack(state, emptyTails().add(this));
	}

	private static Set<CallStack> emptyTails() {
		return HashSet.empty();
	}

	public void pop(CallStackReader reader) {
		if (this == WILDCARD || this == EMPTY) return;
		for (CallStack tail : tails) {
			reader.handleNext(head, tail);
		}
	}

	public interface CallStackReader {
		public void handleNext(GrammarState head, CallStack tail);
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CallStack other = (CallStack) o;

		if (this == WILDCARD) return other == WILDCARD;
		if (this == EMPTY) return other == EMPTY;
		return head.equals(other.head) && tails.equals(other.tails);
	}

	private static int computeHashCode(GrammarState head, Set<CallStack> tails) {
		int result = 3;
		result = 31 * result + head.hashCode();
		result = 31 * result + tails.hashCode();
		return result;
	}

	@Override
	public String toString() {
		if (this == WILDCARD) return "#";
		if (this == EMPTY) return "[]";
		return head + ":" + tails.makeString(",", "{", "}", -1, null);
	}
}
