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

package org.jlato.integration;

import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.parser.ParserConfiguration;
import org.jlato.printer.Printer;
import org.jlato.rewrite.MatchVisitor;
import org.jlato.rewrite.Quotes;
import org.jlato.rewrite.Substitution;
import org.jlato.tree.NodeMap;
import org.jlato.tree.Trees;
import org.jlato.tree.decl.CompilationUnit;
import org.jlato.tree.name.Name;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class LiveRewriteIT {

	private Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
	private Printer printer = new Printer(false);
	private File tmpDir = new File("tmp/");

	@Before
	public void createTempDir() {
		tmpDir.mkdirs();
	}

	@After
	public void eraseTempDir() throws IOException {
		rmdir(tmpDir);
	}

	@Test
	public void parsePrintCompareJavaSLang() throws IOException, ParseException {
		parsePrintCompare("com.javaslang", "javaslang", "1.2.2", parser, printer);
	}

	@Test
	public void parsePrintModifyJavaSLang() throws IOException, ParseException {
		parsePrintModify("com.javaslang", "javaslang", "1.2.2", parser, printer);
	}

	private void parsePrintCompare(String group, String artifactId, String version, Parser parser, Printer printer) throws IOException, ParseException {
		File origDirectory = makeTempDir(artifactId, version, "orig");
		if (!origDirectory.exists()) origDirectory.mkdirs();
		unJar(makeLocalFile(artifactId, version), origDirectory);
		NodeMap<CompilationUnit> orig = parser.parseAll(origDirectory, "UTF-8");

		File printDirectory = makeTempDir(artifactId, version, "print");
		if (!printDirectory.exists()) printDirectory.mkdirs();
		printer.printAll(orig, printDirectory, "UTF-8");
		NodeMap<CompilationUnit> printReparsed = parser.parseAll(printDirectory, "UTF-8");

		for (String path : orig.keys()) {
			Assert.assertEquals(orig.get(path), printReparsed.get(path));
		}
		Assert.assertEquals(orig, printReparsed);
	}

	private void parsePrintModify(String group, String artifactId, String version, Parser parser, Printer printer) throws IOException, ParseException {
		File origDirectory = makeTempDir(artifactId, version, "orig");
		if (!origDirectory.exists()) origDirectory.mkdirs();
		unJar(makeLocalFile(artifactId, version), origDirectory);
		NodeMap<CompilationUnit> orig = parser.parseAll(origDirectory, "UTF-8");

		File modDirectory = makeTempDir(artifactId, version, "mod");
		if (!modDirectory.exists()) modDirectory.mkdirs();

		NodeMap<CompilationUnit> mod = Trees.emptyMap();
		for (String path : orig.keys()) {
			CompilationUnit cu = orig.get(path);
			CompilationUnit rewrote = cu.forAll(Quotes.names(), new MatchVisitor<Name>() {
				@Override
				public Name visit(Name name, Substitution s) {
					return name.withId(name.id() + "__modified");
				}
			});
			mod = mod.put(path, rewrote);
		}
		printer.printAll(mod, modDirectory, "UTF-8");

		File compDirectory = makeTempDir(artifactId, version, "comp");
		if (!compDirectory.exists()) compDirectory.mkdirs();
		unJar(makeModifiedFile(artifactId, version), compDirectory);
		NodeMap<CompilationUnit> comp = parser.parseAll(compDirectory, "UTF-8");

		for (String path : orig.keys()) {
			Assert.assertEquals(comp.get(path), mod.get(path));
		}
		Assert.assertEquals(comp, mod);
	}

	private static void unJar(File file, File workDirectory) throws IOException, ParseException {
		JarFile jarFile = new JarFile(file);
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry jarEntry = entries.nextElement();
			String name = jarEntry.getName();
			if (name.endsWith(".java")) {
				InputStream inputStream = jarFile.getInputStream(jarEntry);

				File out = new File(workDirectory, "/" + name);
				out.getParentFile().mkdirs();
				copyStreams(inputStream, new FileOutputStream(out));
			}
		}
	}

	private static void copyStreams(InputStream is, OutputStream os) throws IOException {
		try {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
	}

	private static void rmdir(File file)
			throws IOException {
		if (file.isDirectory()) {
			for (String name : file.list()) {
				File child = new File(file, name);
				rmdir(child);
			}
		}
		file.delete();
	}

	private File makeLocalFile(String artifactId, String version) {
		return new File("target/dependency/" + artifactId + "-" + version + "-sources.jar");
	}

	private File makeModifiedFile(String artifactId, String version) {
		return new File("src/test/resources/org/jlato/live/" + artifactId + "-" + version + "-modified.jar");
	}

	private File makeTempDir(String artifactId, String version, String variant) {
		return new File(tmpDir, artifactId + "/" + version + "/" + variant + "/");
	}
}
