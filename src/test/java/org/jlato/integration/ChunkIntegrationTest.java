/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2015 The JavaParser Team.
 *
 * This file is part of JavaParser.
 *
 * JavaParser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JavaParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JavaParser.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.integration;

import org.jlato.parser.ParseException;
import org.jlato.integration.utils.BulkTestClass;
import org.jlato.integration.utils.BulkTestRunner;
import org.jlato.integration.utils.NormalizedJsonWriter;
import org.jlato.integration.utils.TestResources;
import org.jlato.tree.Tree;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

/**
 * @author Didier Villevalois
 */
@RunWith(BulkTestRunner.class)
public abstract class ChunkIntegrationTest<T extends Tree> implements BulkTestClass {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	protected abstract T parse(String content) throws ParseException;

	public void runTest(TestResources resources) throws Exception {
		String source = resources.getResourceAsString("source.txt");
		String normalized = resources.getResourceAsString("normalized.txt");
		String failure = resources.getResourceAsString("failure.txt");

		if (normalized != null) {
			T expression = parse(source);
			String actual = NormalizedJsonWriter.write(expression);
			Assert.assertEquals(normalized, actual);
		} else if (failure != null) {
			exception.expect(ParseException.class);
			exception.expectMessage(failure);
			parse(source);
		} else {
			throw new IllegalStateException("There should be either a normalized.txt or a failure.txt file");
		}
	}
}
