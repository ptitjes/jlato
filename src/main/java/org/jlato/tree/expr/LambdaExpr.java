package org.jlato.tree.expr;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.*;
import org.jlato.tree.decl.Parameter;

public class LambdaExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public LambdaExpr instantiate(SLocation location) {
			return new LambdaExpr(location);
		}
	};

	private LambdaExpr(SLocation location) {
		super(location);
	}

	public LambdaExpr(NodeList<Parameter> parameters, Stmt body) {
		super(new SLocation(new SNode(kind, runOf(parameters, body))));
	}

	public NodeList<Parameter> parameters() {
		return location.nodeChild(PARAMETERS);
	}

	public LambdaExpr withParameters(NodeList<Parameter> parameters) {
		return location.nodeWithChild(PARAMETERS, parameters);
	}

	public Stmt body() {
		return location.nodeChild(BODY);
	}

	public LambdaExpr withBody(Stmt body) {
		return location.nodeWithChild(BODY, body);
	}

	private static final int PARAMETERS = 0;
	private static final int BODY = 1;
}
