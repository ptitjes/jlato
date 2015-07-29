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
import org.jlato.internal.bu.WRunRun;
import org.jlato.internal.bu.WTokenRun;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public final class LSToken extends LexicalShape {

	private final Provider provider;

	public LSToken(LToken token) {
		this(new FixedProvider(token));
	}

	public LSToken(Provider provider) {
		this.provider = provider;
	}

	@Override
	public boolean isDefined(STree tree) {
		return true;
	}

	@Override
	public void dress(DressingBuilder<?> builder, STree<?> discriminator) {
		builder.addNullRun();
	}

	@Override
	public boolean acceptsTrailingWhitespace() {
		return false;
	}

	@Override
	public boolean acceptsLeadingWhitespace() {
		return false;
	}

	@Override
	public void dressTrailing(WTokenRun tokens, DressingBuilder<?> builder) {
	}

	@Override
	public void dressLeading(WTokenRun tokens, DressingBuilder<?> builder) {
	}

	@Override
	public void render(STree tree, WRunRun run, Printer printer) {
		final LToken token = provider.tokenFor(tree);
		if (token == null) throw new IllegalStateException();

		printer.append(token, true);
	}

	public interface Provider {
		LToken tokenFor(STree tree);
	}

	private static class FixedProvider implements Provider {

		private final LToken token;

		public FixedProvider(LToken token) {
			this.token = token;
		}

		@Override
		public LToken tokenFor(STree tree) {
			return token;
		}
	}
}
