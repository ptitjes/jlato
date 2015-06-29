package org.jlato.internal.bu;

import org.jlato.tree.Node;

/**
 * @author Didier Villevalois
 */
public abstract class SType<N extends Node, M extends N> {

	protected abstract M instantiateNode(SContext context, STree<M> content);

	protected abstract STree<N> parse(LElement content);
}
