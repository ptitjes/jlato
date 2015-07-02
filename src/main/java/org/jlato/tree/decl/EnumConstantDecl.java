package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.NameExpr;

public class EnumConstantDecl extends Decl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public EnumConstantDecl instantiate(SLocation location) {
			return new EnumConstantDecl(location);
		}
	};

	private EnumConstantDecl(SLocation location) {
		super(location);
	}

	public EnumConstantDecl(NameExpr name, NodeList<Expr> args, NodeList<Decl> classBody/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, runOf(name, args, classBody/*, javadocComment*/))));
	}

	public NameExpr name() {
		return location.nodeChild(NAME);
	}

	public EnumConstantDecl withName(NameExpr name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Expr> args() {
		return location.nodeChild(ARGS);
	}

	public EnumConstantDecl withArgs(NodeList<Expr> args) {
		return location.nodeWithChild(ARGS, args);
	}

	public NodeList<Decl> classBody() {
		return location.nodeChild(CLASS_BODY);
	}

	public EnumConstantDecl withClassBody(NodeList<Decl> classBody) {
		return location.nodeWithChild(CLASS_BODY, classBody);
	}
/*

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public EnumConstantDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}
*/

	private static final int NAME = 1;
	private static final int ARGS = 2;
	private static final int CLASS_BODY = 3;
	private static final int JAVADOC_COMMENT = 4;
}
