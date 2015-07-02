package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Expr;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.NameExpr;
import org.jlato.tree.type.Type;

public class AnnotationMemberDecl extends Decl implements Member {

	public final static Tree.Kind kind = new Tree.Kind() {
		public AnnotationMemberDecl instantiate(SLocation location) {
			return new AnnotationMemberDecl(location);
		}
	};

	private AnnotationMemberDecl(SLocation location) {
		super(location);
	}

	public AnnotationMemberDecl(Modifiers modifiers, Type type, NameExpr name, Expr defaultValue/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, runOf(modifiers, type, name, defaultValue/*, javadocComment*/))));
	}

	public Modifiers modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public AnnotationMemberDecl withModifiers(Modifiers modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public Type type() {
		return location.nodeChild(TYPE);
	}

	public AnnotationMemberDecl withType(Type type) {
		return location.nodeWithChild(TYPE, type);
	}

	public NameExpr name() {
		return location.nodeChild(NAME);
	}

	public AnnotationMemberDecl withName(NameExpr name) {
		return location.nodeWithChild(NAME, name);
	}

	public Expr defaultValue() {
		return location.nodeChild(DEFAULT_VALUE);
	}

	public AnnotationMemberDecl withDefaultValue(Expr defaultValue) {
		return location.nodeWithChild(DEFAULT_VALUE, defaultValue);
	}
/*

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public AnnotationMemberDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}
*/

	private static final int MODIFIERS = 0;
	private static final int TYPE = 1;
	private static final int NAME = 2;
	private static final int DEFAULT_VALUE = 3;
//	private static final int JAVADOC_COMMENT = 5;
}
