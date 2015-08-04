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

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.coll.SNodeEither;
import org.jlato.internal.bu.coll.SNodeEither.EitherSide;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;

/**
 * @author Didier Villevalois
 */
public class NodeEither<TL extends Tree, TR extends Tree> extends TDTree<SNodeEither, NodeEither<TL, TR>, NodeEither<TL, TR>> implements Tree {

	public static <TL extends Tree, TR extends Tree> NodeEither<TL, TR> left(TL tree) {
		if (tree == null) throw new NullPointerException();
		return new NodeEither<TL, TR>(tree, EitherSide.Left);
	}

	public static <TL extends Tree, TR extends Tree> NodeEither<TL, TR> right(TR tree) {
		if (tree == null) throw new NullPointerException();
		return new NodeEither<TL, TR>(tree, EitherSide.Right);
	}

	public NodeEither(TDLocation<SNodeEither> location) {
		super(location);
	}

	private NodeEither(Tree element, EitherSide side) {
		super(new TDLocation<SNodeEither>(new BUTree<SNodeEither>(new SNodeEither(treeOf(element), side))));
	}

	public boolean isLeft() {
		return location.tree.state.side == EitherSide.Left;
	}

	public boolean isRight() {
		return location.tree.state.side == EitherSide.Right;
	}

	@SuppressWarnings("unchecked")
	public TL left() {
		return (TL) location.safeTraversal(SNodeEither.leftTraversal());
	}

	@SuppressWarnings("unchecked")
	public TR right() {
		return (TR) location.safeTraversal(SNodeEither.rightTraversal());
	}

	public NodeEither<TL, TR> setLeft(TL element) {
		return location.safeTraversalReplace(SNodeEither.leftTraversal(), element);
	}

	public NodeEither<TL, TR> setRight(TR element) {
		return location.safeTraversalReplace(SNodeEither.rightTraversal(), element);
	}

	public String mkString(String start, String sep, String end) {
		StringBuilder builder = new StringBuilder();
		builder.append(start);

		Tree tree = location.safeTraversal(SNodeEither.elementTraversal());
		builder.append(tree);

		builder.append(end);
		return builder.toString();
	}
}
