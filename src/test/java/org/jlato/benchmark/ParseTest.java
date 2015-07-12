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

package org.jlato.benchmark;

import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.parser.ParserConfiguration;
import org.jlato.tree.decl.CompilationUnit;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class ParseTest {

	public static final int WARM_UP = 4;
	public static final int TOTAL = 10;
	public static final String SAMPLES_DIR = "org/jlato/samples/";
	private Timer parseTimer = new Timer();
	private Timer printTimer = new Timer();

	final Parser parser = new Parser(ParserConfiguration.Default.preserveWhitespaces(false));
//	final Printer printer = new Printer();

	@Test
	public void javaConcepts() throws ParseException, IOException {
		benchmark(SAMPLES_DIR + "JavaConcepts.java");
	}

	@Ignore
	@Test
	public void randoopTest0() throws ParseException, IOException {
		benchmark(SAMPLES_DIR + "RandoopTest0.java");
	}

	private void benchmark(String name) throws ParseException, IOException {

		// Warming up
		for (int i = 0; i < WARM_UP; i++) {
			parseAndPrint(name);
		}

		parseTimer.reset();
		printTimer.reset();
		for (int i = 0; i < TOTAL; i++) {
			parseAndPrint(name);
		}
		System.out.println("Benchmark on " + name + " (" + TOTAL + " * 1000 iterations)");
		System.out.println("  Average parse time: " + parseTimer.time / (TOTAL * 1000) + " ms");
		System.out.println("  Average print time: " + printTimer.time / (TOTAL * 1000) + " ms");
	}

	static class Timer {
		private long startTime;
		public double time;

		public void reset() {
			time = 0;
		}

		public void start() {
			startTime = System.currentTimeMillis();
		}

		public void stop() {
			time += System.currentTimeMillis() - startTime;
		}
	}

	private void parseAndPrint(String name) throws ParseException, IOException {
		parseTimer.start();
		for (int i = 0; i < 1000; i++) {
			final InputStream inputStream = ClassLoader.getSystemResourceAsStream(name);
			final CompilationUnit cu = parser.parse(inputStream, "UTF-8");
			inputStream.close();
		}
		parseTimer.stop();
//		printTimer.start();
//		Printer.printToString(cu, format, formattingSettings);
//		printTimer.stop();
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
