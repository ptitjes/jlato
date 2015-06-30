package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.NameExpr;
import org.jlato.tree.stmt.BlockStmt;

public class ConstructorDecl extends BodyDecl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ConstructorDecl instantiate(SLocation location) {
			return new ConstructorDecl(location);
		}
	};

	private ConstructorDecl(SLocation location) {
		super(location);
	}

	public ConstructorDecl(int modifiers, NodeList<TypeParameter> typeParameters, NameExpr name, NodeList<Parameter> parameters, NodeList<NameExpr> throws_, BlockStmt block, JavadocComment javadocComment) {
		super(new SLocation(new SNode(kind, runOf(modifiers, typeParameters, name, parameters, throws_, block, javadocComment))));
	}

	public int modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public ConstructorDecl withModifiers(int modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public NodeList<TypeParameter> typeParameters() {
		return location.nodeChild(TYPE_PARAMETERS);
	}

	public ConstructorDecl withTypeParameters(NodeList<TypeParameter> typeParameters) {
		return location.nodeWithChild(TYPE_PARAMETERS, typeParameters);
	}

	public NameExpr name() {
		return location.nodeChild(NAME);
	}

	public ConstructorDecl withName(NameExpr name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Parameter> parameters() {
		return location.nodeChild(PARAMETERS);
	}

	public ConstructorDecl withParameters(NodeList<Parameter> parameters) {
		return location.nodeWithChild(PARAMETERS, parameters);
	}

	public NodeList<NameExpr> throws_() {
		return location.nodeChild(THROWS_);
	}

	public ConstructorDecl withThrows_(NodeList<NameExpr> throws_) {
		return location.nodeWithChild(THROWS_, throws_);
	}

	public BlockStmt block() {
		return location.nodeChild(BLOCK);
	}

	public ConstructorDecl withBlock(BlockStmt block) {
		return location.nodeWithChild(BLOCK, block);
	}

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public ConstructorDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}

	private static final int MODIFIERS = 1;
	private static final int TYPE_PARAMETERS = 2;
	private static final int NAME = 3;
	private static final int PARAMETERS = 4;
	private static final int THROWS_ = 5;
	private static final int BLOCK = 6;
	private static final int JAVADOC_COMMENT = 7;
}
