package org.jlato.internal.bu.decl;

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
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDEnumConstantDecl;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SEnumConstantDecl extends SNodeState<SEnumConstantDecl> implements SMemberDecl {

	public static STree<SEnumConstantDecl> make(STree<SNodeListState> modifiers, STree<SName> name, STree<SNodeOptionState> args, STree<SNodeOptionState> classBody) {
		return new STree<SEnumConstantDecl>(new SEnumConstantDecl(modifiers, name, args, classBody));
	}

	public final STree<SNodeListState> modifiers;

	public final STree<SName> name;

	public final STree<SNodeOptionState> args;

	public final STree<SNodeOptionState> classBody;

	public SEnumConstantDecl(STree<SNodeListState> modifiers, STree<SName> name, STree<SNodeOptionState> args, STree<SNodeOptionState> classBody) {
		this.modifiers = modifiers;
		this.name = name;
		this.args = args;
		this.classBody = classBody;
	}

	@Override
	public Kind kind() {
		return Kind.EnumConstantDecl;
	}

	public STree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SEnumConstantDecl withModifiers(STree<SNodeListState> modifiers) {
		return new SEnumConstantDecl(modifiers, name, args, classBody);
	}

	public STree<SName> name() {
		return name;
	}

	public SEnumConstantDecl withName(STree<SName> name) {
		return new SEnumConstantDecl(modifiers, name, args, classBody);
	}

	public STree<SNodeOptionState> args() {
		return args;
	}

	public SEnumConstantDecl withArgs(STree<SNodeOptionState> args) {
		return new SEnumConstantDecl(modifiers, name, args, classBody);
	}

	public STree<SNodeOptionState> classBody() {
		return classBody;
	}

	public SEnumConstantDecl withClassBody(STree<SNodeOptionState> classBody) {
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
		public STree<?> doTraverse(SEnumConstantDecl state) {
			return state.modifiers;
		}

		@Override
		public SEnumConstantDecl doRebuildParentState(SEnumConstantDecl state, STree<SNodeListState> child) {
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
		public STree<?> doTraverse(SEnumConstantDecl state) {
			return state.name;
		}

		@Override
		public SEnumConstantDecl doRebuildParentState(SEnumConstantDecl state, STree<SName> child) {
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
		public STree<?> doTraverse(SEnumConstantDecl state) {
			return state.args;
		}

		@Override
		public SEnumConstantDecl doRebuildParentState(SEnumConstantDecl state, STree<SNodeOptionState> child) {
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
		public STree<?> doTraverse(SEnumConstantDecl state) {
			return state.classBody;
		}

		@Override
		public SEnumConstantDecl doRebuildParentState(SEnumConstantDecl state, STree<SNodeOptionState> child) {
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
