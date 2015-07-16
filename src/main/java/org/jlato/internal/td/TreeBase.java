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

package org.jlato.internal.td;

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public abstract class TreeBase<S extends STreeState<S>, ST extends Tree, T extends ST> {

	protected final SLocation<S> location;

	protected TreeBase(SLocation<S> location) {
		this.location = location;
	}

	public Tree parent() {
		SLocation<?> parentLocation = location.parent();
		return parentLocation == null ? null : parentLocation.facade;
	}

	public Tree root() {
		return location.root().facade;
	}

	public static SLocation<? extends STreeState> locationOf(Tree facade) {
		return facade == null ? null : ((TreeBase<?, ?, ?>) facade).location;
	}

	public static STree<? extends STreeState<?>> treeOf(Tree facade) {
		return facade == null ? null : ((TreeBase<?, ?, ?>) facade).location.tree;
	}

	public static ArrayList<STree<? extends STreeState<?>>> treesOf(Tree... facades) {
		final Builder<STree<?>, ArrayList<STree<?>>> builder = ArrayList.<STree<?>>factory().newBuilder();
		for (Tree facade : facades) {
			builder.add(treeOf(facade));
		}
		return builder.build();
	}

	public static ArrayList<STree<? extends STreeState<?>>> arrayOf(STree<?>... trees) {
		final Builder<STree<?>, ArrayList<STree<?>>> builder = ArrayList.<STree<?>>factory().newBuilder();
		for (STree<?> tree : trees) {
			builder.add(tree);
		}
		return builder.build();
	}

	public static ArrayList<Object> dataOf(Object... attributes) {
		final Builder<Object, ArrayList<Object>> builder = ArrayList.factory().newBuilder();
		for (Object attribute : attributes) {
			builder.add(attribute);
		}
		return builder.build();
	}

	public static Vector<STree<? extends STreeState<?>>> treeListOf(Tree... facades) {
		final Builder<STree<?>, Vector<STree<?>>> builder = Vector.<STree<?>>factory().newBuilder();
		for (Tree facade : facades) {
			builder.add(treeOf(facade));
		}
		return builder.build();
	}

}
