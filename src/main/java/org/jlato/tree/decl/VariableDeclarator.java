package org.jlato.tree.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.space;

public class VariableDeclarator extends Tree {

	public final static Tree.Kind kind = new Tree.Kind() {
		public VariableDeclarator instantiate(SLocation location) {
			return new VariableDeclarator(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private VariableDeclarator(SLocation location) {
		super(location);
	}

	public VariableDeclarator(VariableDeclaratorId id, Expr init) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(id, init)))));
	}

	public VariableDeclaratorId id() {
		return location.nodeChild(ID);
	}

	public VariableDeclarator withId(VariableDeclaratorId id) {
		return location.nodeWithChild(ID, id);
	}

	public Expr init() {
		return location.nodeChild(INIT);
	}

	public VariableDeclarator withInit(Expr init) {
		return location.nodeWithChild(INIT, init);
	}

	private static final int ID = 0;
	private static final int INIT = 1;

	public final static LexicalShape shape = composite(
			child(ID),
			nonNullChild(INIT, composite(
					token(LToken.Assign).withSpacing(space(), space()),
					child(INIT)
			))
	);
}
