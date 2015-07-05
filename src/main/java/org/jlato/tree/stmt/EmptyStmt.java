package org.jlato.tree.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SLeafState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.SLocation;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.token;

public class EmptyStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public EmptyStmt instantiate(SLocation location) {
			return new EmptyStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private EmptyStmt(SLocation location) {
		super(location);
	}

	public EmptyStmt() {
		super(new SLocation(new SLeaf(kind, new SLeafState(dataOf()))));
	}

	public final static LexicalShape shape = token(LToken.SemiColon);
}
