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

package org.jlato.unit.printer;

import org.jlato.parser.ParseException;
import org.jlato.printer.FormattingSettings;
import org.jlato.printer.Printer;
import org.jlato.tree.decl.CompilationUnit;
import org.jlato.unit.util.BaseTestFromFiles;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class BasicPrinterTest extends BaseTestFromFiles {

	@Test
	public void commentsOnStatements() throws ParseException, IOException {
		final String original = resourceAsString("org/jlato/samples/TestClass_nonformated.java");
		CompilationUnit cu = parse(original, true);

		Assert.assertNotEquals(Printer.printToString(cu, FormattingSettings.Default),
				Printer.printToString(cu, false, FormattingSettings.Default));

		Assert.assertEquals(Printer.printToString(cu, FormattingSettings.Default),
				Printer.printToString(cu, true, FormattingSettings.Default));
	}
}
