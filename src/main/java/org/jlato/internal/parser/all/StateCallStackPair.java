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

/**
 * @author Didier Villevalois
 */
public class StateCallStackPair {

	public final int state;
	public final CallStack callStack;

	public StateCallStackPair(int state, CallStack callStack) {
		this.state = state;
		this.callStack = callStack;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StateCallStackPair that = (StateCallStackPair) o;

		if (state != that.state) return false;
		return callStack.equals(that.callStack);

	}

	@Override
	public int hashCode() {
		int result = state;
		result = 31 * result + callStack.hashCode();
		return result;
	}
}
