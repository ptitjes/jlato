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

	public LambdaExpr(NodeList<Parameter> parameters, boolean parametersEnclosed, Stmt body) {
		super(new SLocation(new SNode(kind, runOf(parameters, parametersEnclosed, body))));
	}

	public NodeList<Parameter> parameters() {
		return location.nodeChild(PARAMETERS);
	}

	public LambdaExpr withParameters(NodeList<Parameter> parameters) {
		return location.nodeWithChild(PARAMETERS, parameters);
	}

	public boolean parametersEnclosed() {
		return location.nodeChild(PARAMETERS_ENCLOSED);
	}

	public LambdaExpr withParametersEnclosed(boolean parametersEnclosed) {
		return location.nodeWithChild(PARAMETERS_ENCLOSED, parametersEnclosed);
	}

	public Stmt body() {
		return location.nodeChild(BODY);
	}

	public LambdaExpr withBody(Stmt body) {
		return location.nodeWithChild(BODY, body);
	}

	private static final int PARAMETERS = 0;
	private static final int PARAMETERS_ENCLOSED = 1;
	private static final int BODY = 2;
}
