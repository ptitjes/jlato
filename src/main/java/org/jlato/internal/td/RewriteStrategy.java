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

package org.jlato.internal.td;

import org.jlato.rewrite.RewriteRules;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public abstract class RewriteStrategy {

	@SuppressWarnings("unchecked")
	public <R extends Tree> R rewrite(R tree, RewriteRules rewriter) {
		TDLocation location = TDTree.locationOf(tree);
		return (R) doRewrite(location, rewriter).facade;
	}

	// TODO Trampoline recursion

	protected abstract TDLocation doRewrite(TDLocation location, RewriteRules rewriter);

	public static final RewriteStrategy OutermostNeeded = new RewriteStrategy() {

		protected TDLocation doRewrite(TDLocation location, RewriteRules rewriter) {
			TDLocation maybeRewrote = doApplyRewrite(location, rewriter);

			// If rewrote, then try to rewrite again
			if (maybeRewrote != location) return doRewrite(maybeRewrote, rewriter);

			// Else try to rewrite children
			TDLocation maybeRewroteThroughChild = doRewriteChildren(location, rewriter);

			// If rewrote, then try to rewrite again
			if (maybeRewroteThroughChild != location) return doRewrite(maybeRewroteThroughChild, rewriter);

			return location;
		}

		private TDLocation doApplyRewrite(TDLocation location, RewriteRules rewriter) {
			TDLocation rewrote = TDTree.locationOf(rewriter.rewrite(location.facade));
			return location.withTree(rewrote.tree);
		}

		private TDLocation doRewriteChildren(TDLocation location, RewriteRules rewriter) {
			TDLocation child = location.firstChild();
			if (child == null) return location;

			while (child != null) {
				TDLocation maybeRewrote = doRewrite(child, rewriter);

				// If rewrote, then try to rewrite parent again
				if (maybeRewrote != child) return maybeRewrote.parent();

				child = child.rightSibling();
			}

			return location;
		}
	};
}
