package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.*;
import org.jlato.tree.name.Name;

public class EnumConstantDecl extends Decl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public EnumConstantDecl instantiate(SLocation location) {
			return new EnumConstantDecl(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private EnumConstantDecl(SLocation location) {
		super(location);
	}

	public EnumConstantDecl(Modifiers modifiers, Name name, NodeList<Expr> args, NodeList<Decl> classBody/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, name, args, classBody/*, javadocComment*/)))));
	}

	public Modifiers modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public VariableDecl withModifiers(Modifiers modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public EnumConstantDecl withName(Name name) {
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

	private static final int MODIFIERS = 0;
	private static final int NAME = 1;
	private static final int ARGS = 2;
	private static final int CLASS_BODY = 3;
//	private static final int JAVADOC_COMMENT = 4;
}
