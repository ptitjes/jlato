package org.jlato.tree.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.*;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;

public class AnnotationMemberDecl extends Decl implements Member {

	public final static Tree.Kind kind = new Tree.Kind() {
		public AnnotationMemberDecl instantiate(SLocation location) {
			return new AnnotationMemberDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private AnnotationMemberDecl(SLocation location) {
		super(location);
	}

	public AnnotationMemberDecl(Modifiers modifiers, Type type, Name name, NodeList<ArrayDim> dimensions, Expr defaultValue/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, type, name, dimensions, defaultValue/*, javadocComment*/)))));
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

	public NodeList<ArrayDim> dimensions() {
		return location.nodeChild(DIMENSIONS);
	}

	public VariableDeclaratorId withDimensions(NodeList<ArrayDim> dimensions) {
		return location.nodeWithChild(DIMENSIONS, dimensions);
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
	private static final int DIMENSIONS = 3;
	private static final int DEFAULT_VALUE = 4;
//	private static final int JAVADOC_COMMENT = 5;

	public final static LexicalShape shape = composite(
			child(MODIFIERS),
			child(TYPE), child(NAME),
			token(LToken.ParenthesisLeft), token(LToken.ParenthesisRight),
			nonNullChild(DEFAULT_VALUE, composite(token(LToken.Default), child(DEFAULT_VALUE))),
			token(LToken.SemiColon)
	);
}
