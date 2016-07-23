package org.jlato.internal.bu.type;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDArrayType;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an array type.
 */
public class SArrayType extends SNode<SArrayType> implements SReferenceType {

	/**
	 * Creates a <code>BUTree</code> with a new array type.
	 *
	 * @param componentType the component type child <code>BUTree</code>.
	 * @param dims          the dimensions child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an array type.
	 */
	public static BUTree<SArrayType> make(BUTree<? extends SType> componentType, BUTree<SNodeList> dims) {
		return new BUTree<SArrayType>(new SArrayType(componentType, dims));
	}

	/**
	 * The component type of this array type state.
	 */
	public final BUTree<? extends SType> componentType;

	/**
	 * The dimensions of this array type state.
	 */
	public final BUTree<SNodeList> dims;

	/**
	 * Constructs an array type state.
	 *
	 * @param componentType the component type child <code>BUTree</code>.
	 * @param dims          the dimensions child <code>BUTree</code>.
	 */
	public SArrayType(BUTree<? extends SType> componentType, BUTree<SNodeList> dims) {
		this.componentType = componentType;
		this.dims = dims;
	}

	/**
	 * Returns the kind of this array type.
	 *
	 * @return the kind of this array type.
	 */
	@Override
	public Kind kind() {
		return Kind.ArrayType;
	}

	/**
	 * Replaces the component type of this array type state.
	 *
	 * @param componentType the replacement for the component type of this array type state.
	 * @return the resulting mutated array type state.
	 */
	public SArrayType withComponentType(BUTree<? extends SType> componentType) {
		return new SArrayType(componentType, dims);
	}

	/**
	 * Replaces the dimensions of this array type state.
	 *
	 * @param dims the replacement for the dimensions of this array type state.
	 * @return the resulting mutated array type state.
	 */
	public SArrayType withDims(BUTree<SNodeList> dims) {
		return new SArrayType(componentType, dims);
	}

	/**
	 * Builds an array type facade for the specified array type <code>TDLocation</code>.
	 *
	 * @param location the array type <code>TDLocation</code>.
	 * @return an array type facade for the specified array type <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SArrayType> location) {
		return new TDArrayType(location);
	}

	/**
	 * Returns the shape for this array type state.
	 *
	 * @return the shape for this array type state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this array type state.
	 *
	 * @return the first child traversal for this array type state.
	 */
	@Override
	public STraversal firstChild() {
		return COMPONENT_TYPE;
	}

	/**
	 * Returns the last child traversal for this array type state.
	 *
	 * @return the last child traversal for this array type state.
	 */
	@Override
	public STraversal lastChild() {
		return DIMS;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SArrayType state = (SArrayType) o;
		if (componentType == null ? state.componentType != null : !componentType.equals(state.componentType))
			return false;
		if (!dims.equals(state.dims))
			return false;
		return true;
	}

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
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
