package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Decl;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.Type;

public class VariableDecl extends Decl implements Member {

	public final static Tree.Kind kind = new Tree.Kind() {
		public VariableDecl instantiate(SLocation location) {
			return new VariableDecl(location);
		}

		public LexicalShape shape() {
			return null;
		}
	};

	private VariableDecl(SLocation location) {
		super(location);
	}

	public VariableDecl(Modifiers modifiers, Type type, NodeList<VariableDeclarator> variables/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, type, variables/*, javadocComment*/)))));
	}

	public Modifiers modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public VariableDecl withModifiers(Modifiers modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public VariableDecl withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public NodeList<VariableDeclarator> variables() {
		return location.nodeChild(VARIABLES);
	}

	public VariableDecl withVariables(NodeList<VariableDeclarator> variables) {
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

	private static final int MODIFIERS = 0;
	private static final int TYPE = 1;
	private static final int VARIABLES = 2;
//	private static final int JAVADOC_COMMENT = 4;
}
