package org.jlato.tree.expr;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.tree.*;
import org.jlato.tree.type.ClassOrInterfaceType;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.space;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.spacing;
import static org.jlato.printer.FormattingSettings.IndentationContext.TYPE_BODY;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

public class ObjectCreationExpr extends Expr {

	public final static Tree.Kind kind = new Tree.Kind() {
		public ObjectCreationExpr instantiate(SLocation location) {
			return new ObjectCreationExpr(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private ObjectCreationExpr(SLocation location) {
		super(location);
	}

	public ObjectCreationExpr(Expr scope, ClassOrInterfaceType type, NodeList<Type> typeArgs, NodeList<Expr> args, NodeList<Decl> anonymousClassBody) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(scope, type, typeArgs, args, anonymousClassBody)))));
	}

	public Expr scope() {
		return location.nodeChild(SCOPE);
	}

	public ObjectCreationExpr withScope(Expr scope) {
		return location.nodeWithChild(SCOPE, scope);
	}

	public ClassOrInterfaceType type() {
		return location.nodeChild(TYPE);
	}

	public ObjectCreationExpr withType(ClassOrInterfaceType type) {
		return location.nodeWithChild(TYPE, type);
	}

	public NodeList<Type> typeArguments() {
		return location.nodeChild(TYPE_ARGUMENTS);
	}

	public ObjectCreationExpr withTypeArguments(NodeList<Type> typeArguments) {
		return location.nodeWithChild(TYPE_ARGUMENTS, typeArguments);
	}

	public NodeList<Expr> arguments() {
		return location.nodeChild(ARGUMENTS);
	}

	public ObjectCreationExpr withArguments(NodeList<Expr> arguments) {
		return location.nodeWithChild(ARGUMENTS, arguments);
	}

	public NodeList<Decl> anonymousClassBody() {
		return location.nodeChild(ANONYMOUS_CLASS_BODY);
	}

	public ObjectCreationExpr withAnonymousClassBody(NodeList<Decl> anonymousClassBody) {
		return location.nodeWithChild(ANONYMOUS_CLASS_BODY, anonymousClassBody);
	}

	private static final int SCOPE = 0;
	private static final int TYPE = 1;
	private static final int TYPE_ARGUMENTS = 2;
	private static final int ARGUMENTS = 3;
	private static final int ANONYMOUS_CLASS_BODY = 4;

	public final static LexicalShape shape = composite(
			nonNullChild(SCOPE, composite(child(SCOPE), token(LToken.Dot))),
			token(LToken.New),
			children(TYPE_ARGUMENTS,
					token(LToken.Less).withSpacingBefore(space()),
					token(LToken.Comma),
					token(LToken.Greater)
			),
			child(TYPE),
			token(LToken.ParenthesisLeft), children(ARGUMENTS, token(LToken.Comma)), token(LToken.ParenthesisRight),
			nonNullChild(ANONYMOUS_CLASS_BODY,
					nonEmptyChildren(ANONYMOUS_CLASS_BODY,
							children(ANONYMOUS_CLASS_BODY,
									composite(token(LToken.BraceLeft).withSpacing(space(), spacing(ClassBody_BeforeMembers)), indent(TYPE_BODY)),
									none().withSpacing(spacing(ClassBody_BetweenMembers)),
									composite(unIndent(TYPE_BODY), token(LToken.BraceRight).withSpacingBefore(spacing(ClassBody_AfterMembers)))
							),
							composite(
									token(LToken.BraceLeft).withSpacing(space(), spacing(ClassBody_Empty)), indent(TYPE_BODY),
									unIndent(TYPE_BODY), token(LToken.BraceRight)
							)
					)
			)
	);
}
