package org.jlato.benchmark;

import org.jlato.parser.ParseException;
import org.jlato.parser.Parser;
import org.jlato.tree.decl.CompilationUnit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Didier Villevalois
 */
@RunWith(JUnit4.class)
public class ParseTest {

	public static final int WARM_UP = 10;
	public static final int TOTAL = 1000;
	public static final String SAMPLES_DIR = "org/jlato/samples/";

	@Test
	public void javaConcepts() throws ParseException, FileNotFoundException {
		benchmark(SAMPLES_DIR + "JavaConcepts.java");
		memory(SAMPLES_DIR + "JavaConcepts.java");
	}

	@Test
	public void randoopTest0() throws ParseException, FileNotFoundException {
		benchmark(SAMPLES_DIR + "RandoopTest0.java");
		memory(SAMPLES_DIR + "RandoopTest0.java");
	}

	private void benchmark(String name) throws ParseException {
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream(name);
		final Parser parser = new Parser();

		final Timer timer = new Timer();

		// Warming up
		for (int i = 0; i < WARM_UP; i++) {
			parse1000Times(inputStream, parser, timer);
		}

		timer.reset();
		for (int i = 0; i < TOTAL; i++) {
			parse1000Times(inputStream, parser, timer);
		}
		System.out.println("Benchmark on " + name + " (" + TOTAL + " * 1000 iterations)");
		System.out.println("  Average time: " + timer.time / TOTAL + " ms");
	}

	private void parse1000Times(InputStream inputStream, Parser parser, Timer timer) throws ParseException {
		timer.start();
		for (int i = 0; i < 1000; i++) {
			final CompilationUnit cu = parser.parse(inputStream, "UTF-8");
		}
		timer.stop();
	}

	private void memory(String name) throws ParseException {
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream(name);
		final Parser parser = new Parser();

		long startFreeMemory = freeMemory();
		final CompilationUnit cu = parser.parse(inputStream, "UTF-8");

		double memory = startFreeMemory - freeMemory();

		// To force not being gc'ed in case of compiler optimization
		Assert.assertNotNull(cu);

		System.out.println("Memory test on " + name);
		System.out.println("  Average memory: " + memory / (1024 * 1024) + " MB");
	}

	private long freeMemory() {
		runtime.gc();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		return runtime.freeMemory();
	}

	private static final Runtime runtime = Runtime.getRuntime();

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
}
