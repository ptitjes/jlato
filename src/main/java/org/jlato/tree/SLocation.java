package org.jlato.tree;

import org.jlato.internal.bu.*;
import org.jlato.internal.td.SContext;

/**
 * @author Didier Villevalois
 */
public class SLocation {

	public final SContext context;
	public final STree tree;
	public final boolean changed;
	public final Tree facade;

	public SLocation(STree tree) {
		this(new SContext.Root(), tree, false);
	}

	public SLocation(SContext context, STree tree) {
		this(context, tree, false);
	}

	public SLocation(SContext context, STree tree, boolean changed) {
		this.context = context;
		this.tree = tree;
		this.changed = changed;
		this.facade = tree.kind.instantiate(this);
	}

	public SLocation withTree(STree newTree) {
		return newTree == tree ? this : new SLocation(context, newTree, true);
	}

	public Tree parent() {
		final SLocation parentLocation = changed ? context.rebuiltWith(tree) : context.original();
		return parentLocation == null ? null : parentLocation.facade;
	}

	public Tree root() {
		final Tree parent = parent();
		return parent == null ? facade : parent.root();
	}

	@SuppressWarnings("unchecked")
	public <S extends Tree, T extends S> T replace(T replacement) {
		final STree newTree = Tree.treeOf(replacement);
		return (T) withTree(newTree).facade;
	}

	/* Node methods */

	@SuppressWarnings("unchecked")
	public <C extends Tree> C nodeChild(final int index) {
		final SNode node = (SNode) this.tree;
		final STree childTree = node.state.child(index);
		if (childTree == null) return null;

		final SContext childContext = new SContext.Child(this, index);
		final SLocation childLocation = new SLocation(childContext, childTree);
		return (C) childLocation.facade;
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, C extends Tree> T nodeWithChild(int index, C child) {
		final SNode node = (SNode) this.tree;
		final SNode newNode = node.withState(node.state.withChild(index, Tree.treeOf(child)));
		return (T) withTree(newNode).facade;
	}

	@SuppressWarnings("unchecked")
	public <A> A nodeData(final int index) {
		final SNode node = (SNode) this.tree;
		return (A) node.state.data(index);
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, A> T nodeWithData(int index, A attribute) {
		final SNode node = (SNode) this.tree;
		final SNode newNode = node.withState(node.state.withData(index, attribute));
		return (T) withTree(newNode).facade;
	}

	/* Leaf methods */

	@SuppressWarnings("unchecked")
	public <A> A leafData(final int index) {
		final SLeaf leaf = (SLeaf) this.tree;
		return (A) leaf.state.data(index);
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, A> T leafWithData(int index, A attribute) {
		final SLeaf leaf = (SLeaf) this.tree;
		final SLeaf newLeaf = leaf.withState(leaf.state.withData(index, attribute));
		return (T) withTree(newLeaf).facade;
	}

	/* NodeList methods */

	@SuppressWarnings("unchecked")
	public <C extends Tree> C nodeListChild(final int index) {
		final SNodeList nodeList = (SNodeList) this.tree;
		final STree childTree = nodeList.run.tree(index);
		if (childTree == null) return null;

		final SContext childContext = new SContext.Child(this, index);
		final SLocation childLocation = new SLocation(childContext, childTree);
		return (C) childLocation.facade;
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, C extends Tree> T nodeListWithChild(int index, C newChild) {
		final SNodeList nodeList = (SNodeList) this.tree;
		final SNodeList newNodeList = nodeList.with(nodeList.run.set(index, Tree.treeOf(newChild)));
		return (T) withTree(newNodeList).facade;
	}

	public LRun nodeListRun() {
		final SNodeList nodeList = (SNodeList) this.tree;
		return nodeList.run;
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, C extends Tree> T nodeListWithRun(LRun newRun) {
		final SNodeList nodeList = (SNodeList) this.tree;
		final SNodeList newNodeList = nodeList.with(newRun);
		return (T) withTree(newNodeList).facade;
	}
}
