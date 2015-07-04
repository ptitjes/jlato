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
		SLocation parentLocation = changed ? context.rebuiltWith(tree) : context.original();
		return parentLocation == null ? null : parentLocation.facade;
	}

	public Tree root() {
		Tree parent = parent();
		return parent == null ? facade : parent.root();
	}

	@SuppressWarnings("unchecked")
	public <S extends Tree, T extends S> T replace(T replacement) {
		STree newTree = Tree.treeOf(replacement);
		return (T) withTree(newTree).facade;
	}

	/* Node methods */

	@SuppressWarnings("unchecked")
	public <C extends Tree> C nodeChild(final int index) {
		STree childTree = ((SNode) tree).data.child(index);
		if (childTree == null) return null;

		SContext childContext = new SContext.Child(this, index);
		SLocation childLocation = new SLocation(childContext, childTree);
		return (C) childLocation.facade;
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, C extends Tree> T nodeWithChild(int index, C child) {
		SNode node = (SNode) this.tree;
		SNode newNode = node.withData(node.data.withChild(index, Tree.treeOf(child)));
		return (T) withTree(newNode).facade;
	}

	@SuppressWarnings("unchecked")
	public <A> A nodeAttribute(final int index) {
		return (A) ((SNode) tree).data.attribute(index);
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, A> T nodeWithAttribute(int index, A attribute) {
		SNode node = (SNode) this.tree;
		SNode newNode = node.withData(node.data.withAttribute(index, attribute));
		return (T) withTree(newNode).facade;
	}

	/* Leaf methods */

	public LToken leafToken() {
		SLeaf leaf = (SLeaf) this.tree;
		return leaf.token;
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree> T leafWithToken(LToken newToken) {
		SLeaf leaf = (SLeaf) this.tree;
		SLeaf newLeaf = leaf.with(newToken);
		return (T) withTree(newLeaf).facade;
	}


	/* NodeList methods */

	@SuppressWarnings("unchecked")
	public <C extends Tree> C nodeListChild(final int index) {
		final SNodeList nodeList = (SNodeList) this.tree;
		STree childTree = nodeList.run.tree(index);
		if (childTree == null) return null;

		SContext childContext = new SContext.Child(this, index);
		SLocation childLocation = new SLocation(childContext, childTree);
		return (C) childLocation.facade;
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, C extends Tree> T nodeListWithChild(int index, C newChild) {
		SNodeList nodeList = (SNodeList) this.tree;
		SNodeList newNode = nodeList.with(nodeList.run.set(index, Tree.treeOf(newChild)));
		return (T) withTree(newNode).facade;
	}

	public LRun nodeListRun() {
		SNodeList node = (SNodeList) this.tree;
		return node.run;
	}

	@SuppressWarnings("unchecked")
	public <T extends Tree, C extends Tree> T nodeListWithRun(LRun newRun) {
		SNodeList node = (SNodeList) this.tree;
		SNodeList newNode = node.with(newRun);
		return (T) withTree(newNode).facade;
	}
}
