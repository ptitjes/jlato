package org.jlato.tree.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.SLocation;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class ContinueStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ContinueStmt instantiate(SLocation location) {
			return new ContinueStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ContinueStmt(SLocation location) {
		super(location);
	}

	public ContinueStmt(Name id) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(id)))));
	}

	public Name id() {
		return location.nodeChild(ID);
	}

	public ContinueStmt withId(Name id) {
		return location.nodeWithChild(ID, id);
	}

	private static final int ID = 0;

	public final static LexicalShape shape = composite(
			token(LToken.Continue),
			child(ID),
			token(LToken.SemiColon)
	);
}
