package org.jlato.internal.td.decl;

import org.jlato.internal.bu.coll.SNodeList;
import org.jlato.internal.bu.decl.SPackageDecl;
import org.jlato.internal.bu.name.SQualifiedName;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.Node;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.PackageDecl;
import org.jlato.tree.expr.AnnotationExpr;
import org.jlato.tree.name.QualifiedName;
import org.jlato.util.Mutation;

public class TDPackageDecl extends TDTree<SPackageDecl, Node, PackageDecl> implements PackageDecl {

	public Kind kind() {
		return Kind.PackageDecl;
	}

	public TDPackageDecl(TDLocation<SPackageDecl> location) {
		super(location);
	}

	public TDPackageDecl(NodeList<AnnotationExpr> annotations, QualifiedName name) {
		super(new TDLocation<SPackageDecl>(SPackageDecl.make(TDTree.<SNodeList>treeOf(annotations), TDTree.<SQualifiedName>treeOf(name))));
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
