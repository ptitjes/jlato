/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2015 The JavaParser Team.
 *
 * This file is part of JavaParser.
 *
 * JavaParser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JavaParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JavaParser.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jlato.printer;

import com.github.andrewoma.dexx.collection.TreeMap;

import static org.jlato.printer.FormattingSettings.Spacing.*;
import static org.jlato.printer.FormattingSettings.Spacing.Unit.Line;
import static org.jlato.printer.FormattingSettings.Spacing.Unit.Space;

/**
 * @author Didier Villevalois
 */
public class FormattingSettings {

	public enum IndentationContext {
		TYPE_BODY,
		BLOCK,
		PARAMETERS,
		STATEMENT,
		SWITCH,
		SWITCH_CASE,
		IF_ELSE,
		TRY_RESOURCES,
		LABEL,
		// Keep the last comma
		;
	}

	public enum SpacingLocation {
		DefaultNewLine(Line),
		CompilationUnit_AfterPackageDecl(Line),
		CompilationUnit_AfterImports(Line),
		CompilationUnit_BetweenTopLevelDecl(Line),
		ClassBody_Empty(Line),
		ClassBody_BeforeMembers(Line),
		ClassBody_BetweenMembers(Line),
		ClassBody_AfterMembers(Line),
		EnumBody_BeforeConstants(Line),
		EnumBody_BetweenConstants(Line),
		EnumBody_AfterConstants(Line),
		EnumConstant_AfterBody(Line),

		DefaultSpace(Space),
		LabeledStmt_AfterLabel(Space),
		SwitchStmt_AfterSwitchKeyword(Space),

		// Keep the last comma
		;

		public final Spacing.Unit defaultUnit;

		SpacingLocation(Spacing.Unit defaultUnit) {
			this.defaultUnit = defaultUnit;
		}
	}

	public static FormattingSettings Default = new FormattingSettings()
			.withIndentationLevel(IndentationContext.PARAMETERS, 2)
			.withIndentationLevel(IndentationContext.STATEMENT, 2)
			.withIndentationLevel(IndentationContext.TRY_RESOURCES, 2)
			.withIndentationLevel(IndentationContext.LABEL, 0)

			.withSpacing(SpacingLocation.DefaultSpace, spaces(1))
			.withSpacing(SpacingLocation.DefaultNewLine, lines(1))

			.withSpacing(SpacingLocation.CompilationUnit_AfterPackageDecl, lines(2))
			.withSpacing(SpacingLocation.CompilationUnit_AfterPackageDecl, lines(2))
			.withSpacing(SpacingLocation.CompilationUnit_AfterImports, lines(2))
			.withSpacing(SpacingLocation.CompilationUnit_BetweenTopLevelDecl, lines(2))
			.withSpacing(SpacingLocation.ClassBody_BeforeMembers, lines(2))
			.withSpacing(SpacingLocation.ClassBody_BetweenMembers, lines(2))
			.withSpacing(SpacingLocation.EnumConstant_AfterBody, lines(0))

			// Keep semi-colon separated
			;

	public static FormattingSettings JavaParser = Default
			.withIndentation("    ")

			.withIndentationLevel(IndentationContext.TRY_RESOURCES, 1)

			.withSpacing(SpacingLocation.EnumBody_BeforeConstants, lines(2))
			.withSpacing(SpacingLocation.EnumBody_BetweenConstants, oneSpace)
			.withSpacing(SpacingLocation.EnumConstant_AfterBody, oneLine)

			.withSpacing(SpacingLocation.LabeledStmt_AfterLabel, oneSpace)
			.withSpacing(SpacingLocation.SwitchStmt_AfterSwitchKeyword, noSpace)

			// Keep semi-colon separated
			;

	private final String indentationImage;
	private final String newLineImage;
	private final TreeMap<IndentationContext, Integer> indentationLevels;
	private final TreeMap<SpacingLocation, Spacing> spacingCounts;

	public FormattingSettings() {
		this("\t", "\n", new TreeMap<IndentationContext, Integer>(), new TreeMap<SpacingLocation, Spacing>());
	}

	public FormattingSettings(String indentationImage, String newLineImage,
	                          TreeMap<IndentationContext, Integer> indentationLevels,
	                          TreeMap<SpacingLocation, Spacing> spacingCounts) {
		this.indentationImage = indentationImage;
		this.newLineImage = newLineImage;
		this.indentationLevels = indentationLevels;
		this.spacingCounts = spacingCounts;
	}

	public String indentation() {
		return indentationImage;
	}

	public FormattingSettings withIndentation(String indentationImage) {
		return new FormattingSettings(indentationImage, newLineImage, indentationLevels, spacingCounts);
	}

	public String newLine() {
		return newLineImage;
	}

	public FormattingSettings withNewLine(String newLineImage) {
		return new FormattingSettings(indentationImage, newLineImage, indentationLevels, spacingCounts);
	}

	public FormattingSettings withIndentationLevel(IndentationContext context, int level) {
		return new FormattingSettings(indentationImage, newLineImage, indentationLevels.put(context, level), spacingCounts);
	}

	public FormattingSettings withSpacing(SpacingLocation location, Spacing spacing) {
		return new FormattingSettings(indentationImage, newLineImage, indentationLevels, spacingCounts.put(location, spacing));
	}

	public int indentation(IndentationContext context) {
		if (indentationLevels.containsKey(context)) return indentationLevels.get(context);
		else return 1;
	}

	public Spacing spacing(SpacingLocation location) {
		if (spacingCounts.containsKey(location)) return spacingCounts.get(location);
		else return new Spacing(1, location.defaultUnit);
	}

	public static class Spacing {

		public static Spacing spaces(int count) {
			return new Spacing(count, Space);
		}

		public static Spacing lines(int count) {
			return new Spacing(count, Unit.Line);
		}

		public static final Spacing oneLine = lines(1);

		public static final Spacing noSpace = spaces(0);
		public static final Spacing oneSpace = spaces(1);

		public final int count;
		public final Unit unit;

		public Spacing(int count, Unit unit) {
			this.count = count;
			this.unit = unit;
		}

		public enum Unit {
			Space, Line
		}
	}
}
