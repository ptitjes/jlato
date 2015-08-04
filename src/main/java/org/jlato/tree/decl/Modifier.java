package org.jlato.tree.decl;

import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface Modifier extends ExtendedModifier, TreeCombinators<Modifier> {

	ModifierKeyword keyword();

	Modifier withKeyword(ModifierKeyword keyword);

	Modifier withKeyword(Mutation<ModifierKeyword> mutation);
}
