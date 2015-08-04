package org.jlato.tree.name;

import org.jlato.tree.Node;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface QualifiedName extends Node, TreeCombinators<QualifiedName> {

	NodeOption<QualifiedName> qualifier();

	QualifiedName withQualifier(NodeOption<QualifiedName> qualifier);

	QualifiedName withQualifier(Mutation<NodeOption<QualifiedName>> mutation);

	Name name();

	QualifiedName withName(Name name);

	QualifiedName withName(Mutation<Name> mutation);
}
