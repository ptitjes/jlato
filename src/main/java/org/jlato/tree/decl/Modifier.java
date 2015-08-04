package org.jlato.tree.decl;

import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

import static org.jlato.tree.TreeFactory.modifier;

public interface Modifier extends ExtendedModifier, TreeCombinators<Modifier> {

	Modifier Public = modifier(ModifierKeyword.Public);

	Modifier Protected = modifier(ModifierKeyword.Protected);

	Modifier Private = modifier(ModifierKeyword.Private);

	Modifier Abstract = modifier(ModifierKeyword.Abstract);

	Modifier Default = modifier(ModifierKeyword.Default);

	Modifier Static = modifier(ModifierKeyword.Static);

	Modifier Final = modifier(ModifierKeyword.Final);

	Modifier Transient = modifier(ModifierKeyword.Transient);

	Modifier Volatile = modifier(ModifierKeyword.Volatile);

	Modifier Synchronized = modifier(ModifierKeyword.Synchronized);

	Modifier Native = modifier(ModifierKeyword.Native);

	Modifier StrictFP = modifier(ModifierKeyword.StrictFP);

	ModifierKeyword keyword();

	Modifier withKeyword(ModifierKeyword keyword);

	Modifier withKeyword(Mutation<ModifierKeyword> mutation);
}
