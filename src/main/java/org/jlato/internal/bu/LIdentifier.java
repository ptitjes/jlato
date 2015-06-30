package org.jlato.internal.bu;

/**
 * @author Didier Villevalois
 */
public class LIdentifier extends LToken {
	// TODO Check identifier validity

	private final String identifier;

	public LIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public int width() {
		return identifier.length();
	}
}
