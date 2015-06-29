package org.jlato.internal.bu;

import org.jlato.internal.pc.IndexedSeq;
import org.jlato.internal.pc.IntArray;
import org.jlato.tree.Node;

/**
 * @author Didier Villevalois
 */
public class SNode<N extends Node> extends STree<N> {

	public final LRun lexicalElement;
	public final IndexedSeq<STree<?>> children;
	public final IntArray childElementMap;

	public SNode(SType<? super N, N> type, LRun lexicalElement, IndexedSeq<STree<?>> children, IntArray childElementMap) {
		super(type);
		this.lexicalElement = lexicalElement;
		this.children = children;
		this.childElementMap = childElementMap;
	}

	@Override
	public LRun lexicalElement() {
		return lexicalElement;
	}

	@Override
	public boolean isNode() {
		return true;
	}

	public SNode<N> copyWith(int index, STree child) {
		LRun run = lexicalElement == null ? null :
				lexicalElement.copyWith(childElementMap.get(index), child.lexicalElement());
		return new SNode<N>(type, run, children.set(index, child), childElementMap);
	}
}
