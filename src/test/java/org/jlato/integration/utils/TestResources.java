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

package org.jlato.integration.utils;

import java.io.*;

/**
 * @author Didier Villevalois
 */
public class TestResources {

	private final ResourceHelper helper;
	private final String testResourcesPath;
	private final String directory;

	TestResources(ResourceHelper helper, String testResourcesPath, String directory) {
		this.helper = helper;
		this.testResourcesPath = testResourcesPath;
		this.directory = directory;
	}

	public String getResourceAsString(String path) throws IOException {
		return getResourceAsString(path, "UTF-8");
	}

	public String getResourceAsString(String path, String encoding) throws IOException {
		path = path.startsWith("/") ? path : '/' + path;
		return fromStream(getResourceAsStream(path), encoding);
	}

	public InputStream getResourceAsStream(String path) {
		String fullPath = testResourcesPath + directory + path;
		return ClassLoader.getSystemResourceAsStream(fullPath);
	}

	public void updateResource(String path, String content) {
		// Guess test resource directory path (really dirty!)
		String testResourceDirectory = "src/test/resources/";
		if (!new File(testResourceDirectory).exists())
			throw new IllegalStateException("Can't guess test resource directory path");

		File file = new File(testResourceDirectory + testResourcesPath + directory +
				(path.startsWith("/") ? path : '/' + path));
		try {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}

			toStream(content, new FileOutputStream(file), "UTF-8");
		} catch (IOException e) {
			throw new IllegalStateException("Can't update resource: " + file.getAbsolutePath(), e);
		}
	}

	private String fromStream(InputStream inputStream, String encoding)
			throws IOException {
		return inputStream == null ? null : new String(readFully(inputStream), encoding);
	}

	private void toStream(String content, OutputStream outputStream, String encoding)
			throws IOException {
		byte[] bytes = content.getBytes(encoding);
		writeFully(bytes, outputStream);
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

	private void writeFully(byte[] bytes, OutputStream outputStream)
			throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = bais.read(buffer)) != -1) {
			outputStream.write(buffer, 0, length);
		}
	}
}
