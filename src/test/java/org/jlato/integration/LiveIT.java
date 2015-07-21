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

import org.jlato.integration.utils.WGet;
import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.parser.ParserConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class LiveIT {

	private Parser parser = new Parser();
	private Parser preservingParser = new Parser(ParserConfiguration.Default.preserveWhitespaces(true));

	private ParserInstance jlatoParser = new ParserInstance() {
		public Object parse(InputStream inputStream) throws ParseException {
			return parser.parse(inputStream, "UTF-8");
		}
	};

	private ParserInstance jlatoParserPreserving = new ParserInstance() {
		public Object parse(InputStream inputStream) throws ParseException {
			return preservingParser.parse(inputStream, "UTF-8");
		}
	};

	@Test
	public void parseJavaParser() throws IOException, ParseException {
		parseFrom("com.github.javaparser", "javaparser-core", "2.1.0");
	}

	@Test
	public void parseJavaSLang() throws IOException, ParseException {
		parseFrom("com.javaslang", "javaslang", "1.2.2");
	}

	private void parseFrom(String group, String artifactId, String version) throws IOException, ParseException {
		File file = makeLocalFile(artifactId, version);
		WGet.get(makeMavenCentralUrlString(group, artifactId, version), file);

		doParse(jlatoParser, file);
		doParse(jlatoParserPreserving, file);
	}

	private void doParse(ParserInstance instance, File file) throws IOException, ParseException {
		JarFile jarFile = new JarFile(file);
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry jarEntry = entries.nextElement();
			String name = jarEntry.getName();
			if (name.endsWith(".java")) {
				InputStream inputStream = jarFile.getInputStream(jarEntry);
				instance.parse(inputStream);
			}
		}
	}

	private File makeLocalFile(String artifactId, String version) {
		return new File("target/work/" + artifactId + "-" + version + "-sources.jar");
	}

	private String makeMavenCentralUrlString(String groupId, String artifactId, String version) {
		return "http://search.maven.org/remotecontent?filepath=" + groupId.replace('.', '/') +
				"/" + artifactId + "/" + version + "/" + artifactId + "-" + version + "-sources.jar";
	}

	public interface ParserInstance {
		Object parse(InputStream inputStream) throws ParseException;
	}
}
