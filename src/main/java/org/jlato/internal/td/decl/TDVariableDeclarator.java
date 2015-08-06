package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.decl.SVariableDeclarator;
import org.jlato.internal.bu.decl.SVariableDeclaratorId;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeOption;
import org.jlato.tree.decl.VariableDeclarator;
import org.jlato.tree.decl.VariableDeclaratorId;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

/**
 * A variable declarator.
 */
public class TDVariableDeclarator extends TDTree<SVariableDeclarator, Node, VariableDeclarator> implements VariableDeclarator {

	/**
	 * Returns the kind of this variable declarator.
	 *
	 * @return the kind of this variable declarator.
	 */
	public Kind kind() {
		return Kind.VariableDeclarator;
	}

	/**
	 * Creates a variable declarator for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDVariableDeclarator(TDLocation<SVariableDeclarator> location) {
		super(location);
	}

	/**
	 * Creates a variable declarator with the specified child trees.
	 *
	 * @param id   the identifier child tree.
	 * @param init the init child tree.
	 */
	public TDVariableDeclarator(VariableDeclaratorId id, NodeOption<Expr> init) {
		super(new TDLocation<SVariableDeclarator>(SVariableDeclarator.make(TDTree.<SVariableDeclaratorId>treeOf(id), TDTree.<SNodeOption>treeOf(init))));
	}

	/**
	 * Returns the identifier of this variable declarator.
	 *
	 * @return the identifier of this variable declarator.
	 */
	public VariableDeclaratorId id() {
		return location.safeTraversal(SVariableDeclarator.ID);
	}

	/**
	 * Replaces the identifier of this variable declarator.
	 *
	 * @param id the replacement for the identifier of this variable declarator.
	 * @return the resulting mutated variable declarator.
	 */
	public VariableDeclarator withId(VariableDeclaratorId id) {
		return location.safeTraversalReplace(SVariableDeclarator.ID, id);
	}

	/**
	 * Mutates the identifier of this variable declarator.
	 *
	 * @param mutation the mutation to apply to the identifier of this variable declarator.
	 * @return the resulting mutated variable declarator.
	 */
	public VariableDeclarator withId(Mutation<VariableDeclaratorId> mutation) {
		return location.safeTraversalMutate(SVariableDeclarator.ID, mutation);
	}

	/**
	 * Returns the init of this variable declarator.
	 *
	 * @return the init of this variable declarator.
	 */
	public NodeOption<Expr> init() {
		return location.safeTraversal(SVariableDeclarator.INIT);
	}

	/**
	 * Replaces the init of this variable declarator.
	 *
	 * @param init the replacement for the init of this variable declarator.
	 * @return the resulting mutated variable declarator.
	 */
	public VariableDeclarator withInit(NodeOption<Expr> init) {
		return location.safeTraversalReplace(SVariableDeclarator.INIT, init);
	}

	/**
	 * Mutates the init of this variable declarator.
	 *
	 * @param mutation the mutation to apply to the init of this variable declarator.
	 * @return the resulting mutated variable declarator.
	 */
	public VariableDeclarator withInit(Mutation<NodeOption<Expr>> mutation) {
		return location.safeTraversalMutate(SVariableDeclarator.INIT, mutation);
	}
}
