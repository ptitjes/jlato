package org.jlato.internal.bu;

import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public abstract class STree extends LElement {

	public final Tree.Kind kind;
	public final STreeState state;

	public STree(Tree.Kind kind, STreeState state) {
		this.kind = kind;
		this.state = state;
	}

	@Override
	public boolean isToken() {
		return false;
	}
}
