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

package org.jlato.internal.td.coll;

import com.github.andrewoma.dexx.collection.Iterable;
import com.github.andrewoma.dexx.collection.TreeMap;
import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.coll.SNodeMap;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.NodeMap;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class TDNodeMap<T extends Tree> extends TDTree<SNodeMap, org.jlato.tree.NodeMap<T>, org.jlato.tree.NodeMap<T>> implements NodeMap<T> {

	public static <T extends Tree> NodeMap<T> empty() {
		return new TDNodeMap<T>();
	}

	public TDNodeMap(TDLocation<SNodeMap> location) {
		super(location);
	}

	public TDNodeMap() {
		this(new TDLocation<SNodeMap>(new BUTree<SNodeMap>(new SNodeMap())));
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(String key) {
		return (T) location.safeTraversal(SNodeMap.treeTraversal(key));
	}

	@Override
	public NodeMap<T> put(String key, T tree) {
		return location.safeTraversalReplace(SNodeMap.treeTraversal(key), tree);
	}

	@Override
	public Iterable<String> keys() {
		return location.tree.state.trees.keys();
	}
}
