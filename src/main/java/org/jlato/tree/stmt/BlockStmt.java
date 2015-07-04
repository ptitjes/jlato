package org.jlato.tree.stmt;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Stmt;
import org.jlato.tree.Tree;

public class BlockStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public BlockStmt instantiate(SLocation location) {
			return new BlockStmt(location);
		}
	};

	private BlockStmt(SLocation location) {
		super(location);
	}

	public BlockStmt(NodeList<Stmt> stmts) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(stmts)))));
	}

	public NodeList<Stmt> stmts() {
		return location.nodeChild(STMTS);
	}

	public BlockStmt withStmts(NodeList<Stmt> stmts) {
		return location.nodeWithChild(STMTS, stmts);
	}

	private static final int STMTS = 0;
}
