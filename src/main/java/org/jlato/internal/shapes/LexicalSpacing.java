package org.jlato.internal.shapes;

import com.github.andrewoma.dexx.collection.IndexedList;
import org.jlato.internal.bu.LToken;
import org.jlato.parser.ASTParserConstants;
import org.jlato.printer.FormattingSettings.Spacing;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.printer.Printer;

/**
 * @author Didier Villevalois
 */
public interface LexicalSpacing {

	// TODO add context argument (Expression, Statement, Block, Declaration, ...)

	void render(IndexedList<LToken> tokens, Printer printer);

	class Factory {

		public static LexicalSpacing empty() {
			return new LexicalSpacing() {
				public void render(IndexedList<LToken> tokens, Printer printer) {
					if (tokens != null && containsComments(tokens)) dump(tokens, printer, true);
				}
			};
		}

		public static LexicalSpacing space() {
			return spacing(SpacingLocation.DefaultSpace);
		}

		public static LexicalSpacing newLine() {
			return spacing(SpacingLocation.DefaultNewLine);
		}

		public static LexicalSpacing spacing(final SpacingLocation location) {
			return new LexicalSpacing() {
				public void render(IndexedList<LToken> tokens, Printer printer) {
					final Spacing expectedSpacing = printer.formattingSettings.spacing(location);
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
			};
		}

		private static int emptyLineCount(IndexedList<LToken> tokens) {
			int count = 0;
			boolean emptyLine = true;
			for (LToken token : tokens) {
				switch (token.kind) {
					case ASTParserConstants.SINGLE_LINE_COMMENT:
					case ASTParserConstants.MULTI_LINE_COMMENT:
					case ASTParserConstants.JAVA_DOC_COMMENT:
						emptyLine = false;
						break;
					case ASTParserConstants.NEWLINE:
						if (emptyLine) count++;
						emptyLine = true;
						break;
					case ASTParserConstants.WHITESPACE:
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
					case ASTParserConstants.SINGLE_LINE_COMMENT:
					case ASTParserConstants.MULTI_LINE_COMMENT:
					case ASTParserConstants.JAVA_DOC_COMMENT:
						return true;
					case ASTParserConstants.NEWLINE:
					case ASTParserConstants.WHITESPACE:
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
					case ASTParserConstants.JAVA_DOC_COMMENT:
						// TODO format javadoc comment
						printer.append(token, requiresFormatting);
						break;
					case ASTParserConstants.SINGLE_LINE_COMMENT:
					case ASTParserConstants.MULTI_LINE_COMMENT:
						printer.append(token, requiresFormatting);
						break;
					case ASTParserConstants.NEWLINE:
						printer.appendNewLine(token.string);
						break;
					case ASTParserConstants.WHITESPACE:
						printer.appendWhiteSpace(token.string);
						break;
					default:
						throw new IllegalArgumentException("Tokens are supposed to be meaningless");
				}
			}
		}
	}
}
