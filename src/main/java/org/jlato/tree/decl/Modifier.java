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

package org.jlato.tree.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SLeafState;
import org.jlato.internal.bu.STree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.SLocation; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Tree; import org.jlato.internal.td.TreeBase; import org.jlato.internal.bu.SNodeState;

import static org.jlato.internal.shapes.LexicalShape.token;

public class Modifier extends TreeBase<SLeafState> implements Tree, ExtendedModifier {

	public final static TreeBase.Kind kind = new TreeBase.Kind() {
		public Tree instantiate(SLocation location) {
			return new Modifier(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	public static final Modifier Public = new Modifier(LToken.Public);
	public static final Modifier Protected = new Modifier(LToken.Protected);
	public static final Modifier Private = new Modifier(LToken.Private);
	public static final Modifier Abstract = new Modifier(LToken.Abstract);
	public static final Modifier Default = new Modifier(LToken.Default);
	public static final Modifier Static = new Modifier(LToken.Static);
	public static final Modifier Final = new Modifier(LToken.Final);
	public static final Modifier Transient = new Modifier(LToken.Transient);
	public static final Modifier Volatile = new Modifier(LToken.Volatile);
	public static final Modifier Synchronized = new Modifier(LToken.Synchronized);
	public static final Modifier Native = new Modifier(LToken.Native);
	public static final Modifier StrictFP = new Modifier(LToken.StrictFP);

	protected Modifier(SLocation<SLeafState> location) {
		super(location);
	}

	private Modifier(LToken keyword) {
		super(new SLocation<SLeafState>(new STree<SLeafState>(kind, new SLeafState(dataOf(keyword)))));
	}

	public String toString() {
		return location.data(KEYWORD).toString();
	}

	public static final int KEYWORD = 0;

	public final static LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			return (LToken) tree.state.data(KEYWORD);
		}
	});
}
