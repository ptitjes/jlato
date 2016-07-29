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

package org.jlato.parser;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.patterns.TreePattern;
import org.jlato.rewrite.Pattern;
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
		return context.callProduction(parserInstance);
	}

	public <T extends Tree> Pattern<T> parse(ParseContext<T> context, String content) throws ParseException {
		final StringReader reader = new StringReader(content);
		return new TreePattern<T>(parse(context, reader));
	}
}
