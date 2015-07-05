package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.tree.Decl;
import org.jlato.tree.Expr;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
import org.jlato.tree.Type;

public class AnnotationMemberDecl extends Decl implements Member {

	public final static Tree.Kind kind = new Tree.Kind() {
		public AnnotationMemberDecl instantiate(SLocation location) {
			return new AnnotationMemberDecl(location);
		}
	};

	private AnnotationMemberDecl(SLocation location) {
		super(location);
	}

	public AnnotationMemberDecl(Modifiers modifiers, Type type, Name name, Expr defaultValue/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, type, name, defaultValue/*, javadocComment*/)))));
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

	public Name name() {
		return location.nodeChild(NAME);
	}

	public AnnotationMemberDecl withName(Name name) {
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
