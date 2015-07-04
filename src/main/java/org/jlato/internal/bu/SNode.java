package org.jlato.internal.bu;

import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class SNode extends STree {

	public final SNodeData data;
	public final LRun run;

	public SNode(Tree.Kind kind, SNodeData data) {
		this(kind, data, null);
	}

	public SNode(Tree.Kind kind, SNodeData data, LRun run) {
		super(kind);
		this.data = data;
		this.run = run;
	}

	@Override
	public int width() {
		return run == null ? 0 : run.width();
	}

	public SNode withData(SNodeData data) {
		return new SNode(kind, data, run);
	}
}
