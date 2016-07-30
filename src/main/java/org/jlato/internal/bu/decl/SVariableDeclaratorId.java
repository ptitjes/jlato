package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDVariableDeclaratorId;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ArrayDim;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a variable declarator identifier.
 */
public class SVariableDeclaratorId extends SNode<SVariableDeclaratorId> implements STree {

	/**
	 * Creates a <code>BUTree</code> with a new variable declarator identifier.
	 *
	 * @param name the name child <code>BUTree</code>.
	 * @param dims the dimensions child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a variable declarator identifier.
	 */
	public static BUTree<SVariableDeclaratorId> make(BUTree<SName> name, BUTree<SNodeList> dims) {
		return new BUTree<SVariableDeclaratorId>(new SVariableDeclaratorId(name, dims));
	}

	/**
	 * The name of this variable declarator identifier state.
	 */
	public final BUTree<SName> name;

	/**
	 * The dimensions of this variable declarator identifier state.
	 */
	public final BUTree<SNodeList> dims;

	/**
	 * Constructs a variable declarator identifier state.
	 *
	 * @param name the name child <code>BUTree</code>.
	 * @param dims the dimensions child <code>BUTree</code>.
	 */
	public SVariableDeclaratorId(BUTree<SName> name, BUTree<SNodeList> dims) {
		this.name = name;
		this.dims = dims;
	}

	/**
	 * Returns the kind of this variable declarator identifier.
	 *
	 * @return the kind of this variable declarator identifier.
	 */
	@Override
	public Kind kind() {
		return Kind.VariableDeclaratorId;
	}

	/**
	 * Replaces the name of this variable declarator identifier state.
	 *
	 * @param name the replacement for the name of this variable declarator identifier state.
	 * @return the resulting mutated variable declarator identifier state.
	 */
	public SVariableDeclaratorId withName(BUTree<SName> name) {
		return new SVariableDeclaratorId(name, dims);
	}

	/**
	 * Replaces the dimensions of this variable declarator identifier state.
	 *
	 * @param dims the replacement for the dimensions of this variable declarator identifier state.
	 * @return the resulting mutated variable declarator identifier state.
	 */
	public SVariableDeclaratorId withDims(BUTree<SNodeList> dims) {
		return new SVariableDeclaratorId(name, dims);
	}

	/**
	 * Builds a variable declarator identifier facade for the specified variable declarator identifier <code>TDLocation</code>.
	 *
	 * @param location the variable declarator identifier <code>TDLocation</code>.
	 * @return a variable declarator identifier facade for the specified variable declarator identifier <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SVariableDeclaratorId> location) {
		return new TDVariableDeclaratorId(location);
	}

	/**
	 * Returns the shape for this variable declarator identifier state.
	 *
	 * @return the shape for this variable declarator identifier state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this variable declarator identifier state.
	 *
	 * @return the first child traversal for this variable declarator identifier state.
	 */
	@Override
	public STraversal firstChild() {
		return NAME;
	}

	/**
	 * Returns the last child traversal for this variable declarator identifier state.
	 *
	 * @return the last child traversal for this variable declarator identifier state.
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
		SVariableDeclaratorId state = (SVariableDeclaratorId) o;
		if (name == null ? state.name != null : !name.equals(state.name))
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
