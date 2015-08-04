package org.jlato.internal.td.name;

import org.jlato.internal.bu.SNodeOption;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeOption;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

public class TDQualifiedName extends TDTree<SQualifiedName, Node, QualifiedName> implements QualifiedName {

	public Kind kind() {
		return Kind.QualifiedName;
	}

	public TDQualifiedName(TDLocation<SQualifiedName> location) {
		super(location);
	}

	public TDQualifiedName(NodeOption<QualifiedName> qualifier, Name name) {
		super(new TDLocation<SQualifiedName>(SQualifiedName.make(TDTree.<SNodeOption>treeOf(qualifier), TDTree.<SName>treeOf(name))));
	}

	public NodeOption<QualifiedName> qualifier() {
		return location.safeTraversal(SQualifiedName.QUALIFIER);
	}

	public QualifiedName withQualifier(NodeOption<QualifiedName> qualifier) {
		return location.safeTraversalReplace(SQualifiedName.QUALIFIER, qualifier);
	}

	public QualifiedName withQualifier(Mutation<NodeOption<QualifiedName>> mutation) {
		return location.safeTraversalMutate(SQualifiedName.QUALIFIER, mutation);
	}

	public Name name() {
		return location.safeTraversal(SQualifiedName.NAME);
	}

	public QualifiedName withName(Name name) {
		return location.safeTraversalReplace(SQualifiedName.NAME, name);
	}

	public QualifiedName withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SQualifiedName.NAME, mutation);
	}
}
