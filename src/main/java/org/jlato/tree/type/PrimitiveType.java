package org.jlato.tree.type;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SLeaf;
import org.jlato.internal.bu.SNode;
import org.jlato.tree.Tree;

public class PrimitiveType extends Type {

	public final static Tree.Kind kind = new Tree.Kind() {
		public PrimitiveType instantiate(SLocation location) {
			return new PrimitiveType(location);
		}
	};

	private PrimitiveType(SLocation location) {
		super(location);
	}

	public PrimitiveType(Primitive type) {
		super(new SLocation(new SNode(kind, runOf(type))));
	}

	public Primitive type() {
		return location.nodeChild(TYPE);
	}

	public PrimitiveType withType(Primitive type) {
		return location.nodeWithChild(TYPE, type);
	}

	private static final int TYPE = 1;

	public static class Primitive extends Tree {

		public final static Tree.Kind kind = new Tree.Kind() {
			public Primitive instantiate(SLocation location) {
				return new Primitive(location);
			}
		};

		public static final Primitive Boolean = new Primitive(LToken.Boolean);
		public static final Primitive Char = new Primitive(LToken.Char);
		public static final Primitive Byte = new Primitive(LToken.Byte);
		public static final Primitive Short = new Primitive(LToken.Short);
		public static final Primitive Int = new Primitive(LToken.Int);
		public static final Primitive Long = new Primitive(LToken.Long);
		public static final Primitive Float = new Primitive(LToken.Float);
		public static final Primitive Double = new Primitive(LToken.Double);

		private Primitive(SLocation location) {
			super(location);
		}

		private Primitive(LToken token) {super(new SLocation(new SLeaf(kind, token)));}

		public String toString() {
			return location.leafToken().toString();
		}

	}
}
