package org.jlato.tree;

import org.jlato.internal.bu.*;
import org.jlato.internal.pc.Array;

/**
 * @author Didier Villevalois
 */
public abstract class Node<N extends Node<N>> {

	protected final SContext<?> context;
	protected final STree<N> tree;

	protected Node(SContext context, STree<N> tree) {
		this.context = context;
		this.tree = tree;
	}

	protected int childrenCount() {
		return tree.isNode() ? ((SNode) tree).children.length() : 0;
	}

	@SuppressWarnings("unchecked")
	protected <C extends Node<C>> C child(int index) {
		if (!tree.isNode()) {
			throw new UnsupportedOperationException();
		}

		SNode<N> node = (SNode<N>) tree;
		STree<C> childTree = (STree<C>) node.children.get(index);
		return childTree.asNode(new SContext<N>((N) this, node, index));
	}

	@SuppressWarnings("unchecked")
	protected <C extends Node<C>> N with(int index, C newChild) {
		return (N) this.<C>child(index).replace(newChild).parent();
	}

	protected String asString() {
		if (tree.isNode()) {
			throw new UnsupportedOperationException();
		}

		return ((SLeaf<N>) tree).lexicalElement.toString();
	}

	protected void setString(String string) {
		if (tree.isNode()) {
			throw new UnsupportedOperationException();
		}
	}

	public N replace(N replacement) {
		return replacement.tree.asNode(context);
	}

	public LLocation lexicalView() {
		return new LLocation(new LContext(context, this.tree), tree.lexicalElement());
	}

	public Node parent() {
		return parentRebuiltIfNeeded(context, tree);
	}

	private <P extends Node<P>> P parentRebuiltIfNeeded(SContext<P> context, STree<?> content) {
		boolean changed = context.parent.children.get(context.indexInParent) == content;
		return changed ? context.parentNode : context.parent.copyWith(context.indexInParent, content).asNode(context.parentNode.context);
	}

	public Node upmost() {
		return context == null ? this : parent();
	}

	protected static Array<STree<?>> treesOf(Node... nodes) {
		STree<?>[] trees = new STree<?>[nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			trees[i] = nodes[i].tree;
		}
		return Array.of(trees);
	}
}
