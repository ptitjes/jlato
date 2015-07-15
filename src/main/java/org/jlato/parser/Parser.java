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

package org.jlato.parser;

import org.jlato.tree.Tree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.TreeSet;
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
	private ParserImpl parserInstance = null;

	public Parser() {
		this(ParserConfiguration.Default);
	}

	public Parser(ParserConfiguration configuration) {
		this.configuration = configuration;
	}

	public <T extends Tree> T parse(ParseContext<T> context, InputStream inputStream, String encoding) throws ParseException {
		if (parserInstance == null) parserInstance = ParserImpl.newInstance(inputStream, encoding, configuration);
		else parserInstance.reset(inputStream, encoding);
		return context.callProduction(parserInstance);
	}

	public <T extends Tree> T parse(ParseContext<T> context, Reader reader) throws ParseException {
		if (parserInstance == null) parserInstance = ParserImpl.newInstance(reader, configuration);
		else parserInstance.reset(reader);
		return context.callProduction(parserInstance);
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

	public TreeSet<CompilationUnit> parseAll(File directory, String encoding) throws ParseException, FileNotFoundException {
		List<File> files = collectAllJavaFiles(directory, new ArrayList<File>());

		String rootPath = directory.getAbsolutePath();
		if (!rootPath.endsWith("/")) rootPath = rootPath + "/";

		TreeSet<CompilationUnit> set = new TreeSet<CompilationUnit>(rootPath);
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
