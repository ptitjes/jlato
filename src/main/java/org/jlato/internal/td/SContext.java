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
