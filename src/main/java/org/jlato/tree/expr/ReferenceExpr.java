package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.Type;

public class ReferenceExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ReferenceExpr instantiate(SLocation location) {
			return new ReferenceExpr(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private ReferenceExpr(SLocation location) {
		super(location);
	}

	public ReferenceExpr(Expr scope, NodeList<Type> typeArguments, Name identifier) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(scope, typeArguments, identifier)))));
	}

	public Expr scope() {
		return location.nodeChild(SCOPE);
	}

	public ReferenceExpr withScope(Expr scope) {
		return location.nodeWithChild(SCOPE, scope);
	}

	public NodeList<Type> typeArguments() {
		return location.nodeChild(TYPE_ARGUMENTS);
	}

	public ReferenceExpr withTypeArguments(NodeList<Type> typeArguments) {
		return location.nodeWithChild(TYPE_ARGUMENTS, typeArguments);
	}

	public Name identifier() {
		return location.nodeChild(IDENTIFIER);
	}

	public ReferenceExpr withIdentifier(Name identifier) {
		return location.nodeWithChild(IDENTIFIER, identifier);
	}

	private static final int SCOPE = 0;
	private static final int TYPE_ARGUMENTS = 1;
	private static final int IDENTIFIER = 2;
}
