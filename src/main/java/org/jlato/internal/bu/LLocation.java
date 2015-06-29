package org.jlato.internal.bu;

import org.jlato.tree.Node;

/**
 * @author Didier Villevalois
 */
public class LLocation {

	public final LContext context;
	public final LElement content;

	public LLocation(LContext context, LElement content) {
		this.context = context;
		this.content = content;
	}
/*
	public Node parent() {
		if (context.parent.lexicalElement() == tree) {
			return new SLocation(context.parentContext, context.parent);
		} else {
			return new SLocation(context.parentContext, context.parent.type.parse(tree));
		}
	}

	public LLocation replace(LToken modified) {
		return new LLocation(context, modified);
	}

	public SLocation upmost() {
		return parent();
	}*/
}
