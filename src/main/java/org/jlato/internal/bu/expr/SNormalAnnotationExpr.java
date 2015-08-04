package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDNormalAnnotationExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SNormalAnnotationExpr extends SNode<SNormalAnnotationExpr> implements SAnnotationExpr {

	public static BUTree<SNormalAnnotationExpr> make(BUTree<SQualifiedName> name, BUTree<SNodeList> pairs) {
		return new BUTree<SNormalAnnotationExpr>(new SNormalAnnotationExpr(name, pairs));
	}

	public final BUTree<SQualifiedName> name;

	public final BUTree<SNodeList> pairs;

	public SNormalAnnotationExpr(BUTree<SQualifiedName> name, BUTree<SNodeList> pairs) {
		this.name = name;
		this.pairs = pairs;
	}

	@Override
	public Kind kind() {
		return Kind.NormalAnnotationExpr;
	}

	public BUTree<SQualifiedName> name() {
		return name;
	}

	public SNormalAnnotationExpr withName(BUTree<SQualifiedName> name) {
		return new SNormalAnnotationExpr(name, pairs);
	}

	public BUTree<SNodeList> pairs() {
		return pairs;
	}

	public SNormalAnnotationExpr withPairs(BUTree<SNodeList> pairs) {
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
		public BUTree<?> doTraverse(SNormalAnnotationExpr state) {
			return state.name;
		}

		@Override
		public SNormalAnnotationExpr doRebuildParentState(SNormalAnnotationExpr state, BUTree<SQualifiedName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return PAIRS;
		}
	};

	public static STypeSafeTraversal<SNormalAnnotationExpr, SNodeList, NodeList<MemberValuePair>> PAIRS = new STypeSafeTraversal<SNormalAnnotationExpr, SNodeList, NodeList<MemberValuePair>>() {

		@Override
		public BUTree<?> doTraverse(SNormalAnnotationExpr state) {
			return state.pairs;
		}

		@Override
		public SNormalAnnotationExpr doRebuildParentState(SNormalAnnotationExpr state, BUTree<SNodeList> child) {
			return state.withPairs(child);
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
			token(LToken.At), child(NAME),
			token(LToken.ParenthesisLeft),
			child(PAIRS, list(token(LToken.Comma).withSpacingAfter(space()))),
			token(LToken.ParenthesisRight)
	);
}
