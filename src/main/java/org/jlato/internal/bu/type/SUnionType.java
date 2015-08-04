package org.jlato.internal.bu.type;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDUnionType;
import org.jlato.tree.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LexicalShape.child;
import static org.jlato.internal.shapes.LexicalShape.composite;

public class SUnionType extends SNodeState<SUnionType> implements SType {

	public static BUTree<SUnionType> make(BUTree<SNodeListState> types) {
		return new BUTree<SUnionType>(new SUnionType(types));
	}

	public final BUTree<SNodeListState> types;

	public SUnionType(BUTree<SNodeListState> types) {
		this.types = types;
	}

	@Override
	public Kind kind() {
		return Kind.UnionType;
	}

	public BUTree<SNodeListState> types() {
		return types;
	}

	public SUnionType withTypes(BUTree<SNodeListState> types) {
		return new SUnionType(types);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SUnionType> location) {
		return new TDUnionType(location);
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
		SUnionType state = (SUnionType) o;
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

	public static STypeSafeTraversal<SUnionType, SNodeListState, NodeList<Type>> TYPES = new STypeSafeTraversal<SUnionType, SNodeListState, NodeList<Type>>() {

		@Override
		public BUTree<?> doTraverse(SUnionType state) {
			return state.types;
		}

		@Override
		public SUnionType doRebuildParentState(SUnionType state, BUTree<SNodeListState> child) {
			return state.withTypes(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(TYPES, org.jlato.internal.bu.type.SType.unionShape)
	);
}
