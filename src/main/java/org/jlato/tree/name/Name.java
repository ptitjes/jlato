package org.jlato.tree.name;

import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.Expr;
import org.jlato.util.Mutation;

public interface Name extends Expr, TreeCombinators<Name> {

	String id();

	Name withId(String id);

	Name withId(Mutation<String> mutation);
}
