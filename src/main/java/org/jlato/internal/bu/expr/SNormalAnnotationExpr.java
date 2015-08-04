package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDNormalAnnotationExpr;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.MemberValuePair;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SNormalAnnotationExpr extends SNodeState<SNormalAnnotationExpr> implements SAnnotationExpr {

	public static STree<SNormalAnnotationExpr> make(STree<SQualifiedName> name, STree<SNodeListState> pairs) {
		return new STree<SNormalAnnotationExpr>(new SNormalAnnotationExpr(name, pairs));
	}

	public final STree<SQualifiedName> name;

	public final STree<SNodeListState> pairs;

	public SNormalAnnotationExpr(STree<SQualifiedName> name, STree<SNodeListState> pairs) {
		this.name = name;
		this.pairs = pairs;
	}

	@Override
	public Kind kind() {
		return Kind.NormalAnnotationExpr;
	}

	public STree<SQualifiedName> name() {
		return name;
	}

	public SNormalAnnotationExpr withName(STree<SQualifiedName> name) {
		return new SNormalAnnotationExpr(name, pairs);
	}

	public STree<SNodeListState> pairs() {
		return pairs;
	}

	public SNormalAnnotationExpr withPairs(STree<SNodeListState> pairs) {
		return new SNormalAnnotationExpr(name, pairs);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SNormalAnnotationExpr> location) {
		return new TDNormalAnnotationExpr(location);
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
		return PAIRS;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SNormalAnnotationExpr state = (SNormalAnnotationExpr) o;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!pairs.equals(state.pairs))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + pairs.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SNormalAnnotationExpr, SQualifiedName, QualifiedName> NAME = new STypeSafeTraversal<SNormalAnnotationExpr, SQualifiedName, QualifiedName>() {

		@Override
		public STree<?> doTraverse(SNormalAnnotationExpr state) {
			return state.name;
		}

		@Override
		public SNormalAnnotationExpr doRebuildParentState(SNormalAnnotationExpr state, STree<SQualifiedName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return PAIRS;
		}
	};

	public static STypeSafeTraversal<SNormalAnnotationExpr, SNodeListState, NodeList<MemberValuePair>> PAIRS = new STypeSafeTraversal<SNormalAnnotationExpr, SNodeListState, NodeList<MemberValuePair>>() {

		@Override
		public STree<?> doTraverse(SNormalAnnotationExpr state) {
			return state.pairs;
		}

		@Override
		public SNormalAnnotationExpr doRebuildParentState(SNormalAnnotationExpr state, STree<SNodeListState> child) {
			return state.withPairs(child);
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
			token(LToken.At), child(NAME),
			token(LToken.ParenthesisLeft),
			child(PAIRS, list(token(LToken.Comma).withSpacingAfter(space()))),
			token(LToken.ParenthesisRight)
	);
}
