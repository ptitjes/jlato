package org.jlato.tree.type;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;

public class WildcardType extends AnnotatedType {

	public final static Tree.Kind kind = new Tree.Kind() {
		public WildcardType instantiate(SLocation location) {
			return new WildcardType(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private WildcardType(SLocation location) {
		super(location);
	}

	public WildcardType(NodeList<AnnotationExpr> annotations, ReferenceType ext, ReferenceType sup) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(annotations, ext, sup)))));
	}

	public ReferenceType ext() {
		return location.nodeChild(EXT);
	}

	public WildcardType withExt(ReferenceType ext) {
		return location.nodeWithChild(EXT, ext);
	}

	public ReferenceType sup() {
		return location.nodeChild(SUP);
	}

	public WildcardType withSup(ReferenceType sup) {
		return location.nodeWithChild(SUP, sup);
	}

	private static final int EXT = 1;
	private static final int SUP = 2;
}
