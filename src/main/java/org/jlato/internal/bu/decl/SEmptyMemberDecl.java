package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDEmptyMemberDecl;
import org.jlato.tree.*;

import static org.jlato.internal.shapes.LexicalShape.token;

public class SEmptyMemberDecl extends SNode<SEmptyMemberDecl> implements SMemberDecl {

	public static BUTree<SEmptyMemberDecl> make() {
		return new BUTree<SEmptyMemberDecl>(new SEmptyMemberDecl());
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
