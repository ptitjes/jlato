package org.jlato.tree.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SNode;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.NameExpr;
import org.jlato.tree.type.ClassOrInterfaceType;

public class TypeDecl extends Decl implements TopLevel, Member {

	public final static Tree.Kind kind = new Tree.Kind() {
		public TypeDecl instantiate(SLocation location) {
			return new TypeDecl(location);
		}
	};

	protected TypeDecl(SLocation location) {
		super(location);
	}

	public <M extends Decl & Member> TypeDecl(Modifiers modifiers, TypeKind typeKind, NameExpr name, NodeList<TypeParameter> typeParameters, NodeList<ClassOrInterfaceType> extendsClause, NodeList<ClassOrInterfaceType> implementsClause, NodeList<M> members/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, runOf(modifiers, typeKind, name, typeParameters, extendsClause, implementsClause, members/*, javadocComment*/))));
	}

	public Modifiers modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public TypeDecl withModifiers(Modifiers modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public TypeKind typeKind() {
		return location.nodeChild(TYPE_KIND);
	}

	public TypeDecl withTypeKind(TypeKind typeKind) {
		return location.nodeWithChild(TYPE_KIND, typeKind);
	}

	public NameExpr name() {
		return location.nodeChild(NAME);
	}

	public TypeDecl withName(NameExpr name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<TypeParameter> typeParameters() {
		return location.nodeChild(TYPE_PARAMETERS);
	}

	public TypeDecl withTypeParameters(NodeList<TypeParameter> typeParameters) {
		return location.nodeWithChild(TYPE_PARAMETERS, typeParameters);
	}

	public NodeList<ClassOrInterfaceType> extendsClause() {
		return location.nodeChild(EXTENDS_CLAUSE);
	}

	public TypeDecl withExtendsClause(NodeList<ClassOrInterfaceType> extendsClause) {
		return location.nodeWithChild(EXTENDS_CLAUSE, extendsClause);
	}

	public NodeList<ClassOrInterfaceType> implementsClause() {
		return location.nodeChild(IMPLEMENTS_CLAUSE);
	}

	public TypeDecl withImplementsClause(NodeList<ClassOrInterfaceType> implementsClause) {
		return location.nodeWithChild(IMPLEMENTS_CLAUSE, implementsClause);
	}

	public <M extends Decl & Member> NodeList<M> members() {
		return location.nodeChild(MEMBERS);
	}

	public <M extends Decl & Member> TypeDecl withMembers(NodeList<M> members) {
		return location.nodeWithChild(MEMBERS, members);
	}

	private static final int MODIFIERS = 0;
	private static final int TYPE_KIND = 1;
	private static final int NAME = 2;
	private static final int TYPE_PARAMETERS = 3;
	private static final int EXTENDS_CLAUSE = 4;
	private static final int IMPLEMENTS_CLAUSE = 5;
	private static final int MEMBERS = 6;

	public static class TypeKind extends Tree {

		public final static Kind kind = new Tree.Kind() {
			public Tree instantiate(SLocation location) {
				return new TypeKind(location);
			}
		};

		public static final TypeKind Class = new TypeKind(LToken.Class);
		public static final TypeKind Interface = new TypeKind(LToken.Interface);
		public static final TypeKind Enum = new TypeKind(LToken.Enum);
		public static final TypeKind AnnotationType = new TypeKind(LToken.AnnotationType);

		protected TypeKind(SLocation location) {
			super(location);
		}

		private TypeKind(LToken keyword) {
			super(new SLocation(new SLeaf(kind, keyword)));
		}

		public String toString() {
			return location.leafToken().toString();
		}
	}
}
