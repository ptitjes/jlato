package org.jlato.tree.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.*;
import org.jlato.tree.name.Name;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.ClassOrInterfaceType;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.space;

public class MethodDecl extends Decl implements Member {

	public final static Tree.Kind kind = new Tree.Kind() {
		public MethodDecl instantiate(SLocation location) {
			return new MethodDecl(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private MethodDecl(SLocation location) {
		super(location);
	}

	public MethodDecl(Modifiers modifiers, NodeList<TypeParameter> typeParameters, Type type, Name name, NodeList<Parameter> parameters, NodeList<ArrayDim> dimensions, NodeList<ClassOrInterfaceType> throwsClause, BlockStmt body/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, typeParameters, type, name, parameters, dimensions, throwsClause, body/*, javadocComment*/)))));
	}

	public Modifiers modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public LocalVariableDecl withModifiers(Modifiers modifiers) {
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

	public Name name() {
		return location.nodeChild(NAME);
	}

	public MethodDecl withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Parameter> parameters() {
		return location.nodeChild(PARAMETERS);
	}

	public MethodDecl withParameters(NodeList<Parameter> parameters) {
		return location.nodeWithChild(PARAMETERS, parameters);
	}

	public NodeList<ArrayDim> dimensions() {
		return location.nodeChild(DIMENSIONS);
	}

	public VariableDeclaratorId withDimensions(NodeList<ArrayDim> dimensions) {
		return location.nodeWithChild(DIMENSIONS, dimensions);
	}

	public NodeList<ClassOrInterfaceType> throwsClause() {
		return location.nodeChild(THROWS_CLAUSE);
	}

	public ConstructorDecl withThrowsClause(NodeList<ClassOrInterfaceType> throwsClause) {
		return location.nodeWithChild(THROWS_CLAUSE, throwsClause);
	}

	public BlockStmt body() {
		return location.nodeChild(BODY);
	}

	public MethodDecl withBody(BlockStmt body) {
		return location.nodeWithChild(BODY, body);
	}
/*

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public MethodDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}
*/

	private static final int MODIFIERS = 0;
	private static final int TYPE_PARAMETERS = 1;
	private static final int TYPE = 2;
	private static final int NAME = 3;
	private static final int PARAMETERS = 4;
	private static final int DIMENSIONS = 5;
	private static final int THROWS_CLAUSE = 6;
	private static final int BODY = 7;
//	private static final int JAVADOC_COMMENT = 10;

	public final static LexicalShape shape = composite(
			child(MODIFIERS),
			children(TYPE_PARAMETERS, token(LToken.Less), token(LToken.Comma), token(LToken.Greater).withSpacingAfter(space())),
			child(TYPE), child(NAME),
			token(LToken.ParenthesisLeft), children(PARAMETERS, token(LToken.Comma)), token(LToken.ParenthesisRight),
			children(DIMENSIONS),
			children(THROWS_CLAUSE, token(LToken.Throws).withSpacingBefore(space()), token(LToken.Comma), none()),
			nonNullChild(BODY,
					composite(none().withSpacing(space()), child(BODY)),
					token(LToken.SemiColon)
			)
	);
}
