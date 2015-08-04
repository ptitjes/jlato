package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.expr.TDSingleMemberAnnotationExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SSingleMemberAnnotationExpr extends SNodeState<SSingleMemberAnnotationExpr> implements SAnnotationExpr {

	public static STree<SSingleMemberAnnotationExpr> make(STree<SQualifiedName> name, STree<? extends SExpr> memberValue) {
		return new STree<SSingleMemberAnnotationExpr>(new SSingleMemberAnnotationExpr(name, memberValue));
	}

	public final STree<SQualifiedName> name;

	public final STree<? extends SExpr> memberValue;

	public SSingleMemberAnnotationExpr(STree<SQualifiedName> name, STree<? extends SExpr> memberValue) {
		this.name = name;
		this.memberValue = memberValue;
	}

	@Override
	public Kind kind() {
		return Kind.SingleMemberAnnotationExpr;
	}

	public STree<SQualifiedName> name() {
		return name;
	}

	public SSingleMemberAnnotationExpr withName(STree<SQualifiedName> name) {
		return new SSingleMemberAnnotationExpr(name, memberValue);
	}

	public STree<? extends SExpr> memberValue() {
		return memberValue;
	}

	public SSingleMemberAnnotationExpr withMemberValue(STree<? extends SExpr> memberValue) {
		return new SSingleMemberAnnotationExpr(name, memberValue);
	}

	@Override
	protected Tree doInstantiate(SLocation<SSingleMemberAnnotationExpr> location) {
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
		public STree<?> doTraverse(SSingleMemberAnnotationExpr state) {
			return state.name;
		}

		@Override
		public SSingleMemberAnnotationExpr doRebuildParentState(SSingleMemberAnnotationExpr state, STree<SQualifiedName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return MEMBER_VALUE;
		}
	};

	public static STypeSafeTraversal<SSingleMemberAnnotationExpr, SExpr, Expr> MEMBER_VALUE = new STypeSafeTraversal<SSingleMemberAnnotationExpr, SExpr, Expr>() {

		@Override
		public STree<?> doTraverse(SSingleMemberAnnotationExpr state) {
			return state.memberValue;
		}

		@Override
		public SSingleMemberAnnotationExpr doRebuildParentState(SSingleMemberAnnotationExpr state, STree<SExpr> child) {
			return state.withMemberValue(child);
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
			child(MEMBER_VALUE),
			token(LToken.ParenthesisRight)
	);
}
