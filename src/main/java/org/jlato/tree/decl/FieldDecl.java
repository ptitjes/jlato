package org.jlato.tree.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.Decl;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Type;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalShape.Factory.children;
import static org.jlato.internal.shapes.LexicalShape.Factory.none;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.space;

public class FieldDecl extends Decl implements Member {

	public final static Kind kind = new Kind() {
		public FieldDecl instantiate(SLocation location) {
			return new FieldDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private FieldDecl(SLocation location) {
		super(location);
	}

	public FieldDecl(Modifiers modifiers, Type type, NodeList<VariableDeclarator> variables/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, type, variables/*, javadocComment*/)))));
	}

	public Modifiers modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public FieldDecl withModifiers(Modifiers modifiers) {
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

	private static final int MODIFIERS = 0;
	private static final int TYPE = 1;
	private static final int VARIABLES = 2;
//	private static final int JAVADOC_COMMENT = 4;

	public final static LexicalShape shape = composite(
			child(MODIFIERS),
			child(TYPE),
			none().withSpacing(space()),
			children(VARIABLES, token(LToken.Comma)),
			token(LToken.SemiColon)
	);
}
