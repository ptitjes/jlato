package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDMarkerAnnotationExpr;
import org.jlato.tree.*;
import org.jlato.tree.name.*;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SMarkerAnnotationExpr extends SNodeState<SMarkerAnnotationExpr> implements SAnnotationExpr {

	public static BUTree<SMarkerAnnotationExpr> make(BUTree<SQualifiedName> name) {
		return new BUTree<SMarkerAnnotationExpr>(new SMarkerAnnotationExpr(name));
	}

	public final BUTree<SQualifiedName> name;

	public SMarkerAnnotationExpr(BUTree<SQualifiedName> name) {
		this.name = name;
	}

	@Override
	public Kind kind() {
		return Kind.MarkerAnnotationExpr;
	}

	public BUTree<SQualifiedName> name() {
		return name;
	}

	public SMarkerAnnotationExpr withName(BUTree<SQualifiedName> name) {
		return new SMarkerAnnotationExpr(name);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SMarkerAnnotationExpr> location) {
		return new TDMarkerAnnotationExpr(location);
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
		return NAME;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SMarkerAnnotationExpr state = (SMarkerAnnotationExpr) o;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (name != null) result = 37 * result + name.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SMarkerAnnotationExpr, SQualifiedName, QualifiedName> NAME = new STypeSafeTraversal<SMarkerAnnotationExpr, SQualifiedName, QualifiedName>() {

		@Override
		public BUTree<?> doTraverse(SMarkerAnnotationExpr state) {
			return state.name;
		}

		@Override
		public SMarkerAnnotationExpr doRebuildParentState(SMarkerAnnotationExpr state, BUTree<SQualifiedName> child) {
			return state.withName(child);
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
			token(LToken.At), child(NAME)
	);
}
