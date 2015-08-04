package org.jlato.internal.bu.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.bu.STraversal;
import org.jlato.internal.bu.STree;
import org.jlato.internal.shapes.*;
import org.jlato.internal.td.SLocation;
import org.jlato.internal.td.stmt.TDEmptyStmt;
import org.jlato.parser.ParserImplConstants;
import org.jlato.printer.FormattingSettings.IndentationContext;
import org.jlato.printer.FormattingSettings.SpacingLocation;
import org.jlato.tree.Kind;
import org.jlato.tree.Tree;

import static org.jlato.internal.shapes.LSCondition.*;
import static org.jlato.internal.shapes.LexicalShape.*;
import static org.jlato.printer.FormattingSettings.IndentationContext.*;
import static org.jlato.printer.FormattingSettings.SpacingLocation.*;
import static org.jlato.printer.IndentationConstraint.*;
import static org.jlato.printer.SpacingConstraint.*;

public class SEmptyStmt extends SNodeState<SEmptyStmt> implements SStmt {

	public static STree<SEmptyStmt> make() {
		return new STree<SEmptyStmt>(new SEmptyStmt());
	}

	public SEmptyStmt() {
	}

	@Override
	public Kind kind() {
		return Kind.EmptyStmt;
	}

	@Override
	protected Tree doInstantiate(SLocation<SEmptyStmt> location) {
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
