package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Decl;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.stmt.BlockStmt;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class InitializerDecl extends Decl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public InitializerDecl instantiate(SLocation location) {
			return new InitializerDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private InitializerDecl(SLocation location) {
		super(location);
	}

	public InitializerDecl(Modifiers modifiers, BlockStmt body/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, body/*, javadocComment*/)))));
	}

	public Modifiers modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public InitializerDecl withModifiers(Modifiers modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public BlockStmt body() {
		return location.nodeChild(BODY);
	}

	public InitializerDecl withBody(BlockStmt body) {
		return location.nodeWithChild(BODY, body);
	}

	/*
		public JavadocComment javadocComment() {
			return location.nodeChild(JAVADOC_COMMENT);
		}

		public InitializerDecl withJavadocComment(JavadocComment javadocComment) {
			return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
		}
	*/
	private static final int MODIFIERS = 0;
	private static final int BODY = 1;
//	private static final int JAVADOC_COMMENT = 3;

	public final static LexicalShape shape = composite(
			child(MODIFIERS),
			child(BODY)
	);
}
