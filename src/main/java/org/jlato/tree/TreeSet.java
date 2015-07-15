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

import com.github.andrewoma.dexx.collection.*;
import com.github.andrewoma.dexx.collection.Iterable;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.td.TreeBase;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STreeSetState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.printer.FormattingSettings;
import org.jlato.printer.Printer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Didier Villevalois
 */
public class TreeSet<T extends Tree> extends TreeBase<STreeSetState> implements Tree {

	public final static Kind<Tree> kind = new Kind<Tree>();

	@SuppressWarnings("unchecked")
	public static <T extends Tree> Kind<T> kind() {
		return (Kind<T>) kind;
	}

	public static class Kind<T extends Tree> implements TreeBase.Kind {
		public Tree instantiate(SLocation location) {
			return new TreeSet<T>(location);
		}

		public LexicalShape shape() {
			throw new UnsupportedOperationException();
		}
	}

	private TreeSet(SLocation<STreeSetState> location) {
		super(location);
	}

	public TreeSet(String rootPath) {
		this(new SLocation<STreeSetState>(new STree<STreeSetState>(kind, new STreeSetState(dataOf(rootPath)))));
	}

	public TreeSet(String rootPath, TreeMap<String, STree<?>> trees) {
		this(new SLocation<STreeSetState>(new STree<STreeSetState>(kind, new STreeSetState(trees, dataOf(rootPath)))));
	}

	public T get(String path) {
		return location.safeTraversal(STreeSetState.treeTraversal(path));
	}

	public TreeSet<T> put(String path, T tree) {
		return location.safeTraversalReplace(STreeSetState.treeTraversal(path), tree);
	}

	public Iterable<String> paths() {
		return location.tree.state.trees.keys();
	}

	public void updateOnDisk() throws IOException {
		updateOnDisk(false, FormattingSettings.Default);
	}

	public void updateOnDisk(boolean format, FormattingSettings formattingSettings) throws IOException {
		final String rootPath = location.data(ROOT_PATH);
		for (Pair<String, STree<?>> pair : location.tree.state.trees) {
			final String path = pair.component1();
			final STree tree = pair.component2();

			final PrintWriter writer = new PrintWriter(new FileWriter(rootPath + path));
			final Printer printer = new Printer(writer, format, formattingSettings);
			printer.print(tree.asTree());
			writer.close();
		}
	}

	private static final int ROOT_PATH = 0;
}
