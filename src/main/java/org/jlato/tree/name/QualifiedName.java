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

package org.jlato.tree.name;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SKind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;

public class QualifiedName extends TreeBase<QualifiedName.State, Tree, QualifiedName> implements Tree {

	public final static SKind<QualifiedName.State> kind = new SKind<QualifiedName.State>() {

	};

	public static QualifiedName of(String nameString) {
		final String[] split = nameString.split("\\.");
		QualifiedName name = null;
		for (String part : split) {
			name = new QualifiedName(NodeOption.of(name), new Name(part));
		}
		return name;
	}

	public static STree<QualifiedName.State> make(NodeOption<QualifiedName> qualifier, Name name) {
		return new STree<QualifiedName.State>(kind, new QualifiedName.State(TreeBase.<SNodeOptionState>nodeOf(qualifier), TreeBase.<Name.State>nodeOf(name)));
	}

	private QualifiedName(SLocation<QualifiedName.State> location) {
		super(location);
	}

	public QualifiedName(NodeOption<QualifiedName> qualifier, Name name) {
		super(new SLocation<QualifiedName.State>(make(qualifier, name)));
	}

	public NodeOption<QualifiedName> qualifier() {
		return location.safeTraversal(QUALIFIER);
	}

	public QualifiedName withQualifier(NodeOption<QualifiedName> qualifier) {
		return location.safeTraversalReplace(QUALIFIER, qualifier);
	}

	public QualifiedName withQualifier(Mutation<NodeOption<QualifiedName>> mutation) {
		return location.safeTraversalMutate(QUALIFIER, mutation);
	}

	public Name name() {
		return location.safeTraversal(NAME);
	}

	public QualifiedName withName(Name name) {
		return location.safeTraversalReplace(NAME, name);
	}

	public QualifiedName withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(NAME, mutation);
	}

	public boolean isQualified() {
		return qualifier().isDefined();
	}

	@Override
	public String toString() {
		final NodeOption<QualifiedName> qualifier = qualifier();
		final Name name = name();
		return qualifier.isDefined() ? qualifier.get().toString() + "." + name.toString() : name.toString();
	}

	private static final STraversal<QualifiedName.State> QUALIFIER = new STraversal<QualifiedName.State>() {

		public STree<?> traverse(QualifiedName.State state) {
			return state.qualifier;
		}

		public QualifiedName.State rebuildParentState(QualifiedName.State state, STree<?> child) {
			return state.withQualifier((STree) child);
		}

		public STraversal<QualifiedName.State> leftSibling(QualifiedName.State state) {
			return null;
		}

		public STraversal<QualifiedName.State> rightSibling(QualifiedName.State state) {
			return NAME;
		}
	};
	private static final STraversal<QualifiedName.State> NAME = new STraversal<QualifiedName.State>() {

		public STree<?> traverse(QualifiedName.State state) {
			return state.name;
		}

		public QualifiedName.State rebuildParentState(QualifiedName.State state, STree<?> child) {
			return state.withName((STree) child);
		}

		public STraversal<QualifiedName.State> leftSibling(QualifiedName.State state) {
			return QUALIFIER;
		}

		public STraversal<QualifiedName.State> rightSibling(QualifiedName.State state) {
			return null;
		}
	};

	public final static LexicalShape qualifierShape = composite(element(), token(LToken.Dot));

	public final static LexicalShape shape = composite(
			child(QUALIFIER, when(some(), qualifierShape)),
			child(NAME)
	);

	public static class State extends SNodeState<State> {

		public final STree<SNodeOptionState> qualifier;

		public final STree<Name.State> name;

		State(STree<SNodeOptionState> qualifier, STree<Name.State> name) {
			this.qualifier = qualifier;
			this.name = name;
		}

		public QualifiedName.State withQualifier(STree<SNodeOptionState> qualifier) {
			return new QualifiedName.State(qualifier, name);
		}

		public QualifiedName.State withName(STree<Name.State> name) {
			return new QualifiedName.State(qualifier, name);
		}

		public STraversal<QualifiedName.State> firstChild() {
			return null;
		}

		public STraversal<QualifiedName.State> lastChild() {
			return null;
		}

		public Tree instantiate(SLocation<QualifiedName.State> location) {
			return new QualifiedName(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	}
}
