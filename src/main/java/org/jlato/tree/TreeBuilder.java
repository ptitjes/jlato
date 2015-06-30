package org.jlato.tree;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.LRun.RunBuilder;

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

	public void stopAs(Tree.Kind type) {
		STree tree = stopTree(type);
		addTree(tree);
	}

	public void tokenAs(LToken token, Tree.Kind type) {
		start();
		addToken(token);
		stopAs(type);
	}

	public void stopAsAndWrap(Tree.Kind type) {
		STree tree = stopTree(type);
		start();
		addTree(tree);
	}

	@SuppressWarnings("unchecked")
	private STree stopTree(Tree.Kind type) {
		LRun run = allRuns.pop().build();

		STree tree;
		if (run.hasOnlyOneToken()) {
			tree = new SLeaf(type, (LToken) run.element(0));
		} else {
			tree = new SNode(type, run);
		}
		return tree;
	}

	@SuppressWarnings("unchecked")
	private void addTree(STree tree) {
		RunBuilder builder = allRuns.peek();
		builder.addTree(tree);
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree> T build(Tree.Kind type) {
		return buildIn(type, new Tree.SContext.Root());
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree> T buildIn(Tree.Kind type, Tree.SContext context) {
		LRun run = allRuns.peek().build();
		STree tree = run.tree(0);
		if (tree.kind != type) throw new IllegalStateException();
		return (T) new Tree.SLocation(context, tree).facade;
	}
}
