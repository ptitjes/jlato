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

import org.jlato.internal.bu.LRun;
import org.jlato.internal.bu.LRun.RunBuilder;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.STree;
import org.jlato.internal.td.SContext;

import java.util.Stack;

/**
 * @author Didier Villevalois
 */
public class TreeBuilder {

	private Stack<RunBuilder> allRuns = new Stack<RunBuilder>();

	public TreeBuilder() {
		reset();
	}

	public void reset() {
		allRuns.clear();
		allRuns.push(RunBuilder.withVariableArity());
	}

	public void start() {
		allRuns.push(RunBuilder.withVariableArity());
	}

	public void addToken(LToken token) {
		allRuns.peek().addToken(token);
	}

	public STree stopAs(Tree.Kind type) {
		STree tree = stopTree(type);
		addTree(tree);
		return tree;
	}

	public STree tokenAs(LToken token, Tree.Kind type) {
		start();
		addToken(token);
		return stopAs(type);
	}

	public void stopAsAndWrap(Tree.Kind type) {
		STree tree = stopTree(type);
		start();
		addTree(tree);
	}

	@SuppressWarnings("unchecked")
	private STree stopTree(Tree.Kind type) {
		LRun run = allRuns.pop().build();

		STree tree = null;
//		if (run.hasOnlyOneToken()) {
//			tree = new SLeaf(type, (LToken) run.element(0));
//		} else {
//			tree = new SNode(type, run);
//		}
		return tree;
	}

	@SuppressWarnings("unchecked")
	public void addTree(STree tree) {
		RunBuilder builder = allRuns.peek();
		builder.addTree(tree);
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree> T build(Tree.Kind type) {
		return buildIn(type, new SContext.Root());
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree> T buildIn(Tree.Kind type, SContext context) {
		LRun run = allRuns.peek().build();
		STree tree = run.tree(0);
		if (tree.kind != type) throw new IllegalStateException();
		return (T) new SLocation(context, tree).facade;
	}
}
