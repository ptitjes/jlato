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

package org.jlato.printer;

import com.github.andrewoma.dexx.collection.TreeMap;

import static org.jlato.printer.Spacing.Unit.Line;
import static org.jlato.printer.Spacing.Unit.Space;
import static org.jlato.printer.Spacing.*;

/**
 * @author Didier Villevalois
 */
public class FormattingSettings {

	public enum IndentationContext {
		TypeBody,
		Block,
		Parameters,
		Statement,
		Switch,
		SwitchCase,
		IfElse,
		TryResources,
		Label,
		// Keep the last comma
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

		IfStmt_ThenExpressionStmt(Space),
		IfStmt_ThenOtherStmt(Line),
		IfStmt_ElseIfStmt(Space),
		IfStmt_ElseExpressionStmt(Space),
		IfStmt_ElseOtherStmt(Line),
		// Keep the last comma
		;

		public final Spacing.Unit defaultUnit;

		SpacingLocation(Spacing.Unit defaultUnit) {
			this.defaultUnit = defaultUnit;
		}
	}

	public static final String NewLine_Unix = "\n";
	public static final String NewLine_Windows = "\r\n";
	public static final String NewLine_Platform = System.getProperty("line.separator");

	public static FormattingSettings Default = new FormattingSettings()
			.withNewLine(NewLine_Unix)

			.withIndentationLevel(IndentationContext.Parameters, 2)
			.withIndentationLevel(IndentationContext.Statement, 2)
			.withIndentationLevel(IndentationContext.TryResources, 2)
			.withIndentationLevel(IndentationContext.Label, 0)

			.withSpacing(SpacingLocation.DefaultSpace, spaces(1))
			.withSpacing(SpacingLocation.DefaultNewLine, lines(1))

			.withSpacing(SpacingLocation.CompilationUnit_AfterPackageDecl, lines(2))
			.withSpacing(SpacingLocation.CompilationUnit_AfterImports, lines(2))
			.withSpacing(SpacingLocation.CompilationUnit_BetweenTopLevelDecl, lines(2))
			.withSpacing(SpacingLocation.ClassBody_BeforeMembers, lines(2))
			.withSpacing(SpacingLocation.ClassBody_BetweenMembers, lines(2))
			.withSpacing(SpacingLocation.EnumConstant_AfterBody, lines(0))

			// Keep semi-colon separated
			;

	public static FormattingSettings JavaParser = Default
			.withNewLine(NewLine_Platform)
			.withIndentation("    ")

			.withIndentationLevel(IndentationContext.TryResources, 1)

			.withSpacing(SpacingLocation.EnumBody_BeforeConstants, lines(2))
			.withSpacing(SpacingLocation.EnumBody_BetweenConstants, oneSpace)
			.withSpacing(SpacingLocation.EnumBody_AfterConstants, oneLine)
			.withSpacing(SpacingLocation.EnumConstant_AfterBody, oneLine)

			.withSpacing(SpacingLocation.LabeledStmt_AfterLabel, oneSpace)
			.withSpacing(SpacingLocation.SwitchStmt_AfterSwitchKeyword, noSpace)
			.withSpacing(SpacingLocation.IfStmt_ThenExpressionStmt, oneLine)
			.withSpacing(SpacingLocation.IfStmt_ElseExpressionStmt, oneLine)

			// Keep semi-colon separated
			;

	private final String indentationImage;

	private final String newLineImage;
	private final TreeMap<IndentationContext, Integer> indentationLevels;
	private final TreeMap<SpacingLocation, Spacing> spacingCounts;
	private final boolean docCommentFormatting;
	private final boolean commentFormatting;

	public FormattingSettings() {
		this("\t", "\n", new TreeMap<IndentationContext, Integer>(), new TreeMap<SpacingLocation, Spacing>(), true, false);
	}

	public FormattingSettings(String indentationImage, String newLineImage,
	                          TreeMap<IndentationContext, Integer> indentationLevels,
	                          TreeMap<SpacingLocation, Spacing> spacingCounts,
	                          boolean docCommentFormatting, boolean commentFormatting) {
		this.indentationImage = indentationImage;
		this.newLineImage = newLineImage;
		this.indentationLevels = indentationLevels;
		this.spacingCounts = spacingCounts;
		this.docCommentFormatting = docCommentFormatting;
		this.commentFormatting = commentFormatting;
	}

	public String indentation() {
		return indentationImage;
	}

	public FormattingSettings withIndentation(String indentationImage) {
		return new FormattingSettings(indentationImage, newLineImage, indentationLevels, spacingCounts, docCommentFormatting, commentFormatting);
	}

	public String newLine() {
		return newLineImage;
	}

	public FormattingSettings withNewLine(String newLineImage) {
		return new FormattingSettings(indentationImage, newLineImage, indentationLevels, spacingCounts, docCommentFormatting, commentFormatting);
	}

	public FormattingSettings withIndentationLevel(IndentationContext context, int level) {
		return new FormattingSettings(indentationImage, newLineImage, indentationLevels.put(context, level), spacingCounts, docCommentFormatting, commentFormatting);
	}

	public FormattingSettings withSpacing(SpacingLocation location, Spacing spacing) {
		return new FormattingSettings(indentationImage, newLineImage, indentationLevels, spacingCounts.put(location, spacing), docCommentFormatting, commentFormatting);
	}

	public int indentation(IndentationContext context) {
		if (indentationLevels.containsKey(context)) return indentationLevels.get(context);
		else return 1;
	}

	public Spacing spacing(SpacingLocation location) {
		if (spacingCounts.containsKey(location)) return spacingCounts.get(location);
		else return new Spacing(1, location.defaultUnit);
	}

	public FormattingSettings withDocCommentFormatting(boolean docCommentFormatting) {
		return new FormattingSettings(indentationImage, newLineImage, indentationLevels, spacingCounts, docCommentFormatting, commentFormatting);
	}

	public FormattingSettings withCommentFormatting(boolean commentFormatting) {
		return new FormattingSettings(indentationImage, newLineImage, indentationLevels, spacingCounts, docCommentFormatting, commentFormatting);
	}

	public boolean docCommentFormatting() {
		return docCommentFormatting;
	}

	public boolean commentFormatting() {
		return commentFormatting;
	}
}
