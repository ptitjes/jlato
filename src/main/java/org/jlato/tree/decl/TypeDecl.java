package org.jlato.tree.decl;

import org.jlato.tree.NodeList;
import org.jlato.tree.expr.NameExpr;

public abstract class TypeDecl extends BodyDecl {

	protected TypeDecl(SLocation location) {
		super(location);
	}

	public NameExpr name() {
		return location.nodeChild(NAME);
	}

	public TypeDecl withName(NameExpr name) {
		return location.nodeWithChild(NAME, name);
	}

	public int modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public TypeDecl withModifiers(int modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public NodeList<BodyDecl> members() {
		return location.nodeChild(MEMBERS);
	}

	public TypeDecl withMembers(NodeList<BodyDecl> members) {
		return location.nodeWithChild(MEMBERS, members);
	}

	private static final int NAME = 1;
	private static final int MODIFIERS = 2;
	private static final int MEMBERS = 3;
}
