package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDPackageDecl;
import org.jlato.tree.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SPackageDecl extends SNode<SPackageDecl> implements STree {

	public static BUTree<SPackageDecl> make(BUTree<SNodeList> annotations, BUTree<SQualifiedName> name) {
		return new BUTree<SPackageDecl>(new SPackageDecl(annotations, name));
	}

	public final BUTree<SNodeList> annotations;

	public final BUTree<SQualifiedName> name;

	public SPackageDecl(BUTree<SNodeList> annotations, BUTree<SQualifiedName> name) {
		this.annotations = annotations;
		this.name = name;
	}

	@Override
	public Kind kind() {
		return Kind.PackageDecl;
	}

	public BUTree<SNodeList> annotations() {
		return annotations;
	}

	public SPackageDecl withAnnotations(BUTree<SNodeList> annotations) {
		return new SPackageDecl(annotations, name);
	}

	public BUTree<SQualifiedName> name() {
		return name;
	}

	public SPackageDecl withName(BUTree<SQualifiedName> name) {
		return new SPackageDecl(annotations, name);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SPackageDecl> location) {
		return new TDPackageDecl(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return ANNOTATIONS;
	}

	@Override
	public STraversal lastChild() {
		return NAME;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SPackageDecl state = (SPackageDecl) o;
		if (!annotations.equals(state.annotations))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + annotations.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SPackageDecl, SNodeList, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<SPackageDecl, SNodeList, NodeList<AnnotationExpr>>() {

		@Override
		public BUTree<?> doTraverse(SPackageDecl state) {
			return state.annotations;
		}

		@Override
		public SPackageDecl doRebuildParentState(SPackageDecl state, BUTree<SNodeList> child) {
			return state.withAnnotations(child);
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

	public static STypeSafeTraversal<SPackageDecl, SQualifiedName, QualifiedName> NAME = new STypeSafeTraversal<SPackageDecl, SQualifiedName, QualifiedName>() {

		@Override
		public BUTree<?> doTraverse(SPackageDecl state) {
			return state.name;
		}

		@Override
		public SPackageDecl doRebuildParentState(SPackageDecl state, BUTree<SQualifiedName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(ANNOTATIONS, list()),
			keyword(LToken.Package),
			child(NAME),
			token(LToken.SemiColon)
	);
}
