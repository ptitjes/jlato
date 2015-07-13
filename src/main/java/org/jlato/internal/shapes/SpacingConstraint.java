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

package org.jlato.internal.shapes;

import org.jlato.printer.FormattingSettings;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.printer.Spacing;

/**
 * @author Didier Villevalois
 */
public abstract class SpacingConstraint {

	public abstract Spacing resolve(FormattingSettings settings);

	// TODO add context argument (Expression, Statement, Block, Declaration, ...) ??

	public static class Factory {

		public static SpacingConstraint empty() {
			return new FixedConstraint(Spacing.noSpace);
		}

		public static SpacingConstraint space() {
			return spacing(SpacingLocation.DefaultSpace);
		}

		public static SpacingConstraint newLine() {
			return spacing(SpacingLocation.DefaultNewLine);
		}

		public static SpacingConstraint spacing(final SpacingLocation location) {
			return new LocationBasedConstraint(location);
		}
	}

	private static class FixedConstraint extends SpacingConstraint {

		public final Spacing spacing;

		public FixedConstraint(Spacing spacing) {
			this.spacing = spacing;
		}

		public Spacing resolve(FormattingSettings settings) {
			return spacing;
		}
	}

	private static class LocationBasedConstraint extends SpacingConstraint {

		public final SpacingLocation location;

		public LocationBasedConstraint(SpacingLocation location) {
			this.location = location;
		}

		public Spacing resolve(FormattingSettings settings) {
			return settings.spacing(location);
		}
	}
}
