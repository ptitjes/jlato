package org.jlato.internal.bu.type;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDPrimitiveType;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.type.Primitive;

import java.util.Collections;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a primitive type.
 */
public class SPrimitiveType extends SNode<SPrimitiveType> implements SType {

	/**
	 * Creates a <code>BUTree</code> with a new primitive type.
	 *
	 * @param annotations the annotations child <code>BUTree</code>.
	 * @param primitive   the primitive child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a primitive type.
	 */
	public static BUTree<SPrimitiveType> make(BUTree<SNodeList> annotations, Primitive primitive) {
		return new BUTree<SPrimitiveType>(new SPrimitiveType(annotations, primitive));
	}

	/**
	 * The annotations of this primitive type state.
	 */
	public final BUTree<SNodeList> annotations;

	/**
	 * The primitive of this primitive type state.
	 */
	public final Primitive primitive;

	/**
	 * Constructs a primitive type state.
	 *
	 * @param annotations the annotations child <code>BUTree</code>.
	 * @param primitive   the primitive child <code>BUTree</code>.
	 */
	public SPrimitiveType(BUTree<SNodeList> annotations, Primitive primitive) {
		this.annotations = annotations;
		this.primitive = primitive;
	}

	/**
	 * Returns the kind of this primitive type.
	 *
	 * @return the kind of this primitive type.
	 */
	@Override
	public Kind kind() {
		return Kind.PrimitiveType;
	}

	/**
	 * Replaces the annotations of this primitive type state.
	 *
	 * @param annotations the replacement for the annotations of this primitive type state.
	 * @return the resulting mutated primitive type state.
	 */
	public SPrimitiveType withAnnotations(BUTree<SNodeList> annotations) {
		return new SPrimitiveType(annotations, primitive);
	}

	/**
	 * Replaces the primitive of this primitive type state.
	 *
	 * @param primitive the replacement for the primitive of this primitive type state.
	 * @return the resulting mutated primitive type state.
	 */
	public SPrimitiveType withPrimitive(Primitive primitive) {
		return new SPrimitiveType(annotations, primitive);
	}

	/**
	 * Builds a primitive type facade for the specified primitive type <code>TDLocation</code>.
	 *
	 * @param location the primitive type <code>TDLocation</code>.
	 * @return a primitive type facade for the specified primitive type <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SPrimitiveType> location) {
		return new TDPrimitiveType(location);
	}

	/**
	 * Returns the shape for this primitive type state.
	 *
	 * @return the shape for this primitive type state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the properties for this primitive type state.
	 *
	 * @return the properties for this primitive type state.
	 */
	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(PRIMITIVE);
	}

	/**
	 * Returns the first child traversal for this primitive type state.
	 *
	 * @return the first child traversal for this primitive type state.
	 */
	@Override
	public STraversal firstChild() {
		return ANNOTATIONS;
	}

	/**
	 * Returns the last child traversal for this primitive type state.
	 *
	 * @return the last child traversal for this primitive type state.
	 */
	@Override
	public STraversal lastChild() {
		return ANNOTATIONS;
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
		SPrimitiveType state = (SPrimitiveType) o;
		if (!annotations.equals(state.annotations))
			return false;
		if (primitive == null ? state.primitive != null : !primitive.equals(state.primitive))
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
		result = 37 * result + annotations.hashCode();
		if (primitive != null) result = 37 * result + primitive.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SPrimitiveType, SNodeList, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<SPrimitiveType, SNodeList, NodeList<AnnotationExpr>>() {

		@Override
		public BUTree<?> doTraverse(SPrimitiveType state) {
			return state.annotations;
		}

		@Override
		public SPrimitiveType doRebuildParentState(SPrimitiveType state, BUTree<SNodeList> child) {
			return state.withAnnotations(child);
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

	public static STypeSafeProperty<SPrimitiveType, Primitive> PRIMITIVE = new STypeSafeProperty<SPrimitiveType, Primitive>() {

		@Override
		public Primitive doRetrieve(SPrimitiveType state) {
			return state.primitive;
		}

		@Override
		public SPrimitiveType doRebuildParentState(SPrimitiveType state, Primitive value) {
			return state.withPrimitive(value);
		}
	};

	public static final LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			token(new LSToken.Provider() {
				public LToken tokenFor(BUTree tree) {
					final Primitive primitive = ((SPrimitiveType) tree.state).primitive;
					switch (primitive) {
						case Boolean:
							return LToken.Boolean;
						case Char:
							return LToken.Char;
						case Byte:
							return LToken.Byte;
						case Short:
							return LToken.Short;
						case Int:
							return LToken.Int;
						case Long:
							return LToken.Long;
						case Float:
							return LToken.Float;
						case Double:
							return LToken.Double;
						default:
							// Can't happen by definition of enum
							throw new IllegalStateException();
					}
				}
			})
	);
}
