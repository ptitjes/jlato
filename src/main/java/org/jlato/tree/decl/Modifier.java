/*
 * Copyright (C) 2015-2016 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

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
