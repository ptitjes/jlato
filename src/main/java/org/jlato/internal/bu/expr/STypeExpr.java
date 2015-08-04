package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.type.SType;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.expr.TDTypeExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class STypeExpr extends SNodeState<STypeExpr> implements SExpr {

	public static STree<STypeExpr> make(STree<? extends SType> type) {
		return new STree<STypeExpr>(new STypeExpr(type));
	}

	public final STree<? extends SType> type;

	public STypeExpr(STree<? extends SType> type) {
		this.type = type;
	}

	@Override
	public Kind kind() {
		return Kind.TypeExpr;
	}

	public STree<? extends SType> type() {
		return type;
	}

	public STypeExpr withType(STree<? extends SType> type) {
		return new STypeExpr(type);
	}

	@Override
	protected Tree doInstantiate(SLocation<STypeExpr> location) {
		return new TDTypeExpr(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return TYPE;
	}

	@Override
	public STraversal lastChild() {
		return TYPE;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		STypeExpr state = (STypeExpr) o;
		if (type == null ? state.type != null : !type.equals(state.type))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (type != null) result = 37 * result + type.hashCode();
		return result;
	}

	public static STypeSafeTraversal<STypeExpr, SType, Type> TYPE = new STypeSafeTraversal<STypeExpr, SType, Type>() {

		@Override
		public STree<?> doTraverse(STypeExpr state) {
			return state.type;
		}

		@Override
		public STypeExpr doRebuildParentState(STypeExpr state, STree<SType> child) {
			return state.withType(child);
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

	public static final LexicalShape shape = child(TYPE);
}
