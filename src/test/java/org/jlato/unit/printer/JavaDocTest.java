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

package org.jlato.unit.printer;

import org.jlato.parser.ParseException;
import org.jlato.printer.Printer;
import org.jlato.tree.decl.*;
import org.jlato.tree.type.*;
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

	@Test
	public void formatJavaDoc() throws FileNotFoundException, ParseException {
		FieldDecl decl = fieldDecl(primitiveType(Primitive.Int))
				.withVariables(listOf(
						variableDeclarator().withId(variableDeclaratorId().withName(name("field")))
				))
				.insertLeadingComment("/** A simple comment. */");
		Assert.assertEquals("/**\n" +
						" * A simple comment.\n" +
						" */\n" +
						"int field;",
				Printer.printToString(decl)
		);
	}

	@Test
	public void formatJavaDocWithEmptyLines() throws FileNotFoundException, ParseException {
		FieldDecl decl = fieldDecl(primitiveType(Primitive.Int))
				.withVariables(listOf(
						variableDeclarator().withId(variableDeclaratorId().withName(name("field")))
				))
				.insertLeadingComment("/**\n\n\n\nA simple comment.\n\n\n\n*/");
		Assert.assertEquals("/**\n" +
						" * A simple comment.\n" +
						" */\n" +
						"int field;",
				Printer.printToString(decl)
		);
	}
}
