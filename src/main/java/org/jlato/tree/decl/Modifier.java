package org.jlato.tree.decl;

import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

import static org.jlato.tree.Trees.modifier;

/**
 * A modifier.
 */
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

	/**
	 * Returns the keyword of this modifier.
	 *
	 * @return the keyword of this modifier.
	 */
	ModifierKeyword keyword();

	/**
	 * Replaces the keyword of this modifier.
	 *
	 * @param keyword the replacement for the keyword of this modifier.
	 * @return the resulting mutated modifier.
	 */
	Modifier withKeyword(ModifierKeyword keyword);

	/**
	 * Mutates the keyword of this modifier.
	 *
	 * @param mutation the mutation to apply to the keyword of this modifier.
	 * @return the resulting mutated modifier.
	 */
	Modifier withKeyword(Mutation<ModifierKeyword> mutation);
}
