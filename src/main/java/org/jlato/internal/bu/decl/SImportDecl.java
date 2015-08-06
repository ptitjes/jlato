package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDImportDecl;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QualifiedName;

import java.util.Arrays;

import static org.jlato.internal.shapes.IndentationConstraint.*;
import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;

/**
 * A state object for an import declaration.
 */
public class SImportDecl extends SNode<SImportDecl> implements STree {

	/**
	 * Creates a <code>BUTree</code> with a new import declaration.
	 *
	 * @param name       the name child <code>BUTree</code>.
	 * @param isStatic   the is static child <code>BUTree</code>.
	 * @param isOnDemand the is on-demand child <code>BUTree</code>.
	 * @return the new <code>BUTree</code> with an import declaration.
	 */
	public static BUTree<SImportDecl> make(BUTree<SQualifiedName> name, boolean isStatic, boolean isOnDemand) {
		return new BUTree<SImportDecl>(new SImportDecl(name, isStatic, isOnDemand));
	}

	/**
	 * The name of this import declaration state.
	 */
	public final BUTree<SQualifiedName> name;

	/**
	 * The is static of this import declaration state.
	 */
	public final boolean isStatic;

	/**
	 * The is on-demand of this import declaration state.
	 */
	public final boolean isOnDemand;

	/**
	 * Constructs an import declaration state.
	 *
	 * @param name       the name child <code>BUTree</code>.
	 * @param isStatic   the is static child <code>BUTree</code>.
	 * @param isOnDemand the is on-demand child <code>BUTree</code>.
	 */
	public SImportDecl(BUTree<SQualifiedName> name, boolean isStatic, boolean isOnDemand) {
		this.name = name;
		this.isStatic = isStatic;
		this.isOnDemand = isOnDemand;
	}

	/**
	 * Returns the kind of this import declaration.
	 *
	 * @return the kind of this import declaration.
	 */
	@Override
	public Kind kind() {
		return Kind.ImportDecl;
	}

	/**
	 * Replaces the name of this import declaration state.
	 *
	 * @param name the replacement for the name of this import declaration state.
	 * @return the resulting mutated import declaration state.
	 */
	public SImportDecl withName(BUTree<SQualifiedName> name) {
		return new SImportDecl(name, isStatic, isOnDemand);
	}

	/**
	 * Replaces the is static of this import declaration state.
	 *
	 * @param isStatic the replacement for the is static of this import declaration state.
	 * @return the resulting mutated import declaration state.
	 */
	public SImportDecl setStatic(boolean isStatic) {
		return new SImportDecl(name, isStatic, isOnDemand);
	}

	/**
	 * Replaces the is on-demand of this import declaration state.
	 *
	 * @param isOnDemand the replacement for the is on-demand of this import declaration state.
	 * @return the resulting mutated import declaration state.
	 */
	public SImportDecl setOnDemand(boolean isOnDemand) {
		return new SImportDecl(name, isStatic, isOnDemand);
	}

	/**
	 * Builds an import declaration facade for the specified import declaration <code>TDLocation</code>.
	 *
	 * @param location the import declaration <code>TDLocation</code>.
	 * @return an import declaration facade for the specified import declaration <code>TDLocation</code>.
	 */
	@Override
	protected Tree doInstantiate(TDLocation<SImportDecl> location) {
		return new TDImportDecl(location);
	}

	/**
	 * Returns the shape for this import declaration state.
	 *
	 * @return the shape for this import declaration state.
	 */
	@Override
	public LexicalShape shape() {
		return shape;
	}

	/**
	 * Returns the properties for this import declaration state.
	 *
	 * @return the properties for this import declaration state.
	 */
	@Override
	public Iterable<SProperty> allProperties() {
		return Arrays.<SProperty>asList(STATIC, ON_DEMAND);
	}

	/**
	 * Returns the first child traversal for this import declaration state.
	 *
	 * @return the first child traversal for this import declaration state.
	 */
	@Override
	public STraversal firstChild() {
		return NAME;
	}

	/**
	 * Returns the last child traversal for this import declaration state.
	 *
	 * @return the last child traversal for this import declaration state.
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
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SImportDecl state = (SImportDecl) o;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (isStatic != state.isStatic)
			return false;
		if (isOnDemand != state.isOnDemand)
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
		if (name != null) result = 37 * result + name.hashCode();
		result = 37 * result + (isStatic ? 1 : 0);
		result = 37 * result + (isOnDemand ? 1 : 0);
		return result;
	}

	public static STypeSafeTraversal<SImportDecl, SQualifiedName, QualifiedName> NAME = new STypeSafeTraversal<SImportDecl, SQualifiedName, QualifiedName>() {

		@Override
		public BUTree<?> doTraverse(SImportDecl state) {
			return state.name;
		}

		@Override
		public SImportDecl doRebuildParentState(SImportDecl state, BUTree<SQualifiedName> child) {
			return state.withName(child);
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

	public static STypeSafeProperty<SImportDecl, Boolean> STATIC = new STypeSafeProperty<SImportDecl, Boolean>() {

		@Override
		public Boolean doRetrieve(SImportDecl state) {
			return state.isStatic;
		}

		@Override
		public SImportDecl doRebuildParentState(SImportDecl state, Boolean value) {
			return state.setStatic(value);
		}
	};

	public static STypeSafeProperty<SImportDecl, Boolean> ON_DEMAND = new STypeSafeProperty<SImportDecl, Boolean>() {

		@Override
		public Boolean doRetrieve(SImportDecl state) {
			return state.isOnDemand;
		}

		@Override
		public SImportDecl doRebuildParentState(SImportDecl state, Boolean value) {
			return state.setOnDemand(value);
		}
	};

	public static final LexicalShape shape = composite(
			keyword(LToken.Import),
			when(data(STATIC), keyword(LToken.Static)),
			child(NAME),
			when(data(ON_DEMAND), token(LToken.Dot)),
			when(data(ON_DEMAND), token(LToken.Times)),
			token(LToken.SemiColon)
	);

	public static final LexicalShape listShape = list(
			none(),
			none().withSpacingAfter(newLine()),
			none().withSpacingAfter(spacing(CompilationUnit_AfterImports))
	);
}
