package org.jlato.internal.bu.type;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDIntersectionType;
import org.jlato.tree.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;

public class SIntersectionType extends SNode<SIntersectionType> implements SType {

	public static BUTree<SIntersectionType> make(BUTree<SNodeList> types) {
		return new BUTree<SIntersectionType>(new SIntersectionType(types));
	}

	public final BUTree<SNodeList> types;

	public SIntersectionType(BUTree<SNodeList> types) {
		this.types = types;
	}

	@Override
	public Kind kind() {
		return Kind.IntersectionType;
	}

	public BUTree<SNodeList> types() {
		return types;
	}

	public SIntersectionType withTypes(BUTree<SNodeList> types) {
		return new SIntersectionType(types);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SIntersectionType> location) {
		return new TDIntersectionType(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return TYPES;
	}

	@Override
	public STraversal lastChild() {
		return TYPES;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SIntersectionType state = (SIntersectionType) o;
		if (!types.equals(state.types))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + types.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SIntersectionType, SNodeList, NodeList<Type>> TYPES = new STypeSafeTraversal<SIntersectionType, SNodeList, NodeList<Type>>() {

		@Override
		public BUTree<?> doTraverse(SIntersectionType state) {
			return state.types;
		}

		@Override
		public SIntersectionType doRebuildParentState(SIntersectionType state, BUTree<SNodeList> child) {
			return state.withTypes(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(TYPES, org.jlato.internal.bu.type.SType.intersectionShape)
	);
}
