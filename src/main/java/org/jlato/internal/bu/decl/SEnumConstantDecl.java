package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDEnumConstantDecl;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;

import static org.jlato.internal.shapes.LSCondition.some;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.SpacingConstraint.spacing;

public class SEnumConstantDecl extends SNodeState<SEnumConstantDecl> implements SMemberDecl {

	public static BUTree<SEnumConstantDecl> make(BUTree<SNodeListState> modifiers, BUTree<SName> name, BUTree<SNodeOptionState> args, BUTree<SNodeOptionState> classBody) {
		return new BUTree<SEnumConstantDecl>(new SEnumConstantDecl(modifiers, name, args, classBody));
	}

	public final BUTree<SNodeListState> modifiers;

	public final BUTree<SName> name;

	public final BUTree<SNodeOptionState> args;

	public final BUTree<SNodeOptionState> classBody;

	public SEnumConstantDecl(BUTree<SNodeListState> modifiers, BUTree<SName> name, BUTree<SNodeOptionState> args, BUTree<SNodeOptionState> classBody) {
		this.modifiers = modifiers;
		this.name = name;
		this.args = args;
		this.classBody = classBody;
	}

	@Override
	public Kind kind() {
		return Kind.EnumConstantDecl;
	}

	public BUTree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SEnumConstantDecl withModifiers(BUTree<SNodeListState> modifiers) {
		return new SEnumConstantDecl(modifiers, name, args, classBody);
	}

	public BUTree<SName> name() {
		return name;
	}

	public SEnumConstantDecl withName(BUTree<SName> name) {
		return new SEnumConstantDecl(modifiers, name, args, classBody);
	}

	public BUTree<SNodeOptionState> args() {
		return args;
	}

	public SEnumConstantDecl withArgs(BUTree<SNodeOptionState> args) {
		return new SEnumConstantDecl(modifiers, name, args, classBody);
	}

	public BUTree<SNodeOptionState> classBody() {
		return classBody;
	}

	public SEnumConstantDecl withClassBody(BUTree<SNodeOptionState> classBody) {
		return new SEnumConstantDecl(modifiers, name, args, classBody);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SEnumConstantDecl> location) {
		return new TDEnumConstantDecl(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	@Override
	public STraversal lastChild() {
		return CLASS_BODY;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SEnumConstantDecl state = (SEnumConstantDecl) o;
		if (!modifiers.equals(state.modifiers))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!args.equals(state.args))
			return false;
		if (!classBody.equals(state.classBody))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + modifiers.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + args.hashCode();
		result = 37 * result + classBody.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SEnumConstantDecl, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SEnumConstantDecl, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SEnumConstantDecl state) {
			return state.modifiers;
		}

		@Override
		public SEnumConstantDecl doRebuildParentState(SEnumConstantDecl state, BUTree<SNodeListState> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SEnumConstantDecl, SName, Name> NAME = new STypeSafeTraversal<SEnumConstantDecl, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SEnumConstantDecl state) {
			return state.name;
		}

		@Override
		public SEnumConstantDecl doRebuildParentState(SEnumConstantDecl state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return ARGS;
		}
	};

	public static STypeSafeTraversal<SEnumConstantDecl, SNodeOptionState, NodeOption<NodeList<Expr>>> ARGS = new STypeSafeTraversal<SEnumConstantDecl, SNodeOptionState, NodeOption<NodeList<Expr>>>() {

		@Override
		public BUTree<?> doTraverse(SEnumConstantDecl state) {
			return state.args;
		}

		@Override
		public SEnumConstantDecl doRebuildParentState(SEnumConstantDecl state, BUTree<SNodeOptionState> child) {
			return state.withArgs(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return CLASS_BODY;
		}
	};

	public static STypeSafeTraversal<SEnumConstantDecl, SNodeOptionState, NodeOption<NodeList<MemberDecl>>> CLASS_BODY = new STypeSafeTraversal<SEnumConstantDecl, SNodeOptionState, NodeOption<NodeList<MemberDecl>>>() {

		@Override
		public BUTree<?> doTraverse(SEnumConstantDecl state) {
			return state.classBody;
		}

		@Override
		public SEnumConstantDecl doRebuildParentState(SEnumConstantDecl state, BUTree<SNodeOptionState> child) {
			return state.withClassBody(child);
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
			child(MODIFIERS, org.jlato.internal.bu.decl.SExtendedModifier.multiLineShape),
			child(NAME),
			child(ARGS, when(some(), element(org.jlato.internal.bu.expr.SExpr.argumentsShape))),
			child(CLASS_BODY, when(some(),
					element(org.jlato.internal.bu.decl.SMemberDecl.bodyShape).withSpacingAfter(spacing(EnumConstant_AfterBody))
			))
	);

	public static final LexicalShape listShape = list(
			none().withSpacingAfter(spacing(EnumBody_BeforeConstants)),
			token(LToken.Comma).withSpacingAfter(spacing(EnumBody_BetweenConstants)),
			null
	);
}
