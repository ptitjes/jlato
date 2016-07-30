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

package org.jlato.internal.shapes;

import org.jlato.printer.FormattingSettings;
import org.jlato.printer.FormattingSettings.IndentationContext;

/**
 * @author Didier Villevalois
 */
public abstract class IndentationConstraint {

	public abstract int resolve(FormattingSettings settings);

	// TODO add context argument (Expression, Statement, Block, Declaration, ...) ??

	public static IndentationConstraint indent(IndentationContext context) {
		return new ContextBasedConstraint(context, +1);
	}

	public static IndentationConstraint unIndent(IndentationContext context) {
		return new ContextBasedConstraint(context, -1);
	}

	private static class ContextBasedConstraint extends IndentationConstraint {

		public final IndentationContext context;
		public final int factor;

		public ContextBasedConstraint(IndentationContext context, int factor) {
			this.context = context;
			this.factor = factor;
		}

		public int resolve(FormattingSettings settings) {
			return settings.indentation(context) * factor;
		}
	}
}
