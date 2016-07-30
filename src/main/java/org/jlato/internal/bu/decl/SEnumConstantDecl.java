package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDEnumConstantDecl;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.expr.Expr;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an enum constant declaration.
 */
public class SEnumConstantDecl extends SNode<SEnumConstantDecl> implements SMemberDecl {

	/**
	 * Creates a <code>BUTree</code> with a new enum constant declaration.
	 *
	 * @param modifiers the modifiers child <code>BUTree</code>.
	 * @param name      the name child <code>BUTree</code>.
	 * @param args      the args child <code>BUTree</code>.
	 * @param classBody the class body child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an enum constant declaration.
	 */
	public static BUTree<SEnumConstantDecl> make(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeOption> args, BUTree<SNodeOption> classBody) {
		return new BUTree<SEnumConstantDecl>(new SEnumConstantDecl(modifiers, name, args, classBody));
	}

	/**
	 * The modifiers of this enum constant declaration state.
	 */
	public final BUTree<SNodeList> modifiers;

	/**
	 * The name of this enum constant declaration state.
	 */
	public final BUTree<SName> name;

	/**
	 * The args of this enum constant declaration state.
	 */
	public final BUTree<SNodeOption> args;

	/**
	 * The class body of this enum constant declaration state.
	 */
	public final BUTree<SNodeOption> classBody;

	/**
	 * Constructs an enum constant declaration state.
	 *
	 * @param modifiers the modifiers child <code>BUTree</code>.
	 * @param name      the name child <code>BUTree</code>.
	 * @param args      the args child <code>BUTree</code>.
	 * @param classBody the class body child <code>BUTree</code>.
	 */
	public SEnumConstantDecl(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeOption> args, BUTree<SNodeOption> classBody) {
		this.modifiers = modifiers;
		this.name = name;
		this.args = args;
		this.classBody = classBody;
	}

	/**
	 * Returns the kind of this enum constant declaration.
	 *
	 * @return the kind of this enum constant declaration.
	 */
	@Override
	public Kind kind() {
		return Kind.EnumConstantDecl;
	}

	/**
	 * Replaces the modifiers of this enum constant declaration state.
	 *
	 * @param modifiers the replacement for the modifiers of this enum constant declaration state.
	 * @return the resulting mutated enum constant declaration state.
	 */
	public SEnumConstantDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SEnumConstantDecl(modifiers, name, args, classBody);
	}

	/**
	 * Replaces the name of this enum constant declaration state.
	 *
	 * @param name the replacement for the name of this enum constant declaration state.
	 * @return the resulting mutated enum constant declaration state.
	 */
	public SEnumConstantDecl withName(BUTree<SName> name) {
		return new SEnumConstantDecl(modifiers, name, args, classBody);
	}

	/**
	 * Replaces the args of this enum constant declaration state.
	 *
	 * @param args the replacement for the args of this enum constant declaration state.
	 * @return the resulting mutated enum constant declaration state.
	 */
	public SEnumConstantDecl withArgs(BUTree<SNodeOption> args) {
		return new SEnumConstantDecl(modifiers, name, args, classBody);
	}

	/**
	 * Replaces the class body of this enum constant declaration state.
	 *
	 * @param classBody the replacement for the class body of this enum constant declaration state.
	 * @return the resulting mutated enum constant declaration state.
	 */
	public SEnumConstantDecl withClassBody(BUTree<SNodeOption> classBody) {
		return new SEnumConstantDecl(modifiers, name, args, classBody);
	}

	/**
	 * Builds an enum constant declaration facade for the specified enum constant declaration <code>TDLocation</code>.
	 *
	 * @param location the enum constant declaration <code>TDLocation</code>.
	 * @return an enum constant declaration facade for the specified enum constant declaration <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SEnumConstantDecl> location) {
		return new TDEnumConstantDecl(location);
	}

	/**
	 * Returns the shape for this enum constant declaration state.
	 *
	 * @return the shape for this enum constant declaration state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this enum constant declaration state.
	 *
	 * @return the first child traversal for this enum constant declaration state.
	 */
	@Override
	public STraversal firstChild() {
		return MODIFIERS;
	}

	/**
	 * Returns the last child traversal for this enum constant declaration state.
	 *
	 * @return the last child traversal for this enum constant declaration state.
	 */
	@Override
	public STraversal lastChild() {
		return CLASS_BODY;
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

	/**
	 * Returns a hash code for this state object.
	 *
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + modifiers.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + args.hashCode();
		result = 37 * result + classBody.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SEnumConstantDecl, SNodeList, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SEnumConstantDecl, SNodeList, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SEnumConstantDecl state) {
			return state.modifiers;
		}

		@Override
		public SEnumConstantDecl doRebuildParentState(SEnumConstantDecl state, BUTree<SNodeList> child) {
			return state.withModifiers(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
		public STraversal leftSibling(STree state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return ARGS;
		}
	};

	public static STypeSafeTraversal<SEnumConstantDecl, SNodeOption, NodeOption<NodeList<Expr>>> ARGS = new STypeSafeTraversal<SEnumConstantDecl, SNodeOption, NodeOption<NodeList<Expr>>>() {

		@Override
		public BUTree<?> doTraverse(SEnumConstantDecl state) {
			return state.args;
		}

		@Override
		public SEnumConstantDecl doRebuildParentState(SEnumConstantDecl state, BUTree<SNodeOption> child) {
			return state.withArgs(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return CLASS_BODY;
		}
	};

	public static STypeSafeTraversal<SEnumConstantDecl, SNodeOption, NodeOption<NodeList<MemberDecl>>> CLASS_BODY = new STypeSafeTraversal<SEnumConstantDecl, SNodeOption, NodeOption<NodeList<MemberDecl>>>() {

		@Override
		public BUTree<?> doTraverse(SEnumConstantDecl state) {
			return state.classBody;
		}

		@Override
		public SEnumConstantDecl doRebuildParentState(SEnumConstantDecl state, BUTree<SNodeOption> child) {
			return state.withClassBody(child);
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
