package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.type.ClassOrInterfaceType;

public class ClassOrInterfaceDecl extends TypeDecl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ClassOrInterfaceDecl instantiate(SLocation location) {
			return new ClassOrInterfaceDecl(location);
		}
	};

	private ClassOrInterfaceDecl(SLocation location) {
		super(location);
	}

	public ClassOrInterfaceDecl(boolean interface_, NodeList<TypeParameter> typeParameters, NodeList<ClassOrInterfaceType> extendsList, NodeList<ClassOrInterfaceType> implementsList, JavadocComment javadocComment) {
		super(new SLocation(new SNode(kind, runOf(interface_, typeParameters, extendsList, implementsList, javadocComment))));
	}

	public boolean interface_() {
		return location.nodeChild(INTERFACE_);
	}

	public ClassOrInterfaceDecl withInterface_(boolean interface_) {
		return location.nodeWithChild(INTERFACE_, interface_);
	}

	public NodeList<TypeParameter> typeParameters() {
		return location.nodeChild(TYPE_PARAMETERS);
	}

	public ClassOrInterfaceDecl withTypeParameters(NodeList<TypeParameter> typeParameters) {
		return location.nodeWithChild(TYPE_PARAMETERS, typeParameters);
	}

	public NodeList<ClassOrInterfaceType> extendsList() {
		return location.nodeChild(EXTENDS_LIST);
	}

	public ClassOrInterfaceDecl withExtendsList(NodeList<ClassOrInterfaceType> extendsList) {
		return location.nodeWithChild(EXTENDS_LIST, extendsList);
	}

	public NodeList<ClassOrInterfaceType> implementsList() {
		return location.nodeChild(IMPLEMENTS_LIST);
	}

	public ClassOrInterfaceDecl withImplementsList(NodeList<ClassOrInterfaceType> implementsList) {
		return location.nodeWithChild(IMPLEMENTS_LIST, implementsList);
	}

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public ClassOrInterfaceDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}

	private static final int INTERFACE_ = 4;
	private static final int TYPE_PARAMETERS = 5;
	private static final int EXTENDS_LIST = 6;
	private static final int IMPLEMENTS_LIST = 7;
	private static final int JAVADOC_COMMENT = 8;
}
