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

package org.jlato.integration.utils;

import org.jlato.integration.utils.BulkTestClass;
import org.jlato.integration.utils.BulkTestRunner;
import org.jlato.integration.utils.NormalizedJsonWriter;
import org.jlato.integration.utils.TestResources;
import org.jlato.parser.ParseException;
import org.jlato.printer.Printer;
import org.jlato.tree.Tree;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Didier Villevalois
 */
@RunWith(BulkTestRunner.class)
public abstract class ChunkIntegrationTest<T extends Tree> implements BulkTestClass {

	protected abstract T parse(String content) throws ParseException;

	public void runTest(TestResources resources) throws Exception {
		String source = resources.getResourceAsString("source.txt");

		if (updateMode()) {
			try {
				T tree = parse(source);

				String normalized = NormalizedJsonWriter.write(tree);
				String formatted = Printer.printToString(tree, true) + "\n";

				resources.updateResource("normalized.txt", normalized);
				resources.updateResource("formatted.txt", formatted);
			} catch (ParseException e) {
				resources.updateResource("failure.txt", e.getMessage());
			}
		} else {
			String normalized = resources.getResourceAsString("normalized.txt");
			String formatted = resources.getResourceAsString("formatted.txt");
			String failure = resources.getResourceAsString("failure.txt");

			if (normalized != null) {
				T tree = parse(source);

				String actualNormalized = NormalizedJsonWriter.write(tree);
				String actualFormatted = Printer.printToString(tree, true) + "\n";

				Assert.assertEquals(normalized, actualNormalized);
				Assert.assertEquals(formatted, actualFormatted);
			} else if (failure != null) {
				try {
					parse(source);
				} catch (ParseException e) {
					Assert.assertEquals(failure, e.getMessage());
				}
			} else {
				throw new IllegalStateException("There should be either a normalized.txt or a failure.txt file");
			}
		}
	}

	private boolean updateMode() {
		String updateProp = System.getProperty("updateIntegrationTests");
		return updateProp != null && updateProp.equals("true");
	}
}
