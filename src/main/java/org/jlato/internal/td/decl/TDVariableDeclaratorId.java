package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.decl.SVariableDeclaratorId;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.decl.VariableDeclaratorId;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

public class TDVariableDeclaratorId extends TreeBase<SVariableDeclaratorId, Tree, VariableDeclaratorId> implements VariableDeclaratorId {

	public Kind kind() {
		return Kind.VariableDeclaratorId;
	}

	public TDVariableDeclaratorId(SLocation<SVariableDeclaratorId> location) {
		super(location);
	}

	public TDVariableDeclaratorId(Name name, NodeList<ArrayDim> dims) {
		super(new SLocation<SVariableDeclaratorId>(SVariableDeclaratorId.make(TreeBase.<SName>treeOf(name), TreeBase.<SNodeListState>treeOf(dims))));
	}

	public Name name() {
		return location.safeTraversal(SVariableDeclaratorId.NAME);
	}

	public VariableDeclaratorId withName(Name name) {
		return location.safeTraversalReplace(SVariableDeclaratorId.NAME, name);
	}

	public VariableDeclaratorId withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SVariableDeclaratorId.NAME, mutation);
	}

	public NodeList<ArrayDim> dims() {
		return location.safeTraversal(SVariableDeclaratorId.DIMS);
	}

	public VariableDeclaratorId withDims(NodeList<ArrayDim> dims) {
		return location.safeTraversalReplace(SVariableDeclaratorId.DIMS, dims);
	}

	public VariableDeclaratorId withDims(Mutation<NodeList<ArrayDim>> mutation) {
		return location.safeTraversalMutate(SVariableDeclaratorId.DIMS, mutation);
	}
}
