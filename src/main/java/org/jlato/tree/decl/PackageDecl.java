package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.QName;

public class PackageDecl extends Tree {

	public final static Kind kind = new Kind() {
		public PackageDecl instantiate(SLocation location) {
			return new PackageDecl(location);
		}
	};

	private PackageDecl(SLocation location) {
		super(location);
	}

	public PackageDecl(NodeList<AnnotationExpr> annotations, QName name) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(annotations, name)))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.nodeChild(ANNOTATIONS);
	}

	public Modifiers withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.nodeWithChild(ANNOTATIONS, annotations);
	}

	public QName name() {
		return location.nodeChild(NAME);
	}

	public PackageDecl withName(QName name) {
		return location.nodeWithChild(NAME, name);
	}

	private static final int ANNOTATIONS = 0;
	private static final int NAME = 1;
}
