package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.NameExpr;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.Type;

public class MethodDecl extends BodyDecl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public MethodDecl instantiate(SLocation location) {
			return new MethodDecl(location);
		}
	};

	private MethodDecl(SLocation location) {
		super(location);
	}

	public MethodDecl(int modifiers, NodeList<TypeParameter> typeParameters, Type type, NameExpr name, NodeList<Parameter> parameters, int arrayCount, NodeList<NameExpr> throws_, BlockStmt body, boolean isDefault/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, runOf(modifiers, typeParameters, type, name, parameters, arrayCount, throws_, body, isDefault/*, javadocComment*/))));
	}

	public int modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public MethodDecl withModifiers(int modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public NodeList<TypeParameter> typeParameters() {
		return location.nodeChild(TYPE_PARAMETERS);
	}

	public MethodDecl withTypeParameters(NodeList<TypeParameter> typeParameters) {
		return location.nodeWithChild(TYPE_PARAMETERS, typeParameters);
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public MethodDecl withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public NameExpr name() {
		return location.nodeChild(NAME);
	}

	public MethodDecl withName(NameExpr name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Parameter> parameters() {
		return location.nodeChild(PARAMETERS);
	}

	public MethodDecl withParameters(NodeList<Parameter> parameters) {
		return location.nodeWithChild(PARAMETERS, parameters);
	}

	public int arrayCount() {
		return location.nodeChild(ARRAY_COUNT);
	}

	public MethodDecl withArrayCount(int arrayCount) {
		return location.nodeWithChild(ARRAY_COUNT, arrayCount);
	}

	public NodeList<NameExpr> throws_() {
		return location.nodeChild(THROWS_);
	}

	public MethodDecl withThrows_(NodeList<NameExpr> throws_) {
		return location.nodeWithChild(THROWS_, throws_);
	}

	public BlockStmt body() {
		return location.nodeChild(BODY);
	}

	public MethodDecl withBody(BlockStmt body) {
		return location.nodeWithChild(BODY, body);
	}

	public boolean isDefault() {
		return location.nodeChild(IS_DEFAULT);
	}

	public MethodDecl withIsDefault(boolean isDefault) {
		return location.nodeWithChild(IS_DEFAULT, isDefault);
	}
/*

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public MethodDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}
*/

	private static final int MODIFIERS = 1;
	private static final int TYPE_PARAMETERS = 2;
	private static final int TYPE = 3;
	private static final int NAME = 4;
	private static final int PARAMETERS = 5;
	private static final int ARRAY_COUNT = 6;
	private static final int THROWS_ = 7;
	private static final int BODY = 8;
	private static final int IS_DEFAULT = 9;
	private static final int JAVADOC_COMMENT = 10;
}
