package org.jlato.internal.bu.type;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDUnknownType;
import org.jlato.tree.*;

import static org.jlato.internal.shapes.LexicalShape.none;

public class SUnknownType extends SNode<SUnknownType> implements SType {

	public static BUTree<SUnknownType> make() {
		return new BUTree<SUnknownType>(new SUnknownType());
	}

	public SUnknownType() {
	}

	@Override
	public Kind kind() {
		return Kind.UnknownType;
	}

	@Override
	protected Tree doInstantiate(TDLocation<SUnknownType> location) {
		return new TDUnknownType(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return null;
	}

	@Override
	public STraversal lastChild() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		return result;
	}

	public static final LexicalShape shape = none();
}
