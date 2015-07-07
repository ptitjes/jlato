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

package org.jlato.tree;

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.Builder;
import org.jlato.internal.bu.LRun;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;

/**
 * @author Didier Villevalois
 */
public abstract class Tree {

	protected final SLocation location;

	protected Tree(SLocation location) {
		this.location = location;
	}

	public Tree parent() {
		return location.parent();
	}

	public Tree root() {
		return location.root();
	}

	public static STree treeOf(Tree facade) {
		return facade == null ? null : facade.location.tree;
	}

	protected static ArrayList<STree> treesOf(Tree... facades) {
		final Builder<STree, ArrayList<STree>> builder = ArrayList.<STree>factory().newBuilder();
		for (Tree facade : facades) {
			builder.add(treeOf(facade));
		}
		return builder.build();
	}

	protected static ArrayList<Object> dataOf(Object... attributes) {
		final Builder<Object, ArrayList<Object>> builder = ArrayList.factory().newBuilder();
		for (Object attribute : attributes) {
			builder.add(attribute);
		}
		return builder.build();
	}

	protected static LRun runOf(Tree... facades) {
		final LRun.RunBuilder builder = LRun.RunBuilder.withVariableArity();
		for (Tree facade : facades) {
			builder.addTree(treeOf(facade));
		}
		return builder.build();
	}

	public interface Kind {

		Tree instantiate(SLocation location);

		LexicalShape shape();
	}
}
