package org.jlato.tree.decl;

import org.jlato.internal.td.decl.TDModifier;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

public interface Modifier extends ExtendedModifier, TreeCombinators<Modifier> {

	Modifier Public = new TDModifier(ModifierKeyword.Public);

	Modifier Protected = new TDModifier(ModifierKeyword.Protected);

	Modifier Private = new TDModifier(ModifierKeyword.Private);

	Modifier Abstract = new TDModifier(ModifierKeyword.Abstract);

	Modifier Default = new TDModifier(ModifierKeyword.Default);

	Modifier Static = new TDModifier(ModifierKeyword.Static);

	Modifier Final = new TDModifier(ModifierKeyword.Final);

	Modifier Transient = new TDModifier(ModifierKeyword.Transient);

	Modifier Volatile = new TDModifier(ModifierKeyword.Volatile);

	Modifier Synchronized = new TDModifier(ModifierKeyword.Synchronized);

	Modifier Native = new TDModifier(ModifierKeyword.Native);

	Modifier StrictFP = new TDModifier(ModifierKeyword.StrictFP);

	ModifierKeyword keyword();

	Modifier withKeyword(ModifierKeyword keyword);

	Modifier withKeyword(Mutation<ModifierKeyword> mutation);
}
