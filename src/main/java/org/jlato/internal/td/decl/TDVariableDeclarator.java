package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.decl.SVariableDeclarator;
import org.jlato.internal.bu.decl.SVariableDeclaratorId;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.VariableDeclarator;
import org.jlato.tree.decl.VariableDeclaratorId;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public class TDVariableDeclarator extends TreeBase<SVariableDeclarator, Tree, VariableDeclarator> implements VariableDeclarator {

	public Kind kind() {
		return Kind.VariableDeclarator;
	}

	public TDVariableDeclarator(SLocation<SVariableDeclarator> location) {
		super(location);
	}

	public TDVariableDeclarator(VariableDeclaratorId id, NodeOption<Expr> init) {
		super(new SLocation<SVariableDeclarator>(SVariableDeclarator.make(TreeBase.<SVariableDeclaratorId>treeOf(id), TreeBase.<SNodeOptionState>treeOf(init))));
	}

	public VariableDeclaratorId id() {
		return location.safeTraversal(SVariableDeclarator.ID);
	}

	public VariableDeclarator withId(VariableDeclaratorId id) {
		return location.safeTraversalReplace(SVariableDeclarator.ID, id);
	}

	public VariableDeclarator withId(Mutation<VariableDeclaratorId> mutation) {
		return location.safeTraversalMutate(SVariableDeclarator.ID, mutation);
	}

	public NodeOption<Expr> init() {
		return location.safeTraversal(SVariableDeclarator.INIT);
	}

	public VariableDeclarator withInit(NodeOption<Expr> init) {
		return location.safeTraversalReplace(SVariableDeclarator.INIT, init);
	}

	public VariableDeclarator withInit(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SVariableDeclarator.INIT, mutation);
	}
}
