package org.jlato.internal.bu;

import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class SNode extends STree {

	public final SNodeState state;
	public final LRun run;

	public SNode(Tree.Kind kind, SNodeState state) {
		this(kind, state, null);
	}

	public SNode(Tree.Kind kind, SNodeState state, LRun run) {
		super(kind);
		this.state = state;
		this.run = run;
	}

	@Override
	public int width() {
		return 0;
	}

	public SNode withState(SNodeState state) {
		return new SNode(kind, state, run);
	}
}
