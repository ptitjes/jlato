package org.jlato.tree.stmt;

import org.jlato.internal.bu.LToken;
import org.jlato.internal.bu.SNode;
import org.jlato.internal.bu.SNodeState;
import org.jlato.internal.shapes.LexicalShape;
import org.jlato.printer.FormattingSettings;
import org.jlato.tree.*;

import static org.jlato.internal.shapes.LexicalShape.Factory.*;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.newLine;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.space;
import static org.jlato.internal.shapes.LexicalSpacing.Factory.spacing;
import static org.jlato.printer.FormattingSettings.IndentationContext.BLOCK;
import static org.jlato.printer.FormattingSettings.SpacingLocation.SwitchStmt_AfterSwitchKeyword;

public class SwitchStmt extends Stmt {

	public final static Tree.Kind kind = new Tree.Kind() {
		public SwitchStmt instantiate(SLocation location) {
			return new SwitchStmt(location);
		}

		public LexicalShape shape() {
			return shape;
		}
	};

	private SwitchStmt(SLocation location) {
		super(location);
	}

	public SwitchStmt(Expr selector, NodeList<SwitchEntryStmt> entries) {
		super(new SLocation(new SNode(kind, new SNodeState(treesOf(selector, entries)))));
	}

	public Expr selector() {
		return location.nodeChild(SELECTOR);
	}

	public SwitchStmt withSelector(Expr selector) {
		return location.nodeWithChild(SELECTOR, selector);
	}

	public NodeList<SwitchEntryStmt> entries() {
		return location.nodeChild(ENTRIES);
	}

	public SwitchStmt withEntries(NodeList<SwitchEntryStmt> entries) {
		return location.nodeWithChild(ENTRIES, entries);
	}

	private static final int SELECTOR = 0;
	private static final int ENTRIES = 1;

	public final static LexicalShape shape = composite(
			token(LToken.Switch),
			token(LToken.ParenthesisLeft).withSpacingBefore(spacing(SwitchStmt_AfterSwitchKeyword)),
			child(SELECTOR),
			token(LToken.ParenthesisRight).withSpacingAfter(space()),
			nonEmptyChildren(ENTRIES,
					composite(
							token(LToken.BraceLeft).withSpacingAfter(newLine()), indent(BLOCK),
							children(ENTRIES, none().withSpacing(newLine())),
							unIndent(BLOCK), token(LToken.BraceRight).withSpacingBefore(newLine())
					),
					composite(
							token(LToken.BraceLeft).withSpacingAfter(newLine()), indent(BLOCK),
							unIndent(BLOCK), token(LToken.BraceRight)
					)
			)
	);
}
