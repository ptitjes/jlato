package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeOptionState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.type.SQualifiedType;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.expr.TDObjectCreationExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SObjectCreationExpr extends SNodeState<SObjectCreationExpr> implements SExpr {

	public static STree<SObjectCreationExpr> make(STree<SNodeOptionState> scope, STree<SNodeListState> typeArgs, STree<SQualifiedType> type, STree<SNodeListState> args, STree<SNodeOptionState> body) {
		return new STree<SObjectCreationExpr>(new SObjectCreationExpr(scope, typeArgs, type, args, body));
	}

	public final STree<SNodeOptionState> scope;

	public final STree<SNodeListState> typeArgs;

	public final STree<SQualifiedType> type;

	public final STree<SNodeListState> args;

	public final STree<SNodeOptionState> body;

	public SObjectCreationExpr(STree<SNodeOptionState> scope, STree<SNodeListState> typeArgs, STree<SQualifiedType> type, STree<SNodeListState> args, STree<SNodeOptionState> body) {
		this.scope = scope;
		this.typeArgs = typeArgs;
		this.type = type;
		this.args = args;
		this.body = body;
	}

	@Override
	public Kind kind() {
		return Kind.ObjectCreationExpr;
	}

	public STree<SNodeOptionState> scope() {
		return scope;
	}

	public SObjectCreationExpr withScope(STree<SNodeOptionState> scope) {
		return new SObjectCreationExpr(scope, typeArgs, type, args, body);
	}

	public STree<SNodeListState> typeArgs() {
		return typeArgs;
	}

	public SObjectCreationExpr withTypeArgs(STree<SNodeListState> typeArgs) {
		return new SObjectCreationExpr(scope, typeArgs, type, args, body);
	}

	public STree<SQualifiedType> type() {
		return type;
	}

	public SObjectCreationExpr withType(STree<SQualifiedType> type) {
		return new SObjectCreationExpr(scope, typeArgs, type, args, body);
	}

	public STree<SNodeListState> args() {
		return args;
	}

	public SObjectCreationExpr withArgs(STree<SNodeListState> args) {
		return new SObjectCreationExpr(scope, typeArgs, type, args, body);
	}

	public STree<SNodeOptionState> body() {
		return body;
	}

	public SObjectCreationExpr withBody(STree<SNodeOptionState> body) {
		return new SObjectCreationExpr(scope, typeArgs, type, args, body);
	}

	@Override
	protected Tree doInstantiate(SLocation<SObjectCreationExpr> location) {
		return new TDObjectCreationExpr(location);
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
		return BODY;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SObjectCreationExpr state = (SObjectCreationExpr) o;
		if (!scope.equals(state.scope))
			return false;
		if (!typeArgs.equals(state.typeArgs))
			return false;
		if (type == null ? state.type != null : !type.equals(state.type))
			return false;
		if (!args.equals(state.args))
			return false;
		if (!body.equals(state.body))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + scope.hashCode();
		result = 37 * result + typeArgs.hashCode();
		if (type != null) result = 37 * result + type.hashCode();
		result = 37 * result + args.hashCode();
		result = 37 * result + body.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SObjectCreationExpr, SNodeOptionState, NodeOption<Expr>> SCOPE = new STypeSafeTraversal<SObjectCreationExpr, SNodeOptionState, NodeOption<Expr>>() {

		@Override
		public STree<?> doTraverse(SObjectCreationExpr state) {
			return state.scope;
		}

		@Override
		public SObjectCreationExpr doRebuildParentState(SObjectCreationExpr state, STree<SNodeOptionState> child) {
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

	public static STypeSafeTraversal<SObjectCreationExpr, SNodeListState, NodeList<Type>> TYPE_ARGS = new STypeSafeTraversal<SObjectCreationExpr, SNodeListState, NodeList<Type>>() {

		@Override
		public STree<?> doTraverse(SObjectCreationExpr state) {
			return state.typeArgs;
		}

		@Override
		public SObjectCreationExpr doRebuildParentState(SObjectCreationExpr state, STree<SNodeListState> child) {
			return state.withTypeArgs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return SCOPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPE;
		}
	};

	public static STypeSafeTraversal<SObjectCreationExpr, SQualifiedType, QualifiedType> TYPE = new STypeSafeTraversal<SObjectCreationExpr, SQualifiedType, QualifiedType>() {

		@Override
		public STree<?> doTraverse(SObjectCreationExpr state) {
			return state.type;
		}

		@Override
		public SObjectCreationExpr doRebuildParentState(SObjectCreationExpr state, STree<SQualifiedType> child) {
			return state.withType(child);
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

	public static STypeSafeTraversal<SObjectCreationExpr, SNodeListState, NodeList<Expr>> ARGS = new STypeSafeTraversal<SObjectCreationExpr, SNodeListState, NodeList<Expr>>() {

		@Override
		public STree<?> doTraverse(SObjectCreationExpr state) {
			return state.args;
		}

		@Override
		public SObjectCreationExpr doRebuildParentState(SObjectCreationExpr state, STree<SNodeListState> child) {
			return state.withArgs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SObjectCreationExpr, SNodeOptionState, NodeOption<NodeList<MemberDecl>>> BODY = new STypeSafeTraversal<SObjectCreationExpr, SNodeOptionState, NodeOption<NodeList<MemberDecl>>>() {

		@Override
		public STree<?> doTraverse(SObjectCreationExpr state) {
			return state.body;
		}

		@Override
		public SObjectCreationExpr doRebuildParentState(SObjectCreationExpr state, STree<SNodeOptionState> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return ARGS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(SCOPE, when(some(), composite(element(), token(LToken.Dot)))),
			token(LToken.New).withSpacingAfter(space()),
			child(TYPE_ARGS, org.jlato.internal.bu.type.SType.typeArgumentsShape),
			child(TYPE),
			child(ARGS, SExpr.argumentsShape),
			child(BODY, when(some(), element(org.jlato.internal.bu.decl.SMemberDecl.bodyShape)))
	);
}
