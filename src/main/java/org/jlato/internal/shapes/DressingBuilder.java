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

package org.jlato.internal.shapes;

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.Builder;
import org.jlato.internal.bu.*;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author Didier Villevalois
 */
public class DressingBuilder<S extends STreeState> {

	private final Iterator<WTokenRun> tokenIterator;

	private Stack<STree<?>> treeStack = new Stack<STree<?>>();
	private Stack<STraversal> traversalStack = new Stack<STraversal>();
	private Stack<RunEnvironment> runStack = new Stack<RunEnvironment>();

	public DressingBuilder(STree<S> tree, Iterator<WTokenRun> tokenIterator) {
		treeStack.push(tree);
		this.tokenIterator = tokenIterator;
	}

	public void openChild(STraversal traversal) {
		traversalStack.push(traversal);
		treeStack.push(currentTree().traverse(traversal));
	}

	public STree<?> currentTree() {
		return treeStack.peek();
	}

	public void closeChild() {
		final STree<?> child = treeStack.pop();
		final STree<?> newChild = child/*.withRun(run)*/;

		final STree<?> parent = treeStack.pop();
		final STree<?> newParent = parent.traverseReplace(traversalStack.pop(), newChild);
		treeStack.push(newParent);
	}

	@SuppressWarnings("unchecked")
	public STree<S> build() {
		return (STree<S>) treeStack.pop();
	}

	public void openRun() {
		runStack.push(new RunEnvironment());
	}

	private WRunRun doCloseRun() {
		final Builder<WRun, ArrayList<WRun>> builder = runStack.pop().builder;
		final ArrayList<WRun> subRunElements = builder.build();
		return subRunElements.isEmpty() ? null : new WRunRun(subRunElements);
	}

	public void closeRun() {
		final WRunRun subRun = doCloseRun();
		if (runStack.isEmpty()) {
			final STree<?> tree = treeStack.pop();
			final STree<?> newTree = tree.withRun(subRun);
			treeStack.push(newTree);
		} else addSubRun(subRun);
	}

	public void handleNext(LexicalShape shape) {
		final STree<?> tree = currentTree();

		final RunEnvironment runEnvironment = runStack.peek();

		final boolean defined = shape != null && shape.isDefined(tree);

		if (!defined) {
			if (runEnvironment.firstShape) runEnvironment.firstShape = false;
			else addSubRun(null);

			addSubRun(null);
		} else {
			if (runEnvironment.firstShape) {
				runEnvironment.firstShape = false;
				if (runEnvironment.firstDefinedShape) runEnvironment.firstDefinedShape = false;
			} else if (runEnvironment.firstDefinedShape) {
				runEnvironment.firstDefinedShape = false;
				addSubRun(null);
			} else addSubRun(tokenIterator.next());

			shape.enRun(this);
		}
	}

	public void addSubRun(WRun run) {
		if (!runStack.isEmpty()) runStack.peek().builder.add(run);
	}

	private class RunEnvironment {
		private Builder<WRun, ArrayList<WRun>> builder = ArrayList.<WRun>factory().newBuilder();
		private boolean firstShape = true;
		private boolean firstDefinedShape = true;
	}
}
