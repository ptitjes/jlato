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

package org.jlato.parser;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.parser.ParserInterface;
import org.jlato.internal.parser.ParserNew;
import org.jlato.tree.Tree;
import org.jlato.tree.NodeMap;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.CompilationUnit;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Didier Villevalois
 */
public class Parser {

	private final ParserConfiguration configuration;
	private ParserInterface parserInstance = null;

	public Parser() {
		this(ParserConfiguration.Default);
	}

	public Parser(ParserConfiguration configuration) {
		this.configuration = configuration;
	}

	static final ParserInterface.Factory DefaultFactory = new ParserNew.ParserNewFactory();

	private ParserInterface.Factory factory() {
		return DefaultFactory;
	}

	public <T extends Tree> T parse(ParseContext<T> context, InputStream inputStream, String encoding) throws ParseException {
		if (parserInstance == null) {
			parserInstance = factory().newInstance(inputStream, encoding, configuration, false);
		} else parserInstance.reset(inputStream, encoding);
		BUTree<?> tree = context.callProduction(parserInstance);
		return safeAsTree(tree);
	}

	public <T extends Tree> T parse(ParseContext<T> context, Reader reader) throws ParseException {
		if (parserInstance == null) {
			parserInstance = factory().newInstance(reader, configuration, false);
		} else parserInstance.reset(reader);
		BUTree<?> tree = context.callProduction(parserInstance);
		return safeAsTree(tree);
	}

	@SuppressWarnings("unchecked")
	private <T extends Tree> T safeAsTree(BUTree<?> tree) {
		return (T) tree.asTree();
	}

	public <T extends Tree> T parse(ParseContext<T> context, String content) throws ParseException {
		final StringReader reader = new StringReader(content);
		return parse(context, reader);
	}

	public CompilationUnit parse(InputStream inputStream, String encoding) throws ParseException {
		return parse(ParseContext.CompilationUnit, inputStream, encoding);
	}

	public CompilationUnit parse(File file, String encoding) throws ParseException, FileNotFoundException {
		return parse(ParseContext.CompilationUnit, new FileInputStream(file), encoding);
	}

	public NodeMap<CompilationUnit> parseAll(File directory, String encoding) throws ParseException, FileNotFoundException {
		List<File> files = collectAllJavaFiles(directory, new ArrayList<File>());

		String rootPath = directory.getAbsolutePath();
		if (!rootPath.endsWith("/")) rootPath = rootPath + "/";

		NodeMap<CompilationUnit> set = Trees.emptyMap();
		for (File file : files) {
			final CompilationUnit cu = parse(file, encoding);
			final String path = file.getAbsolutePath().substring(rootPath.length());
			set = set.put(path, cu);
		}
		return set;
	}

	// TODO Use NIO filesystem walker
	private static List<File> collectAllJavaFiles(File rootDirectory, List<File> files) {
		final File[] localFiles = rootDirectory.listFiles(JAVA_FILTER);
		assert localFiles != null;

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
