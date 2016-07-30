package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDPackageDecl;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.QualifiedName;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a package declaration.
 */
public class SPackageDecl extends SNode<SPackageDecl> implements STree {

	/**
	 * Creates a <code>BUTree</code> with a new package declaration.
	 *
	 * @param annotations the annotations child <code>BUTree</code>.
	 * @param name        the name child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a package declaration.
	 */
	public static BUTree<SPackageDecl> make(BUTree<SNodeList> annotations, BUTree<SQualifiedName> name) {
		return new BUTree<SPackageDecl>(new SPackageDecl(annotations, name));
	}

	/**
	 * The annotations of this package declaration state.
	 */
	public final BUTree<SNodeList> annotations;

	/**
	 * The name of this package declaration state.
	 */
	public final BUTree<SQualifiedName> name;

	/**
	 * Constructs a package declaration state.
	 *
	 * @param annotations the annotations child <code>BUTree</code>.
	 * @param name        the name child <code>BUTree</code>.
	 */
	public SPackageDecl(BUTree<SNodeList> annotations, BUTree<SQualifiedName> name) {
		this.annotations = annotations;
		this.name = name;
	}

	/**
	 * Returns the kind of this package declaration.
	 *
	 * @return the kind of this package declaration.
	 */
	@Override
	public Kind kind() {
		return Kind.PackageDecl;
	}

	/**
	 * Replaces the annotations of this package declaration state.
	 *
	 * @param annotations the replacement for the annotations of this package declaration state.
	 * @return the resulting mutated package declaration state.
	 */
	public SPackageDecl withAnnotations(BUTree<SNodeList> annotations) {
		return new SPackageDecl(annotations, name);
	}

	/**
	 * Replaces the name of this package declaration state.
	 *
	 * @param name the replacement for the name of this package declaration state.
	 * @return the resulting mutated package declaration state.
	 */
	public SPackageDecl withName(BUTree<SQualifiedName> name) {
		return new SPackageDecl(annotations, name);
	}

	/**
	 * Builds a package declaration facade for the specified package declaration <code>TDLocation</code>.
	 *
	 * @param location the package declaration <code>TDLocation</code>.
	 * @return a package declaration facade for the specified package declaration <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SPackageDecl> location) {
		return new TDPackageDecl(location);
	}

	/**
	 * Returns the shape for this package declaration state.
	 *
	 * @return the shape for this package declaration state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this package declaration state.
	 *
	 * @return the first child traversal for this package declaration state.
	 */
	@Override
	public STraversal firstChild() {
		return ANNOTATIONS;
	}

	/**
	 * Returns the last child traversal for this package declaration state.
	 *
	 * @return the last child traversal for this package declaration state.
	 */
	@Override
	public STraversal lastChild() {
		return NAME;
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
		SPackageDecl state = (SPackageDecl) o;
		if (!annotations.equals(state.annotations))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
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
