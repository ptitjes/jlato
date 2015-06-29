package org.jlato.internal.bu;

import org.jlato.tree.Node;

/**
 * @author Didier Villevalois
 */
public class SLeaf<N extends Node> extends STree<N> {

	public final LToken lexicalElement;

	public SLeaf(SType<? super N, N> type, LToken lexicalElement) {
		super(type);
		this.lexicalElement = lexicalElement;
	}

	@Override
	public LToken lexicalElement() {
		return lexicalElement;
	}

	@Override
	public boolean isNode() {
		return false;
	}
}
