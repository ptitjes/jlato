package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDSingleMemberAnnotationExpr;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SSingleMemberAnnotationExpr extends SNode<SSingleMemberAnnotationExpr> implements SAnnotationExpr {

	public static BUTree<SSingleMemberAnnotationExpr> make(BUTree<SQualifiedName> name, BUTree<? extends SExpr> memberValue) {
		return new BUTree<SSingleMemberAnnotationExpr>(new SSingleMemberAnnotationExpr(name, memberValue));
	}

	public final BUTree<SQualifiedName> name;

	public final BUTree<? extends SExpr> memberValue;

	public SSingleMemberAnnotationExpr(BUTree<SQualifiedName> name, BUTree<? extends SExpr> memberValue) {
		this.name = name;
		this.memberValue = memberValue;
	}

	@Override
	public Kind kind() {
		return Kind.SingleMemberAnnotationExpr;
	}

	public BUTree<SQualifiedName> name() {
		return name;
	}

	public SSingleMemberAnnotationExpr withName(BUTree<SQualifiedName> name) {
		return new SSingleMemberAnnotationExpr(name, memberValue);
	}

	public BUTree<? extends SExpr> memberValue() {
		return memberValue;
	}

	public SSingleMemberAnnotationExpr withMemberValue(BUTree<? extends SExpr> memberValue) {
		return new SSingleMemberAnnotationExpr(name, memberValue);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SSingleMemberAnnotationExpr> location) {
		return new TDSingleMemberAnnotationExpr(location);
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
		return MEMBER_VALUE;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SSingleMemberAnnotationExpr state = (SSingleMemberAnnotationExpr) o;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (memberValue == null ? state.memberValue != null : !memberValue.equals(state.memberValue))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (name != null) result = 37 * result + name.hashCode();
		if (memberValue != null) result = 37 * result + memberValue.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SSingleMemberAnnotationExpr, SQualifiedName, QualifiedName> NAME = new STypeSafeTraversal<SSingleMemberAnnotationExpr, SQualifiedName, QualifiedName>() {

		@Override
		public BUTree<?> doTraverse(SSingleMemberAnnotationExpr state) {
			return state.name;
		}

		@Override
		public SSingleMemberAnnotationExpr doRebuildParentState(SSingleMemberAnnotationExpr state, BUTree<SQualifiedName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return MEMBER_VALUE;
		}
	};

	public static STypeSafeTraversal<SSingleMemberAnnotationExpr, SExpr, Expr> MEMBER_VALUE = new STypeSafeTraversal<SSingleMemberAnnotationExpr, SExpr, Expr>() {

		@Override
		public BUTree<?> doTraverse(SSingleMemberAnnotationExpr state) {
			return state.memberValue;
		}

		@Override
		public SSingleMemberAnnotationExpr doRebuildParentState(SSingleMemberAnnotationExpr state, BUTree<SExpr> child) {
			return state.withMemberValue(child);
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
			child(MEMBER_VALUE),
			token(LToken.ParenthesisRight)
	);
}
