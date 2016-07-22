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

package org.jlato.internal.td.coll;

import com.github.andrewoma.dexx.collection.Iterable;
import com.github.andrewoma.dexx.collection.Pair;
import com.github.andrewoma.dexx.collection.TreeMap;
import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.coll.SNodeMap;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.printer.FormattingSettings;
import org.jlato.printer.Printer;
import org.jlato.tree.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

	public TDNodeMap(TreeMap<String, BUTree<?>> trees) {
		this(new TDLocation<SNodeMap>(new BUTree<SNodeMap>(new SNodeMap(trees))));
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(String path) {
		return (T) location.safeTraversal(SNodeMap.treeTraversal(path));
	}

	@Override
	public NodeMap<T> put(String path, T tree) {
		return location.safeTraversalReplace(SNodeMap.treeTraversal(path), tree);
	}

	@Override
	public Iterable<String> paths() {
		return location.tree.state.trees.keys();
	}

	@Override
	public void updateOnDisk() throws IOException {
		updateOnDisk(false, FormattingSettings.Default);
	}

	@Override
	public void updateOnDisk(boolean format, FormattingSettings formattingSettings) throws IOException {
		for (Pair<String, BUTree<?>> pair : location.tree.state.trees) {
			final String path = pair.component1();
			final BUTree tree = pair.component2();

			final File file = new File(path);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			final PrintWriter writer = new PrintWriter(new FileWriter(file));
			final Printer printer = new Printer(writer, format, formattingSettings);
			printer.print(tree.asTree());
			writer.close();
		}
	}
}
