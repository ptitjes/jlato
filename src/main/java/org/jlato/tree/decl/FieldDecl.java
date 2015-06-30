package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

public class FieldDecl extends BodyDecl {

	public final static Tree.Kind kind = new Tree.Kind() {
		public FieldDecl instantiate(SLocation location) {
			return new FieldDecl(location);
		}
	};

	private FieldDecl(SLocation location) {
		super(location);
	}

	public FieldDecl(int modifiers, Type type, NodeList<VariableDeclarator> variables/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, runOf(modifiers, type, variables/*, javadocComment*/))));
	}

	public int modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public FieldDecl withModifiers(int modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public FieldDecl withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public NodeList<VariableDeclarator> variables() {
		return location.nodeChild(VARIABLES);
	}

	public FieldDecl withVariables(NodeList<VariableDeclarator> variables) {
		return location.nodeWithChild(VARIABLES, variables);
	}
/*

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public FieldDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}
*/

	private static final int MODIFIERS = 1;
	private static final int TYPE = 2;
	private static final int VARIABLES = 3;
	private static final int JAVADOC_COMMENT = 4;
}
