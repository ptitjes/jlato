package org.jlato.internal.bu.type;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDArrayType;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SArrayType extends SNode<SArrayType> implements SReferenceType {

	public static BUTree<SArrayType> make(BUTree<? extends SType> componentType, BUTree<SNodeList> dims) {
		return new BUTree<SArrayType>(new SArrayType(componentType, dims));
	}

	public final BUTree<? extends SType> componentType;

	public final BUTree<SNodeList> dims;

	public SArrayType(BUTree<? extends SType> componentType, BUTree<SNodeList> dims) {
		this.componentType = componentType;
		this.dims = dims;
	}

	@Override
	public Kind kind() {
		return Kind.ArrayType;
	}

	public BUTree<? extends SType> componentType() {
		return componentType;
	}

	public SArrayType withComponentType(BUTree<? extends SType> componentType) {
		return new SArrayType(componentType, dims);
	}

	public BUTree<SNodeList> dims() {
		return dims;
	}

	public SArrayType withDims(BUTree<SNodeList> dims) {
		return new SArrayType(componentType, dims);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SArrayType> location) {
		return new TDArrayType(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return COMPONENT_TYPE;
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
		SArrayType state = (SArrayType) o;
		if (componentType == null ? state.componentType != null : !componentType.equals(state.componentType))
			return false;
		if (!dims.equals(state.dims))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (componentType != null) result = 37 * result + componentType.hashCode();
		result = 37 * result + dims.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SArrayType, SType, Type> COMPONENT_TYPE = new STypeSafeTraversal<SArrayType, SType, Type>() {

		@Override
		public BUTree<?> doTraverse(SArrayType state) {
			return state.componentType;
		}

		@Override
		public SArrayType doRebuildParentState(SArrayType state, BUTree<SType> child) {
			return state.withComponentType(child);
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

	public static STypeSafeTraversal<SArrayType, SNodeList, NodeList<ArrayDim>> DIMS = new STypeSafeTraversal<SArrayType, SNodeList, NodeList<ArrayDim>>() {

		@Override
		public BUTree<?> doTraverse(SArrayType state) {
			return state.dims;
		}

		@Override
		public SArrayType doRebuildParentState(SArrayType state, BUTree<SNodeList> child) {
			return state.withDims(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return COMPONENT_TYPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(COMPONENT_TYPE),
			child(DIMS, list())
	);
}