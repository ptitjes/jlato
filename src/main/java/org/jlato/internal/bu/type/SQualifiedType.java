package org.jlato.internal.bu.type;

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
import org.jlato.internal.td.type.TDQualifiedType;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.NodeOption;
import org.jlato.tree.Tree;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.Name;
import org.jlato.tree.type.QualifiedType;
import org.jlato.tree.type.Type;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a qualified type.
 */
public class SQualifiedType extends SNode<SQualifiedType> implements SReferenceType {

	/**
	 * Creates a <code>BUTree</code> with a new qualified type.
	 *
	 * @param annotations the annotations child <code>BUTree</code>.
	 * @param scope       the scope child <code>BUTree</code>.
	 * @param name        the name child <code>BUTree</code>.
	 * @param typeArgs    the type args child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a qualified type.
	 */
	public static BUTree<SQualifiedType> make(BUTree<SNodeList> annotations, BUTree<SNodeOption> scope, BUTree<SName> name, BUTree<SNodeOption> typeArgs) {
		return new BUTree<SQualifiedType>(new SQualifiedType(annotations, scope, name, typeArgs));
	}

	/**
	 * The annotations of this qualified type state.
	 */
	public final BUTree<SNodeList> annotations;

	/**
	 * The scope of this qualified type state.
	 */
	public final BUTree<SNodeOption> scope;

	/**
	 * The name of this qualified type state.
	 */
	public final BUTree<SName> name;

	/**
	 * The type args of this qualified type state.
	 */
	public final BUTree<SNodeOption> typeArgs;

	/**
	 * Constructs a qualified type state.
	 *
	 * @param annotations the annotations child <code>BUTree</code>.
	 * @param scope       the scope child <code>BUTree</code>.
	 * @param name        the name child <code>BUTree</code>.
	 * @param typeArgs    the type args child <code>BUTree</code>.
	 */
	public SQualifiedType(BUTree<SNodeList> annotations, BUTree<SNodeOption> scope, BUTree<SName> name, BUTree<SNodeOption> typeArgs) {
		this.annotations = annotations;
		this.scope = scope;
		this.name = name;
		this.typeArgs = typeArgs;
	}

	/**
	 * Returns the kind of this qualified type.
	 *
	 * @return the kind of this qualified type.
	 */
	@Override
	public Kind kind() {
		return Kind.QualifiedType;
	}

	/**
	 * Replaces the annotations of this qualified type state.
	 *
	 * @param annotations the replacement for the annotations of this qualified type state.
	 * @return the resulting mutated qualified type state.
	 */
	public SQualifiedType withAnnotations(BUTree<SNodeList> annotations) {
		return new SQualifiedType(annotations, scope, name, typeArgs);
	}

	/**
	 * Replaces the scope of this qualified type state.
	 *
	 * @param scope the replacement for the scope of this qualified type state.
	 * @return the resulting mutated qualified type state.
	 */
	public SQualifiedType withScope(BUTree<SNodeOption> scope) {
		return new SQualifiedType(annotations, scope, name, typeArgs);
	}

	/**
	 * Replaces the name of this qualified type state.
	 *
	 * @param name the replacement for the name of this qualified type state.
	 * @return the resulting mutated qualified type state.
	 */
	public SQualifiedType withName(BUTree<SName> name) {
		return new SQualifiedType(annotations, scope, name, typeArgs);
	}

	/**
	 * Replaces the type args of this qualified type state.
	 *
	 * @param typeArgs the replacement for the type args of this qualified type state.
	 * @return the resulting mutated qualified type state.
	 */
	public SQualifiedType withTypeArgs(BUTree<SNodeOption> typeArgs) {
		return new SQualifiedType(annotations, scope, name, typeArgs);
	}

