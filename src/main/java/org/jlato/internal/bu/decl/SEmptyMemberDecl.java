package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDEmptyMemberDecl;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LexicalShape.*;

public class SEmptyMemberDecl extends SNodeState<SEmptyMemberDecl> implements SMemberDecl {

	public static STree<SEmptyMemberDecl> make() {
		return new STree<SEmptyMemberDecl>(new SEmptyMemberDecl());
	}

	public SEmptyMemberDecl() {
	}

	@Override
	public Kind kind() {
		return Kind.EmptyMemberDecl;
	}

	@Override
	protected Tree doInstantiate(TDLocation<SEmptyMemberDecl> location) {
		return new TDEmptyMemberDecl(location);
	}

	@Override
	public LexicalShape shape() {
		return shape;
	}

	@Override
	public STraversal firstChild() {
		return null;
	}

	@Override
	public STraversal lastChild() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		return result;
	}

	public static final LexicalShape shape = token(LToken.SemiColon);
}
