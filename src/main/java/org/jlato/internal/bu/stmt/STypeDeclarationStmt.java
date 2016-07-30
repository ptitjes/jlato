package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.decl.STypeDecl;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDTypeDeclarationStmt;
import org.jlato.internal.parser.TokenType;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.TypeDecl;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for a type declaration statement.
 */
public class STypeDeclarationStmt extends SNode<STypeDeclarationStmt> implements SStmt {

	/**
	 * Creates a <code>BUTree</code> with a new type declaration statement.
	 *
	 * @param typeDecl the type declaration child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with a type declaration statement.
	 */
	public static BUTree<STypeDeclarationStmt> make(BUTree<? extends STypeDecl> typeDecl) {
		return new BUTree<STypeDeclarationStmt>(new STypeDeclarationStmt(typeDecl));
	}

	/**
	 * The type declaration of this type declaration statement state.
	 */
	public final BUTree<? extends STypeDecl> typeDecl;

	/**
	 * Constructs a type declaration statement state.
	 *
	 * @param typeDecl the type declaration child <code>BUTree</code>.
	 */
	public STypeDeclarationStmt(BUTree<? extends STypeDecl> typeDecl) {
		this.typeDecl = typeDecl;
	}

	/**
	 * Returns the kind of this type declaration statement.
	 *
	 * @return the kind of this type declaration statement.
	 */
	@Override
	public Kind kind() {
		return Kind.TypeDeclarationStmt;
	}

	/**
	 * Replaces the type declaration of this type declaration statement state.
	 *
	 * @param typeDecl the replacement for the type declaration of this type declaration statement state.
	 * @return the resulting mutated type declaration statement state.
	 */
	public STypeDeclarationStmt withTypeDecl(BUTree<? extends STypeDecl> typeDecl) {
		return new STypeDeclarationStmt(typeDecl);
	}

	/**
	 * Builds a type declaration statement facade for the specified type declaration statement <code>TDLocation</code>.
	 *
	 * @param location the type declaration statement <code>TDLocation</code>.
	 * @return a type declaration statement facade for the specified type declaration statement <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<STypeDeclarationStmt> location) {
		return new TDTypeDeclarationStmt(location);
	}

	/**
	 * Returns the shape for this type declaration statement state.
	 *
	 * @return the shape for this type declaration statement state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the first child traversal for this type declaration statement state.
	 *
	 * @return the first child traversal for this type declaration statement state.
	 */
	@Override
	public STraversal firstChild() {
		return TYPE_DECL;
	}

	/**
	 * Returns the last child traversal for this type declaration statement state.
	 *
	 * @return the last child traversal for this type declaration statement state.
	 */
	@Override
	public STraversal lastChild() {
		return TYPE_DECL;
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
		STypeDeclarationStmt state = (STypeDeclarationStmt) o;
		if (typeDecl == null ? state.typeDecl != null : !typeDecl.equals(state.typeDecl))
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
		if (typeDecl != null) result = 37 * result + typeDecl.hashCode();
		return result;
	}

	public static STypeSafeTraversal<STypeDeclarationStmt, STypeDecl, TypeDecl> TYPE_DECL = new STypeSafeTraversal<STypeDeclarationStmt, STypeDecl, TypeDecl>() {

		@Override
		public BUTree<?> doTraverse(STypeDeclarationStmt state) {
			return state.typeDecl;
		}

		@Override
		public STypeDeclarationStmt doRebuildParentState(STypeDeclarationStmt state, BUTree<STypeDecl> child) {
			return state.withTypeDecl(child);
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

	public static final LexicalShape shape = composite(
			child(TYPE_DECL)
	);
}
