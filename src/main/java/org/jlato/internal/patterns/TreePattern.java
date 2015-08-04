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

	private final STree<? extends STreeState> pattern;

	public TreePattern(STree<? extends STreeState> pattern) {
		this.pattern = pattern;
	}

	@Override
	public Substitution match(Object object, Substitution substitution) {
		if (!(object instanceof Tree)) return null;
		return matchTree(pattern, TDTree.treeOf((Tree) object), substitution);
	}

	protected static Substitution matchTree(STree<?> pattern, STree<?> tree, Substitution substitution) {
		STreeState patternState = pattern.state;
		STreeState state = tree.state;
		boolean isVariable = patternState instanceof SVarState;

		if (isVariable) {
			String name = ((SVarState) patternState).name;
			// Not an anonymous var
			if (!name.equals("_")) {
				if (substitution.binds(name)) {
					STree<?> expected = TDTree.treeOf((Tree) substitution.get(name));
					substitution = matchTree(expected, tree, substitution);
				} else {
					substitution = substitution.bind(name, tree.asTree());
				}
			}
		} else if (patternState instanceof SNodeState) {
			if (!(state instanceof SNodeState)) return null;
			if (((SNodeState) patternState).kind() != ((SNodeState) state).kind()) return null;

			substitution = mathData((SNodeState) patternState, (SNodeState) state, substitution);
			if (substitution == null) return null;
			substitution = mathChildren((SNodeState) patternState, (SNodeState) state, substitution);
		} else if (patternState instanceof SNodeOptionState) {
			if (!(state instanceof SNodeOptionState)) return null;
			if (substitution == null) return null;
			substitution = mathChildren((SNodeOptionState) patternState, (SNodeOptionState) state, substitution);
		} else if (patternState instanceof SNodeListState) {
			if (!(state instanceof SNodeListState)) return null;
			if (substitution == null) return null;
			substitution = mathChildren((SNodeListState) patternState, (SNodeListState) state, substitution);
		}
		return substitution;
	}

	protected static <S extends SNodeState<S>> Substitution mathData(S patternState, S state, Substitution substitution) {
		for (SProperty prop : patternState.allProperties()) {
			substitution = matchObject(prop.retrieve(patternState), prop.retrieve(state), substitution);
			if (substitution == null) return null;
		}
		return substitution;
	}

	protected static <S extends SNodeState<S>> Substitution mathChildren(S patternState, S state, Substitution substitution) {
		STraversal traversal = patternState.firstChild();
		while (traversal != null) {
			substitution = matchTree(traversal.traverse(patternState), traversal.traverse(state), substitution);
			if (substitution == null) return null;
			traversal = traversal.rightSibling(patternState);
		}
		return substitution;
	}

	private static Substitution mathChildren(SNodeOptionState patternState, SNodeOptionState state, Substitution substitution) {
		STree patternElement = patternState.element;
		STree element = state.element;
		return patternElement == null && element == null ? substitution :
				patternElement == null || element == null ? null :
						matchTree(patternElement, element, substitution);
	}

	private static Substitution mathChildren(SNodeListState patternState, SNodeListState state, Substitution substitution) {
		Vector<STree<?>> patternChildren = patternState.children;
		Vector<STree<?>> children = state.children;
		if (patternChildren.size() != children.size()) return null;

		for (int i = 0; i < patternChildren.size(); i++) {
			substitution = matchTree(patternChildren.get(i), children.get(i), substitution);
			if (substitution == null) return null;
		}
		return substitution;
	}

	protected static Substitution matchObject(Object pattern, Object object, Substitution substitution) {
		if (pattern instanceof STree) {
			STreeState patternState = ((STree) pattern).state;

			if (patternState instanceof SVarState) {
				String name = ((SVarState) patternState).name;
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

	private <S extends STreeState> STree<S> buildTree(STree<S> pattern, Substitution substitution) {
		STreeState patternState = pattern.state;
		if (patternState instanceof SVarState) {
			String name = ((SVarState) patternState).name;
			return TDTree.treeOf((Tree) substitution.get(name));
		} else {
			final STree<S> tree;
			if (patternState instanceof SNodeState) {
				final SNodeState nodeState = (SNodeState) patternState;
				tree = (STree<S>) buildNodeTree(nodeState.kind(), nodeState, substitution);
			} else if (patternState instanceof SNodeOptionState) {
				tree = (STree<S>) buildNodeOptionTree((SNodeOptionState) patternState, substitution);
			} else if (patternState instanceof SNodeListState) {
				tree = (STree<S>) buildNodeListTree((SNodeListState) patternState, substitution);
			} else {
				// TODO Handle NodeEither
				throw new UnsupportedOperationException();
			}
			return tree.withDressing(pattern.dressing);
		}
	}

	private <S extends SNodeState<S>> STree<S> buildNodeTree(Kind kind, S patternState, Substitution substitution) {
		S buildState = patternState;

		STraversal traversal = patternState.firstChild();
		while (traversal != null) {
			buildState = (S) traversal.rebuildParentState(buildState, buildTree(traversal.traverse(patternState), substitution));
			traversal = traversal.rightSibling(patternState);
		}
		return new STree<S>(buildState);
	}

	private STree<SNodeOptionState> buildNodeOptionTree(SNodeOptionState patternState, Substitution substitution) {
		final STree<? extends STreeState> elementTree = patternState.element == null ? null : buildTree(patternState.element, substitution);
		return new STree<SNodeOptionState>(new SNodeOptionState(elementTree));
	}

	private STree<SNodeListState> buildNodeListTree(SNodeListState patternState, Substitution substitution) {
		Builder<STree<?>, Vector<STree<?>>> childrenBuilder = Vector.<STree<?>>factory().newBuilder();
		for (STree<?> childPattern : patternState.children) {
			childrenBuilder.add(buildTree(childPattern, substitution));
		}
		return new STree<SNodeListState>(new SNodeListState(childrenBuilder.build()));
	}

	private ArrayList<Object> buildData(ArrayList<Object> pattern, Substitution substitution) {
		Builder<Object, ArrayList<Object>> childrenBuilder = ArrayList.<Object>factory().newBuilder();
		for (Object childPattern : pattern) {
			if (childPattern instanceof STree) {
				STreeState patternState = ((STree) childPattern).state;
				if (patternState instanceof SVarState) {
					String name = ((SVarState) patternState).name;

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
