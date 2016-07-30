/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.internal.shapes;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.WRunRun;
import org.jlato.internal.bu.WTokenRun;

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
	public boolean isDefined(BUTree tree) {
		return true;
	}

	@Override
	public void render(BUTree tree, WRunRun run, Print print) {
		final LToken token = provider.tokenFor(tree);
		if (token == null) throw new IllegalStateException();

		print.append(token);
	}

	public interface Provider {
		LToken tokenFor(BUTree tree);
	}

	private static class FixedProvider implements Provider {

		private final LToken token;

		public FixedProvider(LToken token) {
			this.token = token;
		}

		@Override
		public LToken tokenFor(BUTree tree) {
			return token;
		}
	}
}
