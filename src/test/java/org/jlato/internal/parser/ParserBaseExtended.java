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

package org.jlato.internal.parser;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.decl.SFormalParameter;
import org.jlato.internal.bu.decl.SVariableDeclaratorId;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.bu.type.SUnknownType;
import org.jlato.parser.ParseException;
import org.jlato.parser.ParserBase;
import org.jlato.parser.Token;

/**
 * @author Didier Villevalois
 */
public abstract class ParserBaseExtended extends ParserBase {

	private int lookahead;

	@Override
	protected Token getToken(int index) {
		return null;
	}

	protected boolean backupLookahead(boolean match) {
		int lookaheadBackup = lookahead;
		try {
			return match;
		} finally {
			lookahead = lookaheadBackup;
		}
	}

	protected boolean parse(String token) throws ParseException {
		return false;
	}

	protected boolean match(String token) {
		return false;
	}

	protected boolean matchNext(String... tokens) {
		return false;
	}

	BUTree<SFormalParameter> makeFormalParameter(BUTree<SName> name) {
		return SFormalParameter.make(emptyList(), SUnknownType.make(), false,
				SVariableDeclaratorId.make(name, emptyList())
		);
	}
}
