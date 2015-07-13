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

import org.jlato.internal.td.SLocation;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public abstract class RewriteStrategy {

	@SuppressWarnings("unchecked")
	public <R extends Tree> R rewrite(R tree, Rewriter rewriter) {
		SLocation location = Tree.locationOf(tree);
		return (R) doRewrite(location, rewriter).facade;
	}

	// TODO Trampoline recursion

	protected abstract SLocation doRewrite(SLocation location, Rewriter rewriter);

	public static final RewriteStrategy OutermostNeeded = new RewriteStrategy() {

		protected SLocation doRewrite(SLocation location, Rewriter rewriter) {
			SLocation maybeRewrote = doApplyRewrite(location, rewriter);

			// If rewrote, then try to rewrite again
			if (maybeRewrote != location) return doRewrite(maybeRewrote, rewriter);

			// Else try to rewrite children
			SLocation maybeRewroteThroughChild = doRewriteChildren(location, rewriter);

			// If rewrote, then try to rewrite again
			if (maybeRewroteThroughChild != location) return doRewrite(maybeRewroteThroughChild, rewriter);

			return location;
		}

		private SLocation doApplyRewrite(SLocation location, Rewriter rewriter) {
			SLocation rewrote = Tree.locationOf(rewriter.rewrite(location.facade));
			return location.withTree(rewrote.tree);
		}

		private SLocation doRewriteChildren(SLocation location, Rewriter rewriter) {
			SLocation child = location.firstChild();
			if (child == null) return location;

			while (child != null) {
				SLocation maybeRewrote = doRewrite(child, rewriter);

				// If rewrote, then try to rewrite parent again
				if (maybeRewrote != child) return maybeRewrote.parent();

				child = child.rightSibling();
			}

			return location;
		}
	};
}
