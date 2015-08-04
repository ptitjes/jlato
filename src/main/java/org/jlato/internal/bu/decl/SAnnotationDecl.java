package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDAnnotationDecl;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.name.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SAnnotationDecl extends SNodeState<SAnnotationDecl> implements STypeDecl {

	public static BUTree<SAnnotationDecl> make(BUTree<SNodeListState> modifiers, BUTree<SName> name, BUTree<SNodeListState> members) {
		return new BUTree<SAnnotationDecl>(new SAnnotationDecl(modifiers, name, members));
	}

	public final BUTree<SNodeListState> modifiers;

	public final BUTree<SName> name;

	public final BUTree<SNodeListState> members;

	public SAnnotationDecl(BUTree<SNodeListState> modifiers, BUTree<SName> name, BUTree<SNodeListState> members) {
		this.modifiers = modifiers;
		this.name = name;
		this.members = members;
	}

	@Override
	public Kind kind() {
		return Kind.AnnotationDecl;
	}

	public BUTree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SAnnotationDecl withModifiers(BUTree<SNodeListState> modifiers) {
		return new SAnnotationDecl(modifiers, name, members);
	}

	public BUTree<SName> name() {
		return name;
	}

	public SAnnotationDecl withName(BUTree<SName> name) {
		return new SAnnotationDecl(modifiers, name, members);
	}

	public BUTree<SNodeListState> members() {
		return members;
	}

	public SAnnotationDecl withMembers(BUTree<SNodeListState> members) {
		return new SAnnotationDecl(modifiers, name, members);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SAnnotationDecl> location) {
		return new TDAnnotationDecl(location);
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
		return MEMBERS;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SAnnotationDecl state = (SAnnotationDecl) o;
		if (!modifiers.equals(state.modifiers))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!members.equals(state.members))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + modifiers.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + members.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SAnnotationDecl, SNodeListState, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SAnnotationDecl, SNodeListState, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SAnnotationDecl state) {
			return state.modifiers;
		}

		@Override
		public SAnnotationDecl doRebuildParentState(SAnnotationDecl state, BUTree<SNodeListState> child) {
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

	public static STypeSafeTraversal<SAnnotationDecl, SName, Name> NAME = new STypeSafeTraversal<SAnnotationDecl, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SAnnotationDecl state) {
			return state.name;
		}

		@Override
		public SAnnotationDecl doRebuildParentState(SAnnotationDecl state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return MEMBERS;
		}
	};

	public static STypeSafeTraversal<SAnnotationDecl, SNodeListState, NodeList<MemberDecl>> MEMBERS = new STypeSafeTraversal<SAnnotationDecl, SNodeListState, NodeList<MemberDecl>>() {

		@Override
		public BUTree<?> doTraverse(SAnnotationDecl state) {
			return state.members;
		}

		@Override
		public SAnnotationDecl doRebuildParentState(SAnnotationDecl state, BUTree<SNodeListState> child) {
			return state.withMembers(child);
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
			child(MODIFIERS, SExtendedModifier.multiLineShape),
			token(LToken.At), token(LToken.Interface).withSpacingAfter(space()),
			child(NAME),
			child(MEMBERS, SMemberDecl.bodyShape)
	);
}
