package org.jlato.tree.expr;

import org.jlato.tree.Node;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

public interface MemberValuePair extends Node, TreeCombinators<MemberValuePair> {

	Name name();

	MemberValuePair withName(Name name);

	MemberValuePair withName(Mutation<Name> mutation);

	Expr value();

	MemberValuePair withValue(Expr value);

	MemberValuePair withValue(Mutation<Expr> mutation);
}
