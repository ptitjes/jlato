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

package org.jlato.parser;

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.IndexedList;
import org.jlato.internal.bu.*;
import org.jlato.internal.parser.ParserInterface;
import org.jlato.internal.patterns.TreePattern;
import org.jlato.pattern.Pattern;
import org.jlato.tree.Tree;

import java.io.Reader;
import java.io.StringReader;

/**
 * @author Didier Villevalois
 */
public class QuotesParser {

	private final ParserConfiguration configuration;
	private ParserInterface parserInstance = null;

	public QuotesParser() {
		this(ParserConfiguration.Default.preserveWhitespaces(true));
	}

	public QuotesParser(ParserConfiguration configuration) {
		this.configuration = configuration;
	}

	private ParserInterface.Factory factory() {
		return Parser.DefaultFactory;
	}

	private <T extends Tree> BUTree<?> parse(ParseContext<T> context, Reader reader) throws ParseException {
		if (parserInstance == null) {
			parserInstance = factory().newInstance(reader, configuration, true);
		} else parserInstance.reset(reader);
		return makeDressingsAsNew(context.callProduction(parserInstance));
	}

	@SuppressWarnings("unchecked")
	private <S extends STree> BUTree<S> makeDressingsAsNew(BUTree<S> tree) {
		WDressing dressing = tree.dressing;
		WDressing newDressing = dressing == null ? null :
				dressing.withLeading(dressing.leading == null ? null : dressing.leading.setNewTokens())
						.withTrailing(dressing.trailing == null ? null : dressing.trailing.setNewTokens())
						.withRun(dressing.run == null ? null : makeAsNew(dressing.run));

		S state = tree.state;
		if (state != null) {
			STraversal traversal = state.firstChild();
			while (traversal != null) {
				BUTree<?> childTree = traversal.traverse(state);
				if (childTree != null)
					state = (S) traversal.rebuildParentState(state, makeDressingsAsNew(childTree));

				traversal = traversal.rightSibling(state);
			}
		}

		return tree.withDressing(newDressing).withState(state);
	}

	private WRunRun makeAsNew(WRunRun run) {
		Builder<WRun, ArrayList<WRun>> builder = ArrayList.<WRun>factory().newBuilder();
		for (WRun element : run.elements) {
			if (element == null) builder.add(null);
			else if (element instanceof WTokenRun) builder.add(((WTokenRun) element).setNewTokens());
			else if (element instanceof WRunRun) builder.add(makeAsNew((WRunRun) element));
		}
		return new WRunRun(builder.build());
	}

	public <T extends Tree> Pattern<T> parse(ParseContext<T> context, String content) throws ParseException {
		final StringReader reader = new StringReader(content);
		return new TreePattern<T>(parse(context, reader));
	}
}
