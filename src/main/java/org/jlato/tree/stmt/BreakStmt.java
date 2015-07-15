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

package org.jlato.tree.stmt;

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
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;

public class BreakStmt extends TreeBase<SNodeState> implements Stmt {

	public final static SKind<SNodeState> kind = new SKind<SNodeState>() {
		public BreakStmt instantiate(SLocation<SNodeState> location) {
			return new BreakStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private BreakStmt(SLocation<SNodeState> location) {
		super(location);
	}

	public BreakStmt(NodeOption<Name> id) {
		super(new SLocation<SNodeState>(new STree<SNodeState>(kind, new SNodeState(treesOf(id)))));
	}

	public NodeOption<Name> id() {
		return location.safeTraversal(ID);
	}

	public BreakStmt withId(NodeOption<Name> id) {
		return location.safeTraversalReplace(ID, id);
	}

	public BreakStmt withId(Mutation<NodeOption<Name>> mutation) {
		return location.safeTraversalMutate(ID, mutation);
	}

	private static final STraversal<SNodeState> ID = SNodeState.childTraversal(0);

	public final static LexicalShape shape = composite(
			token(LToken.Break),
			when(childIs(ID, some()), child(ID, element())),
			token(LToken.SemiColon)
	);
}
