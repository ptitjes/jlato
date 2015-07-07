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

import org.jlato.tree.Tree;
import org.jlato.tree.decl.CompilationUnit;

import java.io.*;

/**
 * @author Didier Villevalois
 */
public class Parser {

	public ASTParser parserInstance = null;

	public <T extends Tree> T parse(ParseContext<T> context, InputStream inputStream, String encoding) throws ParseException {
		if (parserInstance == null) parserInstance = new ASTParser(inputStream, encoding);
		else parserInstance.reset(inputStream, encoding);
		return context.callProduction(parserInstance);
	}

	public <T extends Tree> T parse(ParseContext<T> context, Reader reader) throws ParseException {
		if (parserInstance == null) parserInstance = new ASTParser(reader);
		else parserInstance.reset(reader);
		return context.callProduction(parserInstance);
	}

	public <T extends Tree> T parse(ParseContext<T> context, String content) throws ParseException {
		final StringReader reader = new StringReader(content);
		if (parserInstance == null) parserInstance = new ASTParser(reader);
		else parserInstance.reset(reader);
		return context.callProduction(parserInstance);
	}

	public CompilationUnit parse(InputStream inputStream, String encoding) throws ParseException {
		return parse(ParseContext.CompilationUnit, inputStream, encoding);
	}

	public CompilationUnit parse(File file, String encoding) throws ParseException, FileNotFoundException {
		return parse(ParseContext.CompilationUnit, new FileInputStream(file), encoding);
	}
}
