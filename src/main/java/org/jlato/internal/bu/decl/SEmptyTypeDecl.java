package org.jlato.internal.bu.decl;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.decl.TDEmptyTypeDecl;
import org.jlato.tree.*;

import static org.jlato.internal.shapes.LexicalShape.token;

public class SEmptyTypeDecl extends SNodeState<SEmptyTypeDecl> implements STypeDecl {

	public static BUTree<SEmptyTypeDecl> make() {
		return new BUTree<SEmptyTypeDecl>(new SEmptyTypeDecl());
	}

	public SEmptyTypeDecl() {
	}

	@Override
	public Kind kind() {
		return Kind.EmptyTypeDecl;
	}

	@Override
	protected Tree doInstantiate(TDLocation<SEmptyTypeDecl> location) {
		return new TDEmptyTypeDecl(location);
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
