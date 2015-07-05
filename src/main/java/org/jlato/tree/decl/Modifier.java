package org.jlato.tree.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SLeafState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.token;

public class Modifier extends Tree {

	public final static Kind kind = new Tree.Kind() {
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

	protected Modifier(SLocation location) {
		super(location);
	}

	private Modifier(LToken keyword) {
		super(new SLocation(new SLeaf(kind, new SLeafState(dataOf(keyword)))));
	}

	public String toString() {
		return location.leafData(KEYWORD).toString();
	}

	public static final int KEYWORD = 0;

	public final static LexicalShape shape = token(new LSToken.Provider() {
		public LToken tokenFor(STree tree) {
			return (LToken) tree.state.data(KEYWORD);
		}
	});
}
