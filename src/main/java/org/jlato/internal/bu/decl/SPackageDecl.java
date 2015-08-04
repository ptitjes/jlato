package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.decl.TDPackageDecl;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SPackageDecl extends SNodeState<SPackageDecl> implements STreeState {

	public static STree<SPackageDecl> make(STree<SNodeListState> annotations, STree<SQualifiedName> name) {
		return new STree<SPackageDecl>(new SPackageDecl(annotations, name));
	}

	public final STree<SNodeListState> annotations;

	public final STree<SQualifiedName> name;

	public SPackageDecl(STree<SNodeListState> annotations, STree<SQualifiedName> name) {
		this.annotations = annotations;
		this.name = name;
	}

	@Override
	public Kind kind() {
		return Kind.PackageDecl;
	}

	public STree<SNodeListState> annotations() {
		return annotations;
	}

	public SPackageDecl withAnnotations(STree<SNodeListState> annotations) {
		return new SPackageDecl(annotations, name);
	}

	public STree<SQualifiedName> name() {
		return name;
	}

	public SPackageDecl withName(STree<SQualifiedName> name) {
		return new SPackageDecl(annotations, name);
	}

	@Override
	protected Tree doInstantiate(SLocation<SPackageDecl> location) {
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

	public static STypeSafeTraversal<SPackageDecl, SNodeListState, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<SPackageDecl, SNodeListState, NodeList<AnnotationExpr>>() {

		@Override
		public STree<?> doTraverse(SPackageDecl state) {
			return state.annotations;
		}

		@Override
		public SPackageDecl doRebuildParentState(SPackageDecl state, STree<SNodeListState> child) {
			return state.withAnnotations(child);
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

	public static STypeSafeTraversal<SPackageDecl, SQualifiedName, QualifiedName> NAME = new STypeSafeTraversal<SPackageDecl, SQualifiedName, QualifiedName>() {

		@Override
		public STree<?> doTraverse(SPackageDecl state) {
			return state.name;
		}

		@Override
		public SPackageDecl doRebuildParentState(SPackageDecl state, STree<SQualifiedName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
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
