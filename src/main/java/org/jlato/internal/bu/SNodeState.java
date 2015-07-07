/*
 * Copyright (C) 2015 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.bu;

import com.github.andrewoma.dexx.collection.ArrayList;

/**
 * @author Didier Villevalois
 */
public class SNodeState extends STreeState {

	public final ArrayList<STree> children;

	public SNodeState(ArrayList<STree> children) {
		this(children, ArrayList.empty());
	}

	public SNodeState(ArrayList<STree> children, ArrayList<Object> data) {
		super(data);
		this.children = children;
	}

	public STree child(int index) {
		return children.get(index);
	}

	public SNodeState withChild(int index, STree value) {
		return new SNodeState(children.set(index, value), data);
	}

	public SNodeState withData(int index, Object value) {
		return new SNodeState(children, data.set(index, value));
	}
}
