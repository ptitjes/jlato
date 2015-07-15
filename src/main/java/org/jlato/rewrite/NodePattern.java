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

package org.jlato.rewrite;

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.HashSet;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
class NodePattern<T extends Tree> extends TreePattern<T> {

	private final ArrayList<Pattern<? extends Tree>> children;

	public NodePattern(TreeBase.Kind kind, ArrayList<Pattern<?>> data, ArrayList<Pattern<? extends Tree>> children) {
		super(kind, data);
		this.children = children;
	}

	@Override
	protected HashSet<Variable<?>> collectVariables(HashSet<Variable<?>> variables) {
		return collectVariables(children, variables);
	}

	@Override
	protected Substitution matchTree(STree tree, Substitution substitution) {
		substitution = super.matchTree(tree, substitution);
		if (substitution == null) return null;

		SNodeState state = (SNodeState) tree.state;
		for (int i = 0; i < children.size(); i++) {
			Pattern<? extends Tree> pattern = children.get(i);
			STree child = state.child(i);
			substitution = pattern.match(child == null ? null : child.asTree(), substitution);
			if (substitution == null) return null;
		}

		return substitution;
	}

	@Override
	protected STreeState buildState(Substitution substitution) {
		Builder<STree<?>, ArrayList<STree<?>>> childrenBuilder = ArrayList.<STree<?>>factory().newBuilder();
		for (Pattern<? extends Tree> term : children) {
			childrenBuilder.add(TreeBase.treeOf(term.build(substitution)));
		}
		return new SNodeState(childrenBuilder.build(), buildData(substitution));
	}
}
