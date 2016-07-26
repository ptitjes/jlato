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

package org.jlato.unit.parser;

import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.parser.ParserConfiguration;
import org.jlato.printer.Printer;
import org.jlato.tree.Problem;
import org.jlato.tree.Tree;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.Modifier;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import static org.jlato.parser.ParseContext.*;
import static org.jlato.tree.Trees.*;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class ParseContextTest {

	@Test
	public void packageDecl() throws ParseException {
		Assert.assertEquals(
				"package org.jlato;",
				parsePrint(PackageDecl, "package org.jlato;")
		);
	}

	@Test
	public void importDecl() throws ParseException {
		Assert.assertEquals(
				"import org.jlato.tree.Tree;",
				parsePrint(ImportDecl, "import org.jlato.tree.Tree;")
		);
	}

	@Test
	public void typeDecl() throws ParseException {
		Assert.assertEquals(
				"class TestClass {}",
				parsePrint(TypeDecl, "class TestClass {}")
		);
	}

	@Test
	public void modifiers() throws ParseException {
		Assert.assertEquals(
				listOf(
						markerAnnotationExpr(Trees.qualifiedName("Override")),
						Modifier.Public,
						Modifier.Final
				),
				parser.parse(Modifiers, "@Override public final")
		);
	}

	@Test
	public void annotations() throws ParseException {
		Assert.assertEquals(
				listOf(
						singleMemberAnnotationExpr(
								Trees.qualifiedName("Target"),
								fieldAccessExpr(Trees.name("ANNOTATION_TYPE"))
										.withScope(Trees.name("ElementType"))
						)
				),
				parser.parse(Annotations, "@Target(ElementType.ANNOTATION_TYPE)")
		);
	}

	@Test
	public void classMemberDecl() throws ParseException {
		Assert.assertEquals(
				"{}",
				parsePrint(MemberDecl, "{}")
		);
		Assert.assertEquals(
				"static {}",
				parsePrint(MemberDecl, "static {}")
		);
		Assert.assertEquals(
				"public TestClass() { super(); }",
				parsePrint(MemberDecl, "public TestClass() { super(); }")
		);
		Assert.assertEquals(
				"public int hashCode() { return 0; }",
				parsePrint(MemberDecl, "public int hashCode() { return 0; }")
		);
		Assert.assertEquals(
				"public int field = 0;",
				parsePrint(MemberDecl, "public int field = 0;")
		);
	}

	@Test
	public void interfaceMemberDecl() throws ParseException {
		Assert.assertEquals(
				"ERROR: An interface cannot have initializers\n",
				parsePrint(Interface_MemberDecl, "{}")
		);
		Assert.assertEquals(
				"ERROR: An interface cannot have initializers\n",
				parsePrint(Interface_MemberDecl, "static {}")
		);
		Assert.assertEquals(
				"ERROR: An interface cannot have constructors\n",
				parsePrint(Interface_MemberDecl, "public TestClass() { super(); }")
		);
		Assert.assertEquals(
				"public int hashCode() { return 0; }",
				parsePrint(Interface_MemberDecl, "public int hashCode() { return 0; }")
		);
		Assert.assertEquals(
				"public int hashCode();",
				parsePrint(Interface_MemberDecl, "public int hashCode();")
		);
		Assert.assertEquals(
				"public int field = 0;",
				parsePrint(Interface_MemberDecl, "public int field = 0;")
		);
	}

	@Test
	public void annotationTypeMemberDecl() throws ParseException {
		Assert.assertEquals(
				"int value() default 5;",
				parsePrint(Annotation_MemberDecl, "int value() default 5;")
		);
		Assert.assertEquals(
				"public int field = 0;",
				parsePrint(Annotation_MemberDecl, "public int field = 0;")
		);
	}

	@Test
	public void enumMemberDecl() throws ParseException {
		Assert.assertEquals(
				"{}",
				parsePrint(Enum_MemberDecl, "{}")
		);
		Assert.assertEquals(
				"static {}",
				parsePrint(Enum_MemberDecl, "static {}")
		);
		Assert.assertEquals(
				"public TestClass() { super(); }",
				parsePrint(Enum_MemberDecl, "public TestClass() { super(); }")
		);
		Assert.assertEquals(
				"public int hashCode() { return 0; }",
				parsePrint(Enum_MemberDecl, "public int hashCode() { return 0; }")
		);
		Assert.assertEquals(
				"public int field = 0;",
				parsePrint(Enum_MemberDecl, "public int field = 0;")
		);
	}

	@Test
	public void methodDecl() throws ParseException {
		Assert.assertEquals(
				"public int hashCode() { return 0; }",
				parsePrint(MethodDecl, "public int hashCode() { return 0; }")
		);
	}

	@Test
	public void fieldDecl() throws ParseException {
		Assert.assertEquals(
				"public int field = 0;",
				parsePrint(FieldDecl, "public int field = 0;")
		);
	}

	@Test
	public void annotationMemberDecl() throws ParseException {
		Assert.assertEquals(
				"int value() default 5;",
				parsePrint(AnnotationElementDecl, "int value() default 5;")
		);
	}

	@Test
	public void enumConstantDecl() throws ParseException {
		Assert.assertEquals(
				"CONSTANT(1, true) {}",
				parsePrint(EnumConstantDecl, "CONSTANT(1, true) {}")
		);
	}

	@Test
	public void parameter() throws ParseException {
		Assert.assertEquals(
				"@NonNull String str",
				parsePrint(FormalParameter, "@NonNull String str")
		);
	}

	@Test
	public void typeParameter() throws ParseException {
		Assert.assertEquals(
				"X extends Foo",
				parsePrint(TypeParameter, "X extends Foo")
		);
	}

	@Test
	public void statement() throws ParseException {
		Assert.assertEquals(
				"return 0;",
				parsePrint(Statement, "return 0;")
		);
	}

	@Test
	public void statements() throws ParseException {
		Assert.assertEquals(
				listOf(
						returnStmt().withExpr(literalExpr(0)),
						returnStmt().withExpr(literalExpr(1))
				),
				parser.parse(Statements, "return 0; return 1;")
		);
		Assert.assertEquals(
				Trees.emptyList(),
				parser.parse(Statements, "")
		);
	}

	@Test
	public void expression() throws ParseException {
		Assert.assertEquals(
				"1 + 1 / 2",
				parsePrint(Expression, "1 + 1 / 2")
		);
	}

	@Test
	public void type() throws ParseException {
		Assert.assertEquals(
				"Foo<? extends Y>",
				parsePrint(Type, "Foo<? extends Y>")
		);
	}

	@Test
	public void qualifiedName() throws ParseException {
		Assert.assertEquals(
				Trees.qualifiedName("a.b.c.d"),
				parser.parse(QualifiedName, "a.b.c.d")
		);
	}

	@Test
	public void name() throws ParseException {
		Assert.assertEquals(
				"foo",
				parsePrint(Name, "foo")
		);
	}

	private final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
	private final Printer printer = new Printer();

	private <T extends Tree> String parsePrint(ParseContext<T> context, String content) throws ParseException {
		T parse = parser.parse(context, content);
		if (parse.hasProblems()) return printProblems(parse.problems());
		else return printer.printString(parse);
	}

	private String printProblems(Iterable<Problem> problems) {
		StringBuilder builder = new StringBuilder();
		for (Problem problem : problems) {
			builder.append(problem.severity() + ": " + problem.code() + "\n");
		}
		return builder.toString();
	}
}
