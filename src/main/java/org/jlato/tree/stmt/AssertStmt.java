package org.jlato.tree.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.space;

public class AssertStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public AssertStmt instantiate(SLocation location) {
			return new AssertStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private AssertStmt(SLocation location) {
		super(location);
	}

	public AssertStmt(Expr check, Expr msg) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(check, msg)))));
	}

	public Expr check() {
		return location.nodeChild(CHECK);
	}

	public AssertStmt withCheck(Expr check) {
		return location.nodeWithChild(CHECK, check);
	}

	public Expr msg() {
		return location.nodeChild(MSG);
	}

	public AssertStmt withMsg(Expr msg) {
		return location.nodeWithChild(MSG, msg);
	}

	private static final int CHECK = 0;
	private static final int MSG = 1;

	public final static LexicalShape shape = composite(
			token(LToken.Assert),
			child(CHECK),
			nonNullChild(MSG,
					composite(
							token(LToken.Colon).withSpacing(space(), space()),
							child(MSG)
					)
			),
			token(LToken.SemiColon)
	);
}
