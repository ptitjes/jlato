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

package org.jlato.tree.type;

import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.TreeCombinators;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.util.Mutation;

/**
 * A qualified type.
 */
public interface QualifiedType extends ReferenceType, TreeCombinators<QualifiedType> {

	/**
	 * Returns the annotations of this qualified type.
	 *
	 * @return the annotations of this qualified type.
	 */
	NodeList<AnnotationExpr> annotations();

	/**
	 * Replaces the annotations of this qualified type.
	 *
	 * @param annotations the replacement for the annotations of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withAnnotations(NodeList<AnnotationExpr> annotations);

	/**
	 * Mutates the annotations of this qualified type.
	 *
	 * @param mutation the mutation to apply to the annotations of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation);

	/**
	 * Returns the scope of this qualified type.
	 *
	 * @return the scope of this qualified type.
	 */
	NodeOption<QualifiedType> scope();

	/**
	 * Replaces the scope of this qualified type.
	 *
	 * @param scope the replacement for the scope of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withScope(NodeOption<QualifiedType> scope);

	/**
	 * Mutates the scope of this qualified type.
	 *
	 * @param mutation the mutation to apply to the scope of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withScope(Mutation<NodeOption<QualifiedType>> mutation);

	/**
	 * Replaces the scope of this qualified type.
	 *
	 * @param scope the replacement for the scope of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withScope(QualifiedType scope);

	/**
	 * Replaces the scope of this qualified type.
	 *
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withNoScope();

	/**
	 * Returns the name of this qualified type.
	 *
	 * @return the name of this qualified type.
	 */
	Name name();

	/**
	 * Replaces the name of this qualified type.
	 *
	 * @param name the replacement for the name of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withName(Name name);

	/**
	 * Mutates the name of this qualified type.
	 *
	 * @param mutation the mutation to apply to the name of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withName(Mutation<Name> mutation);

	/**
	 * Replaces the name of this qualified type.
	 *
	 * @param name the replacement for the name of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withName(String name);

	/**
	 * Returns the type args of this qualified type.
	 *
	 * @return the type args of this qualified type.
	 */
	NodeOption<NodeList<Type>> typeArgs();

	/**
	 * Replaces the type args of this qualified type.
	 *
	 * @param typeArgs the replacement for the type args of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withTypeArgs(NodeOption<NodeList<Type>> typeArgs);

	/**
	 * Mutates the type args of this qualified type.
	 *
	 * @param mutation the mutation to apply to the type args of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withTypeArgs(Mutation<NodeOption<NodeList<Type>>> mutation);

	/**
	 * Replaces the type args of this qualified type.
	 *
	 * @param typeArgs the replacement for the type args of this qualified type.
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withTypeArgs(NodeList<Type> typeArgs);

	/**
	 * Replaces the type args of this qualified type.
	 *
	 * @return the resulting mutated qualified type.
	 */
	QualifiedType withNoTypeArgs();
}
