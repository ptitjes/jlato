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

import com.github.andrewoma.dexx.collection.Iterable;
import com.github.andrewoma.dexx.collection.Pair;
import com.github.andrewoma.dexx.collection.TreeMap;
import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.coll.STreeSet;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.printer.FormattingSettings;
import org.jlato.printer.Printer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Didier Villevalois
 */
public class TreeSet<T extends Tree> extends TDTree<STreeSet, TreeSet<T>, TreeSet<T>> implements Tree {

	public TreeSet(TDLocation<STreeSet> location) {
		super(location);
	}

	public TreeSet(String rootPath) {
		this(new TDLocation<STreeSet>(new BUTree<STreeSet>(new STreeSet(rootPath))));
	}

	public TreeSet(String rootPath, TreeMap<String, BUTree<?>> trees) {
		this(new TDLocation<STreeSet>(new BUTree<STreeSet>(new STreeSet(rootPath, trees))));
	}

	@SuppressWarnings("unchecked")
	public T get(String path) {
		return (T) location.safeTraversal(STreeSet.treeTraversal(path));
	}

	public TreeSet<T> put(String path, T tree) {
		return location.safeTraversalReplace(STreeSet.treeTraversal(path), tree);
	}

	public Iterable<String> paths() {
		return location.tree.state.trees.keys();
	}

	public void updateOnDisk() throws IOException {
		updateOnDisk(false, FormattingSettings.Default);
	}

	public void updateOnDisk(boolean format, FormattingSettings formattingSettings) throws IOException {
		final String rootPath = location.tree.state.rootPath;
		for (Pair<String, BUTree<?>> pair : location.tree.state.trees) {
			final String path = pair.component1();
			final BUTree tree = pair.component2();

			final File file = new File(rootPath + path);
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
