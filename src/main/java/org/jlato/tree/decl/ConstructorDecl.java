package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.NameExpr;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.tree.type.ClassOrInterfaceType;

public class ConstructorDecl extends Decl implements Member {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ConstructorDecl instantiate(SLocation location) {
			return new ConstructorDecl(location);
		}
	};

	private ConstructorDecl(SLocation location) {
		super(location);
	}

	public ConstructorDecl(Modifiers modifiers, NodeList<TypeParameter> typeParameters, NameExpr name, NodeList<Parameter> parameters, NodeList<ClassOrInterfaceType> throwsClause, BlockStmt block/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, runOf(modifiers, typeParameters, name, parameters, throwsClause, block/*, javadocComment*/))));
	}

	public Modifiers modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public ConstructorDecl withModifiers(Modifiers modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public NodeList<TypeParameter> typeParameters() {
		return location.nodeChild(TYPE_PARAMETERS);
	}

	public ConstructorDecl withTypeParameters(NodeList<TypeParameter> typeParameters) {
		return location.nodeWithChild(TYPE_PARAMETERS, typeParameters);
	}

	public NameExpr name() {
		return location.nodeChild(NAME);
	}

	public ConstructorDecl withName(NameExpr name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Parameter> parameters() {
		return location.nodeChild(PARAMETERS);
	}

	public ConstructorDecl withParameters(NodeList<Parameter> parameters) {
		return location.nodeWithChild(PARAMETERS, parameters);
	}

	public NodeList<ClassOrInterfaceType> throwsClause() {
		return location.nodeChild(THROWS_CLAUSE);
	}

	public ConstructorDecl withThrowsClause(NodeList<ClassOrInterfaceType> throwsClause) {
		return location.nodeWithChild(THROWS_CLAUSE, throwsClause);
	}

	public BlockStmt block() {
		return location.nodeChild(BLOCK);
	}

	public ConstructorDecl withBlock(BlockStmt block) {
		return location.nodeWithChild(BLOCK, block);
	}
/*

	public JavadocComment javadocComment() {
		return location.nodeChild(JAVADOC_COMMENT);
	}

	public ConstructorDecl withJavadocComment(JavadocComment javadocComment) {
		return location.nodeWithChild(JAVADOC_COMMENT, javadocComment);
	}
*/

	private static final int MODIFIERS = 0;
	private static final int TYPE_PARAMETERS = 1;
	private static final int NAME = 2;
	private static final int PARAMETERS = 3;
	private static final int THROWS_CLAUSE = 4;
	private static final int BLOCK = 5;
//	private static final int JAVADOC_COMMENT = 7;
}
