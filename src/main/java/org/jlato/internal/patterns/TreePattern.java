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

package org.jlato.internal.patterns;

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.Vector;
import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.rewrite.Pattern;
import org.jlato.rewrite.Substitution;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;

/**
 * @author Didier Villevalois
 */
public class TreePattern<T extends Tree> extends Pattern<T> {

	private final BUTree<? extends STree> pattern;

	public TreePattern(BUTree<? extends STree> pattern) {
		this.pattern = pattern;
	}

	@Override
	public Substitution match(Object object, Substitution substitution) {
		if (!(object instanceof Tree)) return null;
		return matchTree(pattern, TDTree.treeOf((Tree) object), substitution);
	}

	protected static Substitution matchTree(BUTree<?> pattern, BUTree<?> tree, Substitution substitution) {
		STree patternState = pattern.state;
		STree state = tree.state;
		boolean isVariable = patternState instanceof SVar;

		if (isVariable) {
			String name = ((SVar) patternState).name;
			// Not an anonymous var
			if (!name.equals("_")) {
				if (substitution.binds(name)) {
					BUTree<?> expected = TDTree.treeOf((Tree) substitution.get(name));
					substitution = matchTree(expected, tree, substitution);
				} else {
					substitution = substitution.bind(name, tree.asTree());
				}
			}
		} else if (patternState instanceof SNode) {
			if (!(state instanceof SNode)) return null;
			if (((SNode) patternState).kind() != ((SNode) state).kind()) return null;

			substitution = mathData((SNode) patternState, (SNode) state, substitution);
			if (substitution == null) return null;
			substitution = mathChildren((SNode) patternState, (SNode) state, substitution);
		} else if (patternState instanceof SNodeOption) {
			if (!(state instanceof SNodeOption)) return null;
			if (substitution == null) return null;
			substitution = mathChildren((SNodeOption) patternState, (SNodeOption) state, substitution);
		} else if (patternState instanceof SNodeList) {
			if (!(state instanceof SNodeList)) return null;
			if (substitution == null) return null;
			substitution = mathChildren((SNodeList) patternState, (SNodeList) state, substitution);
		}
		return substitution;
	}

	protected static <S extends SNode<S>> Substitution mathData(S patternState, S state, Substitution substitution) {
		for (SProperty prop : patternState.allProperties()) {
			substitution = matchObject(prop.retrieve(patternState), prop.retrieve(state), substitution);
			if (substitution == null) return null;
		}
		return substitution;
	}

	protected static <S extends SNode<S>> Substitution mathChildren(S patternState, S state, Substitution substitution) {
		STraversal traversal = patternState.firstChild();
		while (traversal != null) {
			substitution = matchTree(traversal.traverse(patternState), traversal.traverse(state), substitution);
			if (substitution == null) return null;
			traversal = traversal.rightSibling(patternState);
		}
		return substitution;
	}

	private static Substitution mathChildren(SNodeOption patternState, SNodeOption state, Substitution substitution) {
		BUTree patternElement = patternState.element;
		BUTree element = state.element;
		return patternElement == null && element == null ? substitution :
				patternElement == null || element == null ? null :
						matchTree(patternElement, element, substitution);
	}

	private static Substitution mathChildren(SNodeList patternState, SNodeList state, Substitution substitution) {
		Vector<BUTree<?>> patternChildren = patternState.children;
		Vector<BUTree<?>> children = state.children;
		if (patternChildren.size() != children.size()) return null;

		for (int i = 0; i < patternChildren.size(); i++) {
			substitution = matchTree(patternChildren.get(i), children.get(i), substitution);
			if (substitution == null) return null;
		}
		return substitution;
	}

	protected static Substitution matchObject(Object pattern, Object object, Substitution substitution) {
		if (pattern instanceof BUTree) {
			STree patternState = ((BUTree) pattern).state;

			if (patternState instanceof SVar) {
				String name = ((SVar) patternState).name;
				// Not an anonymous var
				if (!name.equals("_")) {
					if (substitution.binds(name)) {
						Object expected = substitution.get(name);
						substitution = matchObject(expected, object, substitution);
					} else {
						substitution = substitution.bind(name, object);
					}
				}
			}
			return substitution;
		} else {
			return (pattern == null && object == null) || (pattern != null && pattern.equals(object)) ? substitution : null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public T build(Substitution substitution) {
		return (T) new TDLocation(null, buildTree(pattern, substitution)).facade;
	}

	private <S extends STree> BUTree<S> buildTree(BUTree<S> pattern, Substitution substitution) {
		STree patternState = pattern.state;
		if (patternState instanceof SVar) {
			String name = ((SVar) patternState).name;
			return TDTree.treeOf((Tree) substitution.get(name));
		} else {
			final BUTree<S> tree;
			if (patternState instanceof SNode) {
				final SNode nodeState = (SNode) patternState;
				tree = (BUTree<S>) buildNodeTree(nodeState.kind(), nodeState, substitution);
			} else if (patternState instanceof SNodeOption) {
				tree = (BUTree<S>) buildNodeOptionTree((SNodeOption) patternState, substitution);
			} else if (patternState instanceof SNodeList) {
				tree = (BUTree<S>) buildNodeListTree((SNodeList) patternState, substitution);
			} else {
				// TODO Handle NodeEither
				throw new UnsupportedOperationException();
			}
			return tree.withDressing(pattern.dressing);
		}
	}

	private <S extends SNode<S>> BUTree<S> buildNodeTree(Kind kind, S patternState, Substitution substitution) {
		S buildState = patternState;

		STraversal traversal = patternState.firstChild();
		while (traversal != null) {
			buildState = (S) traversal.rebuildParentState(buildState, buildTree(traversal.traverse(patternState), substitution));
			traversal = traversal.rightSibling(patternState);
		}
		return new BUTree<S>(buildState);
	}

	private BUTree<SNodeOption> buildNodeOptionTree(SNodeOption patternState, Substitution substitution) {
		final BUTree<? extends STree> elementTree = patternState.element == null ? null : buildTree(patternState.element, substitution);
		return new BUTree<SNodeOption>(new SNodeOption(elementTree));
	}

	private BUTree<SNodeList> buildNodeListTree(SNodeList patternState, Substitution substitution) {
		Builder<BUTree<?>, Vector<BUTree<?>>> childrenBuilder = Vector.<BUTree<?>>factory().newBuilder();
		for (BUTree<?> childPattern : patternState.children) {
			childrenBuilder.add(buildTree(childPattern, substitution));
		}
		return new BUTree<SNodeList>(new SNodeList(childrenBuilder.build()));
	}

	private ArrayList<Object> buildData(ArrayList<Object> pattern, Substitution substitution) {
		Builder<Object, ArrayList<Object>> childrenBuilder = ArrayList.<Object>factory().newBuilder();
		for (Object childPattern : pattern) {
			if (childPattern instanceof BUTree) {
				STree patternState = ((BUTree) childPattern).state;
				if (patternState instanceof SVar) {
					String name = ((SVar) patternState).name;

					childrenBuilder.add(substitution.get(name));
				} else {
					childrenBuilder.add(childPattern);
				}
			} else {
				childrenBuilder.add(childPattern);
			}
		}
		return childrenBuilder.build();
	}
}
