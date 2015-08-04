package org.jlato.internal.td.decl;

import org.jlato.internal.bu.SNodeListState;
import org.jlato.internal.bu.decl.SInitializerDecl;
import org.jlato.internal.bu.stmt.SBlockStmt;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.TDTree;
import org.jlato.tree.Kind;
import org.jlato.tree.NodeList;
import org.jlato.tree.decl.ExtendedModifier;
import org.jlato.tree.decl.InitializerDecl;
import org.jlato.tree.decl.MemberDecl;
import org.jlato.tree.stmt.BlockStmt;
import org.jlato.util.Mutation;

public class TDInitializerDecl extends TDTree<SInitializerDecl, MemberDecl, InitializerDecl> implements InitializerDecl {

	public Kind kind() {
		return Kind.InitializerDecl;
	}

	public TDInitializerDecl(SLocation<SInitializerDecl> location) {
		super(location);
	}

	public TDInitializerDecl(NodeList<ExtendedModifier> modifiers, BlockStmt body) {
		super(new SLocation<SInitializerDecl>(SInitializerDecl.make(TDTree.<SNodeListState>treeOf(modifiers), TDTree.<SBlockStmt>treeOf(body))));
	}

	public NodeList<ExtendedModifier> modifiers() {
		return location.safeTraversal(SInitializerDecl.MODIFIERS);
	}

	public InitializerDecl withModifiers(NodeList<ExtendedModifier> modifiers) {
		return location.safeTraversalReplace(SInitializerDecl.MODIFIERS, modifiers);
	}

	public InitializerDecl withModifiers(Mutation<NodeList<ExtendedModifier>> mutation) {
		return location.safeTraversalMutate(SInitializerDecl.MODIFIERS, mutation);
	}

	public BlockStmt body() {
		return location.safeTraversal(SInitializerDecl.BODY);
	}

	public InitializerDecl withBody(BlockStmt body) {
		return location.safeTraversalReplace(SInitializerDecl.BODY, body);
	}

	public InitializerDecl withBody(Mutation<BlockStmt> mutation) {
		return location.safeTraversalMutate(SInitializerDecl.BODY, mutation);
	}
}
