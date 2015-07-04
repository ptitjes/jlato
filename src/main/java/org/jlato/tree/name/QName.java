package org.jlato.tree.name;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;

public class QName extends Tree {

	public final static Kind kind = new Kind() {
		public QName instantiate(SLocation location) {
			return new QName(location);
		}
	};

	private QName(SLocation location) {
		super(location);
	}

	public QName(QName qualifier, Name name) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(qualifier, name)))));
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
}
