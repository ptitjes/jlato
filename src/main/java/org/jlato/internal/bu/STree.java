package org.jlato.internal.bu;

import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public abstract class STree extends LElement {

	public final Tree.Kind kind;

	public STree(Tree.Kind kind) {
		this.kind = kind;
	}

	@Override
	public boolean isToken() {
		return false;
	}
}
