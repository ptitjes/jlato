package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.SProperty;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeProperty;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDImportDecl;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;
import org.jlato.tree.name.QualifiedName;

import java.util.Arrays;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SImportDecl extends SNodeState<SImportDecl> implements STreeState {

	public static STree<SImportDecl> make(STree<SQualifiedName> name, boolean isStatic, boolean isOnDemand) {
		return new STree<SImportDecl>(new SImportDecl(name, isStatic, isOnDemand));
	}

	public final STree<SQualifiedName> name;

	public final boolean isStatic;

	public final boolean isOnDemand;

	public SImportDecl(STree<SQualifiedName> name, boolean isStatic, boolean isOnDemand) {
		this.name = name;
		this.isStatic = isStatic;
		this.isOnDemand = isOnDemand;
	}

	@Override
	public Kind kind() {
		return Kind.ImportDecl;
	}

	public STree<SQualifiedName> name() {
		return name;
	}

	public SImportDecl withName(STree<SQualifiedName> name) {
		return new SImportDecl(name, isStatic, isOnDemand);
	}

	public boolean isStatic() {
		return isStatic;
	}

	public SImportDecl setStatic(boolean isStatic) {
		return new SImportDecl(name, isStatic, isOnDemand);
	}

	public boolean isOnDemand() {
		return isOnDemand;
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
		public STree<?> doTraverse(SImportDecl state) {
			return state.name;
		}

		@Override
		public SImportDecl doRebuildParentState(SImportDecl state, STree<SQualifiedName> child) {
			return state.withName(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
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
			when(data(ON_DEMAND), composite(token(LToken.Dot), token(LToken.Times))),
			token(LToken.SemiColon)
	);

	public static final LexicalShape listShape = list(
			none(),
			none().withSpacingAfter(newLine()),
			none().withSpacingAfter(spacing(CompilationUnit_AfterImports))
	);
}
