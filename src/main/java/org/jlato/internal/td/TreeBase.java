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

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.printer.Printer;
import org.jlato.rewrite.MatchVisitor;
import org.jlato.rewrite.Matcher;
import org.jlato.rewrite.Substitution;
import org.jlato.rewrite.TypeSafeMatcher;
import org.jlato.tree.Tree;

import java.util.LinkedList;

/**
 * @author Didier Villevalois
 */
public abstract class TreeBase<S extends STreeState, ST extends Tree, T extends ST> implements Tree {

	protected final SLocation<S> location;

	protected TreeBase(SLocation<S> location) {
		this.location = location;
	}

	public Tree parent() {
		SLocation<?> parentLocation = location.parent();
		return parentLocation == null ? null : parentLocation.facade;
	}

	public Tree root() {
		return location.root().facade;
	}

	@SuppressWarnings("unchecked")
	private T self() {
		return (T) this;
	}

	@Override
	public String toString() {
		// TODO Add specific cases for NodeList, NodeOption, NodeEither
		return Printer.printToString(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TreeBase<?, ?, ?> treeBase = (TreeBase<?, ?, ?>) o;

		return location.tree.equals(treeBase.location.tree);
	}

	@Override
	public int hashCode() {
		return location.tree.hashCode();
	}

	// Combinators

	public <U extends Tree> T forAll(TypeSafeMatcher<U> matcher, MatchVisitor<U> visitor) {
		return Traversal.forAll(self(), matcher, visitor);
	}

	public Substitution match(Matcher matcher) {
		return matcher.match(this);
	}

	public boolean matches(Matcher matcher) {
		return matcher.match(this) != null;
	}

	public T match(TypeSafeMatcher<T> matcher, MatchVisitor<T> visitor) {
		Substitution match = matcher.match(this);
		return match == null ? self() : visitor.visit(self(), match);
	}

	public <U extends Tree> Iterable<U> findAll(TypeSafeMatcher<U> matcher) {
		final LinkedList<U> list = new LinkedList<U>();
		forAll(matcher, new MatchVisitor<U>() {
			@Override
			public U visit(U t, Substitution s) {
				list.add(t);
				return t;
			}
		});
		return list;
	}

	// Convenience conversion helper methods
	// TODO Move that elsewhere

	public static SLocation<? extends STreeState> locationOf(Tree facade) {
		return facade == null ? null : ((TreeBase<?, ?, ?>) facade).location;
	}

	public static STree<? extends STreeState> treeOf(Tree facade) {
		return facade == null ? null : ((TreeBase<?, ?, ?>) facade).location.tree;
	}

	@SuppressWarnings("unchecked")
	public static <S extends STreeState> STree<S> nodeOf(Tree facade) {
		return facade == null ? null : ((TreeBase<S, ?, ?>) facade).location.tree;
	}
/*

	@SuppressWarnings("unchecked")
	public static STree<SNodeOptionState> nodeOf(NodeOption facade) {
		return facade == null ? null : ((TreeBase<SNodeOptionState, ?, ?>) facade).location.tree;
	}
*/

	public static ArrayList<STree<? extends STreeState>> treesOf(Tree... facades) {
		final Builder<STree<?>, ArrayList<STree<?>>> builder = ArrayList.<STree<?>>factory().newBuilder();
		for (Tree facade : facades) {
			builder.add(treeOf(facade));
		}
		return builder.build();
	}

	public static ArrayList<STree<? extends STreeState>> arrayOf(STree<?>... trees) {
		final Builder<STree<?>, ArrayList<STree<?>>> builder = ArrayList.<STree<?>>factory().newBuilder();
		for (STree<?> tree : trees) {
			builder.add(tree);
		}
		return builder.build();
	}

	public static ArrayList<Object> dataOf(Object... attributes) {
		final Builder<Object, ArrayList<Object>> builder = ArrayList.factory().newBuilder();
		for (Object attribute : attributes) {
			builder.add(attribute);
		}
		return builder.build();
	}

	public static Vector<STree<? extends STreeState>> treeListOf(Tree... facades) {
		final Builder<STree<?>, Vector<STree<?>>> builder = Vector.<STree<?>>factory().newBuilder();
		for (Tree facade : facades) {
			builder.add(treeOf(facade));
		}
		return builder.build();
	}
}
