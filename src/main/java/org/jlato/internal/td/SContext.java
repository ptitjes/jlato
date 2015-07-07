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

package org.jlato.internal.td;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.tree.SLocation;

/**
 * @author Didier Villevalois
 */
public abstract class SContext {

	public abstract SLocation original();

	public abstract SLocation rebuiltWith(STree content);

	public static class Root extends SContext {

		@Override
		public SLocation original() {
			return null;
		}

		@Override
		public SLocation rebuiltWith(STree content) {
			return null;
		}
	}

	public static class Child extends SContext {

		private final SLocation parent;
		private final int index;

		public Child(SLocation parent, int index) {
			this.parent = parent;
			this.index = index;
		}

		@Override
		public SLocation original() {
			return parent;
		}

		@Override
		public SLocation rebuiltWith(STree content) {
			final SNode node = (SNode) parent.tree;
			final SNodeState state = (SNodeState) node.state;
			final SNode newNode = node.withState(state.withChild(index, content));
			return parent.withTree(newNode);
		}
	}
}
