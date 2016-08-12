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

package org.jlato;

import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.parser.ParserConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Didier Villevalois
 */
public class ProfileRunner {

	public static final String OPENJDK_LOCATION = "target/dependency/openjdk-8-src-b132-03_mar_2014.zip";

	private Parser parser = new Parser();
	private Parser preservingParser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));

	public static void main(String[] args) throws IOException, ParseException {
//		System.in.read();

		new ProfileRunner().doParse();
	}

	private void doParse() throws IOException, ParseException {
//		Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));
//		parse("com.github.javaparser", "javaparser-core", "2.5.1", parser, "UTF-8", 10);
//		parse("com.javaslang", "javaslang", "1.2.2", parser, "UTF-8", 10);
		parse("com.javaslang", "javaslang", "1.2.2", preservingParser, "UTF-8", 10);

//		ZipFile zipFile = new ZipFile(OPENJDK_LOCATION);
//		parse(zipFile, "jdk/src/share/classes/", parser, "UTF-8");
	}

	private void parse(String group, String artifactId, String version, Parser parser, String encoding, int count) throws IOException, ParseException {
		printBar(0, count);
		for (int i = 1; i <= count; i++) {
			parse(group, artifactId, version, parser, encoding);
			printBar(i, count);
		}
	}

	private void printBar(int current, int count) {
		System.out.print("[");
		for (int i = 1; i <= count; i++) {
			System.out.print(i <= current ? "#" : " ");
		}
		System.out.print("]");
		if (current == count) System.out.print("\n");
		else System.out.print("\r");
	}

	private void parse(String group, String artifactId, String version, Parser parser, String encoding) throws IOException, ParseException {
		File file = makeLocalFile(artifactId, version);
		JarFile jarFile = new JarFile(file);
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry jarEntry = entries.nextElement();
			String name = jarEntry.getName();
			if (name.endsWith(".java")) {
				InputStream inputStream = jarFile.getInputStream(jarEntry);
				try {
					parser.parse(inputStream, encoding);
				} catch (RuntimeException e) {
					System.out.println(name);
					throw e;
				} catch (ParseException e) {
					e.printStackTrace();
					System.out.flush();
					System.out.println();
					copyStreams(jarFile.getInputStream(jarEntry), System.out);
					System.out.flush();
					throw e;
				}
			}
		}
	}

	private void parse(ZipFile file, String basePath, Parser parser, String encoding) throws IOException, ParseException {
		Enumeration<? extends ZipEntry> entries = file.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			String name = entry.getName();
			if ((basePath == null || name.contains(basePath)) && name.endsWith(".java")) {
				InputStream inputStream = file.getInputStream(entry);
				try {
					parser.parse(inputStream, encoding);
				} catch (RuntimeException e) {
					System.out.println(name);
					throw e;
				} catch (ParseException e) {
					e.printStackTrace();
					System.out.flush();
					System.out.println(name);
					System.out.println();
					copyStreams(file.getInputStream(entry), System.out);
					System.out.flush();
					throw e;
				}
			}
		}
	}

	private File makeLocalFile(String artifactId, String version) {
		return new File("target/dependency/" + artifactId + "-" + version + "-sources.jar");
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
}
