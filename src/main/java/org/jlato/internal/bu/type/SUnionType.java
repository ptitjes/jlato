package org.jlato.internal.bu.type;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDUnionType;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SUnionType extends SNodeState<SUnionType> implements SType {

	public static STree<SUnionType> make(STree<SNodeListState> types) {
		return new STree<SUnionType>(new SUnionType(types));
	}

	public final STree<SNodeListState> types;

	public SUnionType(STree<SNodeListState> types) {
		this.types = types;
	}

	@Override
	public Kind kind() {
		return Kind.UnionType;
	}

	public STree<SNodeListState> types() {
		return types;
	}

	public SUnionType withTypes(STree<SNodeListState> types) {
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
		public STree<?> doTraverse(SUnionType state) {
			return state.types;
		}

		@Override
		public SUnionType doRebuildParentState(SUnionType state, STree<SNodeListState> child) {
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
