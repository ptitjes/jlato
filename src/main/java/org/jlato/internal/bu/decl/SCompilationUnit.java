package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.*;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDCompilationUnit;
import org.jlato.tree.*;
import org.jlato.tree.decl.*;

import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.CompilationUnit_AfterPackageDecl;
import static org.jlato.printer.SpacingConstraint.newLine;
import static org.jlato.printer.SpacingConstraint.spacing;

public class SCompilationUnit extends SNodeState<SCompilationUnit> implements STreeState {

	public static BUTree<SCompilationUnit> make(BUTree<SPackageDecl> packageDecl, BUTree<SNodeListState> imports, BUTree<SNodeListState> types) {
		return new BUTree<SCompilationUnit>(new SCompilationUnit(packageDecl, imports, types));
	}

	public final BUTree<SPackageDecl> packageDecl;

	public final BUTree<SNodeListState> imports;

	public final BUTree<SNodeListState> types;

	public SCompilationUnit(BUTree<SPackageDecl> packageDecl, BUTree<SNodeListState> imports, BUTree<SNodeListState> types) {
		this.packageDecl = packageDecl;
		this.imports = imports;
		this.types = types;
	}

	@Override
	public Kind kind() {
		return Kind.CompilationUnit;
	}

	public BUTree<SPackageDecl> packageDecl() {
		return packageDecl;
	}

	public SCompilationUnit withPackageDecl(BUTree<SPackageDecl> packageDecl) {
		return new SCompilationUnit(packageDecl, imports, types);
	}

	public BUTree<SNodeListState> imports() {
		return imports;
	}

	public SCompilationUnit withImports(BUTree<SNodeListState> imports) {
		return new SCompilationUnit(packageDecl, imports, types);
	}

	public BUTree<SNodeListState> types() {
		return types;
	}

	public SCompilationUnit withTypes(BUTree<SNodeListState> types) {
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
		public BUTree<?> doTraverse(SCompilationUnit state) {
			return state.packageDecl;
		}

		@Override
		public SCompilationUnit doRebuildParentState(SCompilationUnit state, BUTree<SPackageDecl> child) {
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
		public BUTree<?> doTraverse(SCompilationUnit state) {
			return state.imports;
		}

		@Override
		public SCompilationUnit doRebuildParentState(SCompilationUnit state, BUTree<SNodeListState> child) {
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
		public BUTree<?> doTraverse(SCompilationUnit state) {
			return state.types;
		}

		@Override
		public SCompilationUnit doRebuildParentState(SCompilationUnit state, BUTree<SNodeListState> child) {
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
