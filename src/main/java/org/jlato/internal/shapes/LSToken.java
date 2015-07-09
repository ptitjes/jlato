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

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.IndexedList;
import org.jlato.internal.bu.LRun;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.STree;
import org.jlato.printer.Printer;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public final class LSToken extends LexicalShape {

	private final Provider provider;
	private final SpacingConstraint spacingBefore;
	private final SpacingConstraint spacingAfter;
	private final IndentationConstraint indentationBefore;
	private final IndentationConstraint indentationAfter;

	public LSToken(LToken token) {
		this(new FixedProvider(token));
	}

	public LSToken(Provider provider) {
		this(provider, null, null, null, null);
	}

	private LSToken(Provider provider,
	                SpacingConstraint spacingBefore,
	                SpacingConstraint spacingAfter,
	                IndentationConstraint indentationBefore,
	                IndentationConstraint indentationAfter) {
		this.provider = provider;
		this.spacingBefore = spacingBefore;
		this.spacingAfter = spacingAfter;
		this.indentationBefore = indentationBefore;
		this.indentationAfter = indentationAfter;
	}

	public LSToken withSpacing(SpacingConstraint before, SpacingConstraint after) {
		return new LSToken(provider, before, after, indentationBefore, indentationAfter);
	}

	public LSToken withSpacingBefore(SpacingConstraint spacingBefore) {
		return new LSToken(provider, spacingBefore, spacingAfter, indentationBefore, indentationAfter);
	}

	public LSToken withSpacingAfter(SpacingConstraint spacingAfter) {
		return new LSToken(provider, spacingBefore, spacingAfter, indentationBefore, indentationAfter);
	}

	public LSToken withIndentationBefore(IndentationConstraint indentationBefore) {
		return new LSToken(provider, spacingBefore, spacingAfter, indentationBefore, indentationAfter);
	}

	public LSToken withIndentationAfter(IndentationConstraint indentationAfter) {
		return new LSToken(provider, spacingBefore, spacingAfter, indentationBefore, indentationAfter);
	}

	@Override
	public boolean isDefined(STree tree) {
		return true;
	}

	@Override
	public boolean isWhitespaceOnly() {
		return false;
	}

	@Override
	public LRun enRun(STree tree, Iterator<IndexedList<LToken>> tokenIterator) {
		return new LRun(ArrayList.<LRun>empty(), ArrayList.<IndexedList<LToken>>empty());
	}

	public void render(STree tree, LRun run, Printer printer) {
		final LToken token = provider.tokenFor(tree);
		if (token == null) throw new IllegalStateException();

		if (indentationBefore != null) printer.indent(indentationBefore.resolve(printer));

		printer.append(token, true /* TODO Replace by test for whitespace/comment tokens in run */);

		if (indentationAfter != null) printer.indent(indentationAfter.resolve(printer));
	}

	@Override
	public SpacingConstraint spacingBefore(STree tree) {
		return spacingBefore;
	}

	@Override
	public SpacingConstraint spacingAfter(STree tree) {
		return spacingAfter;
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
