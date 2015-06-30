package org.jlato.tree.expr;

import org.jlato.internal.bu.LIdentifier;
import org.jlato.internal.bu.SLeaf;
import org.jlato.tree.Expr;
import org.jlato.tree.Tree;

public class NameExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public NameExpr instantiate(SLocation location) {
			return new NameExpr(location);
		}
	};

	private NameExpr(SLocation location) {
		super(location);
	}

	public NameExpr(LIdentifier identifier) {
		super(new SLocation(new SLeaf(kind, identifier)));
	}

	public NameExpr(String name) {
		this(new LIdentifier(name));
	}

	public String name() {
		return location.leafToken().toString();
	}

	public NameExpr withName(String name) {
		return location.leafWithToken(new LIdentifier(name));
	}
}
