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
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
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
				final SNodeState state = (SNodeState) tree.state;
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
		return childHas(index, kind(kind));
	}

	public static LSCondition childIs(final int index, final LSCondition condition) {
		return new LSCondition() {
			public boolean test(STree tree) {
				final SNodeState state = (SNodeState) tree.state;
				final STree child = state.child(index);
				return condition.test(child);
			}
		};
	}

	public static LSCondition childHas(final int index, final LSCondition condition) {
		return childIs(index, condition);
	}

	public static LSCondition lastChildKind(final Tree.Kind kind) {
		return new LSCondition() {
			public boolean test(STree tree) {
				final SNodeListState state = (SNodeListState) tree.state;
				final Vector<STree> children = state.children;
				final Tree.Kind childKind = children.last().kind;
				return childKind == kind;
			}
		};
	}

	public static LSCondition emptyList() {
		return new LSCondition() {
			@Override
			public boolean test(STree tree) {
				final SNodeListState state = (SNodeListState) tree.state;
				final Vector<STree> children = state.children;
				return children == null || children.isEmpty();
			}
		};
	}

	public static LSCondition emptyList(final int index) {
		return new LSCondition() {
			@Override
			public boolean test(STree tree) {
				final SNodeState state = (SNodeState) tree.state;
				final STree nodeList = state.child(index);
				if (nodeList == null) return true;

				final SNodeListState nodeListState = (SNodeListState) nodeList.state;
				final Vector<STree> children = nodeListState.children;
				return children == null || children.isEmpty();
			}
		};
	}

	public static LSCondition some() {
		return new LSCondition() {
			@Override
			public boolean test(STree tree) {
				final SNodeOptionState state = (SNodeOptionState) tree.state;
				final STree element = state.element;
				return element != null;
			}
		};
	}
}
