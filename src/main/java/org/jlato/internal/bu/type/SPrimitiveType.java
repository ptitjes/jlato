package org.jlato.internal.bu.type;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
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

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SPrimitiveType extends SNodeState<SPrimitiveType> implements SType {

	public static STree<SPrimitiveType> make(STree<SNodeListState> annotations, Primitive primitive) {
		return new STree<SPrimitiveType>(new SPrimitiveType(annotations, primitive));
	}

	public final STree<SNodeListState> annotations;

	public final Primitive primitive;

	public SPrimitiveType(STree<SNodeListState> annotations, Primitive primitive) {
		this.annotations = annotations;
		this.primitive = primitive;
	}

	@Override
	public Kind kind() {
		return Kind.PrimitiveType;
	}

	public STree<SNodeListState> annotations() {
		return annotations;
	}

	public SPrimitiveType withAnnotations(STree<SNodeListState> annotations) {
		return new SPrimitiveType(annotations, primitive);
	}

	public Primitive primitive() {
		return primitive;
	}

	public SPrimitiveType withPrimitive(Primitive primitive) {
		return new SPrimitiveType(annotations, primitive);
	}

	@Override
	protected Tree doInstantiate(SLocation<SPrimitiveType> location) {
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

	public static STypeSafeTraversal<SPrimitiveType, SNodeListState, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<SPrimitiveType, SNodeListState, NodeList<AnnotationExpr>>() {

		@Override
		public STree<?> doTraverse(SPrimitiveType state) {
			return state.annotations;
		}

		@Override
		public SPrimitiveType doRebuildParentState(SPrimitiveType state, STree<SNodeListState> child) {
			return state.withAnnotations(child);
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
				public LToken tokenFor(STree tree) {
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
