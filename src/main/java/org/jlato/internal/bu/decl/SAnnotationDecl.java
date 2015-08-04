package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.name.SName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDAnnotationDecl;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.name.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.SpacingConstraint.space;

public class SAnnotationDecl extends SNode<SAnnotationDecl> implements STypeDecl {

	public static BUTree<SAnnotationDecl> make(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> members) {
		return new BUTree<SAnnotationDecl>(new SAnnotationDecl(modifiers, name, members));
	}

	public final BUTree<SNodeList> modifiers;

	public final BUTree<SName> name;

	public final BUTree<SNodeList> members;

	public SAnnotationDecl(BUTree<SNodeList> modifiers, BUTree<SName> name, BUTree<SNodeList> members) {
		this.modifiers = modifiers;
		this.name = name;
		this.members = members;
	}

	@Override
	public Kind kind() {
		return Kind.AnnotationDecl;
	}

	public BUTree<SNodeList> modifiers() {
		return modifiers;
	}

	public SAnnotationDecl withModifiers(BUTree<SNodeList> modifiers) {
		return new SAnnotationDecl(modifiers, name, members);
	}

	public BUTree<SName> name() {
		return name;
	}

	public SAnnotationDecl withName(BUTree<SName> name) {
		return new SAnnotationDecl(modifiers, name, members);
	}

	public BUTree<SNodeList> members() {
		return members;
	}

	public SAnnotationDecl withMembers(BUTree<SNodeList> members) {
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

	public static STypeSafeTraversal<SAnnotationDecl, SNodeList, NodeList<ExtendedModifier>> MODIFIERS = new STypeSafeTraversal<SAnnotationDecl, SNodeList, NodeList<ExtendedModifier>>() {

		@Override
		public BUTree<?> doTraverse(SAnnotationDecl state) {
			return state.modifiers;
		}

		@Override
		public SAnnotationDecl doRebuildParentState(SAnnotationDecl state, BUTree<SNodeList> child) {
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
		public STraversal leftSibling(STree state) {
			return MODIFIERS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return MEMBERS;
		}
	};

	public static STypeSafeTraversal<SAnnotationDecl, SNodeList, NodeList<MemberDecl>> MEMBERS = new STypeSafeTraversal<SAnnotationDecl, SNodeList, NodeList<MemberDecl>>() {

		@Override
		public BUTree<?> doTraverse(SAnnotationDecl state) {
			return state.members;
		}

		@Override
		public SAnnotationDecl doRebuildParentState(SAnnotationDecl state, BUTree<SNodeList> child) {
			return state.withMembers(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return NAME;
		}

		@Override
		public STraversal rightSibling(STree state) {
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
