package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.Tree;

public class VariableDeclarator extends Tree {

	public final static Tree.Kind kind = new Tree.Kind() {
		public VariableDeclarator instantiate(SLocation location) {
			return new VariableDeclarator(location);
		}
	};

	private VariableDeclarator(SLocation location) {
		super(location);
	}

	public VariableDeclarator(VariableDeclaratorId id, Expr init) {
		super(new SLocation(new SNode(kind, runOf(id, init))));
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
}
