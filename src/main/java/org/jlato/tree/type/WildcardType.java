package org.jlato.tree.type;

import org.jlato.internal.bu.SNode;
import org.jlato.tree.Tree;

public class WildcardType extends Type {

	public final static Tree.Kind kind = new Tree.Kind() {
		public WildcardType instantiate(SLocation location) {
			return new WildcardType(location);
		}
	};

	private WildcardType(SLocation location) {
		super(location);
	}

	public WildcardType(ReferenceType ext, ReferenceType sup) {
		super(new SLocation(new SNode(kind, runOf(ext, sup))));
	}

	public ReferenceType ext() {
		return location.nodeChild(EXT);
	}

	public WildcardType withExt(ReferenceType ext) {
		return location.nodeWithChild(EXT, ext);
	}

	public ReferenceType sup() {
		return location.nodeChild(SUP);
	}

	public WildcardType withSup(ReferenceType sup) {
		return location.nodeWithChild(SUP, sup);
	}

	private static final int EXT = 1;
	private static final int SUP = 2;
}
