package org.jlato.internal.bu.type;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.coll.SNodeOption;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.type.TDWildcardType;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.type.ReferenceType;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a wildcard type.
 */
public class SWildcardType extends SNode<SWildcardType> implements SType {

	/**
	 * Creates a <code>BUTree</code> with a new wildcard type.
	 *
	 * @param annotations the annotations child <code>BUTree</code>.
	 * @param ext         the upper bound child <code>BUTree</code>.
	 * @param sup         the lower bound child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a wildcard type.
	 */
	public static BUTree<SWildcardType> make(BUTree<SNodeList> annotations, BUTree<SNodeOption> ext, BUTree<SNodeOption> sup) {
		return new BUTree<SWildcardType>(new SWildcardType(annotations, ext, sup));
	}

	/**
	 * The annotations of this wildcard type state.
	 */
	public final BUTree<SNodeList> annotations;

	/**
	 * The upper bound of this wildcard type state.
	 */
	public final BUTree<SNodeOption> ext;

	/**
	 * The lower bound of this wildcard type state.
	 */
	public final BUTree<SNodeOption> sup;

	/**
	 * Constructs a wildcard type state.
	 *
	 * @param annotations the annotations child <code>BUTree</code>.
	 * @param ext         the upper bound child <code>BUTree</code>.
	 * @param sup         the lower bound child <code>BUTree</code>.
	 */
	public SWildcardType(BUTree<SNodeList> annotations, BUTree<SNodeOption> ext, BUTree<SNodeOption> sup) {
		this.annotations = annotations;
		this.ext = ext;
		this.sup = sup;
	}

	/**
	 * Returns the kind of this wildcard type.
	 *
	 * @return the kind of this wildcard type.
	 */
	@Override
	public Kind kind() {
		return Kind.WildcardType;
	}

	/**
	 * Replaces the annotations of this wildcard type state.
	 *
	 * @param annotations the replacement for the annotations of this wildcard type state.
	 * @return the resulting mutated wildcard type state.
	 */
	public SWildcardType withAnnotations(BUTree<SNodeList> annotations) {
		return new SWildcardType(annotations, ext, sup);
	}

	/**
	 * Replaces the upper bound of this wildcard type state.
	 *
	 * @param ext the replacement for the upper bound of this wildcard type state.
	 * @return the resulting mutated wildcard type state.
	 */
	public SWildcardType withExt(BUTree<SNodeOption> ext) {
		return new SWildcardType(annotations, ext, sup);
	}

	/**
	 * Replaces the lower bound of this wildcard type state.
	 *
	 * @param sup the replacement for the lower bound of this wildcard type state.
	 * @return the resulting mutated wildcard type state.
	 */
	public SWildcardType withSup(BUTree<SNodeOption> sup) {
		return new SWildcardType(annotations, ext, sup);
	}

	/**
	 * Builds a wildcard type facade for the specified wildcard type <code>TDLocation</code>.
	 *
	 * @param location the wildcard type <code>TDLocation</code>.
	 * @return a wildcard type facade for the specified wildcard type <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SWildcardType> location) {
		return new TDWildcardType(location);
	}

	/**
	 * Returns the shape for this wildcard type state.
	 *
	 * @return the shape for this wildcard type state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this wildcard type state.
	 *
	 * @return the first child traversal for this wildcard type state.
	 */
	@Override
	public STraversal firstChild() {
		return ANNOTATIONS;
	}

	/**
	 * Returns the last child traversal for this wildcard type state.
	 *
	 * @return the last child traversal for this wildcard type state.
	 */
	@Override
	public STraversal lastChild() {
		return SUP;
	}

	/**
	 * Compares this state object to the specified object.
	 *
	 * @param o the object to compare this state with.
	 * @return <code>true</code> if the specified object is equal to this state, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SWildcardType state = (SWildcardType) o;
		if (!annotations.equals(state.annotations))
			return false;
		if (!ext.equals(state.ext))
			return false;
		if (!sup.equals(state.sup))
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
		result = 37 * result + ext.hashCode();
		result = 37 * result + sup.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SWildcardType, SNodeList, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<SWildcardType, SNodeList, NodeList<AnnotationExpr>>() {

		@Override
		public BUTree<?> doTraverse(SWildcardType state) {
			return state.annotations;
		}

		@Override
		public SWildcardType doRebuildParentState(SWildcardType state, BUTree<SNodeList> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return EXT;
		}
	};

	public static STypeSafeTraversal<SWildcardType, SNodeOption, NodeOption<ReferenceType>> EXT = new STypeSafeTraversal<SWildcardType, SNodeOption, NodeOption<ReferenceType>>() {

		@Override
		public BUTree<?> doTraverse(SWildcardType state) {
			return state.ext;
		}

		@Override
		public SWildcardType doRebuildParentState(SWildcardType state, BUTree<SNodeOption> child) {
			return state.withExt(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return SUP;
		}
	};

	public static STypeSafeTraversal<SWildcardType, SNodeOption, NodeOption<ReferenceType>> SUP = new STypeSafeTraversal<SWildcardType, SNodeOption, NodeOption<ReferenceType>>() {

		@Override
		public BUTree<?> doTraverse(SWildcardType state) {
			return state.sup;
		}

		@Override
		public SWildcardType doRebuildParentState(SWildcardType state, BUTree<SNodeOption> child) {
			return state.withSup(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return EXT;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(ANNOTATIONS, org.jlato.internal.bu.expr.SAnnotationExpr.singleLineAnnotationsShape),
			token(LToken.QuestionMark),
			child(EXT, when(some(), composite(keyword(LToken.Extends), element()))),
			child(SUP, when(some(), composite(keyword(LToken.Super), element())))
	);
}
