package org.jlato.internal.bu;

/**
 * @author Didier Villevalois
 */
public class LContext {
	public final SContext parentContext;
	public final STree parent;

	public LContext(SContext parentContext, STree parent) {
		this.parentContext = parentContext;
		this.parent = parent;
	}
}
