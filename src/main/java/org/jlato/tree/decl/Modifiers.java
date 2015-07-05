package org.jlato.tree.decl;

import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;

import static org.jlato.internal.shapes.LexicalShape.Factory.children;
import static org.jlato.internal.shapes.LexicalShape.Factory.composite;
import static org.jlato.internal.shapes.LexicalShape.Factory.none;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.newLine;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.space;

public class Modifiers extends Tree {

	public final static Kind kind = new Kind() {
		public Modifiers instantiate(SLocation location) {
			return new Modifiers(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private Modifiers(SLocation location) {
		super(location);
	}

	public Modifiers(NodeList<Modifier> modifiers, NodeList<AnnotationExpr> annotations) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(modifiers, annotations)))));
	}

	public NodeList<Modifier> modifiers() {
		return location.nodeChild(MODIFIERS);
	}

	public Modifiers withModifiers(NodeList<Modifier> modifiers) {
		return location.nodeWithChild(MODIFIERS, modifiers);
	}

	public boolean contains(Modifier modifier) {
		return modifiers().contains(modifier);
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.nodeChild(ANNOTATIONS);
	}

	public Modifiers withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.nodeWithChild(ANNOTATIONS, annotations);
	}

	private static final int MODIFIERS = 0;
	private static final int ANNOTATIONS = 1;

	public final static LexicalShape shape = composite(
			children(ANNOTATIONS, none(), none().withSpacing(newLine()), none().withSpacing(newLine())),
			children(MODIFIERS, none(), none().withSpacing(space()), none().withSpacing(space()))
	);

	public final static LexicalShape oneLinerShape = composite(
			children(ANNOTATIONS, none(), none().withSpacing(space()), none().withSpacing(space())),
			children(MODIFIERS, none(), none().withSpacing(space()), none().withSpacing(space()))
	);
}
