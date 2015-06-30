package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.type.ClassOrInterfaceType;

public class EnumDecl extends TypeDecl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public EnumDecl instantiate(SLocation location) {
			return new EnumDecl(location);
		}
	};

	private EnumDecl(SLocation location) {
		super(location);
	}

	public EnumDecl(NodeList<ClassOrInterfaceType> implementsList, NodeList<EnumConstantDecl> entries/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, runOf(implementsList, entries/*, javadocComment*/))));
	}

	public NodeList<ClassOrInterfaceType> implementsList() {
		return location.nodeChild(IMPLEMENTS_LIST);
	}

	public EnumDecl withImplementsList(NodeList<ClassOrInterfaceType> implementsList) {
		return location.nodeWithChild(IMPLEMENTS_LIST, implementsList);
	}

	public NodeList<EnumConstantDecl> entries() {
		return location.nodeChild(ENTRIES);
	}

	public EnumDecl withEntries(NodeList<EnumConstantDecl> entries) {
		return location.nodeWithChild(ENTRIES, entries);
	}
/*

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public EnumDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}
*/

	private static final int IMPLEMENTS_LIST = 4;
	private static final int ENTRIES = 5;
	private static final int JAVADOC_COMMENT = 6;
}
