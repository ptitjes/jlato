package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDImportDecl;
import org.jlato.tree.*;
import org.jlato.tree.name.*;

import java.util.Arrays;

import static org.jlato.internal.shapes.LSCondition.data;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.internal.shapes.SpacingConstraint.newLine;
import static org.jlato.internal.shapes.SpacingConstraint.spacing;
import static org.jlato.printer.FormattingSettings.SpacingLocation.CompilationUnit_AfterImports;

public class SImportDecl extends SNode<SImportDecl> implements STree {

	public static BUTree<SImportDecl> make(BUTree<SQualifiedName> name, boolean isStatic, boolean isOnDemand) {
		return new BUTree<SImportDecl>(new SImportDecl(name, isStatic, isOnDemand));
	}

	public final BUTree<SQualifiedName> name;

	public final boolean isStatic;

	public final boolean isOnDemand;

	public SImportDecl(BUTree<SQualifiedName> name, boolean isStatic, boolean isOnDemand) {
		this.name = name;
		this.isStatic = isStatic;
		this.isOnDemand = isOnDemand;
	}

	@Override
	public Kind kind() {
		return Kind.ImportDecl;
	}

	public SImportDecl withName(BUTree<SQualifiedName> name) {
		return new SImportDecl(name, isStatic, isOnDemand);
	}

	public SImportDecl setStatic(boolean isStatic) {
		return new SImportDecl(name, isStatic, isOnDemand);
	}

	public SImportDecl setOnDemand(boolean isOnDemand) {
		return new SImportDecl(name, isStatic, isOnDemand);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SImportDecl> location) {
		return new TDImportDecl(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public Iterable<SProperty> allProperties() {
		return Arrays.<SProperty>asList(STATIC, ON_DEMAND);
	}

	@Override
	public STraversal firstChild() {
		return NAME;
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
		SImportDecl state = (SImportDecl) o;
		if (name == null ? state.name != null : !name.equals(state.name))
			return false;
		if (isStatic != state.isStatic)
			return false;
		if (isOnDemand != state.isOnDemand)
			return false;
		return true;
	}

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
