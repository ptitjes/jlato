package org.jlato.internal.bu.type;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDVoidType;
import org.jlato.tree.*;

import static org.jlato.internal.shapes.LexicalShape.token;

public class SVoidType extends SNode<SVoidType> implements SType {

	public static BUTree<SVoidType> make() {
		return new BUTree<SVoidType>(new SVoidType());
	}

	public SVoidType() {
	}

	@Override
	public Kind kind() {
		return Kind.VoidType;
	}

	@Override
	protected Tree doInstantiate(TDLocation<SVoidType> location) {
		return new TDVoidType(location);
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

	public static final LexicalShape shape = token(LToken.Void);
}
