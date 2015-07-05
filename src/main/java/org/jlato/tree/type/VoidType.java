package org.jlato.tree.type;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.Type;

import static org.jlato.internal.shapes.LexicalShape.Factory.token;

public class VoidType extends Type {

	public final static Tree.Kind kind = new Tree.Kind() {
		public VoidType instantiate(SLocation location) {
			return new VoidType(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private VoidType(SLocation location) {
		super(location);
	}

	public VoidType() {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf()))));
	}

	public final static LexicalShape shape = token(LToken.Void);
}
