package org.jlato.tree.type;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;

import static org.jlato.internal.shapes.LexicalShape.Factory.children;
import static org.jlato.internal.shapes.LexicalShape.Factory.composite;
import static org.jlato.internal.shapes.LexicalShape.Factory.token;

public class PrimitiveType extends AnnotatedType {

	public final static Tree.Kind kind = new Tree.Kind() {
		public PrimitiveType instantiate(SLocation location) {
			return new PrimitiveType(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private PrimitiveType(SLocation location) {
		super(location);
	}

	public PrimitiveType(NodeList<AnnotationExpr> annotations, Primitive type) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(annotations), dataOf(type)))));
	}

	public Primitive type() {
		return location.nodeData(TYPE);
	}

	public PrimitiveType withType(Primitive type) {
		return location.nodeWithData(TYPE, type);
	}

	private static final int TYPE = 0;

	public final static LexicalShape shape = composite(
			children(ANNOTATIONS),
			token(new LSToken.Provider() {
				public LToken tokenFor(STree tree) {
					return ((Primitive) tree.state.data(TYPE)).token;
				}
			})
	);

	public enum Primitive {
		Boolean(LToken.Boolean),
		Char(LToken.Char),
		Byte(LToken.Byte),
		Short(LToken.Short),
		Int(LToken.Int),
		Long(LToken.Long),
		Float(LToken.Float),
		Double(LToken.Double),
		// Keep last comma
		;

		protected final LToken token;

		Primitive(LToken token) {
			this.token = token;
		}

		public String toString() {
			return token.toString();
		}
	}
}
