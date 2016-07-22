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

package org.jlato.tree;

import com.github.andrewoma.dexx.collection.Iterable;
import org.jlato.printer.FormattingSettings;

import java.io.IOException;

/**
 * @author Didier Villevalois
 */
public interface NodeMap<T extends Tree> extends Tree {

	T get(String path);

	NodeMap<T> put(String path, T tree);

	Iterable<String> paths();

	void updateOnDisk() throws IOException;

	void updateOnDisk(boolean format, FormattingSettings formattingSettings) throws IOException;
}
