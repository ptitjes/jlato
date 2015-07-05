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
