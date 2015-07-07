/*
 * Copyright (C) 2015 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.shapes;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public final class LSToken extends LexicalShape {

	private final Provider provider;
	private final LexicalSpacing before;
	private final LexicalSpacing after;

	public LSToken(LToken token) {
		this(new FixedProvider(token));
	}

	public LSToken(Provider provider) {
		this(provider, null, null);
	}

	private LSToken(Provider provider, LexicalSpacing before, LexicalSpacing after) {
		this.provider = provider;
		this.before = before;
		this.after = after;
	}

	public LSToken withSpacing(LexicalSpacing before, LexicalSpacing after) {
		return new LSToken(provider, before, after);
	}

	public LSToken withSpacingBefore(LexicalSpacing before) {
		return new LSToken(provider, before, after);
	}

	public LSToken withSpacingAfter(LexicalSpacing after) {
		return new LSToken(provider, before, after);
	}

	public void render(STree tree, Printer printer) {
		final LToken token = provider.tokenFor(tree);
		if (token == null) throw new IllegalStateException();

		if (before != null) before.render(null, printer);
		else {
			final LexicalSpacing spacing = printer.defaultSpacingBefore(token);
			if (spacing != null) spacing.render(null, printer);
		}

		printer.append(token, true /* TODO Replace by test for whitespace/comment tokens in run */);

		if (after != null) after.render(null, printer);
		else {
			final LexicalSpacing spacing = printer.defaultSpacingAfter(token);
			if (spacing != null) spacing.render(null, printer);
		}
	}

	public interface Provider {
		LToken tokenFor(STree tree);
	}

	private static class FixedProvider implements Provider {

		private final LToken token;

		public FixedProvider(LToken token) {
			this.token = token;
		}

		public LToken tokenFor(STree tree) {
			return token;
		}
	}
}
