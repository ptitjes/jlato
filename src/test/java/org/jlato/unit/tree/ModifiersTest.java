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

package org.jlato.unit.tree;

import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.tree.decl.Modifier;
import org.jlato.tree.decl.ModifierKeyword;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class ModifiersTest {

	@Test
	public void modifierKeywords() {
		Assert.assertEquals(ModifierKeyword.Public, Modifier.Public.keyword());
		Assert.assertEquals(ModifierKeyword.Protected, Modifier.Protected.keyword());
		Assert.assertEquals(ModifierKeyword.Private, Modifier.Private.keyword());
		Assert.assertEquals(ModifierKeyword.Abstract, Modifier.Abstract.keyword());
		Assert.assertEquals(ModifierKeyword.Default, Modifier.Default.keyword());
		Assert.assertEquals(ModifierKeyword.Static, Modifier.Static.keyword());
		Assert.assertEquals(ModifierKeyword.Final, Modifier.Final.keyword());
		Assert.assertEquals(ModifierKeyword.Transient, Modifier.Transient.keyword());
		Assert.assertEquals(ModifierKeyword.Volatile, Modifier.Volatile.keyword());
		Assert.assertEquals(ModifierKeyword.Synchronized, Modifier.Synchronized.keyword());
		Assert.assertEquals(ModifierKeyword.Native, Modifier.Native.keyword());
		Assert.assertEquals(ModifierKeyword.StrictFP, Modifier.StrictFP.keyword());
	}

	@Test
	public void parsedModifiers() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals(Modifier.Public, parseModifier(parser, "public"));
		Assert.assertEquals(Modifier.Protected, parseModifier(parser, "protected"));
		Assert.assertEquals(Modifier.Private, parseModifier(parser, "private"));
		Assert.assertEquals(Modifier.Abstract, parseModifier(parser, "abstract"));
		Assert.assertEquals(Modifier.Default, parseModifier(parser, "default"));
		Assert.assertEquals(Modifier.Static, parseModifier(parser, "static"));
		Assert.assertEquals(Modifier.Final, parseModifier(parser, "final"));
		Assert.assertEquals(Modifier.Transient, parseModifier(parser, "transient"));
		Assert.assertEquals(Modifier.Volatile, parseModifier(parser, "volatile"));
		Assert.assertEquals(Modifier.Synchronized, parseModifier(parser, "synchronized"));
		Assert.assertEquals(Modifier.Native, parseModifier(parser, "native"));
		Assert.assertEquals(Modifier.StrictFP, parseModifier(parser, "strictfp"));
	}

	private Modifier parseModifier(Parser parser, String string) throws ParseException {
		return parser.parse(ParseContext.Modifiers, string).get(0);
	}
}
