package org.jlato.internal.bu.type;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDIntersectionType;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SIntersectionType extends SNodeState<SIntersectionType> implements SType {

	public static STree<SIntersectionType> make(STree<SNodeListState> types) {
		return new STree<SIntersectionType>(new SIntersectionType(types));
	}

	public final STree<SNodeListState> types;

	public SIntersectionType(STree<SNodeListState> types) {
		this.types = types;
	}

	@Override
	public Kind kind() {
		return Kind.IntersectionType;
	}

	public STree<SNodeListState> types() {
		return types;
	}

	public SIntersectionType withTypes(STree<SNodeListState> types) {
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

	public static STypeSafeTraversal<SIntersectionType, SNodeListState, NodeList<Type>> TYPES = new STypeSafeTraversal<SIntersectionType, SNodeListState, NodeList<Type>>() {

		@Override
		public STree<?> doTraverse(SIntersectionType state) {
			return state.types;
		}

		@Override
		public SIntersectionType doRebuildParentState(SIntersectionType state, STree<SNodeListState> child) {
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
			child(TYPES, org.jlato.internal.bu.type.SType.intersectionShape)
	);
}
