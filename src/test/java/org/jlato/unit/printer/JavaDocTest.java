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

import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.parser.ParserConfiguration;
import org.jlato.pattern.MatchVisitor;
import org.jlato.pattern.Pattern;
import org.jlato.pattern.Quotes;
import org.jlato.pattern.Substitution;
import org.jlato.printer.Printer;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.*;
import org.jlato.tree.type.Primitive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.jlato.tree.Trees.*;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class JavaDocTest {

	FieldDecl base = fieldDecl(primitiveType(Primitive.Int))
			.withVariables(listOf(
					variableDeclarator().withId(variableDeclaratorId().withName(name("field")))
			));

	@Test
	public void testForNoJavaDoc() throws FileNotFoundException, ParseException {
		Assert.assertNull(base.docComment());
	}

	@Test
	public void formatJavaDoc() throws FileNotFoundException, ParseException {
		FieldDecl decl = base.withDocComment("A simple comment.");
		Assert.assertEquals("/**\n" +
						" * A simple comment.\n" +
						" */\n" +
						"int field;",
				Printer.printToString(decl)
		);
		Assert.assertEquals("A simple comment.", decl.docComment());
	}

	@Test
	public void formatMethodJavaDoc() throws FileNotFoundException, ParseException {
		ClassDecl classDecl = classDecl(name("Test"))
				.withMembers(Trees.<MemberDecl>listOf(
						methodDecl(voidType(), name("method"))
								.withBody(blockStmt())
								.withDocComment("A simple comment.")
				));
		Assert.assertEquals("class Test {\n" +
						"\n" +
						"\t/**\n" +
						"\t * A simple comment.\n" +
						"\t */\n" +
						"\tvoid method() {\n" +
						"\t}\n" +
						"}",
				Printer.printToString(classDecl)
		);
	}

	@Test
	public void formatMethodAlreadyFormattedJavaDoc() throws FileNotFoundException, ParseException {
		String classDef = "class Test {\n" +
				"\n" +
				"\t/**\n" +
				"\t * A simple comment,\n" +
				"\t * that is on two lines.\n" +
				"\t */\n" +
				"\tvoid method() {\n" +
				"\t}\n" +
				"}";
		Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
		TypeDecl typeDecl = parser.parse(ParseContext.TypeDecl, classDef);
		Assert.assertEquals(
				classDef,
				Printer.printToString(typeDecl, false)
		);
	}

	@Test
	public void formatMethodReplaced() throws FileNotFoundException, ParseException {
		String classDef = "class Test {\n" +
				"\n" +
				"\t/**\n" +
				"\t * A simple comment.\n" +
				"\t */\n" +
				"\tvoid method() {\n" +
				"\t}\n" +
				"}";
		Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
		ClassDecl classDecl = (ClassDecl) parser.parse(ParseContext.TypeDecl, classDef);
		classDecl = classDecl.forAll(Quotes.memberDecl("void method() { }"), new MatchVisitor<MemberDecl>() {
			@Override
			public MemberDecl visit(MemberDecl memberDecl, Substitution s) {
				return methodDecl(voidType(), name("method"))
						.withBody(blockStmt())
						.withDocComment("A simple comment.");
			}
		});

		Assert.assertEquals(
				classDef,
				Printer.printToString(classDecl, false)
		);
	}

	@Test
	public void formatJavaDocReplaced() throws FileNotFoundException, ParseException {
		String classDef = "class Test {\n" +
				"\n" +
				"\t/**\n" +
				"\t * A simple comment.\n" +
				"\t */\n" +
				"\tvoid method() {\n" +
				"\t}\n" +
				"}";
		Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
		ClassDecl classDecl = (ClassDecl) parser.parse(ParseContext.TypeDecl, classDef);
		classDecl = classDecl.forAll(Quotes.memberDecl("void method() { }"), new MatchVisitor<MemberDecl>() {
			@Override
			public MemberDecl visit(MemberDecl memberDecl, Substitution s) {
				return ((MethodDecl) memberDecl).withDocComment("A simple comment.");
			}
		});

		Assert.assertEquals(
				classDef,
				Printer.printToString(classDecl, false)
		);
	}

	@Test
	public void formatMethodReplacedWithAnnotations() throws FileNotFoundException, ParseException {
		String classDef = "class Test extends Other {\n" +
				"\n" +
				"\t/**\n" +
				"\t * A simple comment.\n" +
				"\t */\n" +
				"\t@Override\n" +
				"\tpublic void method() {\n" +
				"\t}\n" +
				"}";
		Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
		ClassDecl classDecl = (ClassDecl) parser.parse(ParseContext.TypeDecl, classDef);
		final Pattern<MemberDecl> quote = Quotes.memberDecl("@Override\npublic void method() { }");
		classDecl = classDecl.forAll(quote, new MatchVisitor<MemberDecl>() {
			@Override
			public MemberDecl visit(MemberDecl memberDecl, Substitution s) {
				return ((MethodDecl) quote.build())
						.withBody(blockStmt())
						.withDocComment("A simple comment.");
			}
		});

		Assert.assertEquals(
				classDef,
				Printer.printToString(classDecl, false)
		);
	}

	@Test
	public void formatJavaDocWithEmptyLines() throws FileNotFoundException, ParseException {
		FieldDecl decl = base.withDocComment("\n\n\n\nA simple comment.\n\n\n\n");
		Assert.assertEquals("/**\n" +
						" * A simple comment.\n" +
						" */\n" +
						"int field;",
				Printer.printToString(decl)
		);
		Assert.assertEquals("A simple comment.", decl.docComment());
	}

	@Test
	public void formatJavaDocTwice() throws FileNotFoundException, ParseException {
		FieldDecl decl = base.withDocComment("A first comment.");
		Assert.assertEquals("/**\n" +
						" * A first comment.\n" +
						" */\n" +
						"int field;",
				Printer.printToString(decl)
		);
		Assert.assertEquals("A first comment.", decl.docComment());

		decl = decl.withDocComment("A second comment.");
		Assert.assertEquals("/**\n" +
						" * A second comment.\n" +
						" */\n" +
						"int field;",
				Printer.printToString(decl)
		);
		Assert.assertEquals("A second comment.", decl.docComment());
	}
}
