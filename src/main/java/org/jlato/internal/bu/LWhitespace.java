package org.jlato.internal.bu;

/**
 * @author Didier Villevalois
 */
public class LWhitespace extends LToken {

	private final String content;

	public LWhitespace(String content) {
		this.content = content;
	}

	@Override
	public int width() {
		return content.length();
	}

	@Override
	public String toString() {
		return content;
	}
}
