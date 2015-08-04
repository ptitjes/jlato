package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDMarkerAnnotationExpr;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SMarkerAnnotationExpr extends SNodeState<SMarkerAnnotationExpr> implements SAnnotationExpr {

	public static STree<SMarkerAnnotationExpr> make(STree<SQualifiedName> name) {
		return new STree<SMarkerAnnotationExpr>(new SMarkerAnnotationExpr(name));
	}

	public final STree<SQualifiedName> name;

	public SMarkerAnnotationExpr(STree<SQualifiedName> name) {
		this.name = name;
	}

	@Override
	public Kind kind() {
		return Kind.MarkerAnnotationExpr;
	}

	public STree<SQualifiedName> name() {
		return name;
	}

	public SMarkerAnnotationExpr withName(STree<SQualifiedName> name) {
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
		public STree<?> doTraverse(SMarkerAnnotationExpr state) {
			return state.name;
		}

		@Override
		public SMarkerAnnotationExpr doRebuildParentState(SMarkerAnnotationExpr state, STree<SQualifiedName> child) {
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
