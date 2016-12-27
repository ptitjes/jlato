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

package org.jlato.internal.parser;

import org.jlato.internal.parser.all.Grammar;
import org.jlato.internal.parser.all.GrammarSerialization;
import org.jlato.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.jlato.parser.ParseContext.PackageDecl;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class GrammarSerializationTest {

	@Test
	public void roundTrip() throws IOException {
		Grammar decoded = GrammarSerialization.VERSION_1.decode(ParserImplementation.serializedGrammar);

		Assert.assertEquals(
				escape(ParserImplementation.serializedGrammar),
				escape(GrammarSerialization.VERSION_1.encode(decoded))
		);

		Assert.assertTrue(
				decoded.contentEquals(
						GrammarSerialization.VERSION_1.decode(GrammarSerialization.VERSION_1.encode(decoded)))
		);
	}

	private String escape(String str) {
		StringBuilder b = new StringBuilder();
		for (char c : str.toCharArray()) {
			if (c >= 256) b.append("\\u").append(String.format("%04X", (int) c));
			else if (c < 256) b.append("\\").append(String.format("%o", (int) c));
		}
		return b.toString();
	}
}
