package org.jlato.internal.bu;

import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class SLeaf extends STree {

	public SLeaf(Tree.Kind kind, SLeafState state) {
		super(kind, state);
	}

	@Override
	public int width() {
		return 0;
	}

	public SLeaf withState(SLeafState state) {
		return new SLeaf(kind, state);
	}
}
