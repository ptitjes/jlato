package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.bu.STreeState;
import org.jlato.internal.bu.STypeSafeTraversal;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDCompilationUnit;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.Tree;
import org.jlato.tree.decl.ImportDecl;
import org.jlato.tree.decl.PackageDecl;
import org.jlato.tree.decl.TypeDecl;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SCompilationUnit extends SNodeState<SCompilationUnit> implements STreeState {

	public static STree<SCompilationUnit> make(STree<SPackageDecl> packageDecl, STree<SNodeListState> imports, STree<SNodeListState> types) {
		return new STree<SCompilationUnit>(new SCompilationUnit(packageDecl, imports, types));
	}

	public final STree<SPackageDecl> packageDecl;

	public final STree<SNodeListState> imports;

	public final STree<SNodeListState> types;

	public SCompilationUnit(STree<SPackageDecl> packageDecl, STree<SNodeListState> imports, STree<SNodeListState> types) {
		this.packageDecl = packageDecl;
		this.imports = imports;
		this.types = types;
	}

	@Override
	public Kind kind() {
		return Kind.CompilationUnit;
	}

	public STree<SPackageDecl> packageDecl() {
		return packageDecl;
	}

	public SCompilationUnit withPackageDecl(STree<SPackageDecl> packageDecl) {
		return new SCompilationUnit(packageDecl, imports, types);
	}

	public STree<SNodeListState> imports() {
		return imports;
	}

	public SCompilationUnit withImports(STree<SNodeListState> imports) {
		return new SCompilationUnit(packageDecl, imports, types);
	}

	public STree<SNodeListState> types() {
		return types;
	}

	public SCompilationUnit withTypes(STree<SNodeListState> types) {
		return new SCompilationUnit(packageDecl, imports, types);
	}

	@Override
	protected Tree doInstantiate(TDLocation<SCompilationUnit> location) {
		return new TDCompilationUnit(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return PACKAGE_DECL;
	}

	@Override
	public STraversal lastChild() {
		return TYPES;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SCompilationUnit state = (SCompilationUnit) o;
		if (packageDecl == null ? state.packageDecl != null : !packageDecl.equals(state.packageDecl))
			return false;
		if (!imports.equals(state.imports))
			return false;
		if (!types.equals(state.types))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (packageDecl != null) result = 37 * result + packageDecl.hashCode();
		result = 37 * result + imports.hashCode();
		result = 37 * result + types.hashCode();
		return result;
	}

	public static STypeSafeTraversal<SCompilationUnit, SPackageDecl, PackageDecl> PACKAGE_DECL = new STypeSafeTraversal<SCompilationUnit, SPackageDecl, PackageDecl>() {

		@Override
		public STree<?> doTraverse(SCompilationUnit state) {
			return state.packageDecl;
		}

		@Override
		public SCompilationUnit doRebuildParentState(SCompilationUnit state, STree<SPackageDecl> child) {
			return state.withPackageDecl(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return null;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return IMPORTS;
		}
	};

	public static STypeSafeTraversal<SCompilationUnit, SNodeListState, NodeList<ImportDecl>> IMPORTS = new STypeSafeTraversal<SCompilationUnit, SNodeListState, NodeList<ImportDecl>>() {

		@Override
		public STree<?> doTraverse(SCompilationUnit state) {
			return state.imports;
		}

		@Override
		public SCompilationUnit doRebuildParentState(SCompilationUnit state, STree<SNodeListState> child) {
			return state.withImports(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return PACKAGE_DECL;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return TYPES;
		}
	};

	public static STypeSafeTraversal<SCompilationUnit, SNodeListState, NodeList<TypeDecl>> TYPES = new STypeSafeTraversal<SCompilationUnit, SNodeListState, NodeList<TypeDecl>>() {

		@Override
		public STree<?> doTraverse(SCompilationUnit state) {
			return state.types;
		}

		@Override
		public SCompilationUnit doRebuildParentState(SCompilationUnit state, STree<SNodeListState> child) {
			return state.withTypes(child);
		}

		@Override
		public STraversal leftSibling(STreeState state) {
			return IMPORTS;
		}

		@Override
		public STraversal rightSibling(STreeState state) {
			return null;
		}
	};

	public static final LexicalShape shape = composite(
			child(PACKAGE_DECL).withSpacingAfter(spacing(CompilationUnit_AfterPackageDecl)),
			child(IMPORTS, SImportDecl.listShape),
			child(TYPES, STypeDecl.listShape),
			none().withSpacingAfter(newLine())
	);
}
