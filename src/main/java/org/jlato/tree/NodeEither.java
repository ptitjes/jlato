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

import org.jlato.internal.bu.SNodeEitherState;
import org.jlato.internal.bu.SNodeEitherState.EitherSide;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;

import java.util.Iterator;

/**
 * @author Didier Villevalois
 */
public class NodeEither<TL extends Tree, TR extends Tree> extends TreeBase<SNodeEitherState, NodeEither<TL, TR>, NodeEither<TL, TR>> implements Tree {

	public final static Kind<Tree, Tree> kind = new Kind<Tree, Tree>();

	@SuppressWarnings("unchecked")
	public static <TL extends Tree, TR extends Tree> Kind<TL, TR> kind() {
		return (Kind<TL, TR>) kind;
	}

	public static class Kind<TL extends Tree, TR extends Tree> implements SKind<SNodeEitherState> {
		public Tree instantiate(SLocation<SNodeEitherState> location) {
			return new NodeEither<TL, TR>(location);
		}

		public LexicalShape shape() {
			throw new UnsupportedOperationException();
		}
	}

	public static <TL extends Tree, TR extends Tree> NodeEither<TL, TR> left(TL tree) {
		if (tree == null) throw new NullPointerException();
		return new NodeEither<TL, TR>(tree, EitherSide.Left);
	}

	public static <TL extends Tree, TR extends Tree> NodeEither<TL, TR> right(TR tree) {
		if (tree == null) throw new NullPointerException();
		return new NodeEither<TL, TR>(tree, EitherSide.Right);
	}

	private NodeEither(SLocation<SNodeEitherState> location) {
		super(location);
	}

	private NodeEither(Tree element, EitherSide side) {
		super(new SLocation<SNodeEitherState>(new STree<SNodeEitherState>(kind, new SNodeEitherState(treeOf(element), side))));
	}

	public boolean isLeft() {
		return location.tree.state.side == EitherSide.Left;
	}

	public boolean isRight() {
		return location.tree.state.side == EitherSide.Right;
	}

	public TL left() {
		return location.safeTraversal(SNodeEitherState.leftTraversal());
	}

	public TR right() {
		return location.safeTraversal(SNodeEitherState.rightTraversal());
	}

	public NodeEither<TL, TR> setLeft(TL element) {
		return location.safeTraversalReplace(SNodeEitherState.leftTraversal(), element);
	}

	public NodeEither<TL, TR> setRight(TR element) {
		return location.safeTraversalReplace(SNodeEitherState.rightTraversal(), element);
	}

	public String mkString(String start, String sep, String end) {
		StringBuilder builder = new StringBuilder();
		builder.append(start);

		Tree tree = location.safeTraversal(SNodeEitherState.elementTraversal());
		if (tree != null) builder.append(tree.toString());

		builder.append(end);
		return builder.toString();
	}
}
