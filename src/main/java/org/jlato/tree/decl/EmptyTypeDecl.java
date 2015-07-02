package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Tree;

public class EmptyTypeDecl extends Decl implements TopLevel, Member {

	public final static Tree.Kind kind = new Tree.Kind() {
		public EmptyTypeDecl instantiate(SLocation location) {
			return new EmptyTypeDecl(location);
		}
	};

	private EmptyTypeDecl(SLocation location) {
		super(location);
	}

	public EmptyTypeDecl() {
		super(new SLocation(new SNode(kind, runOf())));
	}

}
