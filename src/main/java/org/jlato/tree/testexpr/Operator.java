package org.jlato.tree.testexpr;

import org.jlato.internal.bu.LElement;
import org.jlato.internal.bu.SContext;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.SType;
import org.jlato.tree.Node;

/**
 * @author Didier Villevalois
 */
public class Operator<N extends Operator<N>> extends Node<N> {

	protected Operator(SContext context, STree<N> content) {
		super(context, content);
	}

	public static abstract class Type<N extends Operator<N>> extends SType<Operator<N>, N> {
		protected STree<Operator<N>> parse(LElement content) {
			return null;
		}
	}
}
