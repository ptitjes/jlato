package org.jlato.internal.bu;

import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class SLeaf extends STree {

	public final LToken token;

	public SLeaf(Tree.Kind kind, LToken token) {
		super(kind);
		this.token = token;
	}

	@Override
	public int width() {
		return token.width();
	}

	public SLeaf with(LToken token) {
		return new SLeaf(kind, token);
	}
}
