package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.type.SQualifiedType;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
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

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an object creation expression.
 */
public class SObjectCreationExpr extends SNode<SObjectCreationExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new object creation expression.
	 *
	 * @param scope    the scope child <code>BUTree</code>.
	 * @param typeArgs the type args child <code>BUTree</code>.
	 * @param type     the type child <code>BUTree</code>.
	 * @param args     the args child <code>BUTree</code>.
	 * @param body     the body child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an object creation expression.
	 */
	public static BUTree<SObjectCreationExpr> make(BUTree<SNodeOption> scope, BUTree<SNodeList> typeArgs, BUTree<SQualifiedType> type, BUTree<SNodeList> args, BUTree<SNodeOption> body) {
		return new BUTree<SObjectCreationExpr>(new SObjectCreationExpr(scope, typeArgs, type, args, body));
	}

	/**
	 * The scope of this object creation expression state.
	 */
	public final BUTree<SNodeOption> scope;

	/**
	 * The type args of this object creation expression state.
	 */
	public final BUTree<SNodeList> typeArgs;

	/**
	 * The type of this object creation expression state.
	 */
	public final BUTree<SQualifiedType> type;

	/**
	 * The args of this object creation expression state.
	 */
	public final BUTree<SNodeList> args;

	/**
	 * The body of this object creation expression state.
	 */
	public final BUTree<SNodeOption> body;

	/**
	 * Constructs an object creation expression state.
	 *
	 * @param scope    the scope child <code>BUTree</code>.
	 * @param typeArgs the type args child <code>BUTree</code>.
	 * @param type     the type child <code>BUTree</code>.
	 * @param args     the args child <code>BUTree</code>.
	 * @param body     the body child <code>BUTree</code>.
	 */
	public SObjectCreationExpr(BUTree<SNodeOption> scope, BUTree<SNodeList> typeArgs, BUTree<SQualifiedType> type, BUTree<SNodeList> args, BUTree<SNodeOption> body) {
		this.scope = scope;
		this.typeArgs = typeArgs;
		this.type = type;
		this.args = args;
		this.body = body;
	}

	/**
	 * Returns the kind of this object creation expression.
	 *
	 * @return the kind of this object creation expression.
	 */
	@Override
	public Kind kind() {
		return Kind.ObjectCreationExpr;
	}

	/**
	 * Replaces the scope of this object creation expression state.
	 *
	 * @param scope the replacement for the scope of this object creation expression state.
	 * @return the resulting mutated object creation expression state.
	 */
	public SObjectCreationExpr withScope(BUTree<SNodeOption> scope) {
		return new SObjectCreationExpr(scope, typeArgs, type, args, body);
	}

	/**
	 * Replaces the type args of this object creation expression state.
	 *
	 * @param typeArgs the replacement for the type args of this object creation expression state.
	 * @return the resulting mutated object creation expression state.
	 */
	public SObjectCreationExpr withTypeArgs(BUTree<SNodeList> typeArgs) {
		return new SObjectCreationExpr(scope, typeArgs, type, args, body);
	}

	/**
	 * Replaces the type of this object creation expression state.
	 *
	 * @param type the replacement for the type of this object creation expression state.
	 * @return the resulting mutated object creation expression state.
	 */
	public SObjectCreationExpr withType(BUTree<SQualifiedType> type) {
		return new SObjectCreationExpr(scope, typeArgs, type, args, body);
	}

	/**
	 * Replaces the args of this object creation expression state.
	 *
	 * @param args the replacement for the args of this object creation expression state.
	 * @return the resulting mutated object creation expression state.
	 */
	public SObjectCreationExpr withArgs(BUTree<SNodeList> args) {
		return new SObjectCreationExpr(scope, typeArgs, type, args, body);
	}

	/**
	 * Replaces the body of this object creation expression state.
	 *
	 * @param body the replacement for the body of this object creation expression state.
	 * @return the resulting mutated object creation expression state.
	 */
	public SObjectCreationExpr withBody(BUTree<SNodeOption> body) {
		return new SObjectCreationExpr(scope, typeArgs, type, args, body);
	}

	/**
	 * Builds an object creation expression facade for the specified object creation expression <code>TDLocation</code>.
	 *
	 * @param location the object creation expression <code>TDLocation</code>.
	 * @return an object creation expression facade for the specified object creation expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SObjectCreationExpr> location) {
		return new TDObjectCreationExpr(location);
	}

	/**
	 * Returns the shape for this object creation expression state.
	 *
	 * @return the shape for this object creation expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this object creation expression state.
	 *
	 * @return the first child traversal for this object creation expression state.
	 */
	@Override
	public STraversal firstChild() {
		return SCOPE;
	}

	/**
	 * Returns the last child traversal for this object creation expression state.
	 *
	 * @return the last child traversal for this object creation expression state.
	 */
	@Override
	public STraversal lastChild() {
		return BODY;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
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

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
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
