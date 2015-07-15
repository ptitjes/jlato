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
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
abstract class TreePattern<T extends Tree> extends Pattern<T> {

	private final SKind<? extends STreeState<?>> kind;
	private final ArrayList<Pattern<?>> data;

	public TreePattern(SKind<? extends STreeState<?>> kind, ArrayList<Pattern<?>> data) {
		this.kind = kind;
		this.data = data;
	}

	@Override
	protected HashSet<Variable<?>> collectVariables(HashSet<Variable<?>> variables) {
		variables = collectVariables(this.data, variables);
		return variables;
	}

	@Override
	protected Substitution match(Object object, Substitution substitution) {
		if (!(object instanceof Tree)) return null;
		return matchTree(TreeBase.treeOf((Tree) object), substitution);
	}

	protected Substitution matchTree(STree tree, Substitution substitution) {
		if (!(tree.kind == kind)) return null;

		STreeState state = tree.state;
		for (int i = 0; i < data.size(); i++) {
			substitution = data.get(i).match(state.data(i), substitution);
			if (substitution == null) return null;
		}
		return substitution;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T build(Substitution substitution) {
		return (T) new SLocation(null, buildTree(substitution)).facade;
	}

	private STree buildTree(Substitution substitution) {
		return new STree(kind, buildState(substitution));
	}

	protected abstract STreeState buildState(Substitution substitution);

	protected ArrayList<Object> buildData(Substitution substitution) {
		Builder<Object, ArrayList<Object>> dataBuilder = ArrayList.<Object>factory().newBuilder();
		for (Pattern<?> term : data) {
			dataBuilder.add(term.build(substitution));
		}
		return dataBuilder.build();
	}
}
