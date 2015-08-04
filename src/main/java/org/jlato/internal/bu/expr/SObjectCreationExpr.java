package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.type.SQualifiedType;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDObjectCreationExpr;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.type.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SObjectCreationExpr extends SNode<SObjectCreationExpr> implements SExpr {

	public static BUTree<SObjectCreationExpr> make(BUTree<SNodeOption> scope, BUTree<SNodeList> typeArgs, BUTree<SQualifiedType> type, BUTree<SNodeList> args, BUTree<SNodeOption> body) {
		return new BUTree<SObjectCreationExpr>(new SObjectCreationExpr(scope, typeArgs, type, args, body));
	}

	public final BUTree<SNodeOption> scope;

	public final BUTree<SNodeList> typeArgs;

	public final BUTree<SQualifiedType> type;

	public final BUTree<SNodeList> args;

	public final BUTree<SNodeOption> body;

	public SObjectCreationExpr(BUTree<SNodeOption> scope, BUTree<SNodeList> typeArgs, BUTree<SQualifiedType> type, BUTree<SNodeList> args, BUTree<SNodeOption> body) {
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

	public BUTree<SNodeOption> scope() {
		return scope;
	}

	public SObjectCreationExpr withScope(BUTree<SNodeOption> scope) {
		return new SObjectCreationExpr(scope, typeArgs, type, args, body);
	}

	public BUTree<SNodeList> typeArgs() {
		return typeArgs;
	}

	public SObjectCreationExpr withTypeArgs(BUTree<SNodeList> typeArgs) {
		return new SObjectCreationExpr(scope, typeArgs, type, args, body);
	}

	public BUTree<SQualifiedType> type() {
		return type;
	}

	public SObjectCreationExpr withType(BUTree<SQualifiedType> type) {
		return new SObjectCreationExpr(scope, typeArgs, type, args, body);
	}

	public BUTree<SNodeList> args() {
		return args;
	}

	public SObjectCreationExpr withArgs(BUTree<SNodeList> args) {
		return new SObjectCreationExpr(scope, typeArgs, type, args, body);
	}

	public BUTree<SNodeOption> body() {
		return body;
	}

	public SObjectCreationExpr withBody(BUTree<SNodeOption> body) {
		return new SObjectCreationExpr(scope, typeArgs, type, args, body);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SObjectCreationExpr> location) {
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

	public static STypeSafeTraversal<SObjectCreationExpr, SNodeOption, NodeOption<Expr>> SCOPE = new STypeSafeTraversal<SObjectCreationExpr, SNodeOption, NodeOption<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SObjectCreationExpr state) {
			return state.scope;
		}

		@Override
		public SObjectCreationExpr doRebuildParentState(SObjectCreationExpr state, BUTree<SNodeOption> child) {
			return state.withScope(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return TYPE_ARGS;
		}
	};

	public static STypeSafeTraversal<SObjectCreationExpr, SNodeList, NodeList<Type>> TYPE_ARGS = new STypeSafeTraversal<SObjectCreationExpr, SNodeList, NodeList<Type>>() {

		@Override
		public BUTree<?> doTraverse(SObjectCreationExpr state) {
			return state.typeArgs;
		}

		@Override
		public SObjectCreationExpr doRebuildParentState(SObjectCreationExpr state, BUTree<SNodeList> child) {
			return state.withTypeArgs(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return SCOPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return TYPE;
		}
	};

	public static STypeSafeTraversal<SObjectCreationExpr, SQualifiedType, QualifiedType> TYPE = new STypeSafeTraversal<SObjectCreationExpr, SQualifiedType, QualifiedType>() {

		@Override
		public BUTree<?> doTraverse(SObjectCreationExpr state) {
			return state.type;
		}

		@Override
		public SObjectCreationExpr doRebuildParentState(SObjectCreationExpr state, BUTree<SQualifiedType> child) {
			return state.withType(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE_ARGS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return ARGS;
		}
	};

	public static STypeSafeTraversal<SObjectCreationExpr, SNodeList, NodeList<Expr>> ARGS = new STypeSafeTraversal<SObjectCreationExpr, SNodeList, NodeList<Expr>>() {

		@Override
		public BUTree<?> doTraverse(SObjectCreationExpr state) {
			return state.args;
		}

		@Override
		public SObjectCreationExpr doRebuildParentState(SObjectCreationExpr state, BUTree<SNodeList> child) {
			return state.withArgs(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return TYPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return BODY;
		}
	};

	public static STypeSafeTraversal<SObjectCreationExpr, SNodeOption, NodeOption<NodeList<MemberDecl>>> BODY = new STypeSafeTraversal<SObjectCreationExpr, SNodeOption, NodeOption<NodeList<MemberDecl>>>() {

		@Override
		public BUTree<?> doTraverse(SObjectCreationExpr state) {
			return state.body;
		}

		@Override
		public SObjectCreationExpr doRebuildParentState(SObjectCreationExpr state, BUTree<SNodeOption> child) {
			return state.withBody(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return ARGS;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
