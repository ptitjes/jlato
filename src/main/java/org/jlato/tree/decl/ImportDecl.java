package org.jlato.tree.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QName;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.space;

public class ImportDecl extends Tree {

	public final static Kind kind = new Kind() {
		public ImportDecl instantiate(SLocation location) {
			return new ImportDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ImportDecl(SLocation location) {
		super(location);
	}

	public ImportDecl(QName name, boolean isStatic, boolean isOnDemand) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(name), dataOf(isStatic, isOnDemand)))));
	}

	public QName name() {
		return location.nodeChild(NAME);
	}

	public ImportDecl withName(QName name) {
		return location.nodeWithChild(NAME, name);
	}

	public boolean isStatic() {
		return location.nodeData(STATIC);
	}

	public ImportDecl setStatic(boolean isStatic) {
		return location.nodeWithData(STATIC, isStatic);
	}

	public boolean isOnDemand() {
		return location.nodeData(ON_DEMAND);
	}

	public ImportDecl setOnDemand(boolean isOnDemand) {
		return location.nodeWithData(ON_DEMAND, isOnDemand);
	}

	private static final int NAME = 0;

	private static final int STATIC = 0;
	private static final int ON_DEMAND = 1;

	public final static LexicalShape shape = composite(
			token(LToken.Import),
			dataOption(STATIC, token(LToken.Static)),
			child(NAME),
			dataOption(ON_DEMAND, composite(token(LToken.Dot), token(LToken.Times))),
			token(LToken.SemiColon)
	);
}
