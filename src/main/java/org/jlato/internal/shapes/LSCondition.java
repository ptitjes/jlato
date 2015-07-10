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

package org.jlato.internal.shapes;

import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public abstract class LSCondition {
	public abstract boolean test(STree tree);

	public static LSCondition not(final LSCondition condition) {
		return new LSCondition() {
			@Override
			public boolean test(STree tree) {
				return !condition.test(tree);
			}
		};
	}

	public static LSCondition data(final int index) {
		return new LSCondition() {
			@Override
			public boolean test(STree tree) {
				return (Boolean) tree.state.data(index);
			}
		};
	}

	public static LSCondition nonNullChild(final int index) {
		return new LSCondition() {
			public boolean test(STree tree) {
				final SNodeState state = ((SNode) tree).state();
				final STree child = state.child(index);
				return child != null;
			}
		};
	}

	public static LSCondition kind(final Tree.Kind kind) {
		return new LSCondition() {
			public boolean test(STree tree) {
				final Tree.Kind actualKind = tree.kind;
				return actualKind == kind;
			}
		};
	}

	public static LSCondition childKind(final int index, final Tree.Kind kind) {
		return new LSCondition() {
			public boolean test(STree tree) {
				final SNodeState state = ((SNode) tree).state();
				final Tree.Kind childKind = state.child(index).kind;
				return childKind == kind;
			}
		};
	}

	public static LSCondition lastChildKind(final Tree.Kind kind) {
		return new LSCondition() {
			public boolean test(STree tree) {
				final SNodeList nodeList = (SNodeList) tree;
				final Vector<STree> children = nodeList.state().children;
				final Tree.Kind childKind = children.last().kind;
				return childKind == kind;
			}
		};
	}

	public static LSCondition emptyList() {
		return new LSCondition() {
			@Override
			public boolean test(STree tree) {
				final SNodeList nodeList = (SNodeList) tree;
				final Vector<STree> children = nodeList.state().children;
				return children == null || children.isEmpty();
			}
		};
	}

	public static LSCondition emptyList(final int index) {
		return new LSCondition() {
			@Override
			public boolean test(STree tree) {
				final SNodeState state = ((SNode) tree).state();
				final SNodeList nodeList = (SNodeList) state.child(index);
				if (nodeList == null) return true;

				final Vector<STree> children = nodeList.state().children;
				return children == null || children.isEmpty();
			}
		};
	}
}
