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

package org.jlato.rewrite;

import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public abstract class RewriteRules {

	public abstract <T extends Tree> T rewrite(T t);

	public RewriteRules and(final RewriteRules other) {
		return new RewriteRules() {
			@Override
			public <T extends Tree> T rewrite(T t) {
				T maybeRewrote = RewriteRules.this.rewrite(t);
				return maybeRewrote != t ? maybeRewrote : other.rewrite(t);
			}
		};
	}
}