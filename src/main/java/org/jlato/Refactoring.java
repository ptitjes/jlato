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
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.CompilationUnit;
import org.jlato.tree.decl.TypeDecl;
import org.jlato.tree.type.ClassOrInterfaceType;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Didier Villevalois
 */
public class Refactoring {

	public static final List<String> TREE_CLASSES = Arrays.asList("Tree", "Decl", "Stmt", "Expr", "Type");

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		Parser parser = new Parser();

		File rootDirectory = new File("src/main/java/org/jlato/tree/");
		List<File> files = collectAllJavaFiles(rootDirectory, new ArrayList<File>());

		for (File file : files) {
			final CompilationUnit cu = parser.parse(file, "UTF-8");


//			final TypeDecl typeDecl = cu.types().get(0);
//			final String name = typeDecl.name().name();
//
//			final NodeList<ClassOrInterfaceType> extendsClause = typeDecl.extendsClause();
//			final String superclassName = extendsClause == null ? null : extendsClause.get(0).name().name();
//
//			if (superclassName != null && !TREE_CLASSES.contains(name) && TREE_CLASSES.contains(superclassName)) {
//				System.out.println(file.getAbsolutePath());
//			}
		}
	}

	public static List<File> collectAllJavaFiles(File rootDirectory, List<File> files) {
		final File[] localFiles = rootDirectory.listFiles(JAVA_FILTER);
		files.addAll(Arrays.asList(localFiles));

		for (File directory : rootDirectory.listFiles(DIRECTORY_FILTER)) {
			collectAllJavaFiles(directory, files);
		}

		return files;
	}

	private static final FileFilter JAVA_FILTER = new FileFilter() {
		public boolean accept(File file) {
			return file.getName().endsWith(".java");
		}
	};

	private static final FileFilter DIRECTORY_FILTER = new FileFilter() {
		public boolean accept(File file) {
			return file.isDirectory();
		}
	};
}
