package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDVariableDeclaratorId;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.name.*;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SVariableDeclaratorId extends SNode<SVariableDeclaratorId> implements STree {

	public static BUTree<SVariableDeclaratorId> make(BUTree<SName> name, BUTree<SNodeList> dims) {
		return new BUTree<SVariableDeclaratorId>(new SVariableDeclaratorId(name, dims));
	}

	public final BUTree<SName> name;

	public final BUTree<SNodeList> dims;

	public SVariableDeclaratorId(BUTree<SName> name, BUTree<SNodeList> dims) {
		this.name = name;
		this.dims = dims;
	}

	@Override
	public Kind kind() {
		return Kind.VariableDeclaratorId;
	}

	public SVariableDeclaratorId withName(BUTree<SName> name) {
		return new SVariableDeclaratorId(name, dims);
	}

	public SVariableDeclaratorId withDims(BUTree<SNodeList> dims) {
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
		public BUTree<?> doTraverse(SVariableDeclaratorId state) {
			return state.name;
		}

		@Override
		public SVariableDeclaratorId doRebuildParentState(SVariableDeclaratorId state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return DIMS;
		}
	};

	public static STypeSafeTraversal<SVariableDeclaratorId, SNodeList, NodeList<ArrayDim>> DIMS = new STypeSafeTraversal<SVariableDeclaratorId, SNodeList, NodeList<ArrayDim>>() {

		@Override
		public BUTree<?> doTraverse(SVariableDeclaratorId state) {
			return state.dims;
		}

		@Override
		public SVariableDeclaratorId doRebuildParentState(SVariableDeclaratorId state, BUTree<SNodeList> child) {
			return state.withDims(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(NAME),
			child(DIMS, list())
	);
}
