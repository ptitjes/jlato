package org.jlato.internal.td;

import org.jlato.internal.bu.SNode;
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
			SNode node = (SNode) parent.tree;
			SNode newNode = node.withState(node.state.withChild(index, content));
			return parent.withTree(newNode);
		}
	}
}
