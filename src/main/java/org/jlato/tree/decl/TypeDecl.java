package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.Decl;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.name.Name;
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

	public <M extends Decl & Member> TypeDecl(Modifiers modifiers, TypeKind typeKind, Name name, NodeList<TypeParameter> typeParameters, NodeList<ClassOrInterfaceType> extendsClause, NodeList<ClassOrInterfaceType> implementsClause, NodeList<M> members/*, JavadocComment javadocComment*/) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(modifiers, name, typeParameters, extendsClause, implementsClause, members/*, javadocComment*/), attributesOf(typeKind)))));
	}

	public Modifiers modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public TypeDecl withModifiers(Modifiers modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public TypeKind typeKind() {
		return location.nodeAttribute(TYPE_KIND);
	}

	public TypeDecl withTypeKind(TypeKind typeKind) {
		return location.nodeWithAttribute(TYPE_KIND, typeKind);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public TypeDecl withName(Name name) {
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
	private static final int NAME = 1;
	private static final int TYPE_PARAMETERS = 2;
	private static final int EXTENDS_CLAUSE = 3;
	private static final int IMPLEMENTS_CLAUSE = 4;
	private static final int MEMBERS = 5;

	private static final int TYPE_KIND = 0;

	public enum TypeKind {
		Class, Interface, Enum, AnnotationType
	}
}
