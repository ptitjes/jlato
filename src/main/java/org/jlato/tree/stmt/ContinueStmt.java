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
import org.jlato.tree.Kind;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Mutation;
import org.jlato.tree.NodeOption;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LSCondition.childIs;
import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import org.jlato.internal.bu.*;
import org.jlato.tree.Tree;
import org.jlato.internal.bu.*;
import org.jlato.internal.td.*;

public class ContinueStmt extends TreeBase<ContinueStmt.State, Stmt, ContinueStmt> implements Stmt {

	public Kind kind() {
		return Kind.ContinueStmt;
	}

	private ContinueStmt(SLocation<ContinueStmt.State> location) {
		super(location);
	}

	public static STree<ContinueStmt.State> make(STree<SNodeOptionState> id) {
		return new STree<ContinueStmt.State>(new ContinueStmt.State(id));
	}

	public ContinueStmt(NodeOption<Name> id) {
		super(new SLocation<ContinueStmt.State>(make(TreeBase.<SNodeOptionState>nodeOf(id))));
	}

	public NodeOption<Name> id() {
		return location.safeTraversal(ID);
	}

	public ContinueStmt withId(NodeOption<Name> id) {
		return location.safeTraversalReplace(ID, id);
	}

	public ContinueStmt withId(Mutation<NodeOption<Name>> mutation) {
		return location.safeTraversalMutate(ID, mutation);
	}

	public final static LexicalShape shape = composite(
			token(LToken.Continue),
			when(childIs(ID, some()), child(ID, element())),
			token(LToken.SemiColon)
	);
}
