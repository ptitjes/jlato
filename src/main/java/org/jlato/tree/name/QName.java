package org.jlato.tree.name;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class QName extends Tree {

	public final static Kind kind = new Kind() {
		public QName instantiate(SLocation location) {
			return new QName(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	public static QName of(String nameString) {
		final String[] split = nameString.split("\\.");
		QName name = null;
		for (String part : split) {
			name = new QName(name, new Name(part));
		}
		return name;
	}

	private QName(SLocation location) {
		super(location);
	}

	public QName(QName qualifier, Name name) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(qualifier, name)))));
	}

	public QName qualifier() {
		return location.nodeChild(QUALIFIER);
	}

	public QName withQualifier(QName qualifier) {
		return location.nodeWithChild(QUALIFIER, qualifier);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public QName withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public boolean isQualified() {
		return qualifier() != null;
	}

	@Override
	public String toString() {
		final QName qualifier = qualifier();
		final Name name = name();
		return qualifier != null ? qualifier.toString() + "." + name.toString() : name.toString();
	}

	private static final int QUALIFIER = 0;
	private static final int NAME = 1;

	public final static LexicalShape shape = composite(
			nonNullChild(QUALIFIER, composite(child(QUALIFIER), token(LToken.Dot))), child(NAME)
	);
}
