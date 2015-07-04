package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.Type;

public class SelectExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public SelectExpr instantiate(SLocation location) {
			return new SelectExpr(location);
		}
	};

	private SelectExpr(SLocation location) {
		super(location);
	}

	public SelectExpr(Expr scope, NodeList<Type> typeArgs, Name field) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(scope, typeArgs, field)))));
	}

	public Expr scope() {
		return location.nodeChild(SCOPE);
	}

	public SelectExpr withScope(Expr scope) {
		return location.nodeWithChild(SCOPE, scope);
	}

	public NodeList<Type> typeArgs() {
		return location.nodeChild(TYPE_ARGS);
	}

	public SelectExpr withTypeArgs(NodeList<Type> typeArgs) {
		return location.nodeWithChild(TYPE_ARGS, typeArgs);
	}

	public Name field() {
		return location.nodeChild(FIELD);
	}

	public SelectExpr withField(Name field) {
		return location.nodeWithChild(FIELD, field);
	}

	private static final int SCOPE = 0;
	private static final int TYPE_ARGS = 1;
	private static final int FIELD = 2;
}
