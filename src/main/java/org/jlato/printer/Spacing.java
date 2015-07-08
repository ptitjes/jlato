/*
 * Copyright (C) 2015 Didier Villevalois.
 *
 * This file is part of JLaTo.
 *
 * JLaTo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JLaTo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JLaTo.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.printer;

/**
 * @author Didier Villevalois
 */
public class Spacing {

	public static Spacing spaces(int count) {
		return new Spacing(count, Unit.Space);
	}

	public static Spacing lines(int count) {
		return new Spacing(count, Unit.Line);
	}

	public static final Spacing noSpace = spaces(0);
	public static final Spacing oneSpace = spaces(1);

	public static final Spacing oneLine = lines(1);

	public final int count;
	public final Unit unit;

	public Spacing(int count, Unit unit) {
		this.count = count;
		this.unit = unit;
	}

	public Spacing max(Spacing other) {
		if (this.unit == other.unit)
			if (this.count >= other.count) return this;
			else return other;
		else if (this.unit == Unit.Line) return this;
		else return other;
	}

	public enum Unit {
		Space, Line
	}
}
