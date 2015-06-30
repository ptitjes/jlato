package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Tree;
import org.jlato.tree.stmt.BlockStmt;

public class InitializerDecl extends BodyDecl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public InitializerDecl instantiate(SLocation location) {
			return new InitializerDecl(location);
		}
	};

	private InitializerDecl(SLocation location) {
		super(location);
	}

	public InitializerDecl(boolean isStatic, BlockStmt block/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, runOf(isStatic, block/*, javadocComment*/))));
	}

	public boolean isStatic() {
		return location.nodeChild(IS_STATIC);
	}

	public InitializerDecl withIsStatic(boolean isStatic) {
		return location.nodeWithChild(IS_STATIC, isStatic);
	}

	public BlockStmt block() {
		return location.nodeChild(BLOCK);
	}

	public InitializerDecl withBlock(BlockStmt block) {
		return location.nodeWithChild(BLOCK, block);
	}
/*
	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public InitializerDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}
*/
	private static final int IS_STATIC = 1;
	private static final int BLOCK = 2;
	private static final int JAVADOC_COMMENT = 3;
}
