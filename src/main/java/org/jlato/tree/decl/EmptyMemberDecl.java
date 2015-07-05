package org.jlato.tree.decl;

import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SLeafState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Decl;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

public class EmptyMemberDecl extends Decl implements Member {

	public final static Tree.Kind kind = new Tree.Kind() {
		public EmptyMemberDecl instantiate(SLocation location) {
			return new EmptyMemberDecl(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private EmptyMemberDecl(SLocation location) {
		super(location);
	}

	public EmptyMemberDecl() {
		super(new SLocation(new SLeaf(kind, new SLeafState(dataOf()))));
	}
}
