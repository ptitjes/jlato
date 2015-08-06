package org.jlato.internal.bu.type;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.shapes.LSToken;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDPrimitiveType;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.type.*;

import java.util.Collections;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SPrimitiveType extends SNode<SPrimitiveType> implements SType {

	public static BUTree<SPrimitiveType> make(BUTree<SNodeList> annotations, Primitive primitive) {
		return new BUTree<SPrimitiveType>(new SPrimitiveType(annotations, primitive));
	}

	public final BUTree<SNodeList> annotations;

	public final Primitive primitive;

	public SPrimitiveType(BUTree<SNodeList> annotations, Primitive primitive) {
		this.annotations = annotations;
		this.primitive = primitive;
	}

	@Override
	public Kind kind() {
		return Kind.PrimitiveType;
	}

	public SPrimitiveType withAnnotations(BUTree<SNodeList> annotations) {
		return new SPrimitiveType(annotations, primitive);
	}

	public SPrimitiveType withPrimitive(Primitive primitive) {
		return new SPrimitiveType(annotations, primitive);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SPrimitiveType> location) {
		return new TDPrimitiveType(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Collections.<SProperty>singleton(PRIMITIVE);
	}

	@Override
	public STraversal firstChild() {
		return ANNOTATIONS;
	}

	@Override
	public STraversal lastChild() {
		return ANNOTATIONS;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SPrimitiveType state = (SPrimitiveType) o;
		if (!annotations.equals(state.annotations))
			return false;
		if (primitive == null ? state.primitive != null : !primitive.equals(state.primitive))
			return false;
		return true;
	}

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
