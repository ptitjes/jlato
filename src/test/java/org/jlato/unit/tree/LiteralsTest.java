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

import org.jlato.internal.bu.Literals;
import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.jlato.tree.Trees.literalExpr;
import static org.jlato.tree.Trees.nullLiteralExpr;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class LiteralsTest {

	@Test
	public void parsedNull() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals(null, parseLiteral(parser, "null").value());
		Assert.assertEquals(nullLiteralExpr(), parseLiteral(parser, "null"));

		try {
			Literals.from((Class) Void.class, new Object());
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}

		try {
			Literals.valueFor(Void.class, "dummy");
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void parsedBooleans() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals(false, parseLiteral(parser, "false").value());
		Assert.assertEquals(literalExpr(false), parseLiteral(parser, "false"));
		Assert.assertEquals(true, parseLiteral(parser, "true").value());
		Assert.assertEquals(literalExpr(true), parseLiteral(parser, "true"));

		try {
			Literals.from((Class) Boolean.class, new Object());
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}

		try {
			Literals.valueFor(Boolean.class, "dummy");
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void parsedIntegers() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals(42, parseLiteral(parser, "42").value());
		Assert.assertEquals(literalExpr(42), parseLiteral(parser, "42"));
	}

	@Test
	public void parsedHexadecimalIntegers() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals(0x42, parseLiteral(parser, "0x42").value());
		Assert.assertEquals(literalExpr(0x42), parseLiteral(parser, "66"));
	}

	@Test
	public void parsedOctalIntegers() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals(042, parseLiteral(parser, "042").value());
		Assert.assertEquals(literalExpr(042), parseLiteral(parser, "34"));
	}

	@Test
	public void parsedBinaryIntegers() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals(42, parseLiteral(parser, "0b00101010").value());
	}

	@Test
	public void parsedLongs() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals(42L, parseLiteral(parser, "42l").value());
		Assert.assertEquals(42L, parseLiteral(parser, "42L").value());
		Assert.assertEquals("42l", parseLiteral(parser, "42l").toString());
		Assert.assertEquals("42L", parseLiteral(parser, "42L").toString());
		Assert.assertEquals(literalExpr(42L), parseLiteral(parser, "42L"));
	}

	@Test
	public void parsedHexadecimalLongs() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals(0x42L, parseLiteral(parser, "0x42L").value());
		Assert.assertEquals(literalExpr(0x42L), parseLiteral(parser, "66L"));
	}

	@Test
	public void parsedOctalLongs() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals(042L, parseLiteral(parser, "042L").value());
		Assert.assertEquals(literalExpr(042L), parseLiteral(parser, "34L"));
	}

	@Test
	public void parsedBinaryLongs() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals(42L, parseLiteral(parser, "0b00101010L").value());
	}

	@Test
	public void parsedFloats() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals(42f, parseLiteral(parser, "42f").value());
		Assert.assertEquals(42F, parseLiteral(parser, "42F").value());
		Assert.assertEquals(42.42f, parseLiteral(parser, "42.42f").value());
		Assert.assertEquals(42.42F, parseLiteral(parser, "42.42F").value());
		Assert.assertEquals("42f", parseLiteral(parser, "42f").toString());
		Assert.assertEquals("42F", parseLiteral(parser, "42F").toString());
		Assert.assertEquals("42.42f", parseLiteral(parser, "42.42f").toString());
		Assert.assertEquals("42.42F", parseLiteral(parser, "42.42F").toString());
		Assert.assertEquals(literalExpr(42F), parseLiteral(parser, "42.0F"));
		Assert.assertEquals(literalExpr(42.42F), parseLiteral(parser, "42.42F"));
	}

	@Test
	public void parsedHexadecimalFloats() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals(0x42p0f, parseLiteral(parser, "0x42p0f").value());
		Assert.assertEquals(0x42p0F, parseLiteral(parser, "0x42p0F").value());
		Assert.assertEquals(0x42p24f, parseLiteral(parser, "0x42p24f").value());
		Assert.assertEquals(0x42p24F, parseLiteral(parser, "0x42p24F").value());
		Assert.assertEquals("0x42p0f", parseLiteral(parser, "0x42p0f").toString());
		Assert.assertEquals("0x42p0F", parseLiteral(parser, "0x42p0F").toString());
		Assert.assertEquals("0x42p24f", parseLiteral(parser, "0x42p24f").toString());
		Assert.assertEquals("0x42p24F", parseLiteral(parser, "0x42p24F").toString());

		Assert.assertEquals(0x42.42p0f, parseLiteral(parser, "0x42.42p0f").value());
		Assert.assertEquals(0x42.42p24F, parseLiteral(parser, "0x42.42p24F").value());
		Assert.assertEquals("0x42.42p0f", parseLiteral(parser, "0x42.42p0f").toString());
		Assert.assertEquals("0x42.42p24F", parseLiteral(parser, "0x42.42p24F").toString());
	}

	@Test
	public void parsedDoubles() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals(42d, parseLiteral(parser, "42d").value());
		Assert.assertEquals(42D, parseLiteral(parser, "42D").value());
		Assert.assertEquals(42.42d, parseLiteral(parser, "42.42d").value());
		Assert.assertEquals(42.42D, parseLiteral(parser, "42.42D").value());
		Assert.assertEquals(42.42, parseLiteral(parser, "42.42").value());
		Assert.assertEquals(42.42, parseLiteral(parser, "42.42").value());
		Assert.assertEquals("42d", parseLiteral(parser, "42d").toString());
		Assert.assertEquals("42D", parseLiteral(parser, "42D").toString());
		Assert.assertEquals("42.42d", parseLiteral(parser, "42.42d").toString());
		Assert.assertEquals("42.42D", parseLiteral(parser, "42.42D").toString());
		Assert.assertEquals("42.42", parseLiteral(parser, "42.42").toString());
		Assert.assertEquals("42.42", parseLiteral(parser, "42.42").toString());
		Assert.assertEquals(literalExpr(42D), parseLiteral(parser, "42.0"));
		Assert.assertEquals(literalExpr(42.42D), parseLiteral(parser, "42.42"));
		Assert.assertEquals(literalExpr(42.42), parseLiteral(parser, "42.42"));
	}

	@Test
	public void parsedHexadecimalDoubles() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals(0x42p0d, parseLiteral(parser, "0x42p0d").value());
		Assert.assertEquals(0x42p0D, parseLiteral(parser, "0x42p0D").value());
		Assert.assertEquals(0x42p42d, parseLiteral(parser, "0x42p42d").value());
		Assert.assertEquals(0x42p42D, parseLiteral(parser, "0x42p42D").value());
		Assert.assertEquals("0x42p0d", parseLiteral(parser, "0x42p0d").toString());
		Assert.assertEquals("0x42p0D", parseLiteral(parser, "0x42p0D").toString());
		Assert.assertEquals("0x42p42d", parseLiteral(parser, "0x42p42d").toString());
		Assert.assertEquals("0x42p42D", parseLiteral(parser, "0x42p42D").toString());

		Assert.assertEquals(0x42.42p0d, parseLiteral(parser, "0x42.42p0d").value());
		Assert.assertEquals(0x42.42p42D, parseLiteral(parser, "0x42.42p42D").value());
		Assert.assertEquals("0x42.42p0d", parseLiteral(parser, "0x42.42p0d").toString());
		Assert.assertEquals("0x42.42p42D", parseLiteral(parser, "0x42.42p42D").toString());
	}

	@Test
	public void parsedCharacter() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals('a', parseLiteral(parser, "'a'").value());
		Assert.assertEquals(literalExpr('a'), parseLiteral(parser, "'a'"));
		Assert.assertEquals("'a'", parseLiteral(parser, "'a'").toString());

		Assert.assertEquals('\4', parseLiteral(parser, "'\\4'").value());
		Assert.assertEquals(literalExpr('\4'), parseLiteral(parser, "'\4'"));
		Assert.assertEquals("'\\4'", parseLiteral(parser, "'\\4'").toString());

		Assert.assertEquals('\42', parseLiteral(parser, "'\\42'").value());
		Assert.assertEquals(literalExpr('\42'), parseLiteral(parser, "'\\\"'"));
		Assert.assertEquals("'\\42'", parseLiteral(parser, "'\\42'").toString());

		Assert.assertEquals('\042', parseLiteral(parser, "'\\042'").value());
		Assert.assertEquals(literalExpr('\042'), parseLiteral(parser, "'\\\"'"));
		Assert.assertEquals("'\\042'", parseLiteral(parser, "'\\042'").toString());

		Assert.assertEquals('\t', parseLiteral(parser, "'\\t'").value());
		Assert.assertEquals(literalExpr('\t'), parseLiteral(parser, "'\\t'"));
		Assert.assertEquals("'\\t'", parseLiteral(parser, "'\\t'").toString());

		Assert.assertEquals('\b', parseLiteral(parser, "'\\b'").value());
		Assert.assertEquals(literalExpr('\b'), parseLiteral(parser, "'\\b'"));
		Assert.assertEquals("'\\b'", parseLiteral(parser, "'\\b'").toString());

		Assert.assertEquals('\n', parseLiteral(parser, "'\\n'").value());
		Assert.assertEquals(literalExpr('\n'), parseLiteral(parser, "'\\n'"));
		Assert.assertEquals("'\\n'", parseLiteral(parser, "'\\n'").toString());

		Assert.assertEquals('\r', parseLiteral(parser, "'\\r'").value());
		Assert.assertEquals(literalExpr('\r'), parseLiteral(parser, "'\\r'"));
		Assert.assertEquals("'\\r'", parseLiteral(parser, "'\\r'").toString());

		Assert.assertEquals('\f', parseLiteral(parser, "'\\f'").value());
		Assert.assertEquals(literalExpr('\f'), parseLiteral(parser, "'\\f'"));
		Assert.assertEquals("'\\f'", parseLiteral(parser, "'\\f'").toString());

		Assert.assertEquals('\'', parseLiteral(parser, "'\\''").value());
		Assert.assertEquals(literalExpr('\''), parseLiteral(parser, "'\\''"));
		Assert.assertEquals("'\\''", parseLiteral(parser, "'\\''").toString());

		Assert.assertEquals('\"', parseLiteral(parser, "'\\\"'").value());
		Assert.assertEquals(literalExpr('\"'), parseLiteral(parser, "'\\\"'"));
		Assert.assertEquals("'\\\"'", parseLiteral(parser, "'\\\"'").toString());

		Assert.assertEquals('\\', parseLiteral(parser, "'\\\\'").value());
		Assert.assertEquals(literalExpr('\\'), parseLiteral(parser, "'\\\\'"));
		Assert.assertEquals("'\\\\'", parseLiteral(parser, "'\\\\'").toString());
	}

	@Test
	public void parsedString() throws ParseException {
		Parser parser = new Parser();

		Assert.assertEquals("abcdefgh", parseLiteral(parser, "\"abcdefgh\"").value());
		Assert.assertEquals(literalExpr("abcdefgh"), parseLiteral(parser, "\"abcdefgh\""));
		Assert.assertEquals("\"abcdefgh\"", parseLiteral(parser, "\"abcdefgh\"").toString());

		Assert.assertEquals("\42\43\44", parseLiteral(parser, "\"\\42\\43\\44\"").value());
		Assert.assertEquals(literalExpr("\42\43\44"), parseLiteral(parser, "\"\\\"#$\""));
		Assert.assertEquals("\"\\42\\43\\44\"", parseLiteral(parser, "\"\\42\\43\\44\"").toString());

		Assert.assertEquals("\042\043\044", parseLiteral(parser, "\"\\042\\043\\044\"").value());
		Assert.assertEquals(literalExpr("\042\043\044"), parseLiteral(parser, "\"\\\"#$\""));
		Assert.assertEquals("\"\\042\\043\\044\"", parseLiteral(parser, "\"\\042\\043\\044\"").toString());

		Assert.assertEquals("\t\b\n\r\f\'\"\\", parseLiteral(parser, "\"\\t\\b\\n\\r\\f\\'\\\"\\\\\"").value());
		Assert.assertEquals(literalExpr("\t\b\n\r\f\'\"\\"), parseLiteral(parser, "\"\\t\\b\\n\\r\\f\\'\\\"\\\\\""));
		Assert.assertEquals("\"\\t\\b\\n\\r\\f\\'\\\"\\\\\"", parseLiteral(parser, "\"\\t\\b\\n\\r\\f\\'\\\"\\\\\"").toString());
	}

	@Test
	public void legalCharacterLiterals() throws ParseException {
		try {
			Literals.unEscapeChar("\'\'");
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}

		try {
			Literals.unEscapeChar("\"a\"");
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}

		try {
			Literals.unEscapeChar("\'\\777\'");
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}

		try {
			Literals.unEscapeChar("\'\\\'");
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}

		try {
			Literals.unEscapeChar("\'\\g\'");
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}

		try {
			Literals.unEscapeChar("\'\\8\'");
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void legalStringLiterals() throws ParseException {
		Assert.assertEquals("", Literals.unEscapeString("\"\""));

		try {
			Literals.unEscapeString("\'a\'");
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}

		Assert.assertEquals("?7", Literals.unEscapeString("\"\\777\""));

		try {
			Literals.unEscapeString("\"\\\"");
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}

		try {
			Literals.unEscapeString("\"\\g\"");
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}

		try {
			Literals.unEscapeString("\"\\8\"");
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void legalLiterals() throws ParseException {
		try {
			Literals.from(Class.class, this.getClass());
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}

		try {
			Literals.valueFor(Class.class, "dummy");
			Assert.fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
	}

	private LiteralExpr parseLiteral(Parser parser, String string) throws ParseException {
		return (LiteralExpr) parser.parse(ParseContext.Expression, string);
	}
}
