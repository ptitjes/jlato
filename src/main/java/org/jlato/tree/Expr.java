package org.jlato.tree;

import org.jlato.internal.bu.LElement;
import org.jlato.internal.bu.SContext;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.SType;

/**
 * @author Didier Villevalois
 */
public interface Expr<N extends Expr<N>> extends Node<N> {

	protected Expr(SContext context, STree<N> content) {
		super(context, content);
	}

	protected static abstract class Type<N extends Expr<N>> extends SType<Expr<N>, N> {
		protected STree<Expr<N>> parse(LElement content) {
			return null;
		}
	}
}
