package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.decl.SPackageDecl;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TreeBase;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.PackageDecl;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

public class TDPackageDecl extends TreeBase<SPackageDecl, Node, PackageDecl> implements PackageDecl {

	public Kind kind() {
		return Kind.PackageDecl;
	}

	public TDPackageDecl(SLocation<SPackageDecl> location) {
		super(location);
	}

	public TDPackageDecl(NodeList<AnnotationExpr> annotations, QualifiedName name) {
		super(new SLocation<SPackageDecl>(SPackageDecl.make(TreeBase.<SNodeListState>treeOf(annotations), TreeBase.<SQualifiedName>treeOf(name))));
	}

	public NodeList<AnnotationExpr> annotations() {
		return location.safeTraversal(SPackageDecl.ANNOTATIONS);
	}

	public PackageDecl withAnnotations(NodeList<AnnotationExpr> annotations) {
		return location.safeTraversalReplace(SPackageDecl.ANNOTATIONS, annotations);
	}

	public PackageDecl withAnnotations(Mutation<NodeList<AnnotationExpr>> mutation) {
		return location.safeTraversalMutate(SPackageDecl.ANNOTATIONS, mutation);
	}

	public QualifiedName name() {
		return location.safeTraversal(SPackageDecl.NAME);
	}

	public PackageDecl withName(QualifiedName name) {
		return location.safeTraversalReplace(SPackageDecl.NAME, name);
	}

	public PackageDecl withName(Mutation<QualifiedName> mutation) {
		return location.safeTraversalMutate(SPackageDecl.NAME, mutation);
	}
}
