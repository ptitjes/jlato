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
import org.jlato.tree.Kind;

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

	public static <S extends STreeState<S>> LSCondition data(final SProperty<S> property) {
		return new LSCondition() {
			@Override
			public boolean test(STree tree) {
				return (Boolean) property.retrieve((S) tree.state);
			}
		};
	}

	public static LSCondition nonNull() {
		return new LSCondition() {
			public boolean test(STree tree) {
				return tree != null;
			}
		};
	}

	public static LSCondition kind(final Kind kind) {
		return new LSCondition() {
			public boolean test(STree tree) {
				return ((SNodeState) tree.state).kind() == kind;
			}
		};
	}

	public static LSCondition childIs(final STraversal<? extends SNodeState> traversal, final LSCondition condition) {
		return new LSCondition() {
			public boolean test(STree tree) {
				return condition.test(tree.traverse(traversal));
			}
		};
	}

	public static LSCondition childHas(STraversal<? extends SNodeState> traversal, LSCondition condition) {
		return childIs(traversal, condition);
	}

	public static LSCondition lastChildKind(final Kind kind) {
		return new LSCondition() {
			public boolean test(STree tree) {
				final SNodeListState state = (SNodeListState) tree.state;
				final Vector<STree<?>> children = state.children;
				final Kind childKind = ((SNodeState) children.last().state).kind();
				return childKind == kind;
			}
		};
	}

	public static LSCondition emptyList() {
		return new LSCondition() {
			@Override
			public boolean test(STree tree) {
				final SNodeListState state = (SNodeListState) tree.state;
				final Vector<STree<?>> children = state.children;
				return children == null || children.isEmpty();
			}
		};
	}

	public static LSCondition elementIs(final LSCondition condition) {
		return new LSCondition() {
			public boolean test(STree tree) {
				final SNodeOptionState state = (SNodeOptionState) tree.state;
				final STree element = state.element;
				return condition.test(element);
			}
		};
	}

	public static LSCondition elementHas(final LSCondition condition) {
		return elementIs(condition);
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
