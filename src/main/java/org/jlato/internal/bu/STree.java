package org.jlato.internal.bu;

import org.jlato.tree.Node;

/**
 * @author Didier Villevalois
 */
public abstract class STree<N extends Node> {

	public final SType<? super N, N> type;

	public STree(SType<? super N, N> type) {
		this.type = type;
	}

	public abstract LElement lexicalElement();

	public abstract boolean isNode();

	public N asNode(SContext<?> context) {
		return type.instantiateNode(context, this);
	}

	public N asNode() {
		return asNode(null);
	}
}
