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

package org.jlato.unit.util;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.td.TDTree;
import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.parser.ParserConfiguration;
import org.jlato.printer.FormattingSettings;
import org.jlato.printer.Printer;
import org.jlato.tree.decl.CompilationUnit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Didier Villevalois
 */
public class BaseTestFromFiles {

	protected CompilationUnit parse(String original, boolean preserveWhitespaces) throws ParseException {
		final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(preserveWhitespaces));
		return parser.parse(ParseContext.CompilationUnit, original);
	}

	protected void validate(CompilationUnit cu) {
		BUTree tree = TDTree.treeOf(cu);
		tree.validate();
	}

	protected String print(CompilationUnit cu, boolean format, FormattingSettings formattingSettings) {
		return Printer.printToString(cu, format, formattingSettings);
	}

	protected String parseAndPrint(String original, boolean preserveWhitespaces, boolean format, FormattingSettings formattingSettings) throws ParseException {
		final CompilationUnit cu = parse(original, preserveWhitespaces);
		validate(cu);
		return print(cu, format, formattingSettings);
	}

	protected String resourceAsString(String name) throws IOException {
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream(name);
		return new String(readFully(inputStream), "UTF-8");
	}

	private byte[] readFully(InputStream inputStream)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(buffer)) != -1) {
			baos.write(buffer, 0, length);
		}
		return baos.toByteArray();
	}
}
