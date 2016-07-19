package org.jlato.internal.td.name;

import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Trees;
import org.jlato.tree.name.Name;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

/**
 * A qualified name.
 */
public class TDQualifiedName extends TDTree<SQualifiedName, Node, QualifiedName> implements QualifiedName {

	/**
	 * Returns the kind of this qualified name.
	 *
	 * @return the kind of this qualified name.
	 */
	public Kind kind() {
		return Kind.QualifiedName;
	}

	/**
	 * Creates a qualified name for the specified tree location.
	 *
	 * @param location the tree location.
	 */
	public TDQualifiedName(TDLocation<SQualifiedName> location) {
		super(location);
	}

	/**
	 * Creates a qualified name with the specified child trees.
	 *
	 * @param qualifier the qualifier child tree.
	 * @param name      the name child tree.
	 */
	public TDQualifiedName(NodeOption<QualifiedName> qualifier, Name name) {
		super(new TDLocation<SQualifiedName>(SQualifiedName.make(TDTree.<SNodeOption>treeOf(qualifier), TDTree.<SName>treeOf(name))));
	}

	/**
	 * Returns the qualifier of this qualified name.
	 *
	 * @return the qualifier of this qualified name.
	 */
	public NodeOption<QualifiedName> qualifier() {
		return location.safeTraversal(SQualifiedName.QUALIFIER);
	}

	/**
	 * Replaces the qualifier of this qualified name.
	 *
	 * @param qualifier the replacement for the qualifier of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	public QualifiedName withQualifier(NodeOption<QualifiedName> qualifier) {
		return location.safeTraversalReplace(SQualifiedName.QUALIFIER, qualifier);
	}

	/**
	 * Mutates the qualifier of this qualified name.
	 *
	 * @param mutation the mutation to apply to the qualifier of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	public QualifiedName withQualifier(Mutation<NodeOption<QualifiedName>> mutation) {
		return location.safeTraversalMutate(SQualifiedName.QUALIFIER, mutation);
	}

	/**
	 * Returns the name of this qualified name.
	 *
	 * @return the name of this qualified name.
	 */
	public Name name() {
		return location.safeTraversal(SQualifiedName.NAME);
	}

	/**
	 * Replaces the name of this qualified name.
	 *
	 * @param name the replacement for the name of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	public QualifiedName withName(Name name) {
		return location.safeTraversalReplace(SQualifiedName.NAME, name);
	}

	/**
	 * Mutates the name of this qualified name.
	 *
	 * @param mutation the mutation to apply to the name of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	public QualifiedName withName(Mutation<Name> mutation) {
		return location.safeTraversalMutate(SQualifiedName.NAME, mutation);
	}

	/**
	 * Replaces the name of this qualified name.
	 *
	 * @param name the replacement for the name of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	public QualifiedName withName(String name) {
		return location.safeTraversalReplace(SQualifiedName.NAME, Trees.name(name));
	}
}
