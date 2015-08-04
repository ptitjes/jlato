package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDAnnotationDecl;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.name.Name;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SAnnotationDecl extends SNodeState<SAnnotationDecl> implements STypeDecl {

	public static STree<SAnnotationDecl> make(STree<SNodeListState> modifiers, STree<SName> name, STree<SNodeListState> members) {
		return new STree<SAnnotationDecl>(new SAnnotationDecl(modifiers, name, members));
	}

	public final STree<SNodeListState> modifiers;

	public final STree<SName> name;

	public final STree<SNodeListState> members;

	public SAnnotationDecl(STree<SNodeListState> modifiers, STree<SName> name, STree<SNodeListState> members) {
		this.modifiers = modifiers;
		this.name = name;
		this.members = members;
	}

	@Override
	public Kind kind() {
		return Kind.AnnotationDecl;
	}

	public STree<SNodeListState> modifiers() {
		return modifiers;
	}

	public SAnnotationDecl withModifiers(STree<SNodeListState> modifiers) {
		return new SAnnotationDecl(modifiers, name, members);
	}

	public STree<SName> name() {
		return name;
	}

	public SAnnotationDecl withName(STree<SName> name) {
		return new SAnnotationDecl(modifiers, name, members);
	}

	public STree<SNodeListState> members() {
		return members;
	}

	public SAnnotationDecl withMembers(STree<SNodeListState> members) {
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
		public STree<?> doTraverse(SAnnotationDecl state) {
			return state.modifiers;
		}

		@Override
		public SAnnotationDecl doRebuildParentState(SAnnotationDecl state, STree<SNodeListState> child) {
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
		public STree<?> doTraverse(SAnnotationDecl state) {
			return state.name;
		}

		@Override
		public SAnnotationDecl doRebuildParentState(SAnnotationDecl state, STree<SName> child) {
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
		public STree<?> doTraverse(SAnnotationDecl state) {
			return state.members;
		}

		@Override
		public SAnnotationDecl doRebuildParentState(SAnnotationDecl state, STree<SNodeListState> child) {
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
