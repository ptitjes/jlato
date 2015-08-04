package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDVariableDeclaratorId;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.name.*;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SVariableDeclaratorId extends SNodeState<SVariableDeclaratorId> implements STreeState {

	public static STree<SVariableDeclaratorId> make(STree<SName> name, STree<SNodeListState> dims) {
		return new STree<SVariableDeclaratorId>(new SVariableDeclaratorId(name, dims));
	}

	public final STree<SName> name;

	public final STree<SNodeListState> dims;

	public SVariableDeclaratorId(STree<SName> name, STree<SNodeListState> dims) {
		this.name = name;
		this.dims = dims;
	}

	@Override
	public Kind kind() {
		return Kind.VariableDeclaratorId;
	}

	public STree<SName> name() {
		return name;
	}

	public SVariableDeclaratorId withName(STree<SName> name) {
		return new SVariableDeclaratorId(name, dims);
	}

	public STree<SNodeListState> dims() {
		return dims;
	}

	public SVariableDeclaratorId withDims(STree<SNodeListState> dims) {
		return new SVariableDeclaratorId(name, dims);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SVariableDeclaratorId> location) {
		return new TDVariableDeclaratorId(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return NAME;
	}

	@Override
	public STraversal lastChild() {
		return DIMS;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SVariableDeclaratorId state = (SVariableDeclaratorId) o;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!dims.equals(state.dims))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + dims.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SVariableDeclaratorId, SName, Name> NAME = new STypeSafeTraversal<SVariableDeclaratorId, SName, Name>() {

		@Override
		public STree<?> doTraverse(SVariableDeclaratorId state) {
			return state.name;
		}

		@Override
		public SVariableDeclaratorId doRebuildParentState(SVariableDeclaratorId state, STree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return DIMS;
		}
	};

	public static STypeSafeTraversal<SVariableDeclaratorId, SNodeListState, NodeList<ArrayDim>> DIMS = new STypeSafeTraversal<SVariableDeclaratorId, SNodeListState, NodeList<ArrayDim>>() {

		@Override
		public STree<?> doTraverse(SVariableDeclaratorId state) {
			return state.dims;
		}

		@Override
		public SVariableDeclaratorId doRebuildParentState(SVariableDeclaratorId state, STree<SNodeListState> child) {
			return state.withDims(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(NAME),
			child(DIMS, list())
	);
}