	/**
	 * Builds a qualified type facade for the specified qualified type <code>TDLocation</code>.
	 *
	 * @param location the qualified type <code>TDLocation</code>.
	 * @return a qualified type facade for the specified qualified type <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SQualifiedType> location) {
		return new TDQualifiedType(location);
	}

	/**
	 * Returns the shape for this qualified type state.
	 *
	 * @return the shape for this qualified type state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this qualified type state.
	 *
	 * @return the first child traversal for this qualified type state.
	 */
	@Override
	public STraversal firstChild() {
		return ANNOTATIONS;
	}

	/**
	 * Returns the last child traversal for this qualified type state.
	 *
	 * @return the last child traversal for this qualified type state.
	 */
	@Override
	public STraversal lastChild() {
		return TYPE_ARGS;
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
		SQualifiedType state = (SQualifiedType) o;
		if (!annotations.equals(state.annotations))
			return false;
		if (!scope.equals(state.scope))
			return false;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (!typeArgs.equals(state.typeArgs))
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
		result = 37 * result + scope.hashCode();
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + typeArgs.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SQualifiedType, SNodeList, NodeList<AnnotationExpr>> ANNOTATIONS = new STypeSafeTraversal<SQualifiedType, SNodeList, NodeList<AnnotationExpr>>() {

		@Override
		public BUTree<?> doTraverse(SQualifiedType state) {
			return state.annotations;
		}

		@Override
		public SQualifiedType doRebuildParentState(SQualifiedType state, BUTree<SNodeList> child) {
			return state.withAnnotations(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return SCOPE;
		}
	};

	public static STypeSafeTraversal<SQualifiedType, SNodeOption, NodeOption<QualifiedType>> SCOPE = new STypeSafeTraversal<SQualifiedType, SNodeOption, NodeOption<QualifiedType>>() {

		@Override
		public BUTree<?> doTraverse(SQualifiedType state) {
			return state.scope;
		}

		@Override
		public SQualifiedType doRebuildParentState(SQualifiedType state, BUTree<SNodeOption> child) {
			return state.withScope(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return ANNOTATIONS;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return NAME;
		}
	};

	public static STypeSafeTraversal<SQualifiedType, SName, Name> NAME = new STypeSafeTraversal<SQualifiedType, SName, Name>() {

		@Override
		public BUTree<?> doTraverse(SQualifiedType state) {
			return state.name;
		}

		@Override
		public SQualifiedType doRebuildParentState(SQualifiedType state, BUTree<SName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return SCOPE;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return TYPE_ARGS;
		}
	};

	public static STypeSafeTraversal<SQualifiedType, SNodeOption, NodeOption<NodeList<Type>>> TYPE_ARGS = new STypeSafeTraversal<SQualifiedType, SNodeOption, NodeOption<NodeList<Type>>>() {

		@Override
		public BUTree<?> doTraverse(SQualifiedType state) {
			return state.typeArgs;
		}

		@Override
		public SQualifiedType doRebuildParentState(SQualifiedType state, BUTree<SNodeOption> child) {
			return state.withTypeArgs(child);
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

	public static final LexicalShape scopeShape = composite(element(), token(LToken.Dot));

	public static final LexicalShape shape = composite(
			child(SCOPE, when(some(), scopeShape)),
			child(ANNOTATIONS, org.jlato.internal.bu.expr.SAnnotationExpr.singleLineAnnotationsShape),
			child(NAME),
			child(TYPE_ARGS, when(some(), element(org.jlato.internal.bu.type.SType.typeArgumentsOrDiamondShape)))
	);

	public static final LexicalShape extendsClauseShape = list(
			keyword(LToken.Extends),
			token(LToken.Comma).withSpacingAfter(space()),
			null
	);

	public static final LexicalShape implementsClauseShape = list(
			keyword(LToken.Implements),
			token(LToken.Comma).withSpacingAfter(space()),
			null
	);

	public static final LexicalShape throwsClauseShape = list(
			keyword(LToken.Throws),
			token(LToken.Comma).withSpacingAfter(space()),
			null
	);
}
