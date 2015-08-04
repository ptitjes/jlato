package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.expr.TDMethodInvocationExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SMethodInvocationExpr extends SNodeState<SMethodInvocationExpr> implements SExpr {

	public static STree<SMethodInvocationExpr> make(STree<SNodeOptionState> scope, STree<SNodeListState> typeArgs, STree<SName> name, STree<SNodeListState> args) {
		return new STree<SMethodInvocationExpr>(new SMethodInvocationExpr(scope, typeArgs, name, args));
	}

	public final STree<SNodeOptionState> scope;

	public final STree<SNodeListState> typeArgs;

	public final STree<SName> name;

	public final STree<SNodeListState> args;

	public SMethodInvocationExpr(STree<SNodeOptionState> scope, STree<SNodeListState> typeArgs, STree<SName> name, STree<SNodeListState> args) {
		this.scope = scope;
		this.typeArgs = typeArgs;
		this.name = name;
		this.args = args;
	}

	@Override
	public Kind kind() {
		return Kind.MethodInvocationExpr;
	}

	public STree<SNodeOptionState> scope() {
		return scope;
	}

	public SMethodInvocationExpr withScope(STree<SNodeOptionState> scope) {
		return new SMethodInvocationExpr(scope, typeArgs, name, args);
	}

	public STree<SNodeListState> typeArgs() {
		return typeArgs;
	}

	public SMethodInvocationExpr withTypeArgs(STree<SNodeListState> typeArgs) {
		return new SMethodInvocationExpr(scope, typeArgs, name, args);
	}

	public STree<SName> name() {
		return name;
	}

	public SMethodInvocationExpr withName(STree<SName> name) {
		return new SMethodInvocationExpr(scope, typeArgs, name, args);
	}

	public STree<SNodeListState> args() {
		return args;
	}

	public SMethodInvocationExpr withArgs(STree<SNodeListState> args) {
		return new SMethodInvocationExpr(scope, typeArgs, name, args);
	}

	@Override
	protected Tree doInstantiate(SLocation<SMethodInvocationExpr> location) {
		return new TDMethodInvocationExpr(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return SCOPE;
	}

	@Override
	public STraversal lastChild() {
		return ARGS;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SMethodInvocationExpr state = (SMethodInvocationExpr) o;
		if (!scope.equals(state.scope))
			return false;
		if (!typeArgs.equals(state.typeArgs))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!args.equals(state.args))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + scope.hashCode();
		result = 37 * result + typeArgs.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + args.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SMethodInvocationExpr, SNodeOptionState, NodeOption<Expr>> SCOPE = new STypeSafeTraversal<SMethodInvocationExpr, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(SMethodInvocationExpr state) {
			return state.scope;
		}

		@Override
		public SMethodInvocationExpr doRebuildParentState(SMethodInvocationExpr state, STree<SNodeOptionState> child) {
			return state.withScope(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE_ARGS;
		}
	};

	public static STypeSafeTraversal<SMethodInvocationExpr, SNodeListState, NodeList<Type>> TYPE_ARGS = new STypeSafeTraversal<SMethodInvocationExpr, SNodeListState, NodeList<Type>>() {

		@Override
		public STree<?> doTraverse(SMethodInvocationExpr state) {
			return state.typeArgs;
		}

		@Override
		public SMethodInvocationExpr doRebuildParentState(SMethodInvocationExpr state, STree<SNodeListState> child) {
			return state.withTypeArgs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return SCOPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SMethodInvocationExpr, SName, Name> NAME = new STypeSafeTraversal<SMethodInvocationExpr, SName, Name>() {

		@Override
		public STree<?> doTraverse(SMethodInvocationExpr state) {
			return state.name;
		}

		@Override
		public SMethodInvocationExpr doRebuildParentState(SMethodInvocationExpr state, STree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE_ARGS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return ARGS;
		}
	};

	public static STypeSafeTraversal<SMethodInvocationExpr, SNodeListState, NodeList<Expr>> ARGS = new STypeSafeTraversal<SMethodInvocationExpr, SNodeListState, NodeList<Expr>>() {

		@Override
		public STree<?> doTraverse(SMethodInvocationExpr state) {
			return state.args;
		}

		@Override
		public SMethodInvocationExpr doRebuildParentState(SMethodInvocationExpr state, STree<SNodeListState> child) {
			return state.withArgs(child);
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
			child(SCOPE, when(some(), composite(element(), token(LToken.Dot)))),
			child(TYPE_ARGS, org.jlato.internal.bu.type.SType.typeArgumentsShape),
			child(NAME),
			child(ARGS, SExpr.argumentsShape)
	);
}
