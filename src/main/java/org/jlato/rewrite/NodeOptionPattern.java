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
import com.github.andrewoma.dexx.collection.HashSet;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
class NodeOptionPattern<T extends Tree> extends TreePattern<T> {

	private final Pattern<? extends Tree> elementPattern;

	public NodeOptionPattern(TreeBase.Kind kind, ArrayList<Pattern<?>> data, Pattern<? extends Tree> elementPattern) {
		super(kind, data);
		this.elementPattern = elementPattern;
	}

	@Override
	protected HashSet<Variable<?>> collectVariables(HashSet<Variable<?>> variables) {
		return collectVariables(elementPattern, variables);
	}

	@Override
	protected Substitution matchTree(STree tree, Substitution substitution) {
		substitution = super.matchTree(tree, substitution);
		if (substitution == null) return null;

		SNodeOptionState state = (SNodeOptionState) tree.state;
		STree element = state.element;
		substitution = elementPattern.match(element == null ? null : element.asTree(), substitution);
		if (substitution == null) return null;

		return substitution;
	}

	@Override
	protected STreeState buildState(Substitution substitution) {
		STree element = Tree.treeOf(elementPattern.build(substitution));
		return new SNodeOptionState(element, buildData(substitution));
	}
}
