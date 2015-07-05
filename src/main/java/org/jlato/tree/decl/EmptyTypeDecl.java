package org.jlato.tree.decl;

import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SLeafState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

public class EmptyTypeDecl extends TypeDecl implements TopLevel, Member {

	public final static Tree.Kind kind = new Tree.Kind() {
		public EmptyTypeDecl instantiate(SLocation location) {
			return new EmptyTypeDecl(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private EmptyTypeDecl(SLocation location) {
		super(location);
	}

	public EmptyTypeDecl() {
		super(new SLocation(new SLeaf(kind, new SLeafState(dataOf()))));
	}
}
