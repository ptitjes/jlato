package org.jlato.internal;

import org.jlato.internal.bu.*;
import org.jlato.internal.pc.Array;
import org.jlato.internal.pc.IntArray;
import org.jlato.tree.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Didier Villevalois
 */
public class TreeBuilder {

	private static final LElement[] EMPTY_LELEMENT_ARRAY = new LElement[0];

	private Stack<List<LElement>> allElements = new Stack<List<LElement>>();
	private Stack<List<STree<?>>> allTrees = new Stack<List<STree<?>>>();
	private Stack<List<Integer>> allTreeIndexes = new Stack<List<Integer>>();

	public TreeBuilder() {
		reset();
	}

	public void reset() {
		allElements.clear();
		allTrees.clear();
		allTreeIndexes.clear();
		allElements.push(new ArrayList<LElement>());
		allTrees.push(new ArrayList<STree<?>>());
		allTreeIndexes.push(new ArrayList<Integer>());
	}

	public void start() {
		allElements.push(new ArrayList<LElement>());
		allTrees.push(new ArrayList<STree<?>>());
		allTreeIndexes.push(new ArrayList<Integer>());
	}

	public void addToken(LToken token) {
		allElements.peek().add(token);
	}

	public <N extends Node<N>> void stopAs(SType<? super N, N> type) {
		STree<N> tree = makeTree(type);
		addTree(tree);
	}

	public <N extends Node<N>> void lastTokenAs(SType<? super N, N> type) {
		List<LElement> elements = allElements.peek();
		int index = elements.size() - 1;
		SLeaf<N> leaf = new SLeaf<N>(type, (LToken) elements.get(index));
		allTrees.peek().add(leaf);
		allTreeIndexes.peek().add(index);
	}

	public <N extends Node<N>> void stopAsAndWrap(SType<? super N, N> type) {
		STree<N> tree = makeTree(type);
		start();
		addTree(tree);
	}

	private <N extends Node> STree<N> makeTree(SType<? super N, N> type) {
		List<LElement> elements = allElements.pop();
		List<STree<?>> trees = allTrees.pop();
		List<Integer> treeIndexes = allTreeIndexes.pop();

		STree<N> tree;
		if (elements.size() == 1) {
			if (trees.size() != 0 || treeIndexes.size() != 0) throw new IllegalStateException();

			tree = new SLeaf<N>(type, (LToken) elements.get(0));
		} else {
			tree = new SNode<N>(type,
					new LRun(Array.of(elements)),
					Array.of(trees),
					IntArray.of(treeIndexes)
			);
		}
		return tree;
	}

	private <N extends Node<N>> void addTree(STree<N> tree) {
		allElements.peek().add(tree.lexicalElement());
		allTrees.peek().add(tree);
		allTreeIndexes.peek().add(allElements.peek().size());
	}

	@SuppressWarnings("unchecked")
	public <N extends Node<N>> N build(SType<? super N, N> type) {
		STree<N> tree1 = (STree<N>) allTrees.peek().get(0);
		if (tree1.type != type) throw new IllegalStateException();
		return tree1.asNode();
	}
}
