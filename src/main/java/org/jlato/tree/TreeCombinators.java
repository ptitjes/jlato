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

import org.jlato.rewrite.MatchVisitor;
import org.jlato.rewrite.Matcher;
import org.jlato.rewrite.Substitution;
import org.jlato.rewrite.TypeSafeMatcher;

/**
 * @author Didier Villevalois
 */
public interface TreeCombinators<T extends Tree> extends Tree {

	<U extends Tree> T forAll(TypeSafeMatcher<? extends U> matcher, MatchVisitor<U> visitor);

	<U extends Tree> T leftForAll(TypeSafeMatcher<? extends U> matcher, MatchVisitor<U> visitor);

	<U extends Tree> T rightForAll(TypeSafeMatcher<? extends U> matcher, MatchVisitor<U> visitor);

	Substitution match(Matcher matcher);

	boolean matches(Matcher matcher);

	T match(TypeSafeMatcher<? extends T> matcher, MatchVisitor<T> visitor);

	<U extends Tree> Iterable<U> findAll(TypeSafeMatcher<U> matcher);

	<U extends Tree> Iterable<U> leftFindAll(TypeSafeMatcher<U> matcher);

	<U extends Tree> Iterable<U> rightFindAll(TypeSafeMatcher<U> matcher);

	T insertLeadingComment(String commentString);

	T insertTrailingComment(String commentString);

	T insertNewLineBefore();
}
