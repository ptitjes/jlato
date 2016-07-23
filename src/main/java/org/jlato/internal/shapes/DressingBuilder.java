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
public class DressingBuilder<S extends STree> {

	private final Iterator<WTokenRun> tokenIterator;
	private Stack<ChildRunBuilder> descendantStack = new Stack<ChildRunBuilder>();

	public DressingBuilder(BUTree<S> tree, Iterator<WTokenRun> tokenIterator) {
		this.tokenIterator = tokenIterator;
		descendantStack.push(new ChildRunBuilder(null, tree));
	}

	public void openChild(STraversal traversal) {
		final ChildRunBuilder parentStack = descendantStack.peek();

		descendantStack.push(new ChildRunBuilder(traversal, parentStack.tree.traverse(traversal)));
	}

	public void closeChild() {
		final ChildRunBuilder childStack = descendantStack.pop();

		final ChildRunBuilder parentStack = descendantStack.peek();
		parentStack.tree = parentStack.tree.traverseReplace(childStack.traversal, childStack.tree);
	}

	@SuppressWarnings("unchecked")
	public BUTree<S> build() {
		return (BUTree<S>) descendantStack.pop().tree;
	}

	public void openRun() {
		descendantStack.peek().openRun();
	}

	public void closeRun() {
		descendantStack.peek().closeRun();
	}

	public void handleNext(LexicalShape shape, BUTree<?> discriminator) {
		descendantStack.peek().handleNext(shape, discriminator);
	}

	public void setTrailing(WTokenRun tokens) {
		final ChildRunBuilder childStack = descendantStack.peek();

		WDressing dressing = childStack.tree.dressing;
		if (dressing == null) dressing = new WDressing();

		childStack.tree = childStack.tree.withDressing(dressing.withTrailing(tokens));
	}

	public void setLeading(WTokenRun tokens) {
		final ChildRunBuilder childStack = descendantStack.peek();

		WDressing dressing = childStack.tree.dressing;
		if (dressing == null) dressing = new WDressing();

		childStack.tree = childStack.tree.withDressing(dressing.withLeading(tokens));
	}

	private class ChildRunBuilder {
		private STraversal traversal;
		private BUTree<?> tree;
		private RunBuilder runBuilder;

		public ChildRunBuilder(STraversal traversal, BUTree<?> tree) {
			this.traversal = traversal;
			this.tree = tree;
		}

		public void openRun() {
			if (runBuilder != null) throw new IllegalStateException();
			runBuilder = new RunBuilder();
		}

		public void closeRun() {
			final WRunRun run = runBuilder.build();
			if (tree.dressing != null) throw new IllegalStateException();
			if (run != null) tree = tree.withDressing(new WDressing(run));
		}

		public void handleNext(LexicalShape shape, BUTree<?> discriminator) {
			runBuilder.handleNext(shape, discriminator);
		}
	}

	private class RunBuilder {

		private Builder<WRun, ArrayList<WRun>> subRuns = ArrayList.<WRun>factory().newBuilder();
		private boolean firstShape = true;
		private boolean firstDefinedShape = true;
		private LexicalShape lastDefinedShape = null;

		public void handleNext(LexicalShape shape, BUTree<?> discriminator) {
			final boolean defined = shape != null && shape.isDefined(discriminator);

			if (!defined) {
				if (firstShape) firstShape = false;
				else subRuns.add(WTokenRun.NULL);
			} else {
				if (firstShape) {
					firstShape = false;
					if (firstDefinedShape) firstDefinedShape = false;
				} else if (firstDefinedShape) {
					firstDefinedShape = false;
					subRuns.add(WTokenRun.NULL);
				} else {
					WTokenRun tokens = tokenIterator.next();

					if (!(discriminator instanceof BUTreeVar)) {
						final boolean acceptsTrailing = lastDefinedShape.acceptsTrailingWhitespace();
						final boolean acceptsLeading = shape.acceptsLeadingWhitespace();
						if (acceptsTrailing && acceptsLeading) {
							final WTokenRun.ThreeWaySplit split = tokens.splitTrailingAndLeadingComments();
							lastDefinedShape.dressTrailing(split.left, DressingBuilder.this);
							tokens = split.middle;
							shape.dressLeading(split.right, DressingBuilder.this);
						} else if (acceptsTrailing) {
							final WTokenRun.TwoWaySplit split = tokens.splitTrailingComment();
							lastDefinedShape.dressTrailing(split.left, DressingBuilder.this);
							tokens = split.right;
						} else if (acceptsLeading) {
							final WTokenRun.TwoWaySplit split = tokens.splitLeadingComments();
							tokens = split.left;
							shape.dressLeading(split.right, DressingBuilder.this);
						}
					}

					subRuns.add(tokens);
				}

				shape.dress(DressingBuilder.this, discriminator);

				lastDefinedShape = shape;
			}
		}

		public WRunRun build() {
			final ArrayList<WRun> subRunElements = subRuns.build();
			return subRunElements.isEmpty() ? null : new WRunRun(subRunElements);
		}
	}
}
