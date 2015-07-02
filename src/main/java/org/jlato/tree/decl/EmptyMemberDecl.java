package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Tree;

public class EmptyMemberDecl extends Decl implements Member {

	public final static Tree.Kind kind = new Tree.Kind() {
		public EmptyMemberDecl instantiate(SLocation location) {
			return new EmptyMemberDecl(location);
		}
	};

	private EmptyMemberDecl(SLocation location) {
		super(location);
	}

	public EmptyMemberDecl(/*JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, runOf(/*javadocComment*/))));
	}
/*

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public EmptyMemberDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}
*/

	private static final int JAVADOC_COMMENT = 1;
}
