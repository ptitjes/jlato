package org.jlato.internal.bu;

import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class SNode extends STree {

	public final LRun run;

	public SNode(Tree.Kind kind, LRun run) {
		super(kind);
		this.run = run;
	}

	@Override
	public int width() {
		return run.width();
	}

	public SNode with(LRun run) {
		return new SNode(kind, run);
	}
}
