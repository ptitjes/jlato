package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.Type;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class ClassExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ClassExpr instantiate(SLocation location) {
			return new ClassExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ClassExpr(SLocation location) {
		super(location);
	}

	public ClassExpr(Type type) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(type)))));
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public ClassExpr withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	private static final int TYPE = 0;

	public final static LexicalShape shape = composite(
			child(TYPE),
			token(LToken.Dot), token(LToken.Class)
	);
}
