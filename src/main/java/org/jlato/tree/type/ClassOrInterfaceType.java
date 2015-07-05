package org.jlato.tree.type;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.Type;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.space;

public class ClassOrInterfaceType extends ReferenceType {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ClassOrInterfaceType instantiate(SLocation location) {
			return new ClassOrInterfaceType(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ClassOrInterfaceType(SLocation location) {
		super(location);
	}

	public ClassOrInterfaceType(NodeList<AnnotationExpr> annotations, ClassOrInterfaceType scope, Name name, NodeList<Type> typeArgs) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(annotations, scope, name, typeArgs)))));
	}

	public ClassOrInterfaceType scope() {
		return location.nodeChild(SCOPE);
	}

	public ClassOrInterfaceType withScope(ClassOrInterfaceType scope) {
		return location.nodeWithChild(SCOPE, scope);
	}

	public Name name() {
		return location.nodeChild(NAME);
	}

	public ClassOrInterfaceType withName(Name name) {
		return location.nodeWithChild(NAME, name);
	}

	public NodeList<Type> typeArguments() {
		return location.nodeChild(TYPE_ARGUMENTS);
	}

	public ClassOrInterfaceType withTypeArguments(NodeList<Type> typeArguments) {
		return location.nodeWithChild(TYPE_ARGUMENTS, typeArguments);
	}

	private static final int SCOPE = 1;
	private static final int NAME = 2;
	private static final int TYPE_ARGUMENTS = 3;

	public final static LexicalShape shape = composite(
			children(ANNOTATIONS),
			nonNullChild(SCOPE, composite(child(SCOPE), token(LToken.Dot))),
			child(NAME),
			nonNullChild(TYPE_ARGUMENTS,
					nonEmptyChildren(TYPE_ARGUMENTS,
							children(TYPE_ARGUMENTS, token(LToken.Less), token(LToken.Comma), token(LToken.Greater)),
							composite(token(LToken.Less), token(LToken.Greater))
					)
			)
	);
}
