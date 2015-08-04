package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.BUTree;
import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.internal.td.TDLocation;
import org.jlato.internal.td.stmt.TDEmptyStmt;
import org.jlato.tree.*;

import static org.jlato.internal.shapes.LexicalShape.token;

public class SEmptyStmt extends SNodeState<SEmptyStmt> implements SStmt {

	public static BUTree<SEmptyStmt> make() {
		return new BUTree<SEmptyStmt>(new SEmptyStmt());
	}

	public SEmptyStmt() {
	}

	@Override
	public Kind kind() {
		return Kind.EmptyStmt;
	}

	@Override
	protected Tree doInstantiate(TDLocation<SEmptyStmt> location) {
		return new TDEmptyStmt(location);
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
