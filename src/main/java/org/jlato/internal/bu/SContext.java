package org.jlato.internal.bu;

import org.jlato.tree.Node;

/**
 * @author Didier Villevalois
 */
public class SContext<P extends Node<P>> {
	public final P parentNode;
	public final SNode<P> parent;
	public final int indexInParent;

	public SContext(P parentNode, SNode<P> parent, int indexInParent) {
		this.parentNode = parentNode;
		this.parent = parent;
		this.indexInParent = indexInParent;
	}
}
