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

import com.github.andrewoma.dexx.collection.IndexedList;
import org.jlato.internal.bu.LToken;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.printer.Printer;
import org.jlato.printer.Spacing;

/**
 * @author Didier Villevalois
 */
public abstract class SpacingConstraint {

	public abstract Spacing resolve(Printer printer);

	// TODO add context argument (Expression, Statement, Block, Declaration, ...) ??

	public static void render(Spacing expectedSpacing, IndexedList<LToken> tokens, Printer printer) {
		switch (expectedSpacing.unit) {
			case Space:
				if (tokens != null && containsComments(tokens)) dump(tokens, printer, true);
				else {
					for (int i = 0; i < expectedSpacing.count; i++) {
						printer.appendWhiteSpace(" ");
					}
				}
				break;
			case Line:
				if (tokens != null) {
					final int actualEmptyLines = emptyLineCount(tokens);
					printer.appendNewLine(expectedSpacing.count - actualEmptyLines);
					dump(tokens, printer, true);
				} else {
					printer.appendNewLine(expectedSpacing.count);
				}
				break;
		}
	}

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

		public Spacing resolve(Printer printer) {
			return spacing;
		}
	}

	private static class LocationBasedConstraint extends SpacingConstraint {

		public final SpacingLocation location;

		public LocationBasedConstraint(SpacingLocation location) {
			this.location = location;
		}

		public Spacing resolve(Printer printer) {
			return printer.formattingSettings.spacing(location);
		}
	}

	private static int emptyLineCount(IndexedList<LToken> tokens) {
		int count = 0;
		boolean emptyLine = true;
		for (LToken token : tokens) {
			switch (token.kind) {
				case ParserImplConstants.SINGLE_LINE_COMMENT:
				case ParserImplConstants.MULTI_LINE_COMMENT:
				case ParserImplConstants.JAVA_DOC_COMMENT:
					emptyLine = false;
					break;
				case ParserImplConstants.NEWLINE:
					if (emptyLine) count++;
					emptyLine = true;
					break;
				case ParserImplConstants.WHITESPACE:
					break;
				default:
					throw new IllegalArgumentException("Tokens are supposed to be meaningless");
			}
		}
		return count;
	}

	private static boolean containsComments(IndexedList<LToken> tokens) {
		for (LToken token : tokens) {
			switch (token.kind) {
				case ParserImplConstants.SINGLE_LINE_COMMENT:
				case ParserImplConstants.MULTI_LINE_COMMENT:
				case ParserImplConstants.JAVA_DOC_COMMENT:
					return true;
				case ParserImplConstants.NEWLINE:
				case ParserImplConstants.WHITESPACE:
					break;
				default:
					throw new IllegalArgumentException("Tokens are supposed to be meaningless");
			}
		}
		return false;
	}

	private static void dump(IndexedList<LToken> tokens, Printer printer, boolean requiresFormatting) {
		for (LToken token : tokens) {
			switch (token.kind) {
				case ParserImplConstants.JAVA_DOC_COMMENT:
					// TODO format javadoc comment
					printer.append(token, requiresFormatting);
					break;
				case ParserImplConstants.SINGLE_LINE_COMMENT:
				case ParserImplConstants.MULTI_LINE_COMMENT:
					printer.append(token, requiresFormatting);
					break;
				case ParserImplConstants.NEWLINE:
					printer.appendNewLine(token.string);
					break;
				case ParserImplConstants.WHITESPACE:
					printer.appendWhiteSpace(token.string);
					break;
				default:
					throw new IllegalArgumentException("Tokens are supposed to be meaningless");
			}
		}
	}
}
