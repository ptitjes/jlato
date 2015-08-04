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

/**
 * @author Didier Villevalois
 */
public interface NodeEither<TL extends Tree, TR extends Tree> extends Tree {

	boolean isLeft();

	boolean isRight();

	TL left();

	TR right();

	NodeEither<TL, TR> setLeft(TL element);

	NodeEither<TL, TR> setRight(TR element);

	String mkString(String start, String sep, String end);
}
