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

package org.jlato.wip;

import org.jlato.internal.td.RewriteStrategy;
import org.jlato.parser.ParseContext;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.parser.ParserConfiguration;
import org.jlato.printer.FormattingSettings;
import org.jlato.printer.Printer;
import org.jlato.rewrite.MatchVisitor;
import org.jlato.rewrite.Pattern;
import org.jlato.rewrite.RewriteRules;
import org.jlato.rewrite.Substitution;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.CompilationUnit;
import org.jlato.tree.decl.FormalParameter;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.jlato.rewrite.Quotes.*;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class RewriteTest {

	@Test
	public void testTraverse() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/TestClass.java");
		CompilationUnit cu = parse(original, true);

		final Pattern<FormalParameter> paramPattern = param("$t $n").or(param("$t... $n"));
		final Pattern<Type> typePattern = type("$t");

		cu.forAll(paramPattern, new MatchVisitor<FormalParameter>() {
			@Override
			public FormalParameter visit(FormalParameter p, Substitution s) {
				debug("Formal parameter", p);

				System.out.println(p.matches(paramPattern));

				p.forAll(typePattern, new MatchVisitor<Type>() {
					@Override
					public Type visit(Type type, Substitution s) {
						debug("Type", type);
						return type;
					}
				});

				System.out.println();
				return p;
			}
		});
		System.out.println();
		System.out.println();

		final Pattern<Type> parameteredTypePattern = type("$t<..$tps>");
		cu.forAll(parameteredTypePattern, new MatchVisitor<Type>() {
			@Override
			public Type visit(Type type, Substitution s) {
				debug("Type", type);
				NodeOption<NodeList<Type>> typeArgs = ((QualifiedType) type).typeArgs();
				if (typeArgs.isDefined()) {
					NodeList<Type> tps = typeArgs.get();
					for (Type tp : tps) {
						debug("With type parameter", tp);
					}
					System.out.println();
				}
				return type;
			}
		});
	}

	@Test
	public void testQuotes() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/TestClass.java");
		CompilationUnit cu = parse(original, true);

		final Pattern<Expr> callPattern =
				expr("$n(..$args)").or(expr("$s.<..$tas>$n(..$args)"));

		Iterable<Expr> allCalls = cu.findAll(callPattern);
		int count = 0;
		for (Expr call : allCalls) {
			count++;
		}
		Assert.assertEquals(16, count);
	}

	private static void debug(String header, Tree tree) {
		System.out.println(header + ": " + printToString(tree, false, FormattingSettings.Default));
	}

	@Ignore
	@Test
	public void testClass() throws IOException, ParseException {
		final String original = resourceAsString("org/jlato/samples/TestClass.java");

		RewriteRules rewriter = expr("$t.<$e>factory().newBuilder()").rewriteTo(expr("Builder.<$t<$e>>of()"));

		String rewrote = parseRewriteAndPrint(original, true, rewriter, false, FormattingSettings.Default);
		System.out.println(rewrote);
	}

	private String parseRewriteAndPrint(String original, boolean preserveWhitespaces, RewriteRules rewriter, boolean format, FormattingSettings formattingSettings) throws ParseException {
		final CompilationUnit cu = parse(original, preserveWhitespaces);
		CompilationUnit rewrote = RewriteStrategy.OutermostNeeded.rewrite(cu, rewriter);
		return printToString(rewrote, format, formattingSettings);
	}

	private static String printToString(Tree tree, boolean format, FormattingSettings formattingSettings) {
		return Printer.printToString(tree, format, formattingSettings);
	}

	private static CompilationUnit parse(String original, boolean preserveWhitespaces) throws ParseException {
		final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(preserveWhitespaces));
		return parser.parse(ParseContext.CompilationUnit, original);
	}

	private String fileAsString(String name) throws IOException {
		final InputStream inputStream = new FileInputStream(name);
		return new String(readFully(inputStream), "UTF-8");
	}

	private String resourceAsString(String name) throws IOException {
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
