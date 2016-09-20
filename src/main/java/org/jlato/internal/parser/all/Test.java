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

package org.jlato.internal.parser.all;

import org.jlato.internal.parser.Token;
import org.jlato.internal.parser.TokenType;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Didier Villevalois
 */
public class Test {

	public static final int STAT = 0;
	public static final int EXPR = 1;
	public static final int EXPR_PLUS = 2;
	public static final int EXPR_FUNC = 3;

	private static final Grammar exprGrammar = new Grammar() {
		@Override
		protected void initializeProductions(Map<Integer, Expansion> productions) {
			productions.put(STAT, choice(
					sequence(nonTerminal(EXPR), oneOrMore(sequence(terminal(TokenType.ASSIGN), nonTerminal(EXPR))), terminal(TokenType.SEMICOLON)),
					sequence(nonTerminal(EXPR), terminal(TokenType.SEMICOLON))
			));
			productions.put(EXPR, choice(
					sequence(nonTerminal(EXPR_PLUS), oneOrMore(sequence(terminal(TokenType.STAR), nonTerminal(EXPR)))),
					sequence(nonTerminal(EXPR_PLUS))
			));
			productions.put(EXPR_PLUS, choice(
					sequence(nonTerminal(EXPR_FUNC), oneOrMore(sequence(terminal(TokenType.PLUS), nonTerminal(EXPR_PLUS)))),
					sequence(nonTerminal(EXPR_FUNC))
			));
			productions.put(EXPR_FUNC, choice(
					sequence(terminal(TokenType.IDENTIFIER), terminal(TokenType.LPAREN), nonTerminal(EXPR), terminal(TokenType.RPAREN)),
					sequence(terminal(TokenType.IDENTIFIER)),
					sequence(terminal(TokenType.STRING_LITERAL))
			));
		}
	};

	public static void main(String[] args) {
		final Token[][] tokens = new Token[][]{
				{id("a"), tok(TokenType.ASSIGN), id("b"), tok(TokenType.SEMICOLON),},
				{lit("abc"), tok(TokenType.ASSIGN), id("b"), tok(TokenType.SEMICOLON),},
				{id("a"), tok(TokenType.ASSIGN), id("b"), tok(TokenType.ASSIGN), id("b"), tok(TokenType.SEMICOLON),},
				{id("a"), tok(TokenType.PLUS), id("b"), tok(TokenType.STAR), id("b"), tok(TokenType.ASSIGN), id("b"), tok(TokenType.SEMICOLON),},
				{id("a"), tok(TokenType.LPAREN), id("b"), tok(TokenType.RPAREN), tok(TokenType.SEMICOLON),},
				{id("c"), tok(TokenType.LPAREN), id("d"), tok(TokenType.RPAREN), tok(TokenType.ASSIGN), id("b"), tok(TokenType.SEMICOLON),},
				{id("c"), tok(TokenType.ASSIGN), id("c"), tok(TokenType.LPAREN), id("d"), tok(TokenType.RPAREN), tok(TokenType.SEMICOLON),},
		};

		Predictor predictor = new Predictor(exprGrammar);

		for (int i = 0; i < tokens.length; i++) {
			System.out.println(Arrays.toString(tokens[i]));
			System.out.println(">> " + predictor.predict(STAT, tokens[i]));
			System.out.println();
		}
	}

	private static Token id(String id) {
		return new Token(TokenType.IDENTIFIER, id, 0, 0);
	}

	private static Token lit(String str) {
		return new Token(TokenType.STRING_LITERAL, str, 0, 0);
	}

	private static Token tok(int tokenType) {
		return new Token(tokenType, TokenType.tokenImage[tokenType], 0, 0);
	}
}
