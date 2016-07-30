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

package org.jlato.internal.td.expr;

import org.jlato.internal.bu.Literals;
import org.jlato.internal.bu.expr.SLiteralExpr;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.expr.LiteralExpr;

public class TDLiteralExpr<T> extends TDTree<SLiteralExpr, Expr, LiteralExpr<T>> implements LiteralExpr<T> {

	public Kind kind() {
		return Kind.LiteralExpr;
	}

	public TDLiteralExpr(TDLocation<SLiteralExpr> location) {
		super(location);
	}

	public TDLiteralExpr(Class<?> literalClass, String literalString) {
		super(new TDLocation<SLiteralExpr>(SLiteralExpr.make(literalClass, literalString)));
	}

	@SuppressWarnings("unchecked")
	public Class<T> literalClass() {
		return (Class<T>) location.safeProperty(SLiteralExpr.LITERAL_CLASS);
	}

	public String literalString() {
		return location.safeProperty(SLiteralExpr.LITERAL_STRING);
	}

	public T value() {
		return Literals.valueFor(literalClass(), literalString());
	}
}
