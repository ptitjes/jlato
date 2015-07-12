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

package org.jlato;

import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.parser.ParserConfiguration;
import org.jlato.printer.FormattingSettings;
import org.jlato.printer.Printer;
import org.jlato.tree.TreeSet;
import org.jlato.tree.decl.ClassDecl;
import org.jlato.tree.decl.CompilationUnit;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.tree.type.QualifiedType;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Didier Villevalois
 */
public class Refactoring {

	public static final List<String> TREE_CLASSES = Arrays.asList("Tree", "Decl", "Stmt", "Expr", "Type");

	public static void main(String[] args) throws IOException, ParseException {
		Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));

		File rootDirectory = new File("src/main/java");

		final TreeSet<CompilationUnit> treeSet = parser.parseAll(rootDirectory, "UTF-8");
		for (String path : treeSet.paths()) {
			CompilationUnit cu = treeSet.get(path);

			final TypeDecl typeDecl = cu.types().get(0);
			if (typeDecl.typeKind() == TypeDecl.TypeKind.Class) {
				final ClassDecl classDecl = (ClassDecl) typeDecl;
				final String name = classDecl.name().name();

				final QualifiedType extendsClause = classDecl.extendsClause();
				final String superclassName = extendsClause == null ? null : Printer.printToString(extendsClause);

				if (superclassName != null && !TREE_CLASSES.contains(name) && TREE_CLASSES.contains(superclassName)) {

				}
			}
		}
		treeSet.updateOnDisk(false, FormattingSettings.Default);
	}
}
