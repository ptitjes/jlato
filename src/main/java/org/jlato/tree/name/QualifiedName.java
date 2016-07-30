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

package org.jlato.tree.name;

import org.jlato.tree.Node;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.util.Mutation;

/**
 * A qualified name.
 */
public interface QualifiedName extends Node, TreeCombinators<QualifiedName> {

	/**
	 * Returns the qualifier of this qualified name.
	 *
	 * @return the qualifier of this qualified name.
	 */
	NodeOption<QualifiedName> qualifier();

	/**
	 * Replaces the qualifier of this qualified name.
	 *
	 * @param qualifier the replacement for the qualifier of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	QualifiedName withQualifier(NodeOption<QualifiedName> qualifier);

	/**
	 * Mutates the qualifier of this qualified name.
	 *
	 * @param mutation the mutation to apply to the qualifier of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	QualifiedName withQualifier(Mutation<NodeOption<QualifiedName>> mutation);

	/**
	 * Replaces the qualifier of this qualified name.
	 *
	 * @param qualifier the replacement for the qualifier of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	QualifiedName withQualifier(QualifiedName qualifier);

	/**
	 * Replaces the qualifier of this qualified name.
	 *
	 * @return the resulting mutated qualified name.
	 */
	QualifiedName withNoQualifier();

	/**
	 * Returns the name of this qualified name.
	 *
	 * @return the name of this qualified name.
	 */
	Name name();

	/**
	 * Replaces the name of this qualified name.
	 *
	 * @param name the replacement for the name of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	QualifiedName withName(Name name);

	/**
	 * Mutates the name of this qualified name.
	 *
	 * @param mutation the mutation to apply to the name of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	QualifiedName withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this qualified name.
	 *
	 * @param name the replacement for the name of this qualified name.
	 * @return the resulting mutated qualified name.
	 */
	QualifiedName withName(String name);
}
