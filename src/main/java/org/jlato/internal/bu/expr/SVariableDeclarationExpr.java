package org.jlato.internal.bu.expr;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.decl.SLocalVariableDecl;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.expr.TDVariableDeclarationExpr;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.LocalVariableDecl;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a variable declaration expression.
 */
public class SVariableDeclarationExpr extends SNode<SVariableDeclarationExpr> implements SExpr {

	/**
	 * Creates a <code>BUTree</code> with a new variable declaration expression.
	 *
	 * @param declaration the declaration child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a variable declaration expression.
	 */
	public static BUTree<SVariableDeclarationExpr> make(BUTree<SLocalVariableDecl> declaration) {
		return new BUTree<SVariableDeclarationExpr>(new SVariableDeclarationExpr(declaration));
	}

	/**
	 * The declaration of this variable declaration expression state.
	 */
	public final BUTree<SLocalVariableDecl> declaration;

	/**
	 * Constructs a variable declaration expression state.
	 *
	 * @param declaration the declaration child <code>BUTree</code>.
	 */
	public SVariableDeclarationExpr(BUTree<SLocalVariableDecl> declaration) {
		this.declaration = declaration;
	}

	/**
	 * Returns the kind of this variable declaration expression.
	 *
	 * @return the kind of this variable declaration expression.
	 */
	@Override
	public Kind kind() {
		return Kind.VariableDeclarationExpr;
	}

	/**
	 * Replaces the declaration of this variable declaration expression state.
	 *
	 * @param declaration the replacement for the declaration of this variable declaration expression state.
	 * @return the resulting mutated variable declaration expression state.
	 */
	public SVariableDeclarationExpr withDeclaration(BUTree<SLocalVariableDecl> declaration) {
		return new SVariableDeclarationExpr(declaration);
	}

	/**
	 * Builds a variable declaration expression facade for the specified variable declaration expression <code>TDLocation</code>.
	 *
	 * @param location the variable declaration expression <code>TDLocation</code>.
	 * @return a variable declaration expression facade for the specified variable declaration expression <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SVariableDeclarationExpr> location) {
		return new TDVariableDeclarationExpr(location);
	}

	/**
	 * Returns the shape for this variable declaration expression state.
	 *
	 * @return the shape for this variable declaration expression state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this variable declaration expression state.
	 *
	 * @return the first child traversal for this variable declaration expression state.
	 */
	@Override
	public STraversal firstChild() {
		return DECLARATION;
	}

	/**
	 * Returns the last child traversal for this variable declaration expression state.
	 *
	 * @return the last child traversal for this variable declaration expression state.
	 */
	@Override
	public STraversal lastChild() {
		return DECLARATION;
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
		SVariableDeclarationExpr state = (SVariableDeclarationExpr) o;
		if (declaration == null ? state.declaration != null : !declaration.equals(state.declaration))
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
		if (declaration != null) result = 37 * result + declaration.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SVariableDeclarationExpr, SLocalVariableDecl, LocalVariableDecl> DECLARATION = new STypeSafeTraversal<SVariableDeclarationExpr, SLocalVariableDecl, LocalVariableDecl>() {

		@Override
		public BUTree<?> doTraverse(SVariableDeclarationExpr state) {
			return state.declaration;
		}

		@Override
		public SVariableDeclarationExpr doRebuildParentState(SVariableDeclarationExpr state, BUTree<SLocalVariableDecl> child) {
			return state.withDeclaration(child);
		}

		@Override
		public STraversal leftSibling(STree state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STree state) {
			return null;
		}
	};

	public static final LexicalShape shape = child(DECLARATION);
}
