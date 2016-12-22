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

/**
 * @author Didier Villevalois
 */
public class ParserConfiguration {

	public static final ParserConfiguration Default = new ParserConfiguration(false, "2");

	public final boolean preserveWhitespaces;

	public final String parser;

	private ParserConfiguration(boolean preserveWhitespaces, String parser) {
		this.preserveWhitespaces = preserveWhitespaces;
		this.parser = parser;
	}

	public ParserConfiguration preserveWhitespaces(boolean preserveWhitespaces) {
		return new ParserConfiguration(preserveWhitespaces, parser);
	}

	public ParserConfiguration setParser(String parser) {
		return new ParserConfiguration(preserveWhitespaces, parser);
	}
}
