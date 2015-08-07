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

package org.jlato.unit;

import org.jlato.parser.ParseException;
import org.jlato.printer.FormattingSettings;
import org.jlato.unit.util.BaseTestFromFiles;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class SamplesTest extends BaseTestFromFiles {

	@Test
	public void testClass() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/TestClass.java");
		FormattingSettings settings = FormattingSettings.Default;

		Assert.assertEquals(original, parseAndPrint(original, false, true, settings));
		Assert.assertEquals(original, parseAndPrint(original, false, false, settings));
		Assert.assertEquals(original, parseAndPrint(original, true, false, settings));
		Assert.assertEquals(original, parseAndPrint(original, true, true, settings));
	}

	@Test
	public void javaConcepts() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/JavaConcepts.java");
		FormattingSettings settings = FormattingSettings.JavaParser;

		Assert.assertEquals(original, parseAndPrint(original, false, true, settings));
		Assert.assertEquals(original, parseAndPrint(original, false, false, settings));
		Assert.assertEquals(original, parseAndPrint(original, true, false, settings));
		Assert.assertEquals(original, parseAndPrint(original, true, true, settings));
	}

	@Test
	public void testEncoding_CP1252_CRLF() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/TestEncoding_CP1252_CRLF.java", "CP1252");
		FormattingSettings settings = FormattingSettings.Default.withNewLine(FormattingSettings.NewLine_Windows);

		Assert.assertEquals(original, parseAndPrint(original, false, true, settings));
		Assert.assertEquals(original, parseAndPrint(original, false, false, settings));
		Assert.assertEquals(original, parseAndPrint(original, true, false, settings));
		Assert.assertEquals(original, parseAndPrint(original, true, true, settings));
	}

	@Test
	public void testEncoding_UTF8_LF() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/TestEncoding_UTF8_LF.java", "UTF-8");
		FormattingSettings settings = FormattingSettings.Default.withNewLine(FormattingSettings.NewLine_Unix);

		Assert.assertEquals(original, parseAndPrint(original, false, true, settings));
		Assert.assertEquals(original, parseAndPrint(original, false, false, settings));
		Assert.assertEquals(original, parseAndPrint(original, true, false, settings));
		Assert.assertEquals(original, parseAndPrint(original, true, true, settings));
	}

	@Test
	public void randoopTest0() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/RandoopTest0.java");
		Assert.assertEquals(original, parseAndPrint(original, true, false, FormattingSettings.Default));
	}
}
