package org.jlato.internal.bu;

import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class SNodeList extends STree {

	public final LRun run;

	public SNodeList(Tree.Kind kind, LRun run) {
		super(kind, null);
		this.run = run;
	}

	@Override
	public int width() {
		return run.width();
	}

	public SNodeList with(LRun run) {
		return new SNodeList(kind, run);
	}
}
