package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Tree;

public class AnnotationDecl extends TypeDecl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public AnnotationDecl instantiate(SLocation location) {
			return new AnnotationDecl(location);
		}
	};

	private AnnotationDecl(SLocation location) {
		super(location);
	}

	public AnnotationDecl(/*JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, runOf(/*javadocComment*/))));
	}
/*

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public AnnotationDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}
*/

	private static final int JAVADOC_COMMENT = 4;
}
