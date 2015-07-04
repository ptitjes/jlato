package org.jlato.tree.type;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeData;
import org.jlato.tree.NodeList;
import org.jlato.tree.SLocation;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;

public class PrimitiveType extends AnnotatedType {

	public final static Tree.Kind kind = new Tree.Kind() {
		public PrimitiveType instantiate(SLocation location) {
			return new PrimitiveType(location);
		}
	};

	private PrimitiveType(SLocation location) {
		super(location);
	}

	public PrimitiveType(NodeList<AnnotationExpr> annotations, Primitive type) {
		super(new SLocation(new SNode(kind, new SNodeData(treesOf(annotations), attributesOf(type)))));
	}

	public Primitive type() {
		return location.nodeAttribute(TYPE);
	}

	public PrimitiveType withType(Primitive type) {
		return location.nodeWithAttribute(TYPE, type);
	}

	private static final int TYPE = 0;

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
