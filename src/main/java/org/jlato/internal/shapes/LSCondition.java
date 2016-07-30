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

package org.jlato.internal.shapes;

import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.tree.Kind;

/**
 * @author Didier Villevalois
 */
public abstract class LSCondition {
	public abstract boolean test(BUTree tree);

	public static LSCondition not(final LSCondition condition) {
		return new LSCondition() {
			@Override
			public boolean test(BUTree tree) {
				return !condition.test(tree);
			}
		};
	}

	public static <S extends STree> LSCondition data(final SProperty property) {
		return new LSCondition() {
			@Override
			public boolean test(BUTree tree) {
				return (Boolean) property.retrieve((S) tree.state);
			}
		};
	}

	public static LSCondition childIs(final STraversal traversal, final LSCondition condition) {
		return new LSCondition() {
			public boolean test(BUTree tree) {
				return condition.test(tree.traverse(traversal));
			}
		};
	}

	public static LSCondition childHas(STraversal traversal, LSCondition condition) {
		return childIs(traversal, condition);
	}

	public static LSCondition withKind(final Kind kind) {
		return new LSCondition() {
			public boolean test(BUTree tree) {
				return ((SNode) tree.state).kind() == kind;
			}
		};
	}

	public static LSCondition empty() {
		return new LSCondition() {
			@Override
			public boolean test(BUTree tree) {
				if (tree instanceof BUTreeVar) return false;

				final SNodeList state = (SNodeList) tree.state;
				final Vector<BUTree<?>> children = state.children;
				return children == null || children.isEmpty();
			}
		};
	}

	public static LSCondition some() {
		return new LSCondition() {
			@Override
			public boolean test(BUTree tree) {
				final SNodeOption state = (SNodeOption) tree.state;
				final BUTree element = state.element;
				return element != null;
			}
		};
	}
}
